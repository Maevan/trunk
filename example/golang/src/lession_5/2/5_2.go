package main

import (
	"fmt"
)

func main() {
	if i := 10; i < 9 { //optionalStatement1;booleanExpression1 前面的赋值条件是可选的 如果进行赋值 指定变量的作用域是在紧跟着的if else if else代码块里
		fmt.Println("If Test")
	} else if y := 20; y > 9 {
		fmt.Println("Else If Test")
	} else {
		fmt.Println("Else Test")
	}
	i := 3
	switch i { //go中的switch在每个case分支默认携带case的
	case 1:
		fmt.Println("Case 1")
	case 2:
		fmt.Println("Case 2")
	case 3:
		fallthrough //类似Java中的空case块 可以直接跳到下个分支
	case 4:
		fallthrough
	case 5:
		fmt.Println("Case 3 4 5")
	case 6:
		fmt.Println("Case 6")
	default:
		fmt.Println("Default")
	}
	switch { //也可以做这种普通条件的分支
	case i > 0:
		fmt.Println("i is Greater than 0")
	case i < 0:
		fmt.Println("i is Less than 0")
	}
	return
}
