# coding: utf-8
'''
Created on 2011-12-22

@author: Lv9
'''

class TypedProperty(object):
    def __init__(self, name, ptype, default=None):
        self.name = "_" + name;
        self.type = ptype;
        self.default = default if default else ptype();
        
    def __get__(self, instance, cls):
        return getattr(instance, self.name, self.default);

    def __set__(self, instance, value):
        if not isinstance(value, self.type):
            raise TypeError("Must be a %s" % self.type);
        setattr(instance, self.name, value);
        
    def __delete__(self, instance):
        raise AttributeError("Can't delete attribute");

class Foo(object):
    name = TypedProperty("name", str);
    num = TypedProperty("num", int, 42);  
    
f = Foo();
print(dir(Foo));
print(dir(f));
print(type(f.name));
f.name = '1';

print(f.name);