package main

import (
	"errors"
	"fmt"
)

type StringSlice []string

func main() {
	i := []int{1, 2, 3, 4, 5}
	j := []int{6, 7, 8, 9, 10}
	n := append(i, 1) //i的容量足够的时候追加元素 否则重新创建一个切面
	fmt.Println(i, j, n)
	copy(i, j)
	fmt.Println(i, j, n)
	fmt.Println(len(i))

	for _, value := range i {
		fmt.Print(value)
	}
	fmt.Println()
	fmt.Println(shadow())
	fancy := StringSlice{"Lithium", "Sodium", "Potassium", "Rubidium"}
	fmt.Println(fancy)
	fmt.Println([]string(fancy)) //强制转型
	var g interface{} = fancy
	thetype, result := g.([]string) //类型断言 返回(被指定类型的验证参数,是否为指定类型) 如果不接受第二个结果 当参数并非指定类型时 则会发生异常
	fmt.Println(thetype, result)
	thetype, result = g.(StringSlice) //貌似没办法甄别类型之间的父类子类关系
	fmt.Println(thetype, result)
}

func shadow() (err error) { //已经给返回参数命名 函数作用域中默认存在err变量 return的时候默认返回err变量
	err = errors.New("An Error") //
	//	{
	//		err := errors.New("A Other Error") //影子变量
	//		return
	//	}
	return
}
