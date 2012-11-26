#coding=utf-8
'''
Created on 2012-5-4

@author: zhaojp
'''

from re import compile;
from re import escape;

c = compile('\d');#将正则表达式模式字符串编译为正则表达式对象

print(escape('a1_.;'));#将指定字符串中全部非字母数字字符带有反斜杠
