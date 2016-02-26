package com.github.karelcemus.filter

import scala.language.implicitConversions
import sbt._
import sbt.Keys._

import com.typesafe.sbt.web.SbtWeb
import com.typesafe.sbt.web.pipeline.Pipeline

/**
  * @author Karel Cemus
  */
object AssetsFilter extends AutoPlugin {

  override def requires = SbtWeb

  override def trigger = AllRequirements

  val autoImport = Import

  import autoImport._

  override def projectSettings = Seq(
    filter := runFilter.value,
    includeFilter in filter := AllPassFilter,
    excludeFilter in filter := NothingFilter
  )

  private def runFilter: Def.Initialize[ Task[ Pipeline.Stage ] ] = Def.task {
    mappings =>
      val include = ( includeFilter in filter ).value
      val exclude = ( excludeFilter in filter ).value

      mappings.withFilter {
        case (file, path) => include.accept( file )
      }.withFilter {
        case (file, path) => !exclude.accept( file )
      }.map( identity )
  }
}
