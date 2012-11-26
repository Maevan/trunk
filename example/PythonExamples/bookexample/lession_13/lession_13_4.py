#coding=utf-8
'''
Created on 2012-3-8

@author: zhaojp
'''
import inspect;

def test(arg1, arg2):
    '''
                测试
    '''
    frame = inspect.currentframe();#获得当前运行帧
    print(inspect.getargvalues(frame));#通过切片获得切片所在函数的参数
    print(inspect.getframeinfo(frame));#获得切片的详细信息
    print(inspect.getouterframes(frame));#获得切片的详细信息

test(1, 2);
print(inspect.getargspec(test));#获取某个函数的参数列表
print(inspect.getdoc(test));#获取指定元素的注释
print(inspect.getmodule(test));#获取定义指定元素的模块
print(inspect.getmoduleinfo(inspect.getmodule(test).__file__));#返回指定PATH的模块信息 如果不是模块则返回None
print(inspect.getmodulename(inspect.getmodule(test).__file__));#返回指定PATH的模块名 如果不是模块则返回None
print(inspect.getsourcefile(test));#返回定义了此元素的原文件名
print(inspect.getsource(test));#返回定义了此元素的源代码