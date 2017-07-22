import week3._
import org.scalameter.api._
import java.io.File

object BenchmarkMinCut extends PerformanceTest.Quickbenchmark {
  val ex3 = Graph(new File("graphs/kargerMinCut.txt"))  
  val graphs = Gen.single("Graph")((ex3, ex3.edges())) 

  
  performance of "MinCut" in {
      using(graphs) in {
      	pair => new MinCut(pair._1).minCutOneRound(pair._2)
      }
  }
}