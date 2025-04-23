package runner

import loader.{CSVLoaderImpl, JsonLoaderImpl}
import processor.MatchProcessor

import java.nio.file.Paths
import scala.collection.immutable.ListMap

object Main {
  def main(args: Array[String]): Unit = {

    /**
     * Loaded via json file
     */

    val jsonPath = Paths.get("./src/main/resources/22-23.json")
    val jsonLoader = JsonLoaderImpl.make(jsonPath)
    val loadedJson = jsonLoader.loadOrThrow
    val processor = MatchProcessor.make(loadedJson)

    /**
     * Loaded via csv file
     */
    val csvPath = Paths.get("./src/main/resources/22-23.csv")
    val csvLoader = CSVLoaderImpl.make(csvPath)
    val loadedCSV = csvLoader.loadOrThrow
    val processorCSV = MatchProcessor.make(loadedCSV)

    require(processorCSV.gameDetails == processor.gameDetails) // verify the generated game details are the same irrespective of how they are ingested

    /**
     * Sorted results
     */
    val sorted = processor.gameDetails.toSeq.sortBy(fields =>
      (-fields._2.totalPoints.value, -(fields._2.goalsScored.value - fields._2.goalsAgainst.value), -fields._2.goalsScored.value
      )
    )
    val sortedMap = ListMap.from(sorted)
    println(sortedMap.mkString("{\n  ", ",\n  ", "\n}"))


    /**
     * non sorted results
     */

    //    println(processorCSV.gameDetails.mkString("{\n  ", ",\n  ", "\n}"))
    //    println(processor.gameDetails.mkString("{\n  ", ",\n  ", "\n}"))
  }
}
