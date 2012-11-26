#coding=utf-8
'''
Created on 2012-5-4

@author: zhaojp
'''
from codecs import iterencode;
from codecs import iterdecode;

echars = iterencode(['你', '好', '啊'], 'utf-8');
dchars = iterdecode(echars, 'utf-8');

for c in dchars:
    print(c)
