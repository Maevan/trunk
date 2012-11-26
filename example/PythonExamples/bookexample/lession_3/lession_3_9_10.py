# coding: utf-8
'''
Created on 2011-12-8

@author: Lv9
'''

class DistanceFrom(object):
    def __init__(self, origin):
        print('!!!!')
        self.origin = origin;
    
    #类似回调函数
    def __call__(self, x):
        print(x);
        return abs(x - self.origin);

nums = [1, 34, 33, 775, 55, 6, 77, 66];
nums.sort(key=DistanceFrom(10));
