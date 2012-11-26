# coding: utf-8
'''
Created on 2011-12-19

@author: Lv9
'''
#lambda的首要用途是指定短小的回调函数
#lambda中只能定义一条表达式 不能出现多条语句和其他非表达式语句 如for while
func = lambda x, y : x + y;

print(func(1, 1));
