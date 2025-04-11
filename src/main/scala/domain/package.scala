import io.circe.*
import io.circe.generic.semiauto.*
import scala.annotation.StaticAnnotation

package object domain {
  final class JsonLoaderException(message: String) extends Exception(message)

  final class CSVLoaderException(message: String) extends Exception(message)
  
  final class ThrowsException(message: String) extends StaticAnnotation

  final case class LoadedJson(message: String)
  object LoadedJson {
    given decoder: Decoder[LoadedJson] = deriveDecoder[LoadedJson]
  }
  
  final case class LoadedCSV(rows: List[CSVRow])
  final case class CSVRow(message: String, age: Int)

}
