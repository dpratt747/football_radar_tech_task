import io.circe.*
import io.circe.generic.semiauto.*
import scala.annotation.StaticAnnotation

package object domain {
  /**
   * Custom Domain Exceptions
   */

  sealed abstract class DomainException(message: String) extends Exception(message)

  final case class JsonLoaderException(message: String) extends DomainException(message)

  final case class CSVLoaderException(message: String) extends DomainException(message)
  
  final case class ThrowsException(message: String) extends StaticAnnotation

  /**
   * Loaded Case Classes
   */

  //todo: revisit the types
  final case class MatchObject(
                              gameId: Int,
                              homeTeam: String,
                              awayTeam: String,
                              kickoff: String,
                              seasonId: Int,
                              homeGoals: Int,
                              awayGoals: Int
                              )
  
  object MatchObject {
    given decoder: Decoder[MatchObject] = deriveDecoder[MatchObject]
  }
  
  final case class LoadedCSV(rows: List[CSVRow])
  final case class CSVRow(message: String, age: Int)

}
