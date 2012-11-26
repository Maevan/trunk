#coding=utf-8
'''
Created on 2012-5-16

@author: zhaojp
'''

l = [];
for i in range(2, 60):
    l.append(i);

p = 0;

while True:
    n = l[p];#第一个素数
    l = [i for i in l if i == n or i % n != 0];
    
    if p >= len(l) - 1:
        break;
    else:
        p += 1;

print(l)
