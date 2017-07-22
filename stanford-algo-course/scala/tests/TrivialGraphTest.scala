package week3

import org.scalatest.FunSuite

class TrivialGraphTest extends FunSuite {
	val g = new Graph(Array(Array(1), Array(0), Array()))
  
  	test("Check vertex count") {
	    assert(g.vertexCount === 3)
  	}
  
  	test("Check edge count") {
	    assert(g.edgeCount === 1)
  	}  	

    test("Check edges") {
      assert(g.edges === Array((0,1)))
    }   
}