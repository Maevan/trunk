#coding=utf-8
'''
Created on 2012-11-6

@author: zhaojp
'''

def foo(x, y):
    result = 0;
    bits = 0;
    while y != 0:
        if y & 1 == 1:
            result += x << bits;
        y = y >> 1;
        bits += 1;
        print('   ', result, x , y)
    return result;

print(foo(15, 20));

