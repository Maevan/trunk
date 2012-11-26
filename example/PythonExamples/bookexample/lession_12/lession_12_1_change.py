#coding=utf-8
'''
Created on 2012-2-15

@author: zhaojp
'''
print(chr(90));#将指定的整数转换为字符(不光是ASCII字符)
print(float('1.1'));#将指定的参数转换为浮点数 如果参数是字符串型 则转换为字符串描述的浮点数 如果参数是整型 则直接转换为浮点数 不提供任何参数则返回0
print(ascii('Hello World!赵'));#将字符串中非ASCII字符全部转换为合适的转换序列
print(hash('测试'));#返回对象的hashcode
print(bin(0xFF));#将指定的整数转换为2进制的字符串
print(bytearray([1, 11, 111]));#创建一个长度可变字节数组
print(bytearray('你好', 'UTF-8'));#创建一个长度可变字节数组(根据字符串和指定字符集)
print(bytes([1, 11, 111]));#创建一个长度固定的字节数组
print(bytes('你好', 'UTF-8'));#创建一个长度固定的字节数组(根据字符串和指定字符集)
print(abs(-1));#求某个数的绝对值
print(divmod(5, 2));#返回被除数与除数的商和余数(返回结果类型为元组)
