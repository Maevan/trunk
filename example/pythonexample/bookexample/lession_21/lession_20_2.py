#coding=utf-8
'''
Created on 2012-8-16
使用asynchat的异步HTTP服务器
@author: zhaojp
'''
import asynchat, asyncore, socket;
import os;
import mimetypes;

from http.client import responses; #Python 3
# from httplib import responses; Python2

#该类插入到asyncore模块 仅处理接受的事件
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
    
    #收到新连接时 对监听的套接字调用该方法
    def handle_accept(self):
        client, addr = self.accept();
        
        print('Got a connection from %s' % str(addr));
        
        return async_http_handler(self.root, client);

#处理HTTP异步请求的类
class async_http_handler(asynchat.async_chat):
    def __init__(self, root, conn=None):
        asynchat.async_chat.__init__(self, conn);
        self.data = [];
        self.got_header = False;
        self.set_terminator(b'\r\n\r\n');#在通道上设置终止状态 term可以是字符串、整数、None 如果term是字符串 则在输入流中出现该字符串的时候调用found_terminator()方法
        self.root = root;
    
    #获取传入数据并添加到数据缓冲区 用户自己实现
    def collect_incoming_data(self, data):
        if not self.got_header:
            self.data.append(data);

    #到达终止符时会被触发的方法 用户自己实现
    def found_terminator(self):
        self.got_header = True;
        header_data = b"".join(self.data);
        
        #将报头数据(二进制)解码为文本以便进一步处理
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
        self.process_request(op, url);

    #将文本加入到输出流 但首先要编码
    def push_text(self, text):
        self.push(text.encode('utf-8')); #将数据加入到通道的传出生成函数FIFO队列
        
    def send_error(self, code, message):
        self.push_text('HTTP/1.0 %s %s \r\n' % (code, responses[code]));
        self.push_text('Content-type: text/plain\r\n');
        self.push_text('\r\n');
        self.push_text(message);
        
    #处理请求
    def process_request(self, op, url):
        if op == 'GET':
            if not os.path.exists(self.file_name):
                self.send_error(404, "File %s not found\r\n" % url);
            else:
                content_type, encoding = mimetypes.guess_type(url);
                size = os.path.getsize(self.file_name);
                
                self.push_text('HTTP/1.0 200 OK \r\n');
                self.push_text('Content-length: %s\r\n' % size);
                self.push_text('Content-type: %s\r\n' % content_type);
                self.push_text('\r\n');
                self.push_with_producer(file_producer(self.file_name));#将生成函数对象加入到生成函数FIFO队列 传递的参数可以是任意就有more()方法的对象 一旦方法返回空则表示到达数据末尾
        else:
            self.send_error(501, "%s method not implemented" % op);
        self.close_when_done();
        
class file_producer(object):
        def __init__(self, filename, buffer_size=512):
            self.f = open(filename, 'rb');
            self.buffer_size = buffer_size;
            
        def more(self):
            data = self.f.read(self.buffer_size);
            if not data:
                self.f.close();
            return data;

async_http(8080, 'F:/LIBS/FusionCharts_Evaluation/');
async_http(8081, 'F:/LIBS/FusionCharts_Evaluation/');
asyncore.loop();
