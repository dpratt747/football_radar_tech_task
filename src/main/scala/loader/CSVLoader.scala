package loader

import com.github.tototoshi.csv.*
import domain.*
import CSVLoaderImpl.*
import java.nio.file.{Files, Path}
import scala.util.*

trait CSVLoaderAlg {
  // similar to pureconfig's loadOrThrow method - runtime failure
  @ThrowsException("This method may throw a CSVLoaderException")
  def loadOrThrow: List[MatchObject]
}

private final class CSVLoaderImpl(path: Path)(using Using.Releasable[CSVReader]) extends CSVLoaderAlg {
  
  override def loadOrThrow: List[MatchObject] = {
    if (!Files.exists(path)) throw CSVLoaderException(s"Invalid path, file is not found: [$path]")

    val tryReading: Either[Throwable, List[MatchObject]] = Using(CSVReader.open(path.toFile)) { reader =>
      reader.all().map { row =>
        MatchObject(
          gameId = row.head.stringToInt,
          homeTeam = row(1),
          awayTeam = row(2),
          kickoff = row(3),
          seasonId = row(4).stringToInt,
          homeGoals = row(5).stringToInt,
          awayGoals = row(6).stringToInt
        )
      }
    }.toEither

    tryReading match {
      case Right(csvRows) => csvRows
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
