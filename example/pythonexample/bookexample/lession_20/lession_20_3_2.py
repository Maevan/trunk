#coding=utf-8
'''
Created on 2012-7-20

@author: zhaojp
'''

import multiprocessing;

def consumer(input_q):
    while True:
        item = input_q.get();
        # 处理项目
        print(item); #此处替换为有用的工作
        # 发出信号通知任务完成
        input_q.task_done();

def producer(sequence, output_d):
    for item in sequence:
        output_d.put(item);

if __name__ == '__main__':
    q = multiprocessing.JoinableQueue();
    
    for i in range(1,2):
        #运行使用者进程
        cons_p = multiprocessing.Process(target=consumer, args=(q,));
        cons_p.deamon = True;
        cons_p.start();
    
    sequence = [];
    for i in range(1, 10000):
        sequence.append(i);
        
    producer(sequence, q);
    
    q.join();
