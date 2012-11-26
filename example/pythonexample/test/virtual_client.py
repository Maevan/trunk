#coding=utf-8
'''
Created on 2012-8-24

@author: zhaojp
'''
import socket;
import json;

from hashlib import sha1;

buffer_size = 256;
charset = 'utf-8';
salt = b'YiQunSongShu';
host = 'm.guokr.com';
port = 80;

login_message = \
'''POST /api/userinfo/login/ HTTP/1.1
Host: %s
Accept: */*Accept-Language: zh-cnAccept-Encoding: gzip, deflate
Content-Type: application/x-www-form-urlencoded
Content-Length: %d\n
%s''';


get_token_message = \
'''POST /api/userinfo/get_token/ HTTP/1.1
Host: %s
Content-Type: application/x-www-form-urlencoded
Content-Length: %d\n
%s''';


def get_cookie(username, sspassword, susertoken , remember='true', session_id_key='sessionId'):
    client = socket.socket(socket.AF_INET, socket.SOCK_STREAM);
    data = "username=%s&sspassword=%s&susertoken=%s&remember=%s" % (username, sspassword, susertoken, remember);
    
    with client:
        client.connect((host, port));
        client.send((login_message % (host, len(data), data)).replace('\n', '\r\n').encode(charset));
        
        print((login_message % (host, len(data), data)).replace('\n', '\r\n'))
        
        response = b'';
        tm = client.recv(buffer_size);
        while tm:
            response += tm;
            tm = client.recv(buffer_size);
        response = response.decode(charset);
        print('\n\n', response);

def get_token(username):
    client = socket.socket(socket.AF_INET, socket.SOCK_STREAM);
    data = "username=%s" % username;
    
    with client:
        client.connect((host, port));
        client.send((get_token_message % (host, len(data), data)).replace('\n', '\r\n').encode(charset));
        
        response = b'';
        tm = client.recv(buffer_size);
        while tm:
            response += tm;
            tm = client.recv(buffer_size);
            
        return json.loads(response.decode(charset).split('\r\n\r\n')[1])['token'];
        
def encode_password(password, token):
    #SHA1(token+SHA1(SATL+password))
    password = password.encode(charset);
    token = token.encode(charset);
    return sha1(token + sha1(salt + password).hexdigest().encode(charset)).hexdigest();

def encode_token(token, username):
    return sha1(token.encode(charset) + username.encode(charset)).hexdigest();

token = get_token('153996072@qq.com');
sspassword = encode_password('4845849', token);
susertoken= encode_token(token, '153996072@qq.com');
get_cookie('153996072@qq.com', sspassword, susertoken);
