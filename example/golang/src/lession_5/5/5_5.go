package main

import (
	"fmt"
)

func main() {
	fmt.Println("准备抛出异常")
	aPanic()
}

func aPanic() int {
	defer destory() // 保证程序执行结束后必然会被执行 类似finally 无论是否发生异常都会被执行 可以在其中通过recover()函数拦截到异常并处理
	panic("异常")     //类似Java中的异常
}
func destory() {
	e := recover() //捕捉异常 将一个异常转换为一个err对象
	if e != nil {
		fmt.Println("捕捉到异常", e)
	}
	fmt.Println("Destory")
}
