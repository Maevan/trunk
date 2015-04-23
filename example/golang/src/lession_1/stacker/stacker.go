package main

import (
	"fmt"
	"lession_1/stacker/stack" //导入文件 实际包名是在下面编码的时候使用的 文件名与包名无任何关系
)

func main() {
	stack := stack.Stack{}
	stack.Push("hay")
	stack.Push(-15)
	stack.Push([]string{"pin", "clip"})
	stack.Push(81.52)
	fmt.Println(stack.Len())
	fmt.Println(stack.Len())
	for {
		item, err := stack.Pop()
		if err != nil {
			break
		}

		fmt.Println(item)
	}
}
