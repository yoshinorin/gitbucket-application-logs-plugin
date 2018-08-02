package net.yoshinorin.gitbucket.applicationlogs.utils

object Converter {

  implicit class stringConverter(s: String) {

    def toSortType: SortTypes = {
      s.toUpperCase match {
        case "ASC" => SortTypes.ASC
        case "DESC" => SortTypes.DESC
        case _ => SortTypes.ASC
      }
    }

  }

}
