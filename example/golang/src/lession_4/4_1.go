package main

import (
	"fmt"
)

func main() {
	var i int = 10
	var j int = 20
	var product int
	i_ref := &i         //&除了作为与操作符 还可以作为地址操作符
	fmt.Println(*i_ref) //*除了作为乘法操作符 还可以做解引用 他能获取指定地址的引用值 以及更改指定地址的值
	fmt.Println(i_ref)  //打印地址
	fmt.Println(i, j, product)
	swapAndProduct1(&i, &j, &product)
	fmt.Println(i, j, product)
	i, j, product = swapAndProduct2(i, j, product)
	fmt.Println(i, j, product)
	Bob := composer{"Bob", 1972} //composer类型值
	Tony := new(composer)        //composer类型指针
	Tony.name, Tony.birthYear = "Tony", 1985
	Julia := &composer{} //composer类型指针
	Julia.name, Julia.birthYear = "Julia", 1991
	Augusta := &composer{"Augusta", 1995} //composer类型指针
	fmt.Println(Bob, Tony, Julia, Augusta)
	Bob.birthYear = 1973
	fmt.Println(Bob, Tony, Julia, Augusta)
}

func swapAndProduct1(i, j, product *int) {
	*i, *j = *j, *i
	*product = *i * *j
}

func swapAndProduct2(i, j, product int) (int, int, int) {
	return j, i, j * i
}

type composer struct {
	name      string
	birthYear int
}
