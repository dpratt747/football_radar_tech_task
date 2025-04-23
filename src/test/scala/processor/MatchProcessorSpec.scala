package processor

import domain.MatchObject
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

final class MatchProcessorSpec extends AnyFunSpec with Matchers {
  describe("MatchProcessor") {
    it("should return the correct number of games played when there are no entries") {
      val games = List.empty[MatchObject]
      val underTest = MatchProcessor.make(games)

      underTest.gamesPlayed should ===(Map.empty[String, Int])
    }
    it("should return the number of games played when there is one entry") {
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
        "Crystal Palace" -> 1,
        "Arsenal" -> 1
      )

      underTest.gamesPlayed should ===(expectedResults)
    }
    it("should return the number of games played when there are two entry") {
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
        "Crystal Palace" -> 1,
        "Arsenal" -> 2,
        "Manchester United" -> 1
      )

      underTest.gamesPlayed should ===(expectedResults)
    }
  }
}