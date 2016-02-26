package com.github.karelcemus.filter

import java.util.regex.Pattern

import sbt._

/**
  * Filters files based on regular expression pattern. It applies the pattern on their absolute path.
  *
  * @author Karel Cemus
  */
class PatternFileFilter( pattern: Pattern ) extends FileFilter {

  override def accept( file: File ): Boolean =
    pattern.matcher( file.absolutePath ).matches( )
}
