#coding=utf-8
'''
Created on 2012-10-11

@author: zhaojp
'''

class Foo(object):
    def __init__(self, name, age):
        self.name = name;
        self.age = age;

    def __getattr__(self, name):
        print('hello __getattr__');
        
        try:
            return object.__getattribute__(self, name);
        except AttributeError as e:
            print(e);
    
    #当__getattribute__中抛出AttributeError时 会调用__getattr__
    def __getattribute__(self, name):
        print('hello __getattribute__');
        try:
            return object.__getattribute__(self, name);
        except AttributeError as e:
            raise e;

f = Foo('Lv9', 22);

print(f.name);
print(f.sex);
print(dir(type))
