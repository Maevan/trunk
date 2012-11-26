#coding=utf-8
'''
Created on 2012-8-13

@author: zhaojp
'''
import threading;

count = 0;
l = threading.Lock();

def add(to_add, i_max):
    global count;
    print('to_add: %d,i_max %d' % (to_add, i_max));
    while count < i_max:
        l.acquire();
        count += to_add;
        l.release();
    print('i_max %d' % i_max);

for i in range(2):
    threading.Thread(target=add, args=(i, 2000)).start();
