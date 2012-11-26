#coding=utf-8
'''
Created on 2012-8-23

@author: zhaojp
'''

import select;

rlist = [];
wlist = [];
xlist = [];

selector = select.select(rlist, wlist, xlist)
p = selector.poll();