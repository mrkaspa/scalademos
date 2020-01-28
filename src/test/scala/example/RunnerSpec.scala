package scalademos

import org.scalatest._
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import scala.concurrent.ExecutionContext
import scala.concurrent.Future

class RunnerSpec extends FlatSpec with Matchers with Runner {
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.dispatcher

  "The Hello object" should "say hello" in {
    run() shouldEqual Future { "Demo" }
  }
}
