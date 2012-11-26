'''
Created on 2012-8-13

@author: zhaojp
'''

import threading;

t = threading.Timer(5, lambda :print('Hello World!'));
t.start();#启动定时器
#t.cancel();#如果定时器尚未被执行 取消任务
