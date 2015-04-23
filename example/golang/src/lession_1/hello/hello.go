package main

import (
	"fmt"
	"os"
	"strings"
)

func main() {
	who := "World!"
	if len(os.Args) > 1 {
		who = strings.Join(os.Args[1:], " ")
	}

	fmt.Println("Hello", who)
	fmt.Println("Fuck")
	var newstr1 interface{}
	newstr1, newstr := foo()
	fmt.Println("dddd", newstr1, newstr)
	var object interface{}
	object = Bar{}
	fmt.Println(object.(Bar).test());
var i int8 = 1
fmt.Print(i)
}

type Bar struct{}

func (target Bar) test() string{
	return"test"
}
func foo() (string, string) {
	return "1", "2"
}
