package logstat

object App extends App {
  val cliParser = new cliParser
  cliParser.parse(args, DataLocation()) match {
    case Some(dataLocation) => BytesCount.execute(dataLocation)
    case None => println("Restart the application with correct arguments")
  }
}