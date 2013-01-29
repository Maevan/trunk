#coding=utf-8
'''
Created on 2013-1-11

@author: zhaojp
'''
import socket, ssl;

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM);
ssl_s = ssl.wrap_socket(s);
ssl_s.connect(('gmail.google.com', 443));
print(ssl_s.cipher());
#发送请求
ssl_s.write(b"GET / HTTP/1.0 \r\n\r\n");

#获得相应
while True:
    data = ssl_s.read();
    if not data:break;
    print(data);
ssl_s.close();
