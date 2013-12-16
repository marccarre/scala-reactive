package bitcoin.extractors.exchange

import bitcoin.dao.mtgox.MtGoxResult
import scala.concurrent.Future
import scala.util.Try

trait ExchangeExtractor {
  def lastPrice: Future[Try[Double]]
  def shutdown(): Unit
}