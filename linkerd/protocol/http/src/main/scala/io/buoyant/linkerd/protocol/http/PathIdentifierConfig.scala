package io.buoyant.linkerd.protocol.http

import com.fasterxml.jackson.annotation.JsonIgnore
import com.twitter.finagle.{Dtab, Path, Stack}
import io.buoyant.linkerd.IdentifierInitializer
import io.buoyant.linkerd.protocol.HttpIdentifierConfig
import io.buoyant.router.http.PathIdentifier

class PathIdentifierInitializer extends IdentifierInitializer {
  val configClass = classOf[PathIdentifierConfig]
  override val configId = PathIdentifierConfig.kind
}

object PathIdentifierInitializer extends PathIdentifierInitializer

object PathIdentifierConfig {
  val kind = "io.l5d.path"
}

class PathIdentifierConfig extends HttpIdentifierConfig {
  var segments: Option[Int] = None
  var consume: Option[Boolean] = None

  @JsonIgnore
  override def newIdentifier(
    prefix: Path,
    baseDtab: () => Dtab = () => Dtab.base,
    routerParams: Stack.Params = Stack.Params.empty
  ) = PathIdentifier(prefix, segments.getOrElse(1), consume.getOrElse(false), baseDtab)
}
