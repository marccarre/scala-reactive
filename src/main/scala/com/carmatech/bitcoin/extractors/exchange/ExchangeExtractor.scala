package com.carmatech.bitcoin.extractors.exchange

import com.carmatech.bitcoin.extractors.MtGoxResult
import scala.concurrent.Future

trait ExchangeExtractor {
  def lastPrice: Future[Option[MtGoxResult]]
  def shutdown(): Unit
}