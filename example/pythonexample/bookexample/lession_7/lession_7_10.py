# coding: utf-8
'''
Created on 2011-12-28

@author: Lv9
'''
#实例的创建分两个步骤:使用特殊方法__new__()创建新实例 然后使用__init__()方法初始化该实例
class Circle(object):
    def __init__(self, radius):
        self.radius = radius;
    
# c = Circle(4.0);实际执行的步骤如下

c = object.__new__(Circle);
print(dir(c));#注意这个时候radius尚未被初始化
if isinstance(c, Circle):
    c.__init__(4.0);

print(dir(c));

