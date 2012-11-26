# coding: utf-8
'''
Created on 2011-12-2

@author: Lv9
'''
#比较两个对象
def compare(a, b):
    l = [];
    if a is b:#比较地址
        l.append("a is b");
    
    if a == b:#比较值
        l.append("a and b has same value");
    
    if type(a) is type(b):#比较类型
        l.append("a and b has same type");
    
    if type(a) is type(b) and type(a) is str: #判断类型是否为str
        l.append("a and b is string object");
    
    if isinstance(a, str): #判断类型是否为str 用isinstance可以判断某个对象是属于某个类型还是父级类型
        l.append("a and b is string object");
        
    return l;

a = '10';
b = '20';
c = '1' + '0';

for l in compare(a, c):
    print(l, end='、');
print();

for l in compare(a, b):
    print(l, end='、');
print();
