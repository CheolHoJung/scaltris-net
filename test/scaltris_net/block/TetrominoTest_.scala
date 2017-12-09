package scaltris_net.block

import org.scalatest._

class TetrominoTest_ extends FlatSpec with Matchers  {
  
  def fixture =
    new {
      val tetromino = new Tetromino(block = Block.T)
    }
  
  it should "have one block" in {
    for (i <- 0 until 10) {
      val block = new Tetromino().block
      Block.values should contain (block)
    }
  }
  
  it should "have four position" in {
    
    fixture.tetromino.getBlockPositions should have size (4)
  }
  
  
  "withMoveLeft" should "return copied Tetromino with position.x decreased by 1 " in {
    val origin = fixture.tetromino
		val copedWithXdc1 = origin.copy(position = (origin.position._1 - 1, origin.position._2))
    val withLeft = fixture.tetromino.withMoveLeft
    
    withLeft.position should not equal (origin.position)
    withLeft.block should equal (origin.block)
    withLeft.orientation should equal (origin.orientation)
    
    copedWithXdc1.position should equal (withLeft.position)
  }
  
  "withMoveRight" should "return copied Tetromino with position.x increased by 1 " in {
	  val origin = fixture.tetromino
	  val copedWithXic1 = origin.copy(position = (origin.position._1 + 1, origin.position._2))
	  val withRight = fixture.tetromino.withMoveRight
	  
	  withRight.position should not equal (origin.position)
	  withRight.block should equal (origin.block)
	  withRight.orientation should equal (origin.orientation)
	  
	  copedWithXic1.position should equal (withRight.position)
  }
  
  "withMoveDown" should "return copied Tetromino with position.y increased by 1 " in {
	  val origin = fixture.tetromino
	  val copedWithYic1 = origin.copy(position = (origin.position._1, origin.position._2 + 1))
	  val withDown = fixture.tetromino.withMoveDown
	  
	  withDown.position should not equal (origin.position)
	  withDown.block should equal (origin.block)
	  withDown.orientation should equal (origin.orientation)
	  
	  copedWithYic1.position should equal (withDown.position)
  }
  
  "copy" should "copy tetromino`s all attributes" in {
    val origin = fixture.tetromino
    val copiedByOrigin = origin.copy()
    
    copiedByOrigin.block should equal (origin.block)
    copiedByOrigin.position should equal (origin.position)
    copiedByOrigin.orientation should equal (origin.orientation)
  }
}