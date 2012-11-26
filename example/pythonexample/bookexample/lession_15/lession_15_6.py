#coding=utf-8
'''
Created on 2012-4-24

@author: zhaojp
'''
from functools import partial;
from functools import reduce;

def say(message, to):
    print(message, to);

def plus(i1, i2):
    return i1 + i2;

#创建一个类似函数的对象partial 当调用该对象时会使用位置参数args关键字参数kwargs和任何附加位置或关键字参数来调用function 例如

tolv9 = partial(say, to="lv9");
tolv9("Hello World!");

sayhelloworld = partial(say, "Hello World!");

sayhelloworld("lv9");
sayhelloworld("lv15");

print(tolv9.func);#调用partial对象时调用的函数
print(sayhelloworld.args);#一个元组 包含在调用partial对象时提供给它的最左边的位置参数
print(tolv9.keywords);#一个字典 包含在调用partial对象时提供给它的关键字参数

#reduce 向可迭代的items中的项目渐增的应用函数function 并返回一个值 function必须接受两个参数 并首先应用到items中的前两项 然后将此结果以类似方式与items中的后续元素相
#组合 一次与一个元素组合 直到使用了items中的所有元素 initial是在首次计算式 以及items是空时的一个可选开始值 例如
print(reduce(plus, [1, 2, 3, 4, 5], 0));
print(reduce(lambda s1, s2: s2 + ' ' + s1, 'i love python'.split()));
