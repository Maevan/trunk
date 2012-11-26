#coding=utf-8
'''
Created on 2012-8-13

@author: zhaojp
'''
import threading;

produced = threading.Semaphore(0);
consumed = threading.Semaphore(1);
get_item_count = 0;

def producer(item_count):
    while item_count > get_item_count:
        consumed.acquire();
        produce_item();
        produced.release();

def consumer(item_count):
    while item_count > get_item_count:
        produced.acquire();
        get_item();
        consumed.release();

def produce_item():
    print("produce a item");

def get_item():
    global get_item_count;
    get_item_count += 1;
    
    print("get a item");

p = threading.Thread(target=producer, args=(50,));
c = threading.Thread(target=consumer, args=(50,));

p.start();
c.start();
