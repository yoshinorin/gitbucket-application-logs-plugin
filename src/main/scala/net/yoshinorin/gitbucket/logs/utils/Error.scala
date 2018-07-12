package net.yoshinorin.gitbucket.logs.utils

sealed abstract class Error(val message: String)

object Error {

  object NOT_SUPPORTED extends Error("NOT SUPPORTED")
  object FAILURE extends Error("ERROR")
  object DISABLE extends Error("Log setting is disable.")
  object NOT_FOUND_LOGBACK_SETTINGS extends Error("Can not find logback configuration file")
  object NOT_CONFIGURE extends Error("Dosen't configure Logback")
  object FILE_NOT_FOUND extends Error("FILE NOT FOUND")

}
