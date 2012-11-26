#coding=utf-8
'''
Created on 2012-8-13

@author: zhaojp
'''
import threading;


def notify(e):
    e.set();

class EventThread(threading.Thread):
    def __init__(self, i, event):
        threading.Thread.__init__(self);
        self.name = 'Thread-' + str(i);
        self.p_event = event;
        
    def run(self):
        print("thread %s is wait" % self.name);
        self.p_event.wait();
        print("thread %s is notify" % self.name);
        

e = threading.Event();

for i in range(10):
    EventThread(i, e).start();
    
threading.Timer(20, notify, (e,)).start();