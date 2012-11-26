# coding: utf-8
'''
Created on 2011-12-19

@author: Lv9
'''
from functools import wraps

#通常函数的第一条语句会使用文档字符串 用于描述函数的用途 例如

def factorial(n):
    """
Computes n factorial. For example:
        
>>> factorial(6);
120
>>>
    """
    if n <= 1:
        return 1;
    else:
        return n * factorial(n - 1);

print(factorial.__doc__);#可以通过访问__doc__来访问指定函数或者类的文档字符串

#装饰器会破坏被包装函数的文档信息 解决办法是编写可以传递函数名称和文档字符串的装饰器函数
#这是个普遍问题 也可以使用functools自带的@wraps注解解决这个问题
def wrap(func):
    @wraps (func)
    def call(*args, **kwargs):
        return func(*args, **kwargs);
    # 或者直接手动保存属性
    # call.__doc__ = func.__doc__;
    # call.__name__ = func.__name__;
    return call;

@wrap
def foo():
    """
        I'm Foo!
    """
help(foo);

