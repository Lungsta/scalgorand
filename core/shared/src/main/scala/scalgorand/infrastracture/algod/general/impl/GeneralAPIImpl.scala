package scalgorand.infrastracture.algod.general.impl

import scalgorand.domain.genesis.Genesis
import scalgorand.infrastracture.InfrastructureError.GeneralAPIError
import scalgorand.infrastracture.algod.general.GeneralAPI
import sttp.client3._
import sttp.client3.ziojson.asJson
import zio._

final case class GeneralAPIImpl(sttpBackend: SttpBackend[Task, Any]) extends GeneralAPI {
  override def getGenesis: ZIO[Any, Option[GeneralAPIError], Genesis] = {
    val request = basicRequest
      .response(asJson[Genesis])
      .get(uri"http://localhost:4001/genesis")
    val result: ZIO[Any, GeneralAPIError, Either[ResponseException[String, String], Genesis]] = sttpBackend
      .send(request)
      .mapBoth(
        error => GeneralAPIError(error),
        result => result.body,
      )
    result.flatMap {
      case Left(_)  => ZIO.none
      case Right(value) => ZIO.some(value)
    }.some
  }


}

object GeneralAPIImpl {
  val layer: ZLayer[SttpBackend[Task, Any], Any, GeneralAPIImpl] =
    ZLayer.fromFunction(new GeneralAPIImpl(_))
}
