package main

import (
	"fmt"
)

func main() {
	elements := []int{1, 3, 5, 7, 9, 2, 4, 6, 8, 10}
	fmt.Println("To Sort", elements)
	fastsort(elements, 0, len(elements)-1)
	fmt.Println("Sorted", elements)
}

func fastsort(elements []int, start int, end int) {
	l := start
	r := end

	for l < r {
		for l < r && elements[l] >= elements[r] {
			l++
		}
		if l < r {
			swap(elements, l, r)
		}
		for l < r && elements[l] > elements[r] {
			r--
		}
		if l < r {
			swap(elements, l, r)
		}
	}

	if start < l {
		fastsort(elements, start, l-1)
	}
	if r < end {
		fastsort(elements, r+1, end)
	}
}

func swap(elements []int, l int, r int) {
	swap := elements[l]
	elements[l] = elements[r]
	elements[r] = swap
}
