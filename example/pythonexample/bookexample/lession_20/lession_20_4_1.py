#coding=utf-8
'''
Created on 2012-8-13

@author: zhaojp
'''
import threading;
import time;

def clock(interval):
    while True:
        print("The time is %s" % time.ctime());
        time.sleep(interval);

t = threading.Thread(target=clock, args=(15,));
t.daemon = True;
t.start();

class ClockThread(threading.Thread):
    def __init__(self, interval):
        threading.Thread.__init__(self);
        self.daemon = True;
        self.interval = interval;
        
    def run(self):
        while True:
            print("The time is %s" % time.ctime());
            time.sleep(self.interval);

t = ClockThread(15);

t.start();