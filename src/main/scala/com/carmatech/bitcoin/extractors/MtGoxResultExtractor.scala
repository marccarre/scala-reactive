package com.carmatech.bitcoin.extractors

import scala.concurrent._

import spray.json._
import spray.json.DefaultJsonProtocol._ // !!! IMPORTANT, else `convertTo` and `toJson` won't work correctly
import spray.httpx.SprayJsonSupport._ // !!! IMPORTANT to be able to use unmarshall

import spray.http._
import spray.client.pipelining._

case class Price(value: String, value_int: String, display: String, display_short: String, currency: String)
case class Data(last_local: Price, last: Price, last_orig: Price, last_all: Price, buy: Price, sell: Price, now: String)
case class MtGoxResult(result: String, data: Data)

object MtGoxResultJsonProtocol extends DefaultJsonProtocol {
  implicit val priceFormat = jsonFormat5(Price)
  implicit val dataFormat = jsonFormat7(Data)
  implicit val mtGoxResultFormat = jsonFormat2(MtGoxResult)
}

trait MtGoxResultExtractor extends HttpExtractor {
  import system.dispatcher // execution context for futures
  import MtGoxResultJsonProtocol._

  implicit val pipeline: HttpRequest => Future[MtGoxResult] = sendReceive ~> unmarshal[MtGoxResult]
}

/*
 * 
 * 
 * object MtGoxResultJsonProtocol2 extends DefaultJsonProtocol {
  implicit object lastValueJsonFormat extends RootJsonFormat[Option[Double]] {
    def read(value: JsValue): Option[Double] = value match {
      case JsObject(results) => (results.get("result"), results.get("data")) match {
        case (Some(JsString("success")), Some(JsObject(data))) => data.get("last") match {
          case Some(JsObject(last)) => last.get("value") match {
            case Some(JsNumber(value)) => Some(value.toDouble)
            case unexpected => log(unexpected)
          }
          case unexpected => log(unexpected)
        }
        case unexpected => log(unexpected)
      }
      case unexpected => log(unexpected)
    }

    private def log[I, O](unexpected: I): Option[O] = {
      println("Unexpected result format: " + unexpected.toString)
      None
    }
  }
}
 * 
 * 
{
    "result": "success",
    "data": {
        "last_local": {
            "value": "857.98000",
            "value_int": "85798000",
            "display": "$857.98",
            "display_short": "$857.98",
            "currency": "USD"
        },
        "last": {
            "value": "857.98000",
            "value_int": "85798000",
            "display": "$857.98",
            "display_short": "$857.98",
            "currency": "USD"
        },
        "last_orig": {
            "value": "857.98000",
            "value_int": "85798000",
            "display": "$857.98",
            "display_short": "$857.98",
            "currency": "USD"
        },
        "last_all": {
            "value": "857.98000",
            "value_int": "85798000",
            "display": "$857.98",
            "display_short": "$857.98",
            "currency": "USD"
        },
        "buy": {
            "value": "857.98000",
            "value_int": "85798000",
            "display": "$857.98",
            "display_short": "$857.98",
            "currency": "USD"
        },
        "sell": {
            "value": "858.80000",
            "value_int": "85880000",
            "display": "$858.80",
            "display_short": "$858.80",
            "currency": "USD"
        },
        "now": "1387190984368818"
    }
}

 */ 