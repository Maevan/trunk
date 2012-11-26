# coding: utf-8
'''
Created on 2011-12-29

@author: Lv9
'''
#可以通过元类强制限制用户对对象的定义
from types import FunctionType
class DocMeta(type):
    
    #可以通过__new__修改使用该元类的子类的行为
    def __new__(cls, name, bases, dicts):
        dicts["bar"] = DocMeta.bar(name);
        return type.__new__(cls, name, bases, dicts);#结尾处用type的__new__函数创建对象
    
    def __init__(self, name, bases, dicts):
        #获得某个对象的全部属性 并且判断每个属性是否有写注释
        for key, value in dicts.items():
            if(key.startswith("__") and type(value) is FunctionType):
                continue;
            if not getattr(value, "__doc__"):
                raise TypeError("%s must have a docstring" % key);
        
    @staticmethod    
    def bar(name):
        def __call__():
            """Print"""
            print("Hello World", name);
        
        return __call__;
    
class Documented(metaclass=DocMeta):
    def __init__(self):
        pass;
    def foo(self):
        """强制我必须声明一个注释"""
        pass;

d = Documented();
print(dir(Documented));
print(dir(d));
Documented.bar();
Documented.__dict__['bar']()
#print(Documented.__dict__['foo']());
#d.bar();