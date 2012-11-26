#coding=utf-8
'''
Created on 2012-8-16

@author: zhaojp
'''
from socket import socket;
from socket import AF_INET;
from socket import SOCK_STREAM;

client = socket(AF_INET, SOCK_STREAM);#创建TCP套接字

client.bind(('localhost',8887));
client.connect(('localhost', 8888));#连接到服务器

tm = client.recv(1024);#最多接受1024个字节

client.close();
print("The time is %s" % tm.decode("ascii"));
