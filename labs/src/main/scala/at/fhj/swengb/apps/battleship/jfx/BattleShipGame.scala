package at.fhj.swengb.apps.battleship.jfx

import javafx.scene.layout.GridPane

import at.fhj.swengb.apps.battleship.{BattleField, BattlePos, Vessel}


case class BattleShipGame(battleField: BattleField,
                          getCellWidth: Int => Double,
                          getCellHeight: Int => Double,
                          log: String => Unit) {

  var hits: Map[Vessel, Set[BattlePos]] = Map()

  var sunkShips: Set[Vessel] = Set()

  /**
    * We don't ever change cells, they should be initialized only once.
    */
  private val cells = for {x <- 0 until battleField.width
                           y <- 0 until battleField.height
                           pos = BattlePos(x, y)} yield {
    BattleCell(BattlePos(x, y), getCellWidth(x), getCellHeight(y), log, battleField.fleet.findByPos(pos), updateGameState)
  }

  // TODO implement a message which complains about the user if he hits a vessel more than one time on a specific position
  // TODO if all parts of a vessel are hit, a log message should indicate that the vessel has sunk. The name should be displayed.
  // TODO make sure that when a vessel was destroyed, it is sunk and cannot be destroyed again. it is destroyed already.
  // TODO if all vessels are destroyed, display this to the user
  // TODO reset game state when a new game is started
  def updateGameState(vessel: Vessel, pos: BattlePos): Unit = {
    log("Hit Battleship " + vessel.name + " at position " + pos)
    if (hits.contains(vessel)) {


      if (hits(vessel).contains(pos)) {
        log(s"vessel " + vessel.name + " was already hit before on position " + pos + ".")
      }
      else {

        // wieviele Treffer hat ein Schiff schon bekommen
        val n = hits(vessel).size + 1;
        log(s"Vessel ${vessel.name} was hit $n times")
      }
      hits = hits.updated(vessel, hits(vessel) + pos)

      if (hits(vessel) == vessel.occupiedPos) {
        sunkShips = sunkShips + vessel
        log(s"${vessel.name} is destroyed!")
      }

      if (battleField.fleet.vessels == sunkShips) {
        log(s"GAME OVER")
      }

    }
    else {
      log("First hit on vessel " + vessel.name + ".")
      hits = hits.updated(vessel, Set(pos))

    }
  }

  // 'display' cells
  def init(gridPane: GridPane): Unit = {
    gridPane.getChildren.clear()
    for (c <- cells) gridPane.add(c, c.pos.x, c.pos.y)
    cells.foreach(c => c.init)
  }


  /**
    * Create a new game.
    *
    * This means
    *
    * - resetting all cells to 'empty' state
    * - placing your ships at random on the battleground
    *
    */
  def newGame(gridPane: GridPane) = init(gridPane)


}
