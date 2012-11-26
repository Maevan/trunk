#coding=utf-8
'''
Created on 2012-11-12

@author: zhaojp
'''
from web import seeother, application, template, input, debug, redirect;

urls = ('', 'Form',
        '/save', 'Save');

class Form:
    def GET(self):
        render = template.frender("templates/upload.html");
        return render();

class Save:
    def POST(self):
        form = input(myfile={});
        debug(form['owner']);
        debug(form['myfile'].filename);#文件名称
        debug(form['myfile'].value);#文件内容
        debug(form['myfile'].file.read());#文件内容2
        
        raise redirect('/');
app_upload = application(urls, locals());
