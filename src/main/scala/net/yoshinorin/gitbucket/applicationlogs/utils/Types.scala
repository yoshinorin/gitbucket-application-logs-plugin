package net.yoshinorin.gitbucket.applicationlogs.utils

sealed abstract class SortTypes(val value: String)

object SortTypes {

  object ASC extends SortTypes("asc")
  object DESC extends SortTypes("desc")

}
