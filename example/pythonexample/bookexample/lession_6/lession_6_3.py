# coding: utf-8
'''
Created on 2011-12-14

@author: Lv9
'''

a = 100;

def foo():
    a = 14;#访问的局部变量的a 而非全局变量的

def bar():
    global a;#在函数内部定义一个引用 引向全局变量
    a += 1;

def kar():
    #Python支持嵌套函数
    a = 20;
    def karinner():
        nonlocal a;#nonlocal不会把名称绑定到任意函数中定义的局部变量 而是搜索当前调用栈中的下一层函数定义 即(动态作用域) PS.如果不使用这个修饰符 是可以访问到外围的属性 但是无法进行更改
        a += 1;
        pass;
    karinner();
    return a;

#闭包(将函数与函数运行的环境打包到一起称之为闭包)
def closures():
    n = 20;
    def inner():
        nonlocal n;
        n += 1;
        return n;
    
    return inner;

foo();
print(a);

bar();
print(a);

print(kar());

f = closures();
print(f());
print(f());
