
package main

import (
	"sort"	
)

const parallelismCutoff int = 30000

//Sort integer array in place. Function runs in n*log(n) time and n space.
func MergeSort(n []int) {
	if !sort.IntsAreSorted(n) {
		buffer := make([]int, len(n))
		copy(buffer, n)
		mergeSort(n, buffer)	
	}	
}

func mergeSort(toSort []int, buffer []int) {
	size := len(toSort)	
	if size < insertionSortCutoff {
		InsertionSort(toSort)
	} else {
		mid := size >> 1
		mergeSort(buffer[:mid], toSort[:mid])
		mergeSort(buffer[mid:], toSort[mid:])
		merge(buffer[:mid], buffer[mid:], toSort)
	}
}

func merge(left []int, right []int, buffer []int) {
	if left[len(left)-1] < right[0] { //optimized merge when alredy sorted
		copy(buffer, left)
		copy(buffer[len(left):], right)
		return
	}
	leftIdx, rightIdx, idx := 0, 0, 0
	for idx < len(buffer) {
		if left[leftIdx] < right[rightIdx] {
			buffer[idx] = left[leftIdx]
			leftIdx++
		} else {
			buffer[idx] = right[rightIdx]
			rightIdx++
		}
		idx++
		if leftIdx >= len(left) {
			copy(buffer[idx:], right[rightIdx:])
			return
		}
		if rightIdx >= len(right) {
			copy(buffer[idx:], left[leftIdx:])
			return
		}
	}
}

//Run insertion sort on given slice
func InsertionSort(toSort []int) {
	for i := 1; i < len(toSort); i++ {
		x := toSort[i]
		j := i
		for j > 0 && toSort[j-1] > x {
			toSort[j] = toSort[j-1]
			j--
		}
		toSort[j] = x
	}
}

func ParallelSort(n []int) {	
	buffer := make([]int, len(n))
	copy(buffer, n)
	parallelMergeSort(n, buffer, nil)
}

func parallelMergeSort(toSort []int, buffer []int, done chan bool) {	
	size := len(toSort)	
	if (size < parallelismCutoff) {
		MergeSort(toSort)
		if (done != nil) {
			done <- true
		}		
	} else {
		mid := size >> 1
		childDone := make(chan bool)
		go parallelMergeSort(buffer[:mid], toSort[:mid], childDone)
		parallelMergeSort(buffer[mid:], toSort[mid:], nil)
		<- childDone
		merge(buffer[:mid], buffer[mid:], toSort)	
		if (done != nil) {
			done <- true
		}
	}
}