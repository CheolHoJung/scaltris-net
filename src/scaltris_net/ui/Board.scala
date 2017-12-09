package scaltris_net.ui

import scaltris_net.block._

/**
 * @author CheolHoJung
 * @date 2017-11-15
 * This object is pair object with Board class
 */
object Board {
  val Width = 10
  val Height = 24
  val EmptyBoardRow = Array.fill[Block.Value](Width)(Block.EMPTY)
  
  def emptyBoard: Array[Array[Block.Value]] = {
    Array.fill[Array[Block.Value]](Height)(EmptyBoardRow)
  }
  
  def main(args: Array[String]): Unit = {
    val b = new Board
    val l = new Tetromino(Block.L)
    println(b.overlap(l))
    println(b.withTetromino(l).overlap(l))
  }
}

/**
 * @author CheolHoJung
 * @date 2017-11-15
 * This class is board that blocks are piled up on it.
 */
class Board(var board: Array[Array[Block.Value]]) {
  
  /**
    * Contructor for 2d-array filled with empty blocks.
    */
  def this() = this(Board.emptyBoard)
  
  /**
    * Stack blocks to this board
    */
  def withTetromino(tetromino: Tetromino): Board = {
    val boardCopy = clone
    val positions = tetromino.getBlockPositions
    
    positions.foreach {
      position =>  {
        boardCopy.board(position._2)(position._1) = tetromino.block
      }
    }
    
    boardCopy
  }
  
  /**
    * Check which blocks exist in the position values ​​of the block to be stacked.
    */
  def overlap(tetromino: Tetromino): Boolean = {
    val positions = tetromino.getBlockPositions
    !positions.forall {
      position => board(position._2)(position._1).equals(Block.EMPTY)
    }
  }

  private def legalX: Range = (0 until Board.Width)
  private def legalY: Range = (0 until Board.Height)
  
  /**
    * Check that blocks can be stacked on this board.
    */
  def isLegal(tetromino: Tetromino): Boolean = {
    val positions = tetromino.getBlockPositions
    positions.forall {
      position => legalX.contains(position._1) && legalY.contains(position._2)
    } && !overlap(tetromino)
  }
  
  /**
    * Clear rows with all columns filled with blocks not empty.
    */
  def clearFullRows: Int = {
    val nClearedBoard = board.filter(_.contains(Block.EMPTY))
    val clearedRows = Board.Height - nClearedBoard.size
    board = Array.fill[Array[Block.Value]](clearedRows)(Board.EmptyBoardRow) ++ nClearedBoard
    clearedRows
  }
  
  /**
    * Returns board which copied 2d array of own.
    */
  override def clone: Board = new Board(board.map(_.clone))
}