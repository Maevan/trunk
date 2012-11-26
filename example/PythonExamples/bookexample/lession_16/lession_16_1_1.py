#coding=utf-8
'''
Created on 2012-5-4

@author: zhaojp
'''
from codecs import lookup;

c = lookup('utf-8');#返回一个CodeInfo的对象

ub = c.encode('操你大爷');#使用指定的字符集编码 返回一个元组 位置0为被编码后的字符串 位置1则是编码前的字符长度
print(c.decode(ub[0]));#将指定的字符串解码 返回一个元组 位置0为解码后的字符串 位置1是解码前字节长度

rb = open('foo', mode='rb');
wb = open('bar', mode='wb');

reader = c.streamreader(rb);#返回一个StreamReader实例 该实例用于读取已编码的数据

print(reader.readline());#返回一行已解码文本
print(reader.read(chars=10));#返回已解码文本中剩余的全部字符 最多chars个字符
print(reader.readlines());#返回已解码文本中剩余的全部字符 按行生成列表

writer = c.streamwriter(wb);

writer.write('我擦泪个去');
writer.writelines(['下午开会\r\n', '明天放假']);

rb.close();
wb.close();
