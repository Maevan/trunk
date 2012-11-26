#coding=utf-8
'''
Created on 2012-8-16

@author: zhaojp
'''
import time;
from socket import socket;
from socket import AF_INET;
from socket import SOCK_STREAM;

server = socket(AF_INET, SOCK_STREAM);#创建TCP套接字
server.listen(5);#监听 但只能挂起5个以下的连接

print('listening...')
while True:
    client, addr = server.accept();
    
    print('Got a connection from %s' % str(addr));
    
    timestr = time.ctime(time.time()) + "\r\n";
    client.send(timestr.encode('ascii'));
    client.close();
