import sbt.Keys._
import sbt._

object Build extends Build {

  val project = "global_flight"
  val akkaVersion = "2.3.9"
  val sprayVersion = "1.3.3"

  lazy val service = Project(
    id = project,
    base = file(".")
  ).settings(
    name := project,
    version := (version in ThisBuild).value,
    scalacOptions in Compile ~= (_ filterNot (_ == "-Xfatal-warnings")),
    libraryDependencies ++=
      Seq(
        "io.spray"            %%  "spray-json" % "1.3.2",
        "org.json4s"          %%  "json4s-jackson" % "3.3.0",
        "eu.fakod"            %%  "neo4j-scala" % "0.3.3",
        "org.scalikejdbc"     %%  "scalikejdbc" % "2.5.0",
        "ch.qos.logback"      %   "logback-classic" % "1.1.7",
        "io.spray"            %%  "spray-can"     % sprayVersion,
        "io.spray"            %%  "spray-routing" % sprayVersion,
        "io.spray"            %%  "spray-testkit" % sprayVersion  % "test",
        "com.typesafe.akka"   %%  "akka-actor"    % akkaVersion,
        "com.typesafe.akka"   %%  "akka-testkit"  % akkaVersion % "test",
        "org.specs2"          %%  "specs2-core"   % "2.3.11" % "test"
        ),
    initialCommands in console :=
      """
        | import com.flight.services.AirportFinder
        | import com.flight.services.CityFinder
        | import com.flight.services.FlightFinder
        | import com.flight.services.MapPointRetriever
        | import com.flight.services.MockFlightGenerator
        | import com.flight.services.NearestCityService
        | import com.flight.services.NodeConnector
        | import com.flight.services.CSVStore
        | val chicago = CityFinder.getByName("Chicago")
        | val la = CityFinder.getByName("Los Angeles")
        | val now = java.time.LocalDate.now()
      """.stripMargin
  )
}