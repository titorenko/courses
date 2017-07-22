package week3

import java.io.File
import scala.io.Source

/**
Non-directed mutable graph wiht parallel edges
*/
class Graph(val adj : Array[Array[Int]]) {	

	def vertexCount = adj.size
	def edgeCount = adj.foldLeft(0)(_ + _.size) >> 1

	def edges(): Array[Tuple2[Int, Int]] = {
		val result = new Array[Tuple2[Int, Int]](edgeCount)
		var i = 0;
		0 until adj.length foreach { v1 =>
			0 until adj(v1).length foreach { j=>
				val v2 = adj(v1)(j)
				if (v1 < v2) {
					result(i) = (v1, v2)
					i = i + 1
				}
			}
		}
		return result;
	}



}

object Graph {
	def apply(f: File): Graph = {
		new Graph(Source.fromFile(f).getLines.map(parseLine).toArray)
	}

	private[this] def parseLine(line: String): Array[Int] = {		
		Array(line.split("\t").tail.map(_.toInt - 1) : _*)
	}
}