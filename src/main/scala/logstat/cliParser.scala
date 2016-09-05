package logstat

class cliParser extends scopt.OptionParser[DataLocation]("scala-1") {
  opt[String]('i', "input")
      .required()
      .valueName("<file>")
      .action( (value, conf) => conf.copy(logsPath = value) )
      .text("path to hdfs dataset file")

  opt[String]('o', "output")
      .required()
      .valueName("<file>")
      .action( (value, conf) => conf.copy(outputPath = value) )
      .text("path to hdfs folder for output produce")
}