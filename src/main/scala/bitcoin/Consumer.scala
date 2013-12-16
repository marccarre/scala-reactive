package bitcoin

import rx.lang.scala._

import bitcoin.extractors.exchange.MtGoxExtractor
import bitcoin.extractors._
import scala.concurrent.Future
import scala.util.Try


object Consumer {
  private val mtGox = new MtGoxExtractor
  def lastPrice: Observable[Future[Try[Double]]] = Observable(mtGox.lastPrice)
}