name := "DemoCityWeatherScala"

version := "0.1"

scalaVersion := "2.12.8"
val circeVersion = "0.10.0"



//libraryDependencies += Seq(
//  "org.scala-lang.modules" %% "scala-xml" % "1.1.1",
//  "org.scalatest" %% "scalatest" % "3.0.5" % "test",
//  "io.circe"  %% "circe-core"     % circeVersion,
//  "io.circe"  %% "circe-generic"  % circeVersion,
//  "io.circe"  %% "circe-parser"   % circeVersion,
//)

libraryDependencies := {
  libraryDependencies.value ++ Seq(
    "org.scala-lang.modules" %% "scala-xml" % "1.1.1",
    "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.1",
    "org.scala-lang.modules" %% "scala-swing" % "2.0.3",
    "org.scalatest" %% "scalatest" % "3.0.5" % "test"
  )
}