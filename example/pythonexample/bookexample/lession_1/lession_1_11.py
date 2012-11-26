# coding: utf-8
'''
Created on 2011-12-1

@author: Lv9
'''
'''
   首先要说的是python中的除法运算,在python 2.5版本中存在两种除法运算,即所谓的true除法和floor除法.当使用x/y形式进行除法运算时,如果x和y都是整形,那么会对
   结果进行截取,取运算的整数部分,比如2/3的运算结果是0；如果x和y中有一个是浮点数,那么会进行所谓的true除法,比如2.0/3的结果是0.66666666666666663.另外一种
   除法是采用x//y的形式,那么这里采用的是所谓floor除法,即得到不大于结果的最大整数值,这个运算时与操作数无关的.比如2//3的结果是0,-2//3的结果是-1,-2.0//3
   的结果是-1.0.在未来的python 3.0中,x/y将只执行true除法,而与操作数无关；x//y则执行floor除法.如果需要在2.5版本的python中进行这样的用法,则需要在代码前
   加入from __future__ import division的声明.如： 
   from __future__ import division
    a=2/3
   这时变量a的结果将是0.66666666666666663，而不是原来的3了。 
'''

def remainder(a, b):
    q = a // b; # 截断除法运算符(得到不大于结果的最大整数值 floor除法)
    r = a - q * b;#余数 PS.在Java里的求余运算符在这里貌似有别的用处
    return r;

#也可以返回元组类型
def divide(a, b):
    q = a // b;#如果a和b是整数,q就是整数
    r = a - q * b;
    return (q, r);#商,余数

#也可以通过这种方式给函数参数提供一个默认值 如timeout 调用此函数的时候可以省略该参数,省略时将使用定义的默认值
def connect(host, port, timeout=300):
    print(host, port, timeout);

#函数内部的变量的作用域是局部的 函数运行结束后会被马上销毁 要在函数内部修改某个全局变量的值 可以使用global语句
def foo():
    global count;#count必须是一个已经存在的全局变量 如果不存在的话 这个操作会提示异常
    count += 1;

count = 0;

print(remainder(37, 15));#调用函数的方式
quotient, remainder = divide(1456, 33);#使用元组返回多个值的时候可以很容易的将结果放到单独的变量中
print(quotient, remainder);
foo();
print(count);
connect('www.baidu.com', 80);#不指定timeout
connect('www.baidu.com', 80, 600);#指定timeout 
