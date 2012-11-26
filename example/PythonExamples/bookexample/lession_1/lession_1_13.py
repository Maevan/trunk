# coding: utf-8
'''
Created on 2011-12-1

@author: Lv9
'''
#通常 函数运行时要使用一组参数.但是,也可以把函数编写为一个任务.从而能处理发送给他的一系列输入.这类函数成为协程
def print_matches(matchtext):
    print("Looking for", matchtext);
    while True:
        line = (yield);
        if(matchtext) in line:
            print(line);

matcher = print_matches("python");
matcher.__next__();#向前执行到第一条yield语句(开始执行)
matcher.send("python is cool");#使用send为协程发送某个值之前,协程会暂停终止 此时,协程中的(yield)表达式将返回这个值,而接下来的语句会处理他 处理直到下一个(yield)表达式才会结束
matcher.send("Hello python world!")
matcher.close();#匹配调用结束
