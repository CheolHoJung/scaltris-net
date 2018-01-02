package scaltris_net.ui

import scala.swing._
import scaltris_net.block.Block
import java.awt.Color

class TetrisPanel extends Panel {
  preferredSize = new Dimension(Block.BlockSize * (Board.Width + 5),
                                Block.BlockSize * Board.Height)
  
  override def paintComponent(g: Graphics2D) {
    super.paintComponent(g)
    
    // fill board to black
    g.setPaint(Color.black)
    g.fillRect(0, 0, Board.Width * Block.BlockSize, Board.Height * Block.BlockSize)
    
    // draw board with current tetromino
    
    // draw ghost tetromino
    
    
    // draw next tetromino
    g.drawString("Next tetromino:", (Board.Width + 1) * Block.BlockSize, Block.BlockSize / 2)
    
    // draw score
    g.drawString("Score: %d".format(0), (Board.Width + 1) * Block.BlockSize, Block.BlockSize * 6)
  }
}