# coding: utf-8
'''
Created on 2011-12-27

@author: Lv9
'''

class A(object):
    def __init__(self):
        self.__X = 3; # 变形为self._A__X;
        
    def __span(self): # 变形为_A__span()
        pass;
    
    def bar(self):
        self.__span(); # 只调用A.__span()

class B(A):
    def __init__(self):
        A.__init__(self);
        self.__X = 37;