package scalgorand.domain.asset

import zio.Scope
import zio.json.DecoderOps
import zio.test.{Spec, TestEnvironment, ZIOSpecDefault, assertTrue}

import scala.io.Source.fromFile

object AssetSpec extends ZIOSpecDefault {
  override def spec: Spec[TestEnvironment with Scope, Any] =
    test("Test for Asset Serialization") {

      val assetData =
        """
          |{
          |  "index": 185,
          |  "params": {
          |    "clawback": "WLH5LELVSEVQL45LBRQYCLJAX6KQPGWUY5WHJXVRV2NPYZUBQAFPH22Q7A",
          |    "creator": "WLH5LELVSEVQL45LBRQYCLJAX6KQPGWUY5WHJXVRV2NPYZUBQAFPH22Q7A",
          |    "decimals": 0,
          |    "default-frozen": false,
          |    "freeze": "WLH5LELVSEVQL45LBRQYCLJAX6KQPGWUY5WHJXVRV2NPYZUBQAFPH22Q7A",
          |    "manager": "WLH5LELVSEVQL45LBRQYCLJAX6KQPGWUY5WHJXVRV2NPYZUBQAFPH22Q7A",
          |    "name": "myasset",
          |    "name-b64": "bXlhc3NldA==",
          |    "reserve": "WLH5LELVSEVQL45LBRQYCLJAX6KQPGWUY5WHJXVRV2NPYZUBQAFPH22Q7A",
          |    "total": 100,
          |    "unit-name": "MYA",
          |    "unit-name-b64": "TVlB"
          |  }
          |}
          |""".stripMargin
      val decoded = assetData.fromJson[Asset]
      assertTrue(decoded.isRight)

    }
}
