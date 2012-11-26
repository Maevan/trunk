# coding: utf-8
'''
Created on 2011-11-28

@author: Lv9
'''

a = "Hello World";
b = "Python is groovy";
c = """
Content-type:text/html
<h1>Hello World</h1>
""";#字符串前后使用的引号必须是对应匹配的(三个"号或者'号的字符串内容中可以换行 巨汗..)

x = "37";
y = "42";

print("\"");#传统转义符也好使ORz..
print(a, b, c, sep='\n');

#x:y 这种形式的符号叫切片运算符 能够表达某个范围
print(a[4]);#获取字符串中位置4的字节
print(a[4:]);#获取位置4以及以后的字节
print(a[:4]);#获取位置4之前的字节(不包括位置4)
print(a[6:11]);#获取位置6到11的字节

print(a + ' This is test');#通过+号连接字符串
print(x + y);#Python是强类型语言 不会把字符串的值解释为数值数据

x = int(x) + int(y);#可以通过int()转换为整型进行处理)

print(x);

print("The value of x is " + str(x));#需要用指定函数将数值型转换为字符型 这里跟Java不同的是 数值与字符串相加的时候 不会被转换为字符串与字符串相加
print("The value of x is " + repr(x));#repr()与str()的区别是他能更精确的将表示被转换对象的精确值
print("The value of x is " + format(x, "4d"));#可以通过format将一个整数转换为成指定格式的字符串输出
