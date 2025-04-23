package processor

import domain.MatchObject
import cats.syntax.all.*

trait MatchProcessorAlg {
  def gamesPlayed: Map[String, Int]
}

private final class MatchProcessor(loadedJson: List[domain.MatchObject]) extends MatchProcessorAlg {
  override def gamesPlayed: Map[String, Int] = {
    val awayGames: Map[String, Int] = loadedJson.groupBy(_.awayTeam).view.mapValues(_.length).toMap
    val homeGames: Map[String, Int] = loadedJson.groupBy(_.homeTeam).view.mapValues(_.length).toMap

    awayGames |+| homeGames
  }
}

object MatchProcessor {
  def make(loadedJson: List[domain.MatchObject]): MatchProcessorAlg = {
    new MatchProcessor(loadedJson)
  }
}