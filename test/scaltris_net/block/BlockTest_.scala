package scaltris_net.block

import org.scalatest._
import java.awt.Color


class BlockTest_ extends FlatSpec with Matchers {
  
	it should "have color" in {
		Block.getBlockColor(Block.T) should not equal (null)
		Block.getBlockColor(Block.T) shouldBe a [Color]
	}
	
	"nextBlock" should "return a random element in Block.values that not Block.EMPTY." in {
		for(i <- 0 until 10) {
			Block.values should not be (Block.EMPTY)
			Block.values should contain (Block.nextBlock)
		}
	}
	
	"Block.EMPTY instance positions" should "be empty array" in {
		val emptyBlock = Block.EMPTY
		Block.getPositions(emptyBlock) shouldBe empty
	}
}