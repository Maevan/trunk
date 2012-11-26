# coding: utf-8
'''
Created on 2011-12-2

@author: Lv9
'''

print(r'\\');#在字符串前加r或R 可以使字符串变成原始字符串
print('\\');

def foo():
    '''This is a foo method'''#如果模块 类 函数的第一行定义的是一个字符串 那么这个字符串就是相关对象的文档字符串
    
print(foo.__doc__);#

i = 1 + 3j
