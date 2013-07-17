#coding=utf-8
'''
Created on 2013-6-3

@author: zhaojp
'''
from xmlrpc.client import ServerProxy;

s = ServerProxy('http://localhost:8080');
print(s.add(5, 4));
print(s.system.listMethods());
print(s.tan(4.5));
