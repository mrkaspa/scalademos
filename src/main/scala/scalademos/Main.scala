package scalademos

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import scala.concurrent.Future
import scala.util.{Failure, Success}
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
