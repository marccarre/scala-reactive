package bitcoin.extractors

import akka.actor.ActorSystem
import akka.io.IO
import akka.util.Timeout.durationToTimeout
import akka.pattern.ask
import scala.concurrent._
import scala.concurrent.duration._
import spray.util._
import spray.http._
import spray.can.Http
import spray.client.pipelining._


trait HttpExtractor {
  implicit protected val system = ActorSystem()
  import system.dispatcher // execution context for futures

  private val defaultPipeline: HttpRequest => Future[HttpResponse] = sendReceive

  def get[T](url: String, pipeline: HttpRequest => Future[T] = defaultPipeline): Future[T] = pipeline {
    Get(url)
  }

  def shutdown(): Unit = {
    IO(Http).ask(Http.CloseAll)(1 second).await
    system.shutdown()
  }
}