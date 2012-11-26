# coding: utf-8
'''
Created on 2011-12-28

@author: Lv9
'''

#可以通过设置__slots__元组来限制对合法实例属性名称的设置
#使用__slots__的目的不是为了安全 实际上是对内存和执行速度的一种优化
#使用__slots__的实例不再使用__dict__来存储实例数据 相反 会使用基于元组的更加紧凑
#的数据结构在会创建大量对象的程序中 使用__slots__可以显著减少内存占用和执行时间
class Foo(object):
    __slots__ = ('name', 'balance');
    
    def __init__(self):
        pass;
   
#Foo.name = 'Lv9';
#Foo.balance = 800.00;
#Foo.age = 20; 
 
f = Foo();
f.name = 'Lv9';
f.balance = 800.00;
# f.bar = lambda a, b:a + b; 不仅仅是属性 函数貌似也无法添加
# f.age = 20; 发生异常 因为这个名称不存在于slots里

print(dir(f)); #注意 没有__dict__属性 只有__slots__属性
