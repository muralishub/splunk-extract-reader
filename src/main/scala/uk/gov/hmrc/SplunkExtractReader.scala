package uk.gov.hmrc

import java.io.{File, PrintWriter}
import java.util.Base64

object SplunkExtractReader extends App {

  if(args.length == 2) {
    val inputFilePath = args(0)
    val outputFilePath = args(1)
    val extractReader = SplunkExtractReader(inputFilePath, outputFilePath)
    if(extractReader.run()) {
      println("Success")
    } else {
      println("Failed")
      usage()
    }
  } else {
    usage()
  }

  def usage() {
    System.out.println("Usage:")
    System.out.println("\tjava -jar splunk-extract-reader-assembly-0.1.jar <input-file-path> <output-file-path>")
  }
}
case class SplunkExtractReader(inputFileName: String, outputFilePath: String) {

  def run(): Boolean = {
    val bufferedSource = io.Source.fromFile(inputFileName)
    val printWriter = new PrintWriter(new File(outputFilePath))
    for (line <- bufferedSource.getLines) {
      val cols = line.split(",").map(_.trim)
      val extract = FileExtract(cols(0), cols(1), cols(2))
      printWriter.println(UtrExtractor(extract.sourceData).extractFormId.getOrElse(""))
    }
    bufferedSource.close
    printWriter.close()
    true
  }

}

case class FileExtract(reason: String, private val encodedSourceData: String, email: String) {
  def sourceData:String = new String(Base64.getDecoder.decode(encodedSourceData))
}
