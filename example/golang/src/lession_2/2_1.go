package main

import (
	"fmt"
	"math"
)

func main() {
	count, err := fmt.Println("Hello go") //获取打印的字数以及相应的error值
	count, _ = fmt.Println("Hello go")    //获取打印的字数 丢弃掉无用的值(通过_)
	_, err = fmt.Println("Hello go")      //获取打印的字数 丢弃掉无用的值(通过_)
	fmt.Println(count, err, "ok")

	const limit = 512        //常量 类型兼容任何数字
	const top uint16 = 1421  //常量 无符号16位整型
//	start := -19             //变量,推断类型 int
//	end := int64(9876543210) //变量 类型int64
//	var i int                //变量 值为0 类型int
//	var debug = false        //变量 推断类型 bool
//	checkResults := true     //变量 推断类型 bool
//	stepSize := 1.5          //变量 推断类型 float64
//	acronym := "FOSS"        //变量 推断类型 string
	var g int = 12;
	fmt.Println(g);
}

func Uint8FromInt(x int) (uint8, error) {
	if 0 <= x && x <= math.MaxUint8 {
		return uint8(x), nil
	}
	return 0, fmt.Errorf("%d is out of the uint 8 range", x)
}
