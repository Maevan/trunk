# coding: utf-8
'''
Created on 2011-12-16

@author: Lv9
'''
enable_tracing = True;
if enable_tracing:
    debug_log = open("debug.log", mode='w');

#定义一个名为trace的装饰器 默认参数存在一个函数对象
#另外 装饰器必须返回一个结果 因为调用者会通过装饰器调用指定的函数或者其他内容 装饰器会拦截下对原始函数的请求和原始函数的反馈 所以要一一指定
def trace(func):
    if enable_tracing:
        def callf(*args, **kwargs):
            r = func(*args, **kwargs)
            debug_log.write('Calling %s: %s, %s\n' % (func.__name__, args, kwargs));
            debug_log.write('%s returned %s\n' % (func.__name__, r));
            return r;
        return callf;
    else:
        return func

#装饰器会按照从内到外的顺序执行
def foo(func):
    print("I'm foo!");
    return func;


def bar(func):
    print("I'm bar!");
    return func;

@trace
@foo
@bar
def square(x):
    return x * x;

print(square(10));
