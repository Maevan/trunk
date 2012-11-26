# coding: utf-8
'''
Created on 2011-12-5

@author: Lv9
'''

class T(object):
    def __init__(self):
        self.count = 0;
        self.__class__.count = 0;
        
    def foo(self, c):
        self.count += c;
        print(self.count);
    
    #加上classmethod注解的方法才是类方法 类方法与静态方法的区别是类方法可以操作类属性 而静态方法则什么属性都无法操作
    @classmethod
    def bar(cls, c):
        cls.count += c;
        print(cls.count);
    
    @staticmethod
    def hello():
        print("Hello World");

def foo(arg1=None, arg2=None):
    print(arg1, arg2);
t = T();
t2 = T();

t.bar(1);
t2.bar(1);

t.foo(1);
t2.foo(1);
func = lambda x, y : x + y; #可以用lambda运算符在模块级别创建可调用对象
func2 = foo;
func3 = t2.foo;#绑定方法 方法本身持有一个对象实例
func4 = T.foo;#非绑定方法  对象方法或者类方法需要手动传入对象或者类才能够调用

print(func(10, 20));
print(func2.__name__);
print(func2.__defaults__);
print(func3(1));
print(func4(t2, 1));
print(func3.__self__);#可以通过这种方式获得函数对象绑定的实例
