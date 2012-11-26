# coding: utf-8
'''
Created on 2011-12-22

@author: Lv9
'''

class Father():
    count = 0;
    
    def __init__(self, name, age, fav):
        self.name = name;
        self.age = age;
        self.fav = fav;
    
    #父类的对象函数会被子类继承
    def getfav(self):
        return self.fav;
    
    #类函数同样会被子类继承
    @classmethod
    def getcount(cls):
        return Father.count;
    
    def saying(self):
        print("I'm Father!");
    
class Son(Father):
    #子类继承父类并不代表子类在实例化的时候会调用父类的__init__函数 需要手动调用 父类的__init__函数会给子类的self属性赋值
    def __init__(self, name, age, sex):
        Father.__init__(self, name, age, '听音乐')#顺带一提 如果跳过父类的__init__函数不执行 可能会导致一些意外的错误 比如继承的父类函数可能因为缺少必要的属性无法使用 
        self.sex = sex;

    #子类覆盖父类方法 顺带一提 Python中重载的方式与Java语言不同
    def saying(self):
        # Father.saying(self); 调用父类方法
        # super(Son, self).saying(); 另一种调用父类方法的方式 super函数返回一个特殊对象 该对象支持在父类上执行属性查找 如果你希望调用以前的实现 无论他是哪个父类调用的
        print("I'm Son!");

class Foo(object):
    fee = 5.00;
    def fooshow_fee(self):
        self.show(self.fee);

class Bar(object):
    fee = 2.50;
    def barshow_fee(self):
        self.show(self.fee);

#Python允许多继承 但是这样会导致多个父类中的属性或者函数冲突的情况 Python的选择是从最特殊到最不特殊的类别去寻找(比如先去寻找派生类中是否存在这个属性 如果派生类不存在则按照继承基类的顺序去逐个寻找 可以通过__mro__函数查看基类的顺序)
class Ruu(Foo, Bar):
    def show(self, fee):
        print(fee);

s = Son('Lv9', 21, '男');
r = Ruu();

s.saying();
print(dir(s));

r.fooshow_fee();
r.barshow_fee();
print(Ruu.__mro__);#表现出了优先级顺序 从左至右 如果出现冲突属性则按照顺序来选择用哪个类中定义的属性
