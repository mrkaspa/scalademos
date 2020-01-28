package scalademos

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import scala.concurrent.duration._
import akka.util.ByteString

trait Runner {
  def run()(
      implicit ac: ActorSystem,
      am: ActorMaterializer,
      ec: ExecutionContext
  ): Future[String] = {
    for {
      res <- Http().singleRequest(
        HttpRequest(uri = "https://liftit.co", method = HttpMethods.GET)
      )
      ent <- res.entity.toStrict(3.seconds)
      bytes <- ent.dataBytes
        .runFold(ByteString.empty) { case (acc, b) => acc ++ b }
    } yield bytes.utf8String
  }
}
