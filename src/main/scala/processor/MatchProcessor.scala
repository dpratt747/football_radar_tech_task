package processor

import domain.*

trait MatchProcessorAlg {
  def gameDetails: Map[String, Metadata]
}

private final class MatchProcessor(loadedMatches: List[domain.MatchObject]) extends MatchProcessorAlg {
  override def gameDetails: Map[String, Metadata] =
    loadedMatches.foldLeft(Map.empty[String, Metadata]) { case (state, matchObject) =>

      val homeGamePoints = if (matchObject.homeGoals > matchObject.awayGoals) Win
      else if (matchObject.homeGoals == matchObject.awayGoals) Draw
      else Loss

      val homeMetadata = state.get(matchObject.homeTeam) match {
        case Some(metadata) =>
          metadata.copy(
            gamesPlayed = GamesPlayed(metadata.gamesPlayed.value + 1),
            goalsScored = GoalsScored(metadata.goalsScored.value + matchObject.homeGoals),
            gamesWon = if (matchObject.homeGoals > matchObject.awayGoals) GamesWon(metadata.gamesWon.value + 1) else metadata.gamesWon,
            gamesDrawn = if (matchObject.homeGoals == matchObject.awayGoals) GamesDraw(metadata.gamesDrawn.value + 1) else metadata.gamesDrawn,
            gamesLost = if (matchObject.homeGoals < matchObject.awayGoals) GamesLost(metadata.gamesLost.value + 1) else metadata.gamesLost,
            goalsAgainst = GoalsAgainst(metadata.goalsAgainst.value + matchObject.awayGoals),
            totalPoints = TotalPoints(metadata.totalPoints.value + homeGamePoints.points)
          )
        case None => Metadata().copy(
          gamesPlayed = GamesPlayed(1),
          goalsScored = GoalsScored(matchObject.homeGoals),
          gamesWon = if (matchObject.homeGoals > matchObject.awayGoals) GamesWon(1) else GamesWon(0),
          gamesDrawn = if (matchObject.homeGoals == matchObject.awayGoals) GamesDraw(1) else GamesDraw(0),
          gamesLost = if (matchObject.homeGoals < matchObject.awayGoals) GamesLost(1) else GamesLost(0),
          goalsAgainst = GoalsAgainst(matchObject.awayGoals),
          totalPoints = TotalPoints(homeGamePoints.points)
        )
      }

      val awayGamePoints = if (matchObject.awayGoals > matchObject.homeGoals) Win
      else if (matchObject.awayGoals == matchObject.homeGoals) Draw
      else Loss

      val awayMetadata = state.get(matchObject.awayTeam) match {
        case Some(metadata) => metadata.copy(
          gamesPlayed = GamesPlayed(metadata.gamesPlayed.value + 1),
          goalsScored = GoalsScored(metadata.goalsScored.value + matchObject.awayGoals),
          gamesWon = if (matchObject.awayGoals > matchObject.homeGoals) GamesWon(metadata.gamesWon.value + 1) else metadata.gamesWon,
          gamesDrawn = if (matchObject.homeGoals == matchObject.awayGoals) GamesDraw(metadata.gamesDrawn.value + 1) else metadata.gamesDrawn,
          gamesLost = if (matchObject.awayGoals < matchObject.homeGoals) GamesLost(metadata.gamesLost.value + 1) else metadata.gamesLost,
          goalsAgainst = GoalsAgainst(metadata.goalsAgainst.value + matchObject.homeGoals),
          totalPoints = TotalPoints(metadata.totalPoints.value + awayGamePoints.points)
        )
        case None => Metadata(
          gamesPlayed = GamesPlayed(1),
          goalsScored = GoalsScored(matchObject.awayGoals),
          gamesWon = if (matchObject.awayGoals > matchObject.homeGoals) GamesWon(1) else GamesWon(0),
          gamesDrawn = if (matchObject.homeGoals == matchObject.awayGoals) GamesDraw(1) else GamesDraw(0),
          gamesLost = if (matchObject.awayGoals < matchObject.homeGoals) GamesLost(1) else GamesLost(0),
          goalsAgainst = GoalsAgainst(matchObject.homeGoals),
          totalPoints = TotalPoints(awayGamePoints.points)
        )
      }

      state + (matchObject.homeTeam -> homeMetadata) + (matchObject.awayTeam -> awayMetadata)

    }
}

object MatchProcessor {
  def make(loadedJson: List[domain.MatchObject]): MatchProcessorAlg = {
    new MatchProcessor(loadedJson)
  }
}