package main

import (
	f "fmt"
)

func main() {
	result := proxy(func(i int) string {
		return string(10)
	}, 10)
	f.Println(result)
	 var i interface{} = 10;
	 t := i.(int);
	 f.Println(t);
}

/**传递匿名函数*/
func proxy(target func(i int) string, i int) string {
	f.Println("before")
	return target(i)
}
