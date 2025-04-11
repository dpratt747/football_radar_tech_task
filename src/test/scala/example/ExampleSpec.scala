package example

import example.ExampleSpec.*
import org.scalacheck.Gen
import org.scalatest.DoNotDiscover
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

@DoNotDiscover
final class ExampleSpec extends AnyFunSpec with ScalaCheckPropertyChecks with Matchers {
  describe("Example") {
    it("should generate an apple string") {
      forAll(fruitStringsGen) { string =>
        string.toLowerCase should ===("apple")
      }
    }
  }
}

object ExampleSpec {
  private val fruitStringsGen: Gen[String] = Gen.oneOf("Apple", "APPLE", "ApPle")
}