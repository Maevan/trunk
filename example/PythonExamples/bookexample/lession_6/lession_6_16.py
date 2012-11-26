# coding: utf-8
'''
Created on 2011-12-19

@author: Lv9
'''

# eval(str[,globals[,locals]]) 函数执行一个表达式字符串并返回结果(只能执行表达式)
g = {
           'x' : 10,
           'y' : 20,
           'z' : [1, 2, 3, 4]
    };
l = {};
print(eval('1 + 2'));#执行这个字符串内的表达式
print(eval('1 + 2 + x', g));#也可以提供一些全局变量

# print(eval('1 + 2 + x;print(y)', g)); 只能提供一个表达式 否则会报错

# exec(str[,globals[,locals]]) 函数执行一个包含任意Python代码的字符串

print(exec('for i in range(x):print(i,end=" ");', g, l));#可以通过传入一个字典来保存程序运行中使用过的局部变量(locals)
print(l);#显示exec使用过的局部变量

# 给exec或eval函数传递字符串时 解析器首先回把这个字符串编译为字节码 因为这个过程十分耗资源 如果代码要反复执行多次 最好是预编译代码 然后在后续的调用中重用字节码
# compile(str,filename,kind)) 函数将代码字符串编译为字节码 其中str是包含要编译代码的字符串 而filename是定义该字符串的文件(在跟踪生成时用) kind参数指定了要编译代码的类型 single代表一条语句 exec代表一组语句 而eval代表一个表达式 还可以将compile函数返回的代码对象传递给eval函数和exec语句

s = "for i in range(0,10):print(i,end=' ')";
s2 = "3 * x + 4 * y";
c = compile(s, '', 'exec');#编译exec
exec(c);#执行exec
c = compile(s2, '', 'eval');#编译eval
exec(c, g);#执行eval并且提供环境参数
