package scaltris_net.ui

import scala.swing._

object Scaltris extends SimpleSwingApplication {
  val boardPanel = new TetrisPanel
  
  def top = new MainFrame {
    title = "Scaltris-net"
    
    contents = boardPanel
    
    val helpText = """Left: Move left
                     |Right: Move right
                     |Up: Rotate
                     |Down: Move down
                     |Space: Drop all the way down
                     |P: Toggle pause
                     |N: New game""".stripMargin
                     
    def showHelp: Unit = {
      Dialog.showMessage(boardPanel, helpText, "Scaltris-net help", Dialog.Message.Plain)
    }
    
    menuBar = new MenuBar {
      contents += new Menu("Game") {
        contents += new MenuItem(Action("Help") { showHelp })
        contents += new Separator
        contents += new MenuItem(Action("Exit") { sys.exit(0) })
      }
    }
    
    pack
    
    boardPanel.requestFocus
  }
}