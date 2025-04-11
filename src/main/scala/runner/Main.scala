package runner

import loader.{CSVLoaderImpl, JsonLoaderImpl}

import java.nio.file.Paths

object Main {
  def main(args: Array[String]): Unit = {
    val jsonPath = Paths.get("./src/main/resources/jsonLoader.json")
    val jsonLoader = JsonLoaderImpl.make(jsonPath)

    val csvPath = Paths.get("./src/main/resources/csvLoader.csv")
    val csvLoader = CSVLoaderImpl.make(csvPath)

    println(jsonLoader.loadOrThrow)
    println(csvLoader.loadOrThrow)
  }
}
