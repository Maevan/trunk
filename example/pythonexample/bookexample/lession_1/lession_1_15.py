# coding: utf-8
'''
Created on 2011-12-1

@author: Lv9
'''


#用try包围可能发生异常的代码 并捕捉异常交给except块处理
try:
    f = open('t', mode='r');
except IOError as e:
    print(e);#可以通过except关键字对捕捉
f = None;
try:
    f = open('D:/foo.txt', mode='r');
except IOError as e:
    raise RuntimeError("Computer says no");# 也可以通过raise关键字抛出异常
finally:
    #Python也有finally关键字 作用与Java的差不多
    if f != None:
        print("f is closed");
        f.close();
    print("f is clear")

#也可以通过with关键字来简化捕捉异常或者清理错误的步骤 被监控的对象必须实现__exit__和__enter__函数 方便with对对象调用这些清理函数
with open("D:/foo.txt", mode='r') as f:
    for line in f:
        print(line);
#用来测试f是否关闭(被正确的清理)
try:
    print(f.read());
except IOError as e:
    print(e)
