package loader

import domain.{JsonLoaderException, LoadedJson}
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

import java.nio.file.Paths

final class JsonLoaderSpec extends AnyFunSpec with ScalaCheckPropertyChecks with Matchers {
  describe("JsonLoader success") {
    it("should not throw an exception when loadOrThrow is called with a valid path") {
      val underTest = JsonLoaderImpl.make(Paths.get("./src/test/resources/json/success/testJsonLoader.json"))
      underTest.loadOrThrow should ===(LoadedJson("Hello World!"))
    }
  }

  describe("JsonLoader failures") {
    it("should throw an exception when loadOrThrow is called with an invalid path") {
      val filePathString = "file path does not exist"
      val underTest = JsonLoaderImpl.make(Paths.get(filePathString))
      val exception = the[JsonLoaderException] thrownBy {
        underTest.loadOrThrow
      }
      exception.getMessage should ===(s"Invalid path, file is not found: [$filePathString]")
    }
    it("should throw an exception when loadOrThrow is called with an valid path but am unmapped json") {
      val underTest = JsonLoaderImpl.make(Paths.get("./src/test/resources/json/failure/testJsonLoaderFailure1.json"))
      val exception = the[JsonLoaderException] thrownBy {
        underTest.loadOrThrow
      }
      exception.getMessage should ===(s"Failed to decode JSON")
    }
  }

}
