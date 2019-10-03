package uk.gov.hmrc

case class UtrExtractor(text: String) {

  val formIdPattern = "(T\n)([0-9]*)".r

  def extractFormId: Option[String] = {
    formIdPattern.findFirstMatchIn(text) match {
      case Some(m) => Some(m.group(2))
      case _ => None
    }
  }

}
