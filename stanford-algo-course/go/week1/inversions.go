package main

import (
	"fmt"
	"golibs/algocourse"
	"sort"	
)

const insertionSortCutoff int = 32

//Sort integer array in place. Function runs in n*log(n) time and n space.
//Returns number of inversions in input array
func MergeSortCountingInversions(n []int) int {
	if sort.IntsAreSorted(n) {
		return 0
	}
	buffer := make([]int, len(n))
	copy(buffer, n)
	return invMergeSort(n, buffer)
}

func invMergeSort(toSort []int, buffer []int) int {
	size := len(toSort)	
	if size < insertionSortCutoff {
		return InsertionSortCountingInversions(toSort)
	}
	mid := size >> 1
	left_inv := invMergeSort(buffer[:mid], toSort[:mid])
	right_inv := invMergeSort(buffer[mid:], toSort[mid:])
	split_inv := invMerge(buffer[:mid], buffer[mid:], toSort)
	return left_inv + right_inv + split_inv
}

func invMerge(left []int, right []int, buffer []int) int {
	if left[len(left)-1] < right[0] { //optimized merge when alredy sorted
		copy(buffer, left)
		copy(buffer[len(left):], right)
		return 0
	}
	leftIdx, rightIdx, idx := 0, 0, 0
	inversions := 0
	for idx < len(buffer) {
		if left[leftIdx] < right[rightIdx] {
			buffer[idx] = left[leftIdx]
			leftIdx++
		} else {
			buffer[idx] = right[rightIdx]
			rightIdx++
			inversions += len(left) - leftIdx
		}
		idx++
		if leftIdx >= len(left) {
			copy(buffer[idx:], right[rightIdx:])
			break
		}
		if rightIdx >= len(right) {
			copy(buffer[idx:], left[leftIdx:])
			break
		}
	}
	return inversions
}

//Run insertion sort on given slice, returning number of inversions
func InsertionSortCountingInversions(toSort []int) int {
	inversions := 0
	for i := 1; i < len(toSort); i++ {
		x := toSort[i]
		j := i
		for j > 0 && toSort[j-1] > x {
			toSort[j] = toSort[j-1]
			inversions++
			j--
		}
		toSort[j] = x

	}
	return inversions
}

func main() {
	numbers, _ := algocourse.ParseFileWithIntsOnEveryLine("IntegerArray.txt")
	nInversions := MergeSortCountingInversions(numbers)
	fmt.Println("Inversions: ", nInversions)
}
