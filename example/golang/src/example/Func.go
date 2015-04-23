package example

import (
	"fmt"
)

type Wrapper func(i, j int)

func (w Wrapper) execute() {
	fmt.Println("After")
}

func plus(i, j int) {
	fmt.Println(i + j)
}
