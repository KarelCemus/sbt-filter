package com.github.karelcemus.filter

import scala.language.implicitConversions
import scala.util.matching.Regex
import sbt._

import com.typesafe.sbt.web.pipeline.Pipeline

object Import {

  val filter = TaskKey[Pipeline.Stage]("filter", "Filters files on the asset pipeline.")
  implicit def regex2filter( filter: Regex ): FileFilter = new PatternFileFilter( filter.pattern )
}
