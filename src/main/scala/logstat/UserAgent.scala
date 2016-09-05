package logstat

import net.sf.uadetector.service.UADetectorServiceFactory

case class UserAgent(browserFamily: String, browserName: String)

object UserAgent {
  val userAgentParser = UADetectorServiceFactory.getResourceModuleParser

  def apply(userAgent: String): UserAgent = {
    val readableUserAgent = userAgentParser.parse(userAgent)
    UserAgent(readableUserAgent.getFamily.getName, readableUserAgent.getName)
  }
}