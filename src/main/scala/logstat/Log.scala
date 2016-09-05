package logstat

case class Log(id: String, bytes: Int, userAgent: String)

object Log {
  private val pattern = (
    "^(\\S+)\\s" +
    "(\\S+)\\s" +
    "(\\S+)\\s" +
    "\\[([\\w:/]+\\s[+\\-]\\d{4})\\]\\s" +
    "\"(.+?)\"\\s" +
    "(\\d{3})\\s" +
    "(\\d+)\\s" +
    "\"([^\"]+)\"\\s" +
    "\"([^\"]+)\"$")
    .r(
    "id",
    "-",
    "-",
    "dateTime",
    "request",
    "response",
    "bytes",
    "host",
    "browser")

  def apply(line: String): Log = {
    val firstMatch = pattern.findFirstMatchIn(line)
    if (firstMatch.isDefined) {
      val pattern = firstMatch.get
      new Log(pattern.group("id"),
              pattern.group("bytes").toInt,
              pattern.group("browser"))
    } else {
      Log("", -1, "")
    }
  }
}