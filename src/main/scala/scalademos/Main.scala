package scalademos

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import akka.util.ByteString
import scala.concurrent.Future
import scala.util.{Failure, Success}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext

object Main extends Runner with App {
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.dispatcher
  run() onComplete {
    case Success(body) => println(body)
    case Failure(err)  => println(err)
  }
}

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
