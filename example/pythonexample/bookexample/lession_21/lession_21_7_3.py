#coding=utf-8
'''
Created on 2013-1-11

@author: zhaojp
'''
from socketserver import TCPServer, ThreadingMixIn;
from bookexample.lession_21.lession_21_7 import TimeServerHandler;

class TimeServer(ThreadingMixIn, TCPServer):
    daemon_threads = False
    
server = TimeServer(('', 10000), TimeServerHandler);
server.serve_forever();
