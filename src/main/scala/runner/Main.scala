package runner

import loader.JsonLoaderImpl
import processor.MatchProcessor

import java.nio.file.Paths

object Main {
  def main(args: Array[String]): Unit = {
    val jsonPath = Paths.get("./src/main/resources/22-23.json")
    val jsonLoader = JsonLoaderImpl.make(jsonPath)
    val loadedJson = jsonLoader.loadOrThrow
    val processor = MatchProcessor.make(loadedJson)

//    val csvPath = Paths.get("./src/main/resources/csvLoader.csv")
//    val csvLoader = CSVLoaderImpl.make(csvPath)
//    println(csvLoader.loadOrThrow)

    println(processor.gamesPlayed)
  }
}
