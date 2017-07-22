package main

import (
	"golibs/algocourse"
	"runtime"
	"sort"
	"testing"
)

func readFromFile() []int {
	numbers, err := algocourse.ParseFileWithIntsOnEveryLine("IntegerArray.txt")
	if err != nil {
		panic("Failed to read file: " + err.Error())
	}
	return numbers
}

type sortFunction struct {
	sort   func([]int) int
	isSlow bool
	name   string
}

type testpair struct {
	name               string
	input              []int
	expectedInversions int
}

func algos() []sortFunction {
	return []sortFunction{
		{MergeSortCountingInversions, false, "Merge Sort"},		
		{InsertionSortCountingInversions, true, "Insertion Sort"},
	}
}

func tests() []testpair {
	return []testpair{
		{"tiny", []int{3, 1, 2}, 2},
		{"lecture", []int{1, 3, 5, 2, 4, 6}, 3},
		{"large", []int{9, 12, 3, 1, 6, 8, 2, 5, 14, 13, 11, 7, 10, 4, 0}, 56},
		{"problem", readFromFile(), 2407905288},
	}
}

func TestInversionsAndVerifySorting(t *testing.T) {
	for _, algo := range algos() {
		for _, pair := range tests() {
			if algo.isSlow && len(pair.input) > 1000 {
				t.Skipf("Skipping test set of large size %d for %v", len(pair.input), algo.name)
			}
			inv := algo.sort(pair.input)
			if !sort.IntsAreSorted(pair.input) {
				t.Errorf("Expected array to be sorted, but it was not for %s example by %s", pair.name, algo.name)
			}
			if inv != pair.expectedInversions {
				t.Errorf("Expected %d inversions but got %d in %s example by %s", pair.expectedInversions, inv, pair.name, algo.name)
			}
		}
	}
}


func TestParallelSorting(t *testing.T) {	
	for _, pair := range tests() {		
		ParallelSort(pair.input)
		if !sort.IntsAreSorted(pair.input) {
			t.Errorf("Expected array to be sorted, but it was not for %s example by ParallelSort", pair.name)
		}		
	}	
}

func BenchmarkOnRealSet(b *testing.B) {
	numbers := readFromFile()
	b.ResetTimer()
	for i := 0; i < b.N; i++ {
		MergeSortCountingInversions(append([]int(nil), numbers...))
	}
}

func BenchmarkParallelOnRealSet(b *testing.B) {
	numbers := readFromFile()
	runtime.GOMAXPROCS(4)
	defer runtime.GOMAXPROCS(1)
	b.ResetTimer()
	for i := 0; i < b.N; i++ {
		ParallelSort(append([]int(nil), numbers...))
	}
}

func BenchmarkGoEmbeddedSort(b *testing.B) {
	numbers := readFromFile()
	b.ResetTimer()
	for i := 0; i < b.N; i++ {
		sort.Ints(append([]int(nil), numbers...))
	}
}
