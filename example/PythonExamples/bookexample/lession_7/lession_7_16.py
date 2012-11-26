# coding: utf-8
'''
Created on 2011-12-29

@author: Lv9
'''
# 一旦代码多起来 我们就会萌生创建类文件的想法 类的创建是由一种称作原类的特殊对象控制的 简言之 原类就是知道如何创建和管理类的对象
# 在下面的例子中 控制Foo创建原类的是一个名为type的类 实际上 如果查看Foo类型 将会发现它的类型为type
# 可以通过metaclass指定元类 具体方式参考lession_7_15;
# 如果没有显示的指定元类 class语句将检查基类元组(如果存在)中的第一个条目 在这种情况下 元类与第一个基类的类型相同 
# class Foo():pass;
# print(type(Foo));
class_name = ""
class_parents = (object,);#另外 当一个元组内只有一个值的时候 他实际上就不是元组类型 这里需要注意下
class_dict = {};

class_body = """
def __init__(self, x):
    self.x = x;

def __blah__(self):
    print('Hello World');
"""

exec(class_body, globals(), class_dict);

Foo = type(class_name, class_parents, class_dict);

f = Foo(1);
f.__blah__();

