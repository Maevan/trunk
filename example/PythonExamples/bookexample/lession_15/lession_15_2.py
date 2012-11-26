#coding=utf-8
'''
Created on 2012-4-17

@author: zhaojp
'''
import array;
from inspect import getsourcefile;

def func():
    pass;

# 创建一个数组 可以用来做IO操作中的字节操作 第一个参数为typecode 强调两个有用的 一个是b(字节) 另外一个是u(Unicode字符) 其他的还有i(int) l(long) 这俩容易受到操作系统影响导致
# 对应的字节长度不同 另外还存在一些无符号的typecode 比如B(无符号字节)

a = array.array('b', []);#初始化数组 并且指定一个序列作为初始化值

a.fromstring("Hello World");#将字符串转换为字节追加到数组
a.append(77);#追加一个元素到数组
a.extend([78, 79]);#将一个数组或者可迭代对象追加到数组
a.byteswap();#在大尾和小尾之间切换数组中所有项目的字节顺序 仅支持整型值
a.tofile(open("D:/file", mode="wb"));
a.fromfile(open(getsourcefile(func), mode='rb'), 2);

print(a.typecode);#返回数组的类型
print(a.itemsize);#存储数组中的元素大小(按字节算 比如byte就是1 而int可能是4或者8)
print(a.buffer_info());#返回(address,length) 提供用于存储数组的缓冲区的内存位置和长度
print(a.count(1));#返回指定元素的出现次数
print(a.index(77));#返回指定元素首次出现在数组中的索引 如果没出现则抛出异常
print(a.pop(0));#删除指定位置元素并返回 如果指定元素不存在则删除数组中最后一个元素
print(a.remove(77));#从数组中删除第一个指定值的元素 如果未找到则抛出ValueError
print(a.tostring());#将指定元素转换为String类型
print(a.tolist());#将数组转换为普通的值列表
# print(a.tounicode()); 将数组转换为Unicode 如果数组类型不为u 则抛出ValueError
