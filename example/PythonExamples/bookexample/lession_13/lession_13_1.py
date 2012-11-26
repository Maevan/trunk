#coding= utf-8
'''
Created on 2012-3-4

@author: Lv9
'''
import atexit;

func = lambda :print('Hello World');

atexit.register(func);#通过register注册一个在程序推出的时候执行的函数
atexit.unregister(func);#也可以用这个取消注册的函数
