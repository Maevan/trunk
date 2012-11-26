#coding=utf-8
'''
Created on 2012-5-10

@author: zhaojp
'''
# 插入记录用的语句
# insert into portfolio(account,name,shares,price) values(40,'Lv9','中石化','百')

from sqlite3 import connect;

#将每一列的数据转换为字典
def generate_dicts(cur):
    fieldnames = [d[0].lower() for d in cur.description];
    return [dict(zip(fieldnames, row)) for row in cur];


conn = connect("F:/Database/SQLite3/PythonTest.sqlite");#创建数据库连接并且获得Connection对象
cur = conn.cursor();#获取Cursor对象

cur.execute('select name,shares,price,account from portfolio');#通过指定query语句填充游标
print(cur.rowcount); #表示由一种execute*()方法生成的结果中的行数 意味着既没有结果集 行数也不能确定
print(cur.description);#提供当前查询中返回结果集的列信息
print(cur.arraysize);#为fetchmany(size)的size提供一个默认值
#遍历游标的一种方式
while True:
    row = cur.fetchone(); 
    if not row:break;
    name, shares, price, account = row;
    print(name, shares, price, account);
    
# 另一种方式
# for name, shares, price, account in cur:
#     print(name, shares, price, account);

cur.execute("select name,shares,price,account from portfolio where name = ? and account > ?", ('Lv9', 20));# 可以通过?做条件查询

row = cur.fetchone(); #返回由execute()或executemany()生成的下一行结果集 这一结果通常是列表或者元组 包含结果集种不同列的值 如果没有更多的行则返回None
if row is not None:
    name, shares, price, account = row;
    print(name, shares, price, account);
    
cur.execute('select name,shares,price,account from portfolio');

l = generate_dicts(cur);

for d in l:
    print(d['name'], d['shares'], d['price'], d['account'])

# cur.callproc(procname,[,parameters]) 调用存储过程
# cur.fetchmany([size]); 返回结果行的序列 size是要返回的行数 如果省略 cur.arraysize的值就会作为默认值使用 实际返回的行数可能比请求的少 如果没有更多的行则返回空的序列
# cur.fetchall() 返回全部剩余结果行的序列(例如元组列表)
# cur.nextset()  放弃当前结果集中的所有剩余行 跳至下一个结果集(如果有) 如果没有更多的结果集 返回None 否则返回True 接着通过fetch*() 操作从新集合中返回数据(Python3中无此函数)
cur.close();
conn.close(); #关闭数据库的连接


