#coding=utf-8
'''
Created on 2012-7-19

@author: zhaojp
'''

import multiprocessing;
import time;

def clock(interval):
    while True:
        print("The time is %s" % time.ctime());
        time.sleep(interval);

#也可以通过继承Process类 重写run方法 自己定义进程

class ClockProcess(multiprocessing.Process):
    def __init__(self, interval):
        multiprocessing.Process.__init__(self);
        self.interval = interval;  
    
    def run(self):
        while True:
            print("The time is %s" % time.ctime());
            time.sleep(self.interval);

if __name__ == '__main__':
#    p = multiprocessing.Process(target=clock, name='StudyPythonProcess', args=(1,)); #创建一个进程对象 target是当进程启动时可调用的对象 args和kwargs则是传递给target的参数
#    p.start();
    p = ClockProcess(15);
    p.start();
