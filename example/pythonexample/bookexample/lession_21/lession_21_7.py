#coding=utf-8
'''
Created on 2013-1-11

@author: zhaojp
'''
from socketserver import BaseRequestHandler; #Python 3#
# from SocketServer import BaseRequestHandler; #Python 2#

import socket, time;

class TimeServerHandler(BaseRequestHandler):
    def handle(self):
        resp = (time.ctime() + '\r\n').encode('utf-8');
        if isinstance(self.request, socket.socket):
            self.request.sendall(resp);
        else:
            self.server.socket.sendto(resp , self.client_address);

