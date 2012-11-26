#coding=utf-8
'''
Created on 2012-10-18

@author: zhaojp
'''

from socket import AF_INET, SOCK_STREAM, IPPROTO_TCP, NI_NUMERICSERV, has_ipv6;#常量
from socket import create_connection, fromfd;#创建链接
from socket import getaddrinfo, gethostbyname, gethostbyname_ex, gethostname, getnameinfo, getprotobyname, getservbyname, getservbyport; #地址转换
from socket import getdefaulttimeout, getfqdn;

connection = create_connection(('www.bing.com', 80));#创建套接字对象
connection = fromfd(connection.fileno(), AF_INET, SOCK_STREAM);#根据文件描述符创建对象
connection.close();


print(getaddrinfo('www.baidu.com', 80, AF_INET, SOCK_STREAM));#获得地址信息 第一个参数是主机名 第二个参数可以是应用协议名称字符串 也可以是具体的端口 第三个参数是地址族 第四风格参数是套接字类型
print(gethostbyname('baidu.com'));#根据主机名获得IPV4地址 不支持IPV6
print(gethostbyname_ex('www.python.org'));#将主机名转换为IPV4地址 但返回元组(hostname,aliaslist,ipaddrlist)
print(gethostname());#返回本地机器的主机名
print(getnameinfo(('82.94.164.162', 80), NI_NUMERICSERV));#根据ip地址获得主机名称 第二个参数是一些没啥用的选项(比如指定的查询条件针对什么地址族 或者用什么形式返回地址 或返回应用协议名何曾还是应用协议约定的端口)
print(getprotobyname('tcp'), IPPROTO_TCP);#根据协议名称返回协议编号以便传递socket函数的第三个参数
print(getservbyname('http', 'tcp'));#根据提供的应用协议名称获得协议默认的端口号 可以在第二个参数指定传输层协议
print(getservbyport(23));#根据端口号获得协议名称

print(getdefaulttimeout());#获得默认超时时间
print(getfqdn('foo'));#返回完全限定域名name 如果忽略name 那么假定为本机器

print(has_ipv6);