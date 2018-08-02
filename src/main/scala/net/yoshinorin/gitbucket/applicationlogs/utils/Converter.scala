package net.yoshinorin.gitbucket.applicationlogs.utils

object Converter {

  implicit class stringConverter(s: String) {

    def toSortType: SortType = {
      s.toUpperCase match {
        case "ASC" => SortType.ASC
        case "DESC" => SortType.DESC
        case _ => SortType.ASC
      }
    }

  }

}
