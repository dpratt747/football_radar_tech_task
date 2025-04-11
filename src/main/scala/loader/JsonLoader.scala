package loader

import domain.*

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Path}
import io.circe.parser.decode


trait JsonLoaderAlg {
  // similar to pureconfig's loadOrThrow method - runtime failure
  @ThrowsException("This method may throw a JsonLoaderException")
  def loadOrThrow: LoadedJson
}

private final class JsonLoaderImpl(path: Path) extends JsonLoaderAlg {

  override def loadOrThrow: LoadedJson = {
    if (!Files.exists(path)) throw JsonLoaderException(s"Invalid path, file is not found: [$path]")

    val jsonString = Files.readString(path, StandardCharsets.UTF_8)
    decode[LoadedJson](jsonString).getOrElse(throw JsonLoaderException("Failed to decode JSON"))
  }
}

object JsonLoaderImpl {
  def make(path: Path): JsonLoaderAlg = {
    new JsonLoaderImpl(path: Path)
  }
}
