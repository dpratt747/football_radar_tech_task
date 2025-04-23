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
   * ADTs
   */

  sealed trait MatchResult extends Product with Serializable {
    def points: Int
  }

  case object Win extends MatchResult {
    override def points = 3
  }

  case object Draw extends MatchResult {
    override def points = 1
  }

  case object Loss extends MatchResult {
    override def points = 0
  }

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

  final case class GamesPlayed(value: Int) extends AnyVal

  final case class GoalsScored(value: Int) extends AnyVal

  final case class GamesWon(value: Int) extends AnyVal

  final case class GamesDraw(value: Int) extends AnyVal

  final case class GamesLost(value: Int) extends AnyVal

  final case class GoalsAgainst(value: Int) extends AnyVal

  final case class TotalPoints(value: Int) extends AnyVal

  final case class Metadata(
                             gamesPlayed: GamesPlayed = GamesPlayed(0),
                             goalsScored: GoalsScored = GoalsScored(0),
                             gamesWon: GamesWon = GamesWon(0),
                             gamesDrawn: GamesDraw = GamesDraw(0),
                             gamesLost: GamesLost = GamesLost(0),
                             goalsAgainst: GoalsAgainst = GoalsAgainst(0),
                             totalPoints: TotalPoints = TotalPoints(0)
                           )

}
