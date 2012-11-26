#coding=utf-8
'''
Created on 2012-5-15

@author: zhaojp
'''


def sort(l=[]):
    for i in range(len(l)):
        for y in range(i, len(l)):
            if l[i] < l[y]:
                l[i] = l[i] ^ l[y];
                l[y] = l[i] ^ l[y];
                l[i] = l[i] ^ l[y];
                
l = [5, 1, 3, 6, 8, 4, 9, 2, 1, 4, 7, 5, 6, 5, 1, 5, 1, 6, 9, 9];
sort(l);

print(l)
