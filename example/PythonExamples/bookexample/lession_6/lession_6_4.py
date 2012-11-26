# coding: utf-8
'''
Created on 2011-12-16

@author: Lv9
'''
from urllib.request import urlopen;

#将组成函数的语句和这些语句的执行环境打包在一起时 得到的对象称为闭包
def countdown(start):
    def f():
        nonlocal start;
        r = start;
        start -= 1;
        return r;
    return f;

iterator = countdown(10);
print(iterator());
print(iterator());
print(iterator());

#利用Python嵌套函数可以实现惰性求值 或者延迟求值的代码 比如



def page(url):
    def get():
        return urlopen(url).read(url);
    return get;

google = page('http://www.google.com');
baidu = page('http://www.baidu.com');

print(baidu);
print(baidu());