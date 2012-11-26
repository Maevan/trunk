#coding=utf-8
'''
Created on 2012-11-8

@author: zhaojp
'''
from web import application, setcookie, input, cookies;
urls = (
        '/set', 'SetCookie',
        '/get', 'GetCookie'
);

app_cookie = application(urls, locals());

#name (string) - Cookie的名称，由浏览器保存并发送至服务器。
#value (string) -Cookie的值，与Cookie的名称相对应。
#expires (int) - Cookie的过期时间，这是个可选参数，它决定cookie有效时间是多久。以秒为单位。它必须是一个整数，而绝不能是字符串。
#domain (string) - Cookie的有效域－在该域内cookie才是有效的。一般情况下，要在某站点内可用，该参数值该写做站点的域（比如.webpy.org），而不是站主的主机名（比如wiki.webpy.org）
#secure (bool)- 如果为True，要求该Cookie只能通过HTTPS传输。.

class SetCookie:
    def GET(self):
        params = input();
        i = 0;
        for key, value in params.items():
            setcookie(key, value, 86400, '', False, True, '');
            i += 1;
        
        return '共有%d个cookie被成功添加' % i;

class GetCookie:
    def GET(self):
        result = '';
        for key, value in cookies().items():
            result += "key: %s&nbsp;value: %s<br/>" % (key, value);
        return result;
