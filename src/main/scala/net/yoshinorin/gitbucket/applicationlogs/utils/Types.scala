package net.yoshinorin.gitbucket.applicationlogs.utils

sealed abstract class SortType(val value: String)

object SortType {

  object ASC extends SortType("asc")
  object DESC extends SortType("desc")

}
