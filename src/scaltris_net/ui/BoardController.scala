package scaltris_net.ui

import scaltris_net.block.Tetromino
import java.awt.event.ActionListener
import java.awt.event.ActionEvent
import javax.swing.Timer
import jdk.nashorn.internal.ir.WithNode
import scala.swing.Reactor
import scala.swing.event.KeyPressed
import scala.swing.event.Key

/**
 * @author CheolHoJung
 * @date 2017-01-31
 * This class work to game progress
 */
class BoardController(val parent: TetrisPanel) extends Reactor {
   
  var board = new Board
  var currentTetromino = new Tetromino
  var nextTetromino = new Tetromino
  
  val StartTickInterval = 600
  
  var score = 0
  
  var gameRunning = true
  var gameOver = false
  
  def repaint = parent.repaint
  
  /**
   * get interval seconds to game speed and set score
   */
  def getTickInterval(score: Int) = StartTickInterval / (Math.sqrt(score/5).toInt + 1)
  
  /**
   * set game score
   */
  def setScore(newScore: Int): Unit = {
    score = newScore
    tetrisTick.setDelay(getTickInterval(score))
  }
  
  /**
   * change the current tetromino to a tetromino of argument
   */
  def tryMove(tetromino: Tetromino): Unit = {
    if (!gameRunning) return
    if (board.isLegal(tetromino)) {
      currentTetromino = nextTetromino
    }
    repaint
  }
  
  /**
   * get moving the current tetromino to bottom
   */
  def droppedTetromino: Tetromino = {
    var tetromino = currentTetromino
    while (board.isLegal(tetromino.withMoveDown)) {
      tetromino.withMoveDown
    }
    tetromino
  }
  
  /**
   * move the current tetromino to bottom
   */
  def dropTetromino: Unit = {
    if (!gameRunning) return
    currentTetromino = droppedTetromino
    placeTetromino
  }
  
  /**
   * repaint score and put the current tetromino on board.
   * change the current tetromino and create a next tetromino.
   */
  def placeTetromino: Unit = {
    board = board.withTetromino(currentTetromino)
    setScore(score + board.clearFullRows)
    currentTetromino = nextTetromino
    nextTetromino = new Tetromino
  }
  
  /**
   * pause a game
   */
  def pauseGame: Unit = {
    gameRunning = false
    tetrisTick.stop
  }
  
  /**
   * resume a game
   */
  def resumeGame: Unit = {
    gameRunning = true
    tetrisTick.start
  }
  
  /**
   * start a new game
   */
  def newGame: Unit = {
    board = new Board
    setScore(0)
    currentTetromino = nextTetromino
    nextTetromino = new Tetromino
    gameOver = false
    resumeGame
  }
  
  /**
   * switch between pausing and resuming
   */
  def togglePause: Unit = {
    if (gameRunning) {
      pauseGame
    } else {
      resumeGame
    }
  }
  
  val gameLoop = new ActionListener {
    override def actionPerformed(e: ActionEvent) {
      val newTetromino = currentTetromino.withMoveDown
      if (board.isLegal(newTetromino)) {
        currentTetromino = nextTetromino
      } else {
        placeTetromino
        if (!board.isLegal(currentTetromino)) {
          gameOver = true
          pauseGame
        }
      }
    }
  }
  
  
  reactions += {
    case KeyPressed(_, Key.Down, _, _) => tryMove(currentTetromino.withMoveDown)
    
    case KeyPressed(_, Key.Space, _, _) => dropTetromino
    
    case KeyPressed(_, Key.Left, _, _) => tryMove(currentTetromino.withMoveLeft)
    
    case KeyPressed(_, Key.Right, _, _) => tryMove(currentTetromino.withMoveRight)
    
    case KeyPressed(_, Key.Up, _, _) => tryMove(currentTetromino.withRotation)
    
    case KeyPressed(_, Key.P, _, _) => togglePause
    
    case KeyPressed(_, Key.N, _, _) => newGame
  }
  val tetrisTick: Timer = new Timer(getTickInterval(score), gameLoop)
  tetrisTick.start
}