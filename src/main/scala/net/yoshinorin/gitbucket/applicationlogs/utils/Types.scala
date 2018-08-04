package net.yoshinorin.gitbucket.applicationlogs.utils

sealed abstract class SortType(val value: String)

object SortType {

  object ASC extends SortType("asc")
  object DESC extends SortType("desc")

}

sealed abstract class Error(val message: String)

object Error {

  object FAILURE extends Error("ERROR")
  object NOT_FOUND_LOGBACK_SETTINGS extends Error("Can not find logback configuration file")
  object FILE_NOT_FOUND extends Error("FILE NOT FOUND")

}
