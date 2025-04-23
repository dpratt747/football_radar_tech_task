package processor

import cats.*
import domain.*
import cats.syntax.all.*
import processor.MatchProcessor.*

trait MatchProcessorAlg {
  def gameDetails: Map[String, (GamesPlayed, GoalsScored, GamesWon, GamesDraw, GamesLost)]
}

private final class MatchProcessor(loadedJson: List[domain.MatchObject]) extends MatchProcessorAlg {
  override def gameDetails: Map[String, (GamesPlayed, GoalsScored, GamesWon, GamesDraw, GamesLost)] = {
    val awayGames = loadedJson.groupBy(_.awayTeam).view.mapValues { awayMatches =>
      val numberOfGamesPlayed = awayMatches.length
      val goalsScored = awayMatches.map(_.awayGoals).sum

      val gamesWinLossDraw = awayMatches.map { matchDetails =>
        if (matchDetails.awayGoals > matchDetails.homeGoals) Win
        else if (matchDetails.awayGoals == matchDetails.homeGoals) Draw
        else Loss
      }

      (
        GamesPlayed(numberOfGamesPlayed),
        GoalsScored(goalsScored),
        GamesWon(gamesWinLossDraw.count(_ == Win)),
        GamesDraw(gamesWinLossDraw.count(_ == Draw)),
        GamesLost(gamesWinLossDraw.count(_ == Loss))
      )
    }.toMap

    val homeGames = loadedJson.groupBy(_.homeTeam).view.mapValues { homeMatches =>
      val numberOfGamesPlayed = homeMatches.length
      val goalsScored = homeMatches.map(_.homeGoals).sum

      val gamesWinLossDraw = homeMatches.map { matchDetails =>
        if (matchDetails.homeGoals > matchDetails.awayGoals) Win
        else if (matchDetails.homeGoals == matchDetails.awayGoals) Draw
        else Loss
      }

      (
        GamesPlayed(numberOfGamesPlayed),
        GoalsScored(goalsScored),
        GamesWon(gamesWinLossDraw.count(_ == Win)),
        GamesDraw(gamesWinLossDraw.count(_ == Draw)),
        GamesLost(gamesWinLossDraw.count(_ == Loss))
      )
    }.toMap

    awayGames |+| homeGames
  }
}

object MatchProcessor {

  final case class GamesPlayed(value: Int) extends AnyVal
  final case class GoalsScored(value: Int) extends AnyVal
  final case class GamesWon(value: Int) extends AnyVal
  final case class GamesDraw(value: Int) extends AnyVal
  final case class GamesLost(value: Int) extends AnyVal

  given Semigroup[GamesPlayed] = (x: GamesPlayed, y: GamesPlayed) => GamesPlayed(x.value + y.value)
  given Semigroup[GoalsScored] = (x: GoalsScored, y: GoalsScored) => GoalsScored(x.value + y.value)
  given Semigroup[GamesWon] = (x: GamesWon, y: GamesWon) => GamesWon(x.value + y.value)
  given Semigroup[GamesDraw] = (x: GamesDraw, y: GamesDraw) => GamesDraw(x.value + y.value)
  given Semigroup[GamesLost] = (x: GamesLost, y: GamesLost) => GamesLost(x.value + y.value)

  def make(loadedJson: List[domain.MatchObject]): MatchProcessorAlg = {
    new MatchProcessor(loadedJson)
  }
}