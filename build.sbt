sbtPlugin := true

organization := "com.github.karelcemus"

name := "sbt-filter"

scalaVersion := "2.11.7"

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

javacOptions ++= Seq( "-source", "1.7", "-target", "1.7", "-Xlint:unchecked", "-encoding", "UTF-8" )

scalacOptions ++= Seq( "-deprecation", "-feature", "-unchecked" )

homepage := Some( url( "https://github.com/karelcemus/sbt-filter" ) )

licenses := Seq( "Apache 2" -> url( "http://www.apache.org/licenses/LICENSE-2.0" ) )

publishMavenStyle := true

pomExtra :=
  <url>https://github.com/karelcemus/sbt-filter</url>
  <licenses>
    <license>
      <name>Apache License, Version 2.0</name>
      <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git@github.com:karelcemus/sbt-filter.git</url>
    <connection>scm:git@github.com:karelcemus/sbt-filter.git</connection>
  </scm>
  <developers>
    <developer>
      <id>karelcemus</id>
      <name>Karel Cemus</name>
      <url>https://github.com/karelcemus</url>
    </developer>
  </developers>

// Release plugin settings
releaseCrossBuild := true
releaseTagName := ( version in ThisBuild ).value
releasePublishArtifactsAction := PgpKeys.publishSigned.value

// Publish settings
publishTo := {
  if ( isSnapshot.value ) Some( Opts.resolver.sonatypeSnapshots )
  else Some( Opts.resolver.sonatypeStaging )
}
