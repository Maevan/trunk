#coding=utf-8
'''
Created on 2012-10-24

@author: zhaojp
'''
from web import application, seeother, template;
urls = ('', 'retemp',
        '/form', 'form');
        
class form:
    def GET(self):
        render = template.frender("templates/form.html", globals={"name" : 'Lv9', 'get_id':get_id});
        return render();

class retemp:
    def GET(self):
        raise seeother('/index'); #重定向

def get_id():
    return -1;

app_temp = application(urls, locals());
