#coding=utf-8
'''
Created on 2012-6-4

@author: zhaojp
'''
from sqlite3 import connect;

def show(cur):
    print('%20s%20s%20s' % ('symbol', 'shares', 'price'));
    print('-' * 70)
    
    for symbol, shares, price in cur.execute('select * from stocks'):
        print('%20s%20d%20.2f' % (symbol, shares, price));
    print('\n');

conn = connect(':memory:')#detect_types可以实现某些额外类型的检测 可以使register_converter注册的类型生效

cur = conn.cursor();

cur.execute('create table stocks (symbol text,shares integer,price real);');

cur.execute('insert into stocks values(?,?,?)', ('IBM', 50, 91.10));#添加单条记录的方式
cur.execute('insert into stocks values(?,?,?)', ('AAPL', 100, 123.45));

show(cur);

stocks = [('GOOG', 75, 380.13), ('AA', 60, 14.20), ('AIG', 125, 0.19)];#定义一个元组列表

cur.executemany('insert into stocks values(?,?,?)', stocks);

show(cur);

cur.execute('update stocks set shares = ? where symbol = ?', (120, 'IBM'));#更新指定记录

show(cur);

cur.execute('delete from stocks where price < ?', (80,));#删除指定记录

show(cur);

conn.commit();
conn.close();
