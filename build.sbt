name := "scala-1"

version := "1.0"

scalaVersion := "2.10.5"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "1.6.0" % Provided,
  "com.holdenkarau" %% "spark-testing-base" % "1.6.0_0.3.3" % Test,
  "net.sf.uadetector" % "uadetector-resources" % "2014.10",
  "net.sf.uadetector" % "uadetector-core" % "0.9.22",
  "com.github.scopt" %% "scopt" % "3.5.0"
)