#coding=utf-8
'''
Created on 2012-6-6

@author: zhaojp
'''

from tarfile import open as taropen;

tfile = taropen('D:/test.tar.gz', 'w:gz');#打开一个tarfile输出流 压缩格式为gz

tfile.debug = 3;#设置生成调试信息级别 0为不生成 3生成全部调试信息
tfile.errorlevel = 3;#设置错误级别 如果为0则忽略全部错误 如果设置为1 错误将会导致IOError或OSError 

tfile.add('F:/LIBS/checkstyle-5.5/README', 'README');
tfile.add('F:/LIBS/checkstyle-5.5/commons-logging-1.1.1.jar', 'jar/commons-logging-1.1.1.jar');#添加一个文件到压缩文件中 并给添加到压缩文件中的文件起别名或者更改存储路径
tfile.add('F:/LIBS/checkstyle-5.5/checks', 'checks', True);#将checks目录中全部的内容递归添加到压缩文件中

tfile.close();#关闭tar归档文件

tfile = taropen('D:/test.tar.gz', 'r:gz');#打开一个tarfile输入流 压缩格式为gz

tfile.extract('jar/commons-logging-1.1.1.jar', 'D:/');#从归档文件中提取出一个成员输出到指定的目录中

tme = tfile.getmember('README');#查找归档成员name 返回包含相关信息的TarInfo对象
fin = tfile.extractfile('README');#从归档文件中提取成员 返回一个只读类文件 内容能够通过read() readline() readlines() seek() 和 tell()操作读取 member可以是文档成员的名称 或是TarInfo实例
names = tfile.getnames();#返回归档文件的成员名称列表
members = tfile.getmembers();#返回所有归档文件成员的TarInfo对象列表

for name in names:print(name, end=' ');
print('\n', '-' * 30)
for member in names:print(member , end=' ')
print('\n', '-' * 30)
print(fin.readlines());
print('\n', '-' * 30)
tfile.close();
