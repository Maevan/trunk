#coding=utf-8
'''
Created on 2012-4-24

@author: zhaojp
'''
from collections import namedtuple;

#namedtuple用于创建tuple的子类 在tuple中可以使用属性名称来访问元组元素 用这个可以替代任何简单的数据结构对象(类似JavaBean之类的对象)
#基本使用方法与普通的元组一样 使用例子

NetworkAddress = namedtuple("NetworkAddress" , ["host", "port"]);

address = NetworkAddress("www.python.org", 80);

print(address.host);
print(address.port);

host, port = address;

print(len(address));
print(type(address));
