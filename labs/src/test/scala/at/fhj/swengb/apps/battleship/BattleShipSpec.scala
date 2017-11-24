package at.fhj.swengb.apps.battleship

import org.scalatest.WordSpecLike

class BattleShipSpec extends WordSpecLike {

  val bp = BattlePos(0, 0)
  val validxPos = (0 until 5).map(x => BattlePos(x, 0)).toSet
  val validyPos = (0 until 5).map(y => BattlePos(0, y)).toSet
  val illegalPos = Set(BattlePos(3, 1)) ++ validxPos.tail

  // TODO WP5.1 make all tests green
  "Battleship" should {
    "0 pos is illegal" in intercept[IllegalArgumentException](BattleShip("",Set()))
    "1 pos is illegal" in intercept[IllegalArgumentException](BattleShip("",Set(bp)))
    "2 pos is illegal" in intercept[IllegalArgumentException](BattleShip("",Set(bp, bp)))
    "3 pos is illegal" in intercept[IllegalArgumentException](BattleShip("",Set(bp, bp, bp)))
    "4 pos is illegal" in intercept[IllegalArgumentException](BattleShip("",Set(bp, bp, bp, bp)))
    "6 pos is illegal" in intercept[IllegalArgumentException](BattleShip("",Set(bp, bp, bp, bp)))
    "pos has to be horizonal" in intercept[IllegalArgumentException](BattleShip("",validxPos))
    "pos has to be vertical" in intercept[IllegalArgumentException](BattleShip("",validyPos))
    "pos is connected" in intercept[IllegalArgumentException](BattleShip("",illegalPos))

  }
}
