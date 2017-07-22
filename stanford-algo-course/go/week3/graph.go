package main

import (
	"bytes"
	"fmt"
	"golibs/algocourse"
	"math"
	"math/rand"
	"strconv"
	"time"
)

type UF struct {
	id   []int  // id[i] = parent of i
	rank []byte // rank[i] = rank of subtree rooted at i (cannot be more than int bit size)
}

func NewUF(size int) *UF {
	result := new(UF)
	result.rank = make([]byte, size)
	result.id = make([]int, size)
	for i, _ := range result.rank {
		result.id[i] = i
	}
	return result
}

func (this *UF) Connected(p int, q int) bool {
	return this.Find(p) == this.Find(q)
}

func (this *UF) Find(p int) int {
	for p != this.id[p] {
		this.id[p] = this.id[this.id[p]] // path compression by halving
		p = this.id[p]
	}
	return p
}

func (this *UF) Union(p int, q int) {
	i := this.Find(p)
	j := this.Find(q)
	if i == j {
		return
	} else if this.rank[i] < this.rank[j] { // make root of smaller rank point to root of larger rank
		this.id[i] = j
	} else if this.rank[i] > this.rank[j] {
		this.id[j] = i
	} else {
		this.id[j] = i
		this.rank[i]++
	}
}

type Graph struct {
	adj [][]int
}

type Edge struct {
	v1 int
	v2 int
}

func (g *Graph) vertexCount() int {
	return len(g.adj)
}

func (g *Graph) edgeCount() int {
	result := 0
	for v := 0; v < g.vertexCount(); v++ {
		result += len(g.adj[v])
	}
	return result / 2
}

func (g *Graph) getEdges() []Edge {
	r := make([]Edge, g.edgeCount())
	i := 0
	for v1, adjList := range g.adj {
		for _, v2 := range adjList {
			if v1 < v2 {
				r[i] = Edge{v1, v2}
				i++
			}
		}
	}
	return r
}

func (g *Graph) hasConnection(v1 int, v2 int) bool {
	for _, v := range g.adj[v1] {
		if v == v2 {
			return true
		}
	}
	return false
}

func (g *Graph) isValidUndirectedGraph() bool {
	for v, edges := range g.adj {
		for _, e := range edges {
			if !g.hasConnection(e, v) {
				return false
			}
		}
	}
	return true
}

func readGraphFromFile(fileName string) *Graph {
	g := new(Graph)
	input, err := algocourse.ParseFileWithIntArraysOnEveryLine(fileName)
	if err != nil {
		panic("Failed to read file: " + err.Error())
	}
	g.adj = make([][]int, len(input))
	for idx, row := range input {
		g.adj[idx] = sub1(row[1:])
	}
	return g
}

func sub1(input []int) []int {
	for idx, i := range input {
		input[idx] = i - 1
	}
	return input
}

func (g *Graph) toDotNotation() string {
	var buffer bytes.Buffer
	buffer.WriteString("graph G {\n")
	for v, edges := range g.adj {
		buffer.WriteString(strconv.Itoa(v) + " -- {")
		for _, e := range edges {
			if v < e {
				buffer.WriteString(strconv.Itoa(e))
				buffer.WriteString(" ")
			}
		}
		buffer.WriteString("}\n")
	}
	buffer.WriteString("}\n")
	return buffer.String()
}

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

func main() {
	g := readGraphFromFile("kargerMinCut.txt")
	fmt.Println("Start: ", time.Now().UTC())
	fmt.Println(g.MinCut())
	fmt.Println("Finish: ", time.Now().UTC())
}
