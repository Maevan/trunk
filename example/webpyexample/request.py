#coding=utf-8
'''
Created on 2012-11-12

@author: zhaojp
'''
from web import application, seeother, input, header;
urls = ('', 'reindex',
        '/index', 'index',
        '/data', 'data');

app_request = application(urls, globals());

class reindex:
    def GET(self):
        raise seeother('/index');

class index:
    def GET(self):
        header('Content-Type', 'text/plain');
        response = app_request.request('/data', 'POST', {'name':'Lv9', 'age':21}, headers={'User-Agent': 'a small jumping bean/1.0 (compatible)'});
        return response.data;

class data:
    def POST(self):
        data = input();
        print len(data);
        return 'Name is %s, Age is %s' % (data['name'], data['age']);
