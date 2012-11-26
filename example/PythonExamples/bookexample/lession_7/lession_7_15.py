# coding: utf-8
'''
Created on 2011-12-29

@author: Lv9
'''
from abc import ABCMeta, abstractmethod, abstractproperty;

#Python定义抽象类的方式
#定义抽象方法和属性可以强制子类必须实现
#通过metaclass指定元类
class Foo(metaclass=ABCMeta):
    @abstractmethod
    def spam(self, a, b):
        pass;

    @abstractproperty
    def name(self):
        pass;

class Bar(Foo):
    def spam(self, a, b):
        pass;

# 由于是抽象类 所以不能直接实例化
# f = Foo();
# 当抽象类与某个类没有实际的继承关系的时候 可以通过register将某个类注册 使其成为抽象类的子类
# print(issubclass(Bar, Foo));
# Foo.register(Bar);
# print(issubclass(Bar, Foo));
print(type(Foo));
