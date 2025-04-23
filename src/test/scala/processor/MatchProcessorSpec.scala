package processor

import domain.MatchObject
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers
import processor.MatchProcessor.{GamesDraw, GamesLost, GamesPlayed, GamesWon, GoalsScored}
import processor.MatchProcessorSpec.expectedResultsTuple

final class MatchProcessorSpec extends AnyFunSpec with Matchers {
  describe("MatchProcessor") {
    it("should return empty game details when there are no entries") {
      val games = List.empty[MatchObject]
      val underTest = MatchProcessor.make(games)

      underTest.gameDetails should ===(Map.empty)
    }
    it("should return the correct game details when there is one entry") {
      val games = List(
        MatchObject(
          1473436,
          "Crystal Palace",
          "Arsenal",
          "2022-08-05 21:00:00",
          9217,
          0,
          2
        )
      )
      val underTest = MatchProcessor.make(games)

      val expectedResults = Map(
        "Crystal Palace" -> expectedResultsTuple(1, 0, 0, 0, 1),
        "Arsenal" -> expectedResultsTuple(1, 2, 1, 0, 0)
      )

      println(underTest.gameDetails)

      underTest.gameDetails should ===(expectedResults)
    }
    it("should return the correct game details when there are two entry") {
      val games = List(
        MatchObject(
          1473436,
          "Crystal Palace",
          "Arsenal",
          "2022-08-05 21:00:00",
          9217,
          0,
          2
        ),
        MatchObject(
          1473436,
          "Manchester United",
          "Arsenal",
          "2022-08-05 21:00:00",
          9217,
          0,
          2
        )
      )

      val underTest = MatchProcessor.make(games)

      val expectedResults = Map(
        "Crystal Palace" -> expectedResultsTuple(1, 0, 0, 0, 1),
        "Arsenal" -> expectedResultsTuple(2, 4, 2, 0, 0),
        "Manchester United" -> expectedResultsTuple(1, 0, 0, 0, 1)
      )

      underTest.gameDetails should ===(expectedResults)
    }
    it("should return the correct game details when there is a draw") {
      val games = List(
        MatchObject(
          1473436,
          "Crystal Palace",
          "Arsenal",
          "2022-08-05 21:00:00",
          9217,
          2,
          2
        ),
        MatchObject(
          1473436,
          "Manchester United",
          "Arsenal",
          "2022-08-05 21:00:00",
          9217,
          0,
          2
        )
      )

      val underTest = MatchProcessor.make(games)

      val expectedResults = Map(
        "Crystal Palace" -> expectedResultsTuple(1, 2, 0, 1, 0),
        "Arsenal" -> expectedResultsTuple(2, 4, 1, 1, 0),
        "Manchester United" -> expectedResultsTuple(1, 0, 0, 0, 1)
      )

      underTest.gameDetails should ===(expectedResults)
    }
  }
}

object MatchProcessorSpec {
  def expectedResultsTuple(
                            nPlayed: Int,
                            goalsScored: Int,
                            gamesWon: Int,
                            gamesDraw: Int,
                            gamesLost: Int
                          ): (GamesPlayed, GoalsScored, GamesWon, GamesDraw, GamesLost) = {
    (
      GamesPlayed(nPlayed),
      GoalsScored(goalsScored),
      GamesWon(gamesWon),
      GamesDraw(gamesDraw),
      GamesLost(gamesLost)
    )
  }
}