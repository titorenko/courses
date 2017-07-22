package main

/*
import (
	"math"
	"math/rand"
	"time"
)

func (g *Graph) MinCut() int {
	return g.minCut(math.Log(float64(g.vertexCount())))
}

func (g *Graph) minCut(confidence float64) int {
	min := g.edgeCount()
	n := float64(g.vertexCount())
	nIterations := int(n * n * confidence)
	edges := g.getEdges()
	for i := 0; i < nIterations; i++ {
		candidate := g.minCutOneRound(edges)
		if candidate < min {
			min = candidate
		}
	}
	return min
}

func (g *Graph) minCutOneRound(edges []Edge) int {
	rand.Seed(time.Now().UTC().UnixNano())
	uf := NewUF(g.vertexCount())
	for i := 0; i < g.vertexCount()-2; i++ {
		edge := nextEdge(uf, edges)
		uf.Union(edge.v1, edge.v2)
	}
	return countNonSelfLoops(uf, edges)
}

func countNonSelfLoops(uf *UF, edges []Edge) int {
	count := 0
	for _, e := range edges {
		if !uf.Connected(e.v1, e.v2) {
			count++
		}
	}
	return count
}

func nextEdge(uf *UF, edges []Edge) Edge {
	for {
		edgeIndex := rand.Intn(len(edges))
		edge := edges[edgeIndex]
		if !uf.Connected(edge.v1, edge.v2) {
			return edge
		}
	}
}
*/
