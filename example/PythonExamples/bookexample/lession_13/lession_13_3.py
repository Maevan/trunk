#coding=utf-8
'''
Created on 2012-3-8

@author: zhaojp
'''
import gc;

i = 1;
# print(gc.get_referrers(i));
d = dict();
d["i"] = i;

# print(gc.get_referrers(i));#返回直接引用指定对象的所有对象列表
print(gc.get_referents(d));#返回指定对象直接引用的所有对象列表
print(gc.get_threshold());
print(gc.garbage);#这个变量列出不再使用的用户定义实例
print(gc.get_count());#返回一个元组 它包含每个生成物中当前的对象数
print(gc.get_debug());#返回当前设置的调试标志
print(gc.get_threshold());#返回元组形式的当前手机收集阙值
print(gc.isenabled());#如果启用了垃圾收集则返回True
