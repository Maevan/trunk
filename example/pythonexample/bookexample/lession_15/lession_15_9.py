#coding=utf-8
'''
Created on 2012-5-2

@author: zhaojp
'''

from operator import attrgetter;
from operator import itemgetter;

class Foo:
    def __init__(self):
        pass;
    
get = attrgetter("__dict__", "__class__");
iget = itemgetter("name", "age");

print(get(Foo()));
print(iget({"name":"Lv9", "sex":"Man", "age":21}));
