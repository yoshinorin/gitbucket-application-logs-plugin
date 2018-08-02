package net.yoshinorin.gitbucket.applicationlogs.controllers

import java.io.{File, FileInputStream}
import java.nio.charset.Charset
import scala.util.{Failure, Success, Try}
import org.apache.commons.compress.archivers.zip.{ZipArchiveEntry, ZipArchiveOutputStream}
import org.apache.commons.compress.utils.IOUtils
import org.apache.commons.io.output.ByteArrayOutputStream
import org.slf4j.LoggerFactory
import gitbucket.core.controller.ControllerBase
import gitbucket.core.util.AdminAuthenticator
import net.yoshinorin.gitbucket.applicationlogs.models.LogBack
import net.yoshinorin.gitbucket.applicationlogs.services.ApplicationLogService
import net.yoshinorin.gitbucket.applicationlogs.utils.{Error, SortTypes}
import net.yoshinorin.gitbucket.applicationlogs.utils.Converter.stringConverter

class ApplicationLogsController extends ControllerBase with AdminAuthenticator with ApplicationLogService {

  private val logger = LoggerFactory.getLogger(getClass)

  get("/admin/application-logs")(adminOnly {
    redirect(s"/admin/application-logs/configuration")
  })

  get("/admin/application-logs/configuration")(adminOnly {
    net.yoshinorin.gitbucket.applicationlogs.html.configuration(
      LogBack.isEnable,
      LogBack.getConfigurationFilePath,
      LogBack.readConfigurationFile
    )
  })

  get("/admin/application-logs/list")(adminOnly {

    LogBack.isEnable match {
      case true => {
        net.yoshinorin.gitbucket.applicationlogs.html.list(
          LogBack.isEnable,
          LogBack.logFiles
        )
      }
      case false => NotFound()
    }

  })

  get("/admin/application-logs/:id/view")(adminOnly {

    val logId = params("id").toInt
    val sortBy = params("sortBy").toString.toSortType

    LogBack.findById(logId) match {
      case Some(logFile) => {
        var n = defaultDisplayLines
        val lineNum = request.getParameter("lines")
        if (Try(lineNum.toInt).toOption.isDefined) {
          n = lineNum.toInt
        }
        val logs = readLog(logFile, n) match {
          case Success(s) =>
            s match {
              case Some(s) => {
                sortBy match {
                  case SortTypes.ASC => Right(s)
                  case SortTypes.DESC => Right(s.reverse)
                }
              }
              case None => Left(Error.FILE_NOT_FOUND)
            }
          case Failure(f) => {
            logger.error(f.toString)
            Left(Error.FAILURE)
          }
        }

        net.yoshinorin.gitbucket.applicationlogs.html.logviewer(
          LogBack.isEnable,
          logFile,
          defaultDisplayLines,
          displayLimitLines,
          logs,
          n,
          sortBy
        )
      }
      case None => NotFound()
    }
  })

  get("/admin/application-logs/:id/download")(adminOnly {

    val logId = params("id").toInt

    LogBack.findById(logId) match {
      case Some(v) => {
        val file = new File(v.path)
        response.setHeader(
          "Content-Disposition",
          s"attachment; filename=${file.getName}.zip"
        )
        contentType = "application/zip"
        response.setBufferSize(1024 * 1024)

        val byteArrayStream = new ByteArrayOutputStream()
        val zipArchiveOutStream = new ZipArchiveOutputStream(byteArrayStream)

        try {
          zipArchiveOutStream.setEncoding(Charset.defaultCharset().toString) //TODO: Set charset from logback configuration file.
          val zipArchive = new ZipArchiveEntry(file.getName)
          zipArchiveOutStream.putArchiveEntry(zipArchive)
          IOUtils.copy(new FileInputStream(file), zipArchiveOutStream)
        } finally {
          zipArchiveOutStream.closeArchiveEntry()
          zipArchiveOutStream.close()
          byteArrayStream.close()
        }
      }
      case _ => NotFound()
    }
  })
}
