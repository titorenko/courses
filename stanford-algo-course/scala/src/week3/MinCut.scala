package week3

import scala.util.Random

class MinCut(g: Graph) {

	def calculate(): Int = {
		calculate(Math.log(g.vertexCount))
	}

	def calculate(confidence: Double): Int = {
		val n = g.vertexCount
		var min = g.edgeCount
		val nIterations = (n * n * confidence).toInt
		val edges = g.edges()
		1 to nIterations foreach { it =>			
			val m = minCutOneRound(edges)
			if (m < min) min = m
		}
		return min
	}
	

	def minCutOneRound(edges: Array[Tuple2[Int, Int]]): Int = {		
		val r = scala.util.Random
		val uf = new UF(g.vertexCount)
		1 to (g.vertexCount - 2) foreach {_ =>
			val edge = nextEdge(r, uf, edges);
			uf.union(edge._1, edge._2);			
		}		
		return countNonSelfLoops(uf, edges);
	}		

	private[this] def countNonSelfLoops(uf: UF, edges: Array[Tuple2[Int, Int]]): Int = {
		edges.foldLeft(0)((sum, e) => sum + indicator(uf, e))
	}

	private[this] def indicator(uf: UF, e: Tuple2[Int, Int]): Int = {
		if (uf.connected(e._1, e._2)) 0; else 1;
	}

	private[this] def nextEdge(r: Random, uf: UF, edges: Array[Tuple2[Int, Int]]): Tuple2[Int, Int] = {
		while(true) {
			val edgeIndex = r.nextInt(edges.length);
			val edge = edges(edgeIndex);
			if (!uf.connected(edge._1, edge._2)) return edge;
		}
		throw new RuntimeException("Failed")
	}
}