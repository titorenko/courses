package main

import (
	"testing"
)

var testGraphs = []*Graph{
	readGraphFromFile("testCase1a.txt"),
	readGraphFromFile("testCase1b.txt"),
	readGraphFromFile("testCase2a.txt"),
	readGraphFromFile("testCase2b.txt"),
	readGraphFromFile("kargerMinCut.txt"),
}

type dimensions struct {
	v int
	e int
}

var expectedDimensions = []dimensions{
	{8, 14},
	{8, 14},
	{8, 13},
	{8, 13},
	{200, 2517},
}

var expectedMinCuts = []int{2, 2, 1, 1, 17}

func TestGraphsValid(t *testing.T) {
	for idx, g := range testGraphs {
		if !g.isValidUndirectedGraph() {
			t.Errorf("Expected undirected graph, but graph %d was not", idx)
		}
	}
}

func TestGraphDimensions(t *testing.T) {
	for idx, g := range testGraphs {
		if g.edgeCount() != expectedDimensions[idx].e {
			t.Errorf("Expected %d edges but got %d for graph %d", expectedDimensions[idx].e, g.edgeCount(), idx)
		}

		if g.vertexCount() != expectedDimensions[idx].v {
			t.Errorf("Expected %d vertices but got %d for graph %d", expectedDimensions[idx].v, g.vertexCount(), idx)
		}
	}
}

/*func TestRemoveEdge(t *testing.T) {
	g := testGraphs[0].clone()
	if !g.hasConnection(3, 4) || !g.hasConnection(4, 3) {
		t.Error("Unexpected initial state")
	}
	g.removeEdge(3, 4)
	if g.edgeCount() != expectedDimensions[0].e-1 {
		t.Error("Unexpected edge count after removal")
	}
	if !g.isValidUndirectedGraph() {
		t.Error("Invalid graph after edge removal")
	}
	if g.hasConnection(3, 4) || g.hasConnection(4, 3) {
		t.Error("Unexpected final state")
	}
}

func TestAddEdge(t *testing.T) {
	g := testGraphs[0].clone()
	if g.hasConnection(0, 4) || g.hasConnection(4, 0) {
		t.Error("Unexpected initial state")
	}
	g.addEdge(0, 4)
	if g.edgeCount() != expectedDimensions[0].e+1 {
		t.Errorf("Unexpected edge count after addition: %d", g.edgeCount())
	}
	if !g.isValidUndirectedGraph() {
		t.Error("Invalid graph after edge removal")
	}
	if !g.hasConnection(0, 4) || !g.hasConnection(4, 0) {
		t.Error("Unexpected final state")
	}
}

func TestCollapse(t *testing.T) {
	g := testGraphs[0].clone()
	g.collapse(1, 2)
	if !g.isValidUndirectedGraph() {
		t.Error("Invalid graph after vertex collapse")
	}
	if g.edgeCount() != expectedDimensions[0].e {
		t.Error("Unexpected edge count afterafter vertex collapse")
	}
}*/

func TestMinCuts(t *testing.T) {
	for idx, g := range testGraphs {
		if idx == 4 {
			t.Skip("Skipping large example")
		}
		mc := g.MinCut()
		if mc != expectedMinCuts[idx] {
			t.Errorf("Expected min cut %d but was %d for test %d", expectedMinCuts[idx], mc, idx)
		}
	}
}
