package scaltris_net.ui

import java.awt.Color
import java.awt.image.RescaleOp

import scala.swing._

import scaltris_net.block.Block

class TetrisPanel extends Panel {
  val controller = new BoardController(this)
  controller.listenTo(this.keys)
  
  /**
   * The extra width is for the next tetromino and scores
   */
  preferredSize = new Dimension(Block.BlockSize * (Board.Width + 5),
                                Block.BlockSize * Board.Height)
  
  override def paintComponent(g: Graphics2D) {
    super.paintComponent(g)
    
    /**
     * Fill board to black
     */
    g.setPaint(Color.black)
    g.fillRect(0, 0, Board.Width * Block.BlockSize, Board.Height * Block.BlockSize)
    
    
    /**
     * Draw ghost tetromino
     */
    val darkenOperation = new RescaleOp(0.5f, 0.5f, null);
    val ghostTetromino = controller.droppedTetromino
    
    ghostTetromino.getBlockPositions.foreach {
      case (x, y) =>
        val img = Block.getBlockImage(ghostTetromino.block)
        
        g.drawImage(img, null, x * Block.BlockSize, y * Block.BlockSize)
    }
    
    /**
     * Draw board with current tetromino
     */
    controller.board.withTetromino(controller.currentTetromino).board.zipWithIndex.foreach {
      case (row, y) => row.zipWithIndex.foreach {
        case (block, x) => {
          if (block != Block.EMPTY) {
            val img = Block.getBlockImage(block)
            g.drawImage(img, null, x * Block.BlockSize, y * Block.BlockSize)
          }
        }
      }
    }
    
    /**
     * Draw next tetromino
     */
    g.drawString("Next tetromino:", (Board.Width + 1) * Block.BlockSize, Block.BlockSize / 2)
    val nextTetromino = controller.nextTetromino
    nextTetromino.getBlockPositions.foreach {
      case (x, y) => {
         val img = Block.getBlockImage(nextTetromino.block)
         g.drawImage(img, null, (Board.Width / 2 + 2 + x) * Block.BlockSize, (y + 1) * Block.BlockSize)
      }
    }
    
    /**
     * Draw score
     */
    g.drawString("Score: %d".format(controller.score), (Board.Width + 1) * Block.BlockSize, Block.BlockSize * 6)
  }
}