package uk.gov.hmrc

case class UtrExtractor(text: String) {

  val formIdPattern = "(T\n)([0-9]*)".r

  val utrPattern = "([0-9]{5}(?!\\n)\\s{1}[0-9]{5})".r

  val issueDataPattern = "[0-9]{1,2}\\s([A-Z]+\\s[0-9]{4})".r

  def extractFormId: Option[String] = {
    formIdPattern.findFirstMatchIn(text) match {
      case Some(m) => Some(m.group(2))
      case _ => None
    }
  }

  def extractUtr: Option[String] = {
    utrPattern.findFirstMatchIn(text) match {
      case Some(m) => Some(m.toString)
      case _ => None
    }
  }

  def extractIssueData: Option[String] = {
    issueDataPattern.findFirstMatchIn(text) match {
      case Some(m) => Some(m.toString)
      case _ => None
    }
  }

}
