#coding=utf-8
'''
Created on 2012-4-18

@author: zhaojp
'''

from collections import deque;
from collections import defaultdict;

d = deque();#创建一个双端队列

d.append('1');#将指定元素添加到队列的右端
d.appendleft('2');#将指定元素添加到队列左端
d.clear();#删除队列中全部元素
d.extend([1, 2, 3, 4, 5]);#将指定iterable的所有项目添加到队列右端
d.extendleft([6, 7, 8, 9, 10]);#将指定iterable的所有项目添加到队列左端
d.remove(4);#删除首次出现的item 如果未找到匹配的item 则抛出ValueError
d.rotate(1);#将所有items左旋n步 如果n为负数 则右旋

print(d);
print(d.pop());#返回并删除队列右端的项目 如果队列是空的则抛出IndexError
print(d.popleft());#返回并删除队列左端的项目 如果队列是空的则抛出IndexError

#defaultdict与字典基本一样 除了对缺少键的处理上 当查找不存在的键时 将调用default_factory提供的函数来提供一个默认值
#然后将该值保存为关联键的值

def wordcount(s):
    words = s.split(' ');
    wordlocations = defaultdict(list);
    
    for i, w in enumerate(words):
        wordlocations[w].append(i);
    return wordlocations;

print(wordcount('Hello World Hello World Hello Lv9'))
