# coding: utf-8
'''
Created on 2011-12-1

@author: Lv9
'''
#class关键字创造类,使用圆括号是Python指定继承的方式
class Stack(object):
    #__init__用于创建对象后初始化对象 self为对象的句柄(与Java中的this关键字差不多)
    def __init__(self):
        self.stack = [];
        
    #类定义中使用def语句定义了方法。每个方法中的第一个参数始终指向对象本身 根据约定该参数名称使用self 对象方法必须如此
    def push(self, o):
        self.stack.append(o);
    
    #可以用return关键字返回结果
    def pop(self):
        return self.stack.pop();

    #可以通过@staticmethod定义静态方法
    @staticmethod
    def test_staticmethod():
        pass;
    
    #关于参数的意义
    #在参数前面增加*有点类似Java中的可变参数(比如String ...arg) 这个参数的类型是个元组 而以**开头的则是一个字典类型 调用的时候可以指定这个参数的key 比如 foo(key=value) 这回导致这个字典类型的参数被put进一个键值对
    @staticmethod
    def test_staticmethod2(arg, *args, **kwargs):
        print(arg);
        print(args);
        print(kwargs);#字典类型 根据传递参数的方式
    
#也可以直接继承其他的东西 比如list
class Stack2(list):
    def push(self, o):
        self.stack.append(o);

items = [37, 42];
items.append(73);
for l in dir(items):
    print(l);

items = items.__add__([101, 127]);#在上面的函数中输出了指定对象拥有的函数 比如__add__(这个函数重载了'+'操作符 这行代码等于items + [101, 127])

print(items);
s = Stack();#创建一个对象
s.push(1);#调用对象方法

Stack.push(s, 2);#另外一种调用对象方法的方式.. 通过指定self达成这样的效果
Stack.test_staticmethod();#调用静态方法
Stack.test_staticmethod2('1', 1, 2, 3, 4, name='Lv9', age=20);
