package week3

import java.io.File

object Runner extends App {
	val g = Graph(new File("graphs/kargerMinCut.txt"))
	println("Min cun for g is "+new MinCut(g).calculate)
}