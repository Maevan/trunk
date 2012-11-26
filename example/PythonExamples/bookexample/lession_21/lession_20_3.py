#coding=utf-8
'''
Created on 2012-8-17

@author: zhaojp
'''
import asyncore, socket;
import os;
import mimetypes;
import collections;

from http.client import responses;

#该类仅处理接受事件
class async_http(asyncore.dispatcher):
    def __init__(self, port, root):
        asyncore.dispatcher.__init__(self);
        
        self.create_socket(socket.AF_INET, socket.SOCK_STREAM);
        self.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1);
        self.bind(('', port));
        self.listen(5);
        self.root = root;
        
        if not self.root.endswith('/'):
            self.root += '/';
    
    def handle_accept(self):
        client, addr = self.accept();
        
        print('Got a connection from %s' % str(addr));
        
        return async_http_handler(self.root,client);

#处理客户端
class async_http_handler(asyncore.dispatcher):
    def __init__(self, root, sock=None):
        asyncore.dispatcher.__init__(self, sock);
        
        self.got_request = False; #是否读取HTTP请求
        self.request_data = b'';
        self.write_queue = collections.deque();
        self.responding = False;
        self.root = root;

    #仅在尚未读取请求报头时才能读取
    def readable(self):
        return not self.got_request;
    
    #处理传入请求
    def handle_read(self):
        chunk = self.recv(8192);
        self.request_data += chunk;
        
        if b'\r\n\r\n' in self.request_data:
            self.handle_request();
    
    #处理传入请求
    def handle_request(self):
        self.got_request = True;
        header_data = self.request_data[:self.request_data.find(b'\r\n\r\n')];
        header_text = header_data.decode('utf-8');
        header_lines = header_text.splitlines();
        request = header_lines[0].split();
        op = request[0];
        url = '';
        if request[1] == '/' or request[1] == '':
            url += 'index.html';
        else:
            url += request[1][1:];
        self.file_name = self.root + url;
        self.process_request(op, url);print(self.file_name);
    
    #处理传入请求        
    def process_request(self, op, url):
        self.responding = True;
        if op == 'GET':
            if not os.path.exists(self.file_name):
                self.send_error(404, 'File %s not found\r\n' % url);
            else:
                content_type, encoding = mimetypes.guess_type(url);
                size = os.path.getsize(self.file_name);
                
                self.push_text('HTTP/1.0 200 OK\r\n');
                self.push_text('Content-length: %d\r\n' % size);
                self.push_text('Content-type: %s\r\n' % content_type);
                self.push_text('\r\n');
                self.push(open(self.file_name,'rb').read());
        else:
            self.send_error(501, "%s method not implemented" % self.op);
            
    #将二进制数据添加到输出队列            
    def push(self, data):
        self.write_queue.append(data);
    
    #将文本数据添加到输出队列
    def push_text(self, text):
        self.push(text.encode('utf-8'));
    
    
    #错误处理
    def send_error(self, code, message):
        self.push_text('HTTP/1.0 %s %s\r\n' % (code , responses(code)));
        self.push_text('Content-type:text/plain\r\n');
        self.push_text('\r\n');
        self.push_text(message);
        
    #仅在响应准备好时才能写入
    def writeable(self):
        return self.responding and self.write_queue;
    
    #写入响应数据
    def handle_write(self):
        chunk = self.write_queue.popleft();
        bytes_sent = self.send(chunk);
        
        if bytes_sent != len(chunk):
            self.write_queue.appendleft(chunk[bytes_sent:]);
        if not self.write_queue:
            print('------------------------------')
            self.close();
        
async_http(8080, 'F:/LIBS/FusionCharts_Evaluation/');
asyncore.loop();