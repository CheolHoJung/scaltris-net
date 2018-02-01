package scaltris_net.block

import scaltris_net.ui.Board

/**
 * @author CheolHoJung
 * @date 2017-10-24
 * This class is associated with moving based on actual positions and orientation.
 */
class Tetromino(val block: Block.Value,
                val position: Tuple2[Int, Int] = (Board.Width / 2, 1), 
                val orientation: Int = 0) {
  
  /**
    * Create tetromino have a random block
    */
  def this() {
    this(Block.nextBlock)
  }
  
  /**
    * Return positions based on orientation
    */
  def getBlockPositions: Array[(Int, Int)] = {
    Block.getPositions(block)(orientation).map {
      blockPosition => (blockPosition._1 + position._1, blockPosition._2  + position._2)
    }
  }
  
  /**
    * Copy tetromino with block rotate
    */
  def withRotation: Tetromino = copy(orientation = (orientation + 1) % Block.getPositions(block).size)
    
  /**
    * Copy tetromino move to left
    */
  def withMoveLeft: Tetromino = copy(position = (position._1 - 1, position._2))
    
  /**
    * Copy tetromino move to right
    */
  def withMoveRight: Tetromino = copy(position = (position._1 + 1, position._2))
    
  /**
    * Copy tetromino move to down
    */
  def withMoveDown: Tetromino = copy(position = (position._1, position._2 + 1))
    
  /**
    * Copy block based on block, positions and orientation.
    */
  def copy(block: Block.Value = block,
           position: Tuple2[Int, Int] = position,
           orientation: Int = orientation): Tetromino = 
     new Tetromino(block, position, orientation)
}