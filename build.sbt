name := "scala-1"

version := "1.0"

scalaVersion := "2.10.5"

libraryDependencies ++= Seq(
  "org.apache.spark" % "spark-core_2.10" % "1.6.0" % Provided,
  "com.holdenkarau" %% "spark-testing-base" % "1.6.0_0.3.3" % Test
)