package bitcoin.dao.mtgox

import scala.concurrent._
import spray.json._
import spray.json.DefaultJsonProtocol._ // !!! IMPORTANT, else `convertTo` and `toJson` won't work correctly
import spray.httpx.SprayJsonSupport._ // !!! IMPORTANT to be able to use unmarshall
import spray.http._
import spray.client.pipelining._
import bitcoin.extractors.HttpExtractor

// DTOs:
case class Price(value: String, value_int: String, display: String, display_short: String, currency: String)
case class Data(last_local: Price, last: Price, last_orig: Price, last_all: Price, buy: Price, sell: Price, now: String)
case class MtGoxResult(result: String, data: Data)

// Deserializer:
object MtGoxResultJsonProtocol extends DefaultJsonProtocol {
  implicit val priceFormat = jsonFormat5(Price)
  implicit val dataFormat = jsonFormat7(Data)
  implicit val mtGoxResultFormat = jsonFormat2(MtGoxResult)
}

// Pipeline-setter / Extractor-mixin
trait MtGoxResultExtractor extends HttpExtractor {
  import system.dispatcher // execution context for futures
  import MtGoxResultJsonProtocol._

  implicit val pipeline: HttpRequest => Future[MtGoxResult] = sendReceive ~> unmarshal[MtGoxResult]
}