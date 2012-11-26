#coding=utf-8
'''
Created on 2012-4-25

@author: zhaojp
'''

from itertools import chain;
from itertools import combinations;
from itertools import count;
from itertools import cycle;
from itertools import dropwhile;
from itertools import filterfalse;
from itertools import groupby;
def toTraverse(it, loopNum=20, endS=' '):
    for i in it:
        print(i, end=endS);
        loopNum -= 1;
        
        if(loopNum <= 0):
            break;
    print();

toTraverse(chain([1, 2, 3], [4, 5, 6])); #给定一组可迭代对象 通过chain方法将这些对象合并为一个可迭代对象
toTraverse(combinations([1, 2, 3, 4, 5, 6], 2)); #combinations(iterable,r) 创建一个迭代器 返回iterable中所有元素的长度为r的组合
toTraverse(count(10));#count(n) 创建一个迭代器 生成从n开始的连续整数 如果忽略n 则从0开始计算 循环次数上限为system.maxint
toTraverse(cycle([1, 2, 3]));#cycle(iterable) 创建一个迭代器 对iterable中的元素反复执行循环操作内部会生成iterable中的元素的一个副本 此副本用于返回循环中的重复项
toTraverse(dropwhile(lambda i : i % 2 == 1, [1, 2, 3, 4, 5]));#dropwhile(predicate,iterable) 创建一个迭代器 只要函数predicate为True 就丢弃iterable中的项  否则就会生成iterable中的项和所有后续项
toTraverse(filterfalse(lambda i : i % 2 == 1, [1, 2, 3, 4, 5]));#filterfalse(predicate,iterable) 创建一个迭代器 只要函数predicate为False 就保留iterable 最后将iterable中所有predicate的项返回
toTraverse(groupby([1, 2, 5, 3, 6, 8, 1, 2, 2, 5, 6]));#groupby(iter) 创建一个迭代器 对iter中的连续项进行分组(可以先通过排序将所有相同元素弄成连续项 然后再进行分组)
