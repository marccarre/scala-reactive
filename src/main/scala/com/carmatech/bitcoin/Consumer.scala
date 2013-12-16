package com.carmatech.bitcoin

import rx.lang.scala._

import com.carmatech.bitcoin.extractors.exchange.MtGoxExtractor
import com.carmatech.bitcoin.extractors._
import scala.concurrent.Future

object Consumer {
  val mtGox = new MtGoxExtractor
  def lastPrice: Observable[Future[Option[MtGoxResult]]] = Observable(mtGox.lastPrice)
}