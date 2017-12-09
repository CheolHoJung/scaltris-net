package scaltris_net.block

import scaltris_net.ui.Board

object main {
  def main(args: Array[String]): Unit = {
    val board = new Board
   
   val t = new Tetromino(Block.L)
   val initPosition = t.getBlockPositions
   
    val arr = Array(Array.fill[Block.Value](Board.Width)(Block.EMPTY), Array.fill[Block.Value](Board.Width)(Block.EMPTY))
    
   initPosition.foreach(p => {
     arr(p._2)(p._1) = t.block
   })
   
          
   arr.foreach {
      a => 
        println
        a.foreach(b => printf("%6s", b))
    }
  
  }
}