#coding=utf-8
'''
Created on 2012-2-15

@author: zhaojp
'''

class Foo(object):pass;

#通过classmethod将指定的函数对象创建为类方法创建类方法 相当于@classmethod装饰器
Foo.bar = classmethod(lambda cls, name:print(cls.__name__, 'Hello World!', name));
Foo.bar('Lv9');

#通过compile编译代码 第一个参数为源代码 如果代码有多行 需要用\n换行 不区分具体操作系统 第二个参数为源代码所在的文件路径 第三个参数为编译模式
# exec 是执行被编译的全部语句  eval 只支持运行单独的一个表达式  single代表一条可执行语句
exec(compile("print('Hello World');", "", "exec"));
print(hasattr(Foo,'bar'));#判断执行的属性是否存在指定的元素中
delattr(Foo, 'bar');#删除对象的属性
print(dict(name='Lv9', sex='Man'));#创建字典
print(dir(Foo));#返回指定值的属性列表
print([(a, b) for a, b in enumerate("Hello")]);#基于一个序列创建一个新迭代器 他产生的元组包含一个记数和一个根据iter产生的元素
