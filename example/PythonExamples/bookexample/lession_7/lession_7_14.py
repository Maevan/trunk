# coding: utf-8
'''
Created on 2011-12-28

@author: Lv9
'''

class Foo(object):
    def spam(self, a, b):
        pass;

class FooSon(Foo):
    pass;

class FooProxy(object):
    def __init__(self, f):
        self.target = f;
    
    def spam(self, a, b):
        return self.target.spam(a, b);

#重写__instancecheck__和__subclasscheck__可以更改isinstance()与issubclass()针对此对象的行为
#有些时候两个行为相同的类并没有建立实际的继承关系 这个时候可以通过这种方式为两个毫无关系的类建立关系 
class IClass(object):
    def __init__(self):
        self.implementors = set();
        
    def register(self, clazz):
        self.implementors.add(clazz);
    
    def __instancecheck__(self, obj):
        return self.__subclasscheck__(type(obj));
    
    def __subclasscheck__(self, sub):
        return any(c in self.implementors for c in sub.mro());#mro函数返回某个类的全部父类 包括类本身
    
f = Foo();
fs = FooSon();
fp = FooProxy(f);

IFoo = IClass();

IFoo.register(Foo);
IFoo.register(FooSon);
IFoo.register(FooProxy);

#isinstance是判断某个实例是否属于某一类型的 第二个参数可以是实例也可以是类
print(isinstance(f, Foo));#检查f是否是Foo类型
print(isinstance(fs, Foo));#检查fs是否是Foo类型
print(isinstance(fp, Foo));#检查fp是否是Foo类型

#issubclass只能传入class类型比较
print(issubclass(Foo, Foo));#检查Foo类型是不是Foo类型的子类
print(issubclass(FooSon, Foo));#检查Foo类型是不是Foo类型的子类
print(issubclass(FooProxy, Foo));#检查Foo类型是不是Foo类型的子类 

#通过IClass我们可以看出  无论是isinstance还是issubclass都是将第一个参数传递给第二个参数的__instancecheck__或__subclasscheck__方法 所以 实际比较类型都是调用后者重写的方法
print(isinstance(f, IFoo));#检查f是否是Foo类型
print(isinstance(fs, IFoo));#检查fs是否是Foo类型
print(isinstance(fp, IFoo));#检查fp是否是Foo类型

print(issubclass(Foo, IFoo));#检查Foo类型是不是Foo类型的子类
print(issubclass(FooSon, IFoo));#检查Foo类型是不是Foo类型的子类
print(issubclass(FooProxy, IFoo));#检查Foo类型是不是Foo类型的子类 