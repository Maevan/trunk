#coding=utf-8
'''
Created on 2013-5-2

@author: zhaojp
'''
import threading, time;
from queue import Queue;

    
def consumer(sid, q):
    while True:
        time.sleep(1);
        task = q.get();
        if task is None:
            break;
        print("task:", task);
        q.task_done();
    print("thread ", sid, " quit....");
        
def productor(q, consumer_count):
    for i in range(100):
        q.put(i);
    for i in range(consumer_count):
        q.put(None);
    

if __name__ == "__main__":
    q = Queue();
    threading.Thread(target=productor, args=(q, 5)).start();
    for i in range(5):
        threading.Thread(target=consumer, args=(i, q)).start();
    
