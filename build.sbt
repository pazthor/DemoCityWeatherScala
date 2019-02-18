name := "DemoCityWeatherScala"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies := {
  libraryDependencies.value ++ Seq(
    "org.scala-lang.modules" %% "scala-xml" % "1.1.1",
    "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.1",
    "org.scala-lang.modules" %% "scala-swing" % "2.0.3",
    "org.scalatest" %% "scalatest" % "3.0.5" % "test"
  )
}