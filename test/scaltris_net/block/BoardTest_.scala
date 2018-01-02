package scaltris_net.block

import org.scalatest._
import scaltris_net.ui.Board

class BoardTest_  extends FlatSpec with Matchers {
  def fixture =
    new {
      val board = new Board
    }
  
  it should "start with an empty board" in {
    fixture.board.board.flatten.forall(_.equals(Block.EMPTY)) should be (true)
  }
  
  it should "place a block" in {
      val t = new Tetromino(Block.T)
      val l = new Tetromino(Block.L)
      
      fixture.board.overlap(t) should be (false)
      fixture.board.overlap(l) should be (false)
      fixture.board.isLegal(t) should be (true)
      fixture.board.isLegal(l) should be (true)
      
      fixture.board.withTetromino(t).board should be (
          Array(
                Array(Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.T, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY),
                Array(Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.T, Block.T, Block.T, Block.EMPTY, Block.EMPTY, Block.EMPTY)
          )++ Array.fill[Array[Block.Value]](Board.Height-2)(Board.EmptyBoardRow)
      )
      
      
      fixture.board.withTetromino(l).board should be (
          Array(
                Array(Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.L, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY),
                Array(Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.L, Block.L, Block.L, Block.EMPTY, Block.EMPTY, Block.EMPTY)
          )++ Array.fill[Array[Block.Value]](Board.Height-2)(Board.EmptyBoardRow)
      )
  }
  
  it should "notice overlaps" in {
    val l = new Tetromino(Block.L)
    
    fixture.board.overlap(l) should be (false)
    fixture.board.withTetromino(l).overlap(l) should be (true)
  }
  
it should "notice illegal tetrominos" in {
  val l = new Tetromino(Block.L)
  
  fixture.board.isLegal(l) should be (true)
  fixture.board.withTetromino(l).isLegal(l) should be (false)
  
  fixture.board.isLegal(l.withMoveLeft.withMoveLeft.withMoveLeft.withMoveLeft.withMoveLeft) should be (false)
  fixture.board.isLegal(l.withMoveRight.withMoveRight.withMoveRight.withMoveRight.withMoveRight) should be (false)
  fixture.board.isLegal(
    l.withMoveDown.withMoveDown.withMoveDown.withMoveDown.withMoveDown
      .withMoveDown.withMoveDown.withMoveDown.withMoveDown.withMoveDown
      .withMoveDown.withMoveDown.withMoveDown.withMoveDown.withMoveDown
      .withMoveDown.withMoveDown.withMoveDown.withMoveDown.withMoveDown
      .withMoveDown.withMoveDown.withMoveDown.withMoveDown.withMoveDown
  ) should be (false)
}

  it should "clear full rows with empty in board" in {
    val board = new Board(
      Array(
        Array(Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.T, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY),
        Array(Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.T, Block.T, Block.T, Block.EMPTY, Block.EMPTY, Block.EMPTY),
        Board.EmptyBoardRow,
        Array.fill[Block.Value](Board.Width)(Block.L),
        Array.fill[Block.Value](Board.Width)(Block.O)) ++
        Array.fill[Array[Block.Value]](Board.Height-5)(Board.EmptyBoardRow))

    board.clearFullRows should be (2)

    board.board should be (
      Array.fill[Array[Block.Value]](2)(Board.EmptyBoardRow) ++
      Array(
         Array(Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.T, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY),
         Array(Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.EMPTY, Block.T, Block.T, Block.T, Block.EMPTY, Block.EMPTY, Block.EMPTY)
      ) ++ Array.fill[Array[Block.Value]](Board.Height-4)(Board.EmptyBoardRow))
  }
}