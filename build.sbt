ThisBuild / scalaVersion := "3.6.4"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.dpratt747"
ThisBuild / organizationName := "dpratt747"

lazy val root = (project in file("."))
  .settings(
    name := "football_radar_david",
    libraryDependencies ++= dependencies ++ testDependencies
  )

lazy val integration = (project in file("integration"))
  .dependsOn(root)
  .settings(
    publish / skip := true,
    libraryDependencies ++= testDependencies
  )

lazy val circeVersion = "0.14.12"
lazy val scalaCSVVersion = "2.0.0"
lazy val scalatestVersion = "3.2.19"
lazy val scalacheckVersion = "3.2.19.0"

lazy val dependencies = scalaCSVDependencies ++ circeDependencies

lazy val scalaCSVDependencies = Seq(
  "com.github.tototoshi" %% "scala-csv" % scalaCSVVersion
)

lazy val circeDependencies = Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-parser",
  "io.circe" %% "circe-generic"
).map(_ % circeVersion)

lazy val testDependencies = Seq(
  "org.scalatest" %% "scalatest" % scalatestVersion % Test,
  "org.scalatestplus" %% "scalacheck-1-18" % scalacheckVersion % Test
)