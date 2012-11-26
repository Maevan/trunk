# coding: utf-8
'''
Created on 2011-12-5

@author: Lv9
'''

d = {
     'a' : 1,
     'b' : 2,
     'c' : 3,
     'd' : 4,
     'e' : 5,
     'f' : 6,
     'g' : 7,
     'h' : 8,
     'i' : 9,
}
print(len(d));#返回map的长度
print('f' in d);#类似Java中的java.util.Map的containsKey函数
print(d.get('b', None));#类似Java中的java.util.Map的get函数
print(d.pop('h', None));#返回键对应的值 如果无法找到该键对应的值 并且没有提供default值 则会触发KeyError
print(d.popitem());#从map中随机删除一个元素并返回该元素的键值对
print(d.setdefault('j', 10));#在map中找到指定的元素并返回 如果找不到指定的元素则返回default 并且将map[key]的值设置为default指定的值
print(d.update({'k' : 11, 'l' : 12}));#将参数中提供的字典全部更新进调用者得字典中
print(d['b']);#与get的区别是 如果找不到指定的元素就会抛出异常
print(d.copy());#返回一个Map中的副本
print(d.items());#返回由(key,value)对组成的序列
print(d.keys());#返回由key组成的序列
print(d.values());#返回由value组成的序列

del d['i'];#类似Java中的java.util.Map的remove函数
d['i'] = 99;#类似Java中的java.util.Map的put函数
d.clear(); #清理掉map中的全部元素
