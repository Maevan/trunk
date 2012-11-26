# coding: utf-8
'''
Created on 2011-12-12

@author: Lv9
'''
from functools import partial;

def foo(x, y, s):
    return x + y + s;

f = partial(foo, 1, 2);#为foo的参数x和y提供值 这样做可以生成一个新的函数对象 这个函数对象只需要一个参数 就是尚未指定的s 比如在某一次运算中 x 和 y是固定的 只有s不固定 就可以利用这个函数将原始方法生成一个新的方法 x,y都写死 只留一个s传入
print(f(3));#为s提供值
