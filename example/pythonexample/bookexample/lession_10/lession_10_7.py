#coding = utf-8
'''
Created on 2012-2-14

@author: zhaojp
'''
import atexit, gc;
def beforeExit():
    print('古德拜');

atexit.register(beforeExit);#可以通过向atexit.register()注册在程序退出之前执行的函数
atexit.register(gc.collect);#在程序退出的时候调用垃圾回收器
