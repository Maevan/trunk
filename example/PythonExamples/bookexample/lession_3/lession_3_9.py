# coding: utf-8
'''
Created on 2011-12-7

@author: Lv9
'''

class Foo(object):
    clazzproperty = 'hello' #用这种方式初始化类属性
    #创建新实例时调用的类方法
    def __new__(self, *args, **kwargs):
        print('创建对象');
        return object.__new__(self, *args, **kwargs)
    
    #初始化新实例时调用(参数列表可以更改)
    def __init__(self):
        print('初始化对象');
        self.count = 1;
    
    #Python可以重载很多操作符比如 '+'、'>>'、'i:y(切片操作符)'、甚至是通过下标获取元素的方式([] 这个) 也可以增加自己定义类型对Python的内部函数的支持 比如str()、int() 
    
    #销毁实例时调用    
    def __del__(self):
        print('销毁实例');
        del self.count;
        
    #用这种格式的名字可以重载某些特定的操作符
    def __add__(self, count):
        if type(count) is int:
            return self.count + count;
        elif type(count) is Foo:
            return self.count + count.count;
        else:
            raise TypeError("param type is error"); 

    #类似Java的toString() 被str(obj)的时候调用obj的__str__方法
    def __str__(self, *args, **kwargs):
        return str(self.count);
    
    
    
    #属性访问(可以用这个来做面向切面编程)
    def __getattribute__(self, *args, **kwargs):
        print('某个属性被获取')
        return object.__getattribute__(self, *args, **kwargs)
    
    def __setattr__(self, *args, **kwargs):
        print('某个属性被设置')
        return object.__setattr__(self, *args, **kwargs)
    
    #删除属性
    def __delattr__(self, *args, **kwargs):
        return object.__delattr__(self, *args, **kwargs)
    
    #增加对with(上下文管理 类似Java中的finally)的支持
    #进入with块中执行的操作
    def __enter__(self):
        print('with开始了')
    
    #退出with块中执行的操作(如果因为异常导致的with块退出 可以从t中获得异常类型,value可以获得异常对象,tb可以获得跟踪信息)
    def __exit__(self, t, value, tb):
        if t != None:
            print('因为此异常导致退出', t);
            print('异常对象', value);
            print('跟踪信息', tb);
        print('with结束了');
    
f = Foo();
f2 = Foo();
print(Foo.clazzproperty);
print(f + 1);
print(Foo(1, 2));
with f:
    raise RuntimeError('测试异常');
