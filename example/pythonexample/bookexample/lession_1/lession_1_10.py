# coding: utf-8
'''
Created on 2011-11-29

@author: Lv9
'''
#迭代列表
for n in [1, 2, 3, 4, 5, 6, 7, 8, 9]:
    print("2 to the %d power is %d" % (n, 2 ** n));#求2的2次方

#range(i,j,[,步长]) 用这个可以实现类似Java的 for(int i = 1;i < 10;i++)的循环
for n in range(1, 10):
    print("2 to the %d power is %d" % (n, 2 ** n));#求2的2次方

a = range(5);#0,1,2,3,4
b = range(1, 8);#1,2,3,4,5,6,7
c = range(0, 14, 3);#0,3,6,9,12
d = range(8, 1, -1);#8,7,6,5,4,3,2

