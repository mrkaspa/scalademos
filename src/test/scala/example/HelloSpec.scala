package scalademos

import org.scalatest._

class RunnerSpec extends FlatSpec with Matchers with Runner {
  "The Hello object" should "say hello" in {
    run() shouldEqual "hello"
  }
}
