# coding: utf-8
'''
Created on 2011-12-13

@author: Lv9
'''
#for语句
for i in range(0, 10):
    print(i, end=' ');
print();

#for语句也可以加else循环 前提是for语句正常执行完毕 如果中途break了 则不会进入else
for i in range(0, 10):
    print(i, end=' ')
else:
    print('end');

for i in range(0, 10):
    if i > 5:
        break;#终止循环
    print(i, end=' ')
else:
    print('end');

print();

for i in range(0, 10):
    if i % 2 == 1:
        continue;
    print(i, end=' ')
else:
    print('end');

#也可以对序列中的元组直接进行解压操作
for x, y, z in [(1, 2, 3), (4, 5, 6)]:
    print(x, y, z, end=' ')

print();

#也可以同时对多个序列进行迭代操作(如果多个序列的长度不同 以最短的那个为基准)
for x, y, z in zip([1, 4], [2, 5], [3, 6, 9]):
    print(x, y, z, end=' ')
print();

for i, x in enumerate([1, 2, 3]):
    print(i, x);
print();