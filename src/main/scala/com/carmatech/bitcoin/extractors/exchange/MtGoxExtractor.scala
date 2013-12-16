package com.carmatech.bitcoin.extractors.exchange

import com.carmatech.bitcoin.extractors._

import scala.util.{ Success, Failure }
import scala.concurrent.{ Future, promise }

class MtGoxExtractor extends ExchangeExtractor with HttpExtractor with MtGoxResultExtractor {
  import system.dispatcher // execution context for futures

  val baseUrl = "http://data.mtgox.com/api/2"

  def lastPrice: Future[Option[MtGoxResult]] = {
    val lastPrice = promise[Option[MtGoxResult]]
    get(baseUrl + "/BTCUSD/money/ticker_fast", pipeline) onComplete {
      case Success(result) => lastPrice.trySuccess(Some(result))
      case Failure(error) => lastPrice.trySuccess(logAndShutdown(error.getMessage))
    }
    lastPrice.future
  }

  private def logAndShutdown[T](error: String = "Unknown error"): Option[T] = {
    println(error)
    shutdown()
    None
  }
}