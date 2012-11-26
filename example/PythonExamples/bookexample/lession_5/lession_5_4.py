# coding: utf-8
'''
Created on 2011-12-13

@author: Lv9
'''

try:
    raise RuntimeError('测试异常');
except (RuntimeError, IOError) as e: #可以同时捕捉多个异常 也可以只捕捉一个异常 通过as关键字给异常对象起别名
    print(e);
    # raise; 可以直接用raise关键字抛出异常
    # raise RuntimeError('测试异常'); 也可以抛出指定类型的异常
except BaseException as e: #可以跟随多个except块
    print(e);
    
finally:
    print('finally');#try的finally语句 会在执行catch块之前执行 这跟Java的finally语句不一样..


try:
    # raise RuntimeError('测试异常');
    print('不出异常!');
except RuntimeError as e:
    print(e);
else:
    print('没出异常!');#可以在catch块后面Else语句 当try块内代码没有发生异常时 则会进入else语句 否则不会进入else语句
