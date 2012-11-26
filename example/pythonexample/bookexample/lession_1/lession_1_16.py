# file : lession_1_15.py
# coding: utf-8
'''
Created on 2011-12-2

@author: Lv9
'''
import div #通过import创建一个新的命名空间,并在该命名空间中执行与.py文件相关的所有语句
import div as d #可以通过as 起别名
from div import divide;#也可以将具体的定义导入到当前的命名空间中(用from)
# from div import *;#要把素有定义导入到当前的命名空间中也可以这样

print(dir(div));#可以用dir扫描加载进来的命名空间
print(dir(d));

a, b = div.divide(33, 10);
a, b = d.divide(33, 10);
a, b = divide(33, 10);
print(a, b);
