package bitcoin.extractors.exchange

import bitcoin.extractors.HttpExtractor
import bitcoin.dao.mtgox.MtGoxResultExtractor
import akka.event.Logging
import scala.util.{ Try, Success, Failure }
import scala.concurrent.{ Future, promise }


class MtGoxExtractor extends ExchangeExtractor with HttpExtractor with MtGoxResultExtractor {
  import system.dispatcher // execution context for futures
 
  private val log = Logging(system, getClass)
  private val baseUrl = "http://data.mtgox.com/api/2"

  def lastPrice: Future[Try[Double]] = {
    val lastPrice = promise[Try[Double]]
    val url = baseUrl + "/BTCUSD/money/ticker_fast"
    
    get(url, pipeline) onComplete {
      case Success(result) => Success(result.data.last.value.toDouble)
      case Failure(error) => logAndShutdown(error, url)
    }
    
    lastPrice.future
  }

  private def logAndShutdown[T](error: Throwable, url: String): Try[T] = {
    log.error("Failed to retrieve MtGox results from [$url]: ${error.getMessage}")
    shutdown()
    Failure(error)
  }
}