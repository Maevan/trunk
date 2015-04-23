package main

import (
	"fmt"
)

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
