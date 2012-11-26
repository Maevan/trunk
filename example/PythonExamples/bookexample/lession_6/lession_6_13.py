# coding: utf-8
'''
Created on 2011-12-19

@author: Lv9
'''
import sys

#递归
#Python对递归深度做了限制默认是1K 可以修改这个值 但是程序仍然受限于主机操作系统使用的栈大小限制
#在存在yield语句的地方无法使用递归
#还要当心混合使用递归函数和装饰器的问题 如果递归函数使用装饰器 所有内部的递归调用都会通过装饰后的版本进行 如果使用装饰器的目的是进行一些系统管理 如同步或锁定 最好不要使用递归

def factorial(n):
    if n <= 1 :return 1;
    else:return n * factorial(n - 1);

print(sys.getrecursionlimit()); #获得当前程序限制的递归深度
sys.setrecursionlimit(5);#设置当前程序的递归深度

try:
    print(factorial(3));#正常运行递归结果
    print(factorial(4));#超出递归深度的结果 发生异常
except RuntimeError as e:
    print(e);
