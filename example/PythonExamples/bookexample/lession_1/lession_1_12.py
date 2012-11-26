# coding: utf-8
'''
Created on 2011-12-1

@author: Lv9
'''

#可以通过yield语句生成一个结果序列(生成器 迭代器),而不仅仅是一个值
def countdown(n):
    print("Counting down!");
    while(n > 0):
        yield n;
        n -= 1;

c = countdown(1);
print(c);
print(c.__next__());#手动调用next

#通过f循环调用next
for i in countdown(5):
    print(i);
