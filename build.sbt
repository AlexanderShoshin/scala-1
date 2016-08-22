name := "scala-1"

version := "1.0"

scalaVersion := "2.10.5"

libraryDependencies ++= Seq(
  "org.apache.spark" % "spark-core_2.10" % "1.6.0" excludeAll(
    ExclusionRule(organization = "org.scala-lang"),
    ExclusionRule("org.apache.commons", "commons-lang3"),
    ExclusionRule("org.slf4j", "slf4j-api")
  )
)