#coding=utf-8
'''
Created on 2012-5-30

@author: zhaojp
'''

import sqlite3;
from sqlite3 import connect
from sqlite3 import register_converter
from sqlite3 import enable_callback_tracebacks

def foo(s):
    return int(s);

def compare(i, j):
    if(i > j):
        return 1;
    if(i == j):
        return 0;
    if(i < j):
        return -1;

def validator(code, arg1, arg2, dbname, innername):
    print(code, arg1, arg2, dbname, innername)
    return sqlite3.SQLITE_OK

def phandler():
    print('~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~')

def my_row_factory(cur,data):
    print(cur,data)
    return data

class Averager(object):
    def __init__(self):
        self.total = 0.0
        self.count = 0
    
    def step(self, value):
        self.total += value
        self.count += 1
    def finalize(self):
        return self.total / self.count;

register_converter('decimal', foo)#注册新的类型 攻detect_types使用 第一个参数为类型名称 第二个参数是处理原始类型的函数 这个函数接收单一的字节字符串作为输入
enable_callback_tracebacks(True)#处理在转换器和适配器等用户定义回调函数中出现的异常 默认情况下 忽略异常 如果flag为True 则把异常输出到sys.stderr里

conn = connect('F:/Database/SQLite3/PythonTest.sqlite', detect_types=sqlite3.PARSE_COLNAMES)#detect_types可以实现某些额外类型的检测 可以使register_converter注册的类型生效

conn.create_function('upper', 1, lambda s:s.upper())#创建能在SQL中执行的用户定义函数 第一个参数是函数名称 第二个参数是函数参数个数 第三个参数是函数实现
conn.create_aggregate('py_avg', 1, Averager)#创建能在SQL中执行的聚合函数 头两个参数意义同create_function 第三个参数是执行聚合函数操作的类 此类必须支持不带参数初始化 执行接受与num_params中给出的参数数量相同的step(params)方法 并执行finalize方法返回最后结果
conn.create_collation('mcompare', compare)#自定义排序方法
# conn.set_authorizer(validator)#注册一个授权回调函数 在每次访问数据中的一列数据时执行
# conn.set_progress_handler(phandler,1)#set_progress_handler(handler,n) 注册回调函数每n条SQLite虚拟机指令执行一次 handler是一个没有参数的函数
# conn.row_factory = my_row_factory;#通过替换row_factory自己实现创建每一个结果行的方式 这个函数接收两个参数 一个游标对象 一个带有原始结果行的元组

cur = conn.cursor();
cur.execute('select account as "account [decimal]" from portfolio');

while True:
    row = cur.fetchone();
    if not row:break
    account = row[0];
    print(account)
    
cur.execute('select upper(name) from portfolio')
for name in cur:print(name)

cur.execute('select name,py_avg(account) as avgaccount from portfolio group by name')
for name, avgaccount in cur:print(name, avgaccount)

cur.execute('select name,account from portfolio order by account collate mcompare');
for name, account in cur:print(name, account)

#conn.iterdump()返回一个迭代器 将整个数据库的内容转储到SQL中
for order in conn.iterdump():print(order)

print(conn.total_changes) #一个整数 代表连接自数据库连接打开后发生更改的行数
# 另外 可以通过with自动处理事务 commit是在块中所有的语句没有出现任何异常的时候执行的 如果出现任何异常 则执行rollback()操作后 再提示异常
# with conn:
#     conn.execute('insert into sometable values(?,?)',('foo','bar'))
cur.close();
