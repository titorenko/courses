package main

import (
	"fmt"
	"golibs/algocourse"
	"sort"
)

type pivotSelector func(input sort.IntSlice) int

var (
	Ex1 pivotSelector = func(input sort.IntSlice) int {
		return 0
	}
	Ex2 pivotSelector = func(input sort.IntSlice) int {
		return input.Len() - 1
	}
	Ex3 pivotSelector = func(input sort.IntSlice) int {
		last := input.Len() - 1 
		a, b, c := input[0], input[last / 2], input[last]		
		if (a <= b) {
			if (b <= c) {
				return last / 2
			} else if (c <= a) {
				return 0
			} 
		} else {
			if (c <= b) {
				return last / 2
			} else if (a <= c) {
				return 0
			}
		}
		return last		
	}	
)

//Be careful to use same partioning as in lecture
//and return total number of comparisons
func Quicksort(input sort.IntSlice, ps pivotSelector) int {
	if input.Len() < 2 {
		return 0
	}	
	input.Swap(0, ps(input))
	i := 1
	for j := 1; j < input.Len(); j++ {
		if input.Less(j, 0) {
			input.Swap(i, j)
			i++
		}
	}
	input.Swap(0, i-1)
	comparisons := input.Len() - 1
	comparisons += Quicksort(input[:i-1], ps)
	comparisons += Quicksort(input[i:], ps)
	return comparisons
}

func main() {
	numbers, _ := algocourse.ParseFileWithIntsOnEveryLine("QuickSort.txt")
	fmt.Println("Comparisons: ", Quicksort(numbers, Ex1))
}
