#coding=utf-8
'''
Created on 2013-1-29

@author: zhaojp
'''
from threading import Thread;
from urllib.request import Request;
from urllib import request;
import os;
root = 'G:/meiriyiwen/';
message = Request('http://meiriyiwen.com/random', headers={'Accept-Charset': 'UTF-8,utf-8;q=0.7,*;q=0.3'});
template = """<!DOCTYPE html><html><head><meta http-equiv="Content-Type" content="text/html; charset=utf-8" /><title>%s</title></head><body><div>%s</div><a href="./index.html">返回上一级</a></body></html>"""
main_template = """
    <!DOCTYPE html><html><head><meta http-equiv="Content-Type" content="text/html; charset=utf-8" /><title>每日一文本地版</title></head><body><div>作者目录<ul>%s</ul></div></body></html>
"""
author_template = """
    <!DOCTYPE html><html><head><meta http-equiv="Content-Type" content="text/html; charset=utf-8" /><title>%s</title></head><body><div>文章目录<ul>%s</ul></div><a href="../index.html">返回上一级</a></body></html>
"""
if os.access(root , os.F_OK) == 0:
    os.mkdir(root);

def do(requests=500):
    for i in range(0, requests):
        r = request.urlopen(message);
        lines = r.readlines();
        response = b'';
        for line in lines:
            response += line;
        response = response.decode('utf-8');
        response = response[response.index('<div id="article_show">'):];
        title = response[response.index('<h1>') + 4:response.index('</h1>')];
        author = request.quote(response[response.index('<p class="article_author"><span>') + len('<p class="article_author"><span>'):response.index('</span>')]).replace("%", "_");
        detail = response[response.index('<div class="article_text">') + len('<div class="article_text">') : response.index('</div>')];
        if os.access(root + author, os.F_OK) == 0:
            os.mkdir(root + author);
        try:
            f = open(root + author + "/" + request.quote(title).replace("%", "_") + ".html", mode='w', encoding="utf-8");
            f.write(template % (title, detail));
            f.close();
        except UnicodeEncodeError as e:
            print("文章:", title, "转换失败");

def generateIndexs():
    author_dirs = os.listdir(root);
    author_indexs = '';
    author_dirs.sort();
    for author_dir in author_dirs:
        if author_dir != 'index.html':
            author = request.unquote(author_dir.replace("_", "%"));
            author_indexs += '<li><a href="./%s/index.html">%s</a></li>' % (author_dir, author);
            article_files = os.listdir(root + author_dir);
            article_index = '';
            article_files.sort();
            for article_file in article_files:
                if article_file != 'index.html':
                    article_index += '<li><a href="./%s">%s</a></li>' % (article_file, request.unquote(article_file.replace("_", "%")));
            af = open(root + author_dir + "/index.html", mode='w', encoding='utf-8');
            af.write(author_template % (author, article_index));
            af.close();
            
        f = open(root + 'index.html', mode='w', encoding='utf-8');
        f.write(main_template % author_indexs);
        f.close();
    
threads = [];
for i in range(1, 5):
    t = Thread(group=None, target=do, args=(10,), name='spider');
    t.start();
    
    threads.append(t);
for t in threads:
    t.join();
    
generateIndexs();
print('抓取完毕');
