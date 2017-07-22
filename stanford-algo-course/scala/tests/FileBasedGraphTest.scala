package week3

import java.io.File
import org.scalatest.FunSuite
import scala.collection.mutable.ArrayBuffer

class FileBasedGraphTest extends FunSuite {
	val g1 = Graph(new File("graphs/testCase1a.txt"))
	val g2 = Graph(new File("graphs/testCase2a.txt"))
	val g3 = Graph(new File("graphs/kargerMinCut.txt"))
  
  	test("Check vertex count") {
    	assert(g3.vertexCount === 200)
  	}
  
  	test("Check edge count") {
    	assert(g3.edgeCount === 2517)
  	}
  	
  	test("Min cut for Graph1") {
  		assert(new MinCut(g1).calculate === 2)
  	}

  	test("Min cut for Graph2") {
  		assert(new MinCut(g2).calculate === 1)
  	}
}