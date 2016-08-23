class ServerLog(val id: String, val bytes: Int, val browser: String)

object ServerLog {
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

  def apply(line: String) = {
    val firstMatch = pattern.findFirstMatchIn(line)
    if (firstMatch.isDefined) {
      val pattern = firstMatch.get
      new ServerLog(pattern.group("id"),
                    pattern.group("bytes").toInt,
                    pattern.group("browser"))
    } else {
      new ServerLog("", -1, "")
    }
  }
}