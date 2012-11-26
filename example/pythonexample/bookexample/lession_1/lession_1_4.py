# coding: utf-8
'''
Created on 2011-11-28

@author: Lv9
'''
file = "D:/foo.txt";
f = open(file); # 返回一个文件对象
line = f.readline();

while line:
    print(line,end='');#print(line,end='') 本身是可以这样指定结束符的 但是有点问题就是PyDev的语法校验还沿袭Python2.x的校验规则 操
    line = f.readline();#读取一行的内容 包括结尾的换行符
f.close();

f = open(file);

#也可以用迭代来读取内容
for line in f:
    print(line);
f.close();

f = open(file, mode="w");#指定模式为w 该模式下可以向文件写入信息
#line = sys.stdin.readline();#从标准输入流里读取数据
line = input("请输入任意字符:");#通过input函数操作标准输入流
f.write(line);
f.close();
