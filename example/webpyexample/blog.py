#coding=utf-8
'''
Created on 2012-10-22

@author: zhaojp
'''

from web import seeother, application, input;

urls = ('', 'reblog',
        '/(.*)', 'blog');
        
app_blog = application(urls, locals());

class blog:
    def GET(self, path):
        print len(input());
        return "hello " + path;

class reblog:
    def GET(self):
        raise seeother('/index'); #重定向
