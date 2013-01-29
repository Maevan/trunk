#coding=utf-8
'''
Created on 2013-1-11

@author: zhaojp
'''
from socketserver import TCPServer;
from bookexample.lession_21.lession_21_7 import TimeServerHandler;

server = TCPServer(('', 10000), TimeServerHandler);
server.serve_forever();
