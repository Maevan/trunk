#coding=utf-8
'''
Created on 2012-10-30

@author: zhaojp
'''
from threading import Thread;
from time import sleep;

from socket import socket;
from socket import AF_INET, SOCK_STREAM;

class EchoServer(Thread):
    def __init__(self, host, port):
        Thread.__init__(self);
        
        self.host = host;
        self.port = port;
        
    def run(self):
        server = socket(AF_INET, SOCK_STREAM);
        server.bind((self.host, self.port));
        server.listen(5);
        
        while True:
            conn, address = server.accept();
            print(address, ' is connected');
            data = b'';
            buffer = conn.recv(1024);
            while buffer:
                data += buffer;
                buffer = conn.recv(1024);
            conn.send(data);
            conn.close();
            print(address, ' is closed');



if __name__ == '__main__':
    t = EchoServer('127.0.0.1', 7);
    
    t.setDaemon(True);
    t.start();
    
    while True:
        client = socket(AF_INET, SOCK_STREAM);
        client.connect(("127.0.0.1", 7));
        
        message = input("Enter message:");
        if message == 'bye':
            break;
        
        client.send(message.encode('utf-8'));
        client.shutdown(1);
        
        data = b'';
        buffer = client.recv(1024);
        while buffer:
            data += buffer;
            buffer = client.recv(1024);
        print('Response message: ', data.decode('utf-8'))
        client.close();
        
