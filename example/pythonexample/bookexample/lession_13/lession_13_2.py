#coding=utf-8
'''
Created on 2012-3-8

@author: zhaojp
'''

import copy;

class Person(object):
    def __init__(self, name, age, friend=None):
        self.name = name;
        self.age = age;
        
        if(friend and type(friend) is Person):
            self.friend = friend;
            
    def __str__(self, *args, **kwargs):
        result = 'name:' + self.name + ",age:" + self.age;
        if(self.friend):
            result += ',friend:';
            result += self.friend;
        return result;

p1 = Person('Lv9', 20);
p2 = copy.copy(p1);#浅复制
friend = Person('Lv15', 20);

print(p1 == p2);
print(p1.name is p2.name, p1.age is p2.age);

p1 = Person('Lv9', 20, friend);
p2 = copy.copy(p1);

print(p1 == p2, p1.friend == p2.friend);
print(p1.name is p2.name, p1.age is p2.age);

p1 = Person('Lv9', 20, friend);
p2 = copy.deepcopy(p1);

print(p1 == p2, p1.friend == p2.friend);
print(p1.name is p2.name, p1.age is p2.age);