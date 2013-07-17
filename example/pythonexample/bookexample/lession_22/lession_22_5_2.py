#coding=utf-8
'''
Created on 2013-6-3

@author: zhaojp
'''
from xmlrpc.server import SimpleXMLRPCServer;
import math;

def add(x, y):
    "Adds two numbers"
    return x + y;

s = SimpleXMLRPCServer(('', 8080));
s.register_function(add);
s.register_instance(math);
s.register_introspection_functions();
s.serve_forever();
