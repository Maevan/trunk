# coding: utf-8
'''
Created on 2011-12-19

@author: Lv9
'''

#可以给一个函数添加多个属性 被赋予的属性可以在函数对象的__dict__属性中获得
def foo():
    if 'name' in foo.__dict__:
        print(foo.__dict__['name']);

foo.name = 'Lv9';

foo();

#和文档字符串一样 也要注意混合使用函数属性和装饰器的问题 当你向被装饰的函数上设置属性时 实际上是在给装饰器函数设置属性
