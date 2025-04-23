package loader

import com.github.tototoshi.csv.*
import domain.*
import CSVLoaderImpl.*
import java.nio.file.{Files, Path}
import scala.util.*

trait CSVLoaderAlg {
  // similar to pureconfig's loadOrThrow method - runtime failure
  @ThrowsException("This method may throw a CSVLoaderException")
  def loadOrThrow: LoadedCSV
}

private final class CSVLoaderImpl(path: Path)(using Using.Releasable[CSVReader]) extends CSVLoaderAlg {

  private enum Columns:
    case Message, Age

  override def loadOrThrow: LoadedCSV = {
    if (!Files.exists(path)) throw CSVLoaderException(s"Invalid path, file is not found: [$path]")

    val tryReading: Either[Throwable, List[CSVRow]] = Using(CSVReader.open(path.toFile)){ reader =>
    // This gets all rows with the header and maps it into a CSVRow case class
    reader.allWithHeaders().map{ row =>
        CSVRow(
          message = row(Columns.Message.toString),
          age = row(Columns.Age.toString).stringToInt
        )
      }
    }.toEither

    tryReading match {
      case Right(csvRows) => LoadedCSV(csvRows)
      case Left(err) => throw CSVLoaderException(err.getMessage)
    }
  }
}

object CSVLoaderImpl {
  given Using.Releasable[CSVReader] = (reader: CSVReader) => {
    println("Closing csv reader")
    reader.close()
  }

  extension (str: String)
    private def stringToInt: Int = {
      try {
        str.toInt
      } catch {
        case _: NumberFormatException =>
          throw new IllegalArgumentException(s"$str is not a valid Int. Please provide a valid number")
      }
    }

  def make(path: Path): CSVLoaderAlg = {
    new CSVLoaderImpl(path: Path)
  }
}
