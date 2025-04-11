package loader

import domain.*
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

import java.nio.file.Paths

final class CSVLoaderSpec extends AnyFunSpec with ScalaCheckPropertyChecks with Matchers {
  describe("CSVLoader success") {
    it("should not throw an exception when loadOrThrow is called with a valid path") {
      val underTest = CSVLoaderImpl.make(Paths.get("./src/test/resources/csv/success/testCSVLoader.csv"))
      underTest.loadOrThrow should ===(LoadedCSV(List(CSVRow("Hello World!", 20))))
    }
  }

  describe("CSVLoader failures") {
    it("should throw an exception when loadOrThrow is called with an invalid path") {
      val filePathString = "file path does not exist"
      val underTest = CSVLoaderImpl.make(Paths.get(filePathString))
      val exception = the[CSVLoaderException] thrownBy {
        underTest.loadOrThrow
      }
      exception.getMessage should ===(s"Invalid path, file is not found: [$filePathString]")
    }
    it("should throw an exception when loadOrThrow is called with a valid path but unmapped/unexpected headers") {
      val underTest = CSVLoaderImpl.make(Paths.get("./src/test/resources/csv/failure/testCSVLoaderFailure1.csv"))
      val exception = the[CSVLoaderException] thrownBy {
        underTest.loadOrThrow
      }
      exception.getMessage should ===("key not found: Message")
    }
    it("should throw an exception when loadOrThrow is called with a valid path but contains rows with the wrong type") {
      val underTest = CSVLoaderImpl.make(Paths.get("./src/test/resources/csv/failure/testCSVLoaderFailure2.csv"))
      val exception = the[CSVLoaderException] thrownBy {
        underTest.loadOrThrow
      }
      exception.getMessage should ===("2a0 is not a valid Int. Please provide a valid number")
    }
    it("should throw an exception when loadOrThrow is called with a valid path but contains a valid row and one invalid row") {
      val underTest = CSVLoaderImpl.make(Paths.get("./src/test/resources/csv/failure/testCSVLoaderFailure3.csv"))
      val exception = the[CSVLoaderException] thrownBy {
        underTest.loadOrThrow
      }
      exception.getMessage should ===("3NaN is not a valid Int. Please provide a valid number")
    }
  }
}
