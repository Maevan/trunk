#coding=utf-8
'''
Created on 2012-10-22

@author: zhaojp
'''
from web import application, seeother, notfound, internalerror, ctx, input, header;
from web import loadhook, unloadhook;
from blog import app_blog;
from temp import app_temp;
from session import app_session;
from cookie import app_cookie;
from upload import app_upload;

#url匹配这里指匹配path部分 不包含查询字符串 锚点等url的其他部分 其他部分请用ctx获取
urls = ("/(.*)/", "redirect",
        "/", "index",
        "/user/list/(.+)/(.+)", "user_list", #每个正则匹配的部分都将会作为一个参数传递到函数中去
        "/blog", app_blog,
        "/temp", app_temp,
        "/session", app_session,
        "/cookie", app_cookie,
        '/upload', app_upload,);#也可以像这样添加子应用

class index:
    def GET(self):
        return 'hello, world';
    
class redirect:
    def GET(self, path):
        seeother("/" + path);

class user_list:
    def GET(self, clazz, name):
        print ctx.get("query");#可以通过ctx获得各种头信息
        params = input();#通过input可以获得一些参数
        
        for key, value in params.items():
            print key, value;
        return "Listing info about class: {0},user: {1}".format(clazz, name);

def my_processor(handler):
    print 'before handling';
    result = handler();
    header('Content-Type', 'text/html; charset=UTF-8');
    print 'after handling';
    
    return result;

def my_loadhook():
    print 'my load hook';

def my_unloadhook():
    print 'my unload hook';

def my_notfound():
    return notfound("Sorry, the page you were looking for was not found.")

def my_internalerror():
    return internalerror("internalerror")

app = application(urls, locals());

#app.add_processor(my_processor);
#app.add_processor(loadhook(my_loadhook));
#app.add_processor(unloadhook(my_unloadhook));

app.notfound = my_notfound;#自定义找不到资源的错误
app.internalerror = my_internalerror;#自定义发生内部错误的处理方式

if __name__ == '__main__':
    app.run();
