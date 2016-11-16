import sbt.Keys._
import sbt._

object Build extends Build {

  val project = "global_flight"

  lazy val service = Project(
    id = project,
    base = file(".")
  ).settings(
    name := project,
    version := (version in ThisBuild).value,
    scalacOptions in Compile ~= (_ filterNot (_ == "-Xfatal-warnings")),
    libraryDependencies ++=
      Seq(
        "io.spray" %%  "spray-json" % "1.3.2",
        "org.json4s" %% "json4s-jackson" % "3.3.0",
        "eu.fakod" %% "neo4j-scala" % "0.3.3",
        "org.scalikejdbc" %% "scalikejdbc" % "2.5.0",
        "ch.qos.logback" % "logback-classic" % "1.1.7"
        )
  )
}

