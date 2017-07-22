package main

import (
	"golibs/algocourse"
	"sort"
	"testing"
)

type pivotToExpectedCmps struct {
	name string
	ps   pivotSelector
	cmps int
}

type teststruct struct {
	input []int
	cases []pivotToExpectedCmps
}

var tests = []teststruct{
	{[]int{3, 1, 2}, []pivotToExpectedCmps{
		{"tiny1", Ex1, 3},
		{"tiny2", Ex2, 2},
		{"tiny2", Ex3, 2},
	}},
	{[]int{3, 9, 8, 4, 6, 10, 2, 5, 7, 1}, []pivotToExpectedCmps{
		{"forum10_1", Ex1, 25},
		{"forum10_2", Ex2, 29},
		{"forum10_3", Ex3, 21},
	}},
	{[]int{57, 97, 17, 31, 54, 98, 87, 27, 89, 81, 18, 70, 3, 34, 63, 100, 46, 30, 99, 10, 33, 65, 96, 38, 48, 80, 95, 6, 16, 19, 56, 61, 1, 47, 12, 73, 49, 41, 37, 40, 59, 67, 93, 26, 75, 44, 58, 66, 8, 55, 94, 74, 83, 7, 15, 86, 42, 50, 5, 22, 90, 13, 69, 53, 43, 24, 92, 51, 23, 39, 78, 85, 4, 25, 52, 36, 60, 68, 9, 64, 79, 14, 45, 2, 77, 84, 11, 71, 35, 72, 28, 76, 82, 88, 32, 21, 20, 91, 62, 29}, []pivotToExpectedCmps{
		{"forum100_1", Ex1, 615},
		{"forum100_2", Ex2, 587},
		{"forum100_3", Ex3, 518},
	}},
	{readFromFile(), []pivotToExpectedCmps{
		{"exercise1", Ex1, 162085},
		{"exercise2", Ex2, 164123},
		{"exercise3", Ex3, 138382},
	}},
}

func clone(input []int) []int {
	return append([]int(nil), input...)
}

func TestPivotSelector3(t *testing.T) {
	p := Ex3(tests[0].input)
	if p != 2 {
		t.Errorf("Expected to select last element, but was: %v", p)
	}
	p = Ex3(tests[1].input)
	if p != 0 {
		t.Errorf("Expected to select first element, but was: %v", p)
	}
}

func TestSortingAndComparisonCount(t *testing.T) {
	for _, test := range tests {
		for _, c := range test.cases {
			input := clone(test.input)
			comparisons := Quicksort(input, c.ps)
			if !sort.IntsAreSorted(input) {
				t.Errorf("Expected array to be sorted, but it was not for %s, details: %v", c.name, input)
			}
			if c.cmps != comparisons {
				t.Errorf("Expected %d comparisons but was %d", c.cmps, comparisons)
			}
		}
	}
}

func readFromFile() []int {
	numbers, err := algocourse.ParseFileWithIntsOnEveryLine("QuickSort.txt")
	if err != nil {
		panic("Failed to read file: " + err.Error())
	}
	return numbers
}

func BenchmarkOnRealSetPvt1(b *testing.B) {
	numbers := readFromFile()
	b.ResetTimer()
	for i := 0; i < b.N; i++ {
		Quicksort(clone(numbers), Ex1)
	}
}

func BenchmarkOnRealSetPvt2(b *testing.B) {
	numbers := readFromFile()
	b.ResetTimer()
	for i := 0; i < b.N; i++ {
		Quicksort(clone(numbers), Ex2)
	}
}

func BenchmarkOnRealSetPvt3(b *testing.B) {
	numbers := readFromFile()
	b.ResetTimer()
	for i := 0; i < b.N; i++ {
		Quicksort(clone(numbers), Ex3)
	}
}

func BenchmarkGoEmbeddedSort(b *testing.B) {
	numbers := readFromFile()
	b.ResetTimer()
	for i := 0; i < b.N; i++ {
		sort.Ints(clone(numbers))
	}
}
