# coding: utf-8
'''
Created on 2011-11-28

@author: Lv9
'''
stock = ('GOOG', 100, 490.10);#元组内可以定义多种类型的变量 相当于Java的数组,只不过元组一旦被创建就不能修改它的内容(无法替换、删除现有元组中的元素或插入新元素) 用小括号包('()')起来是元组
address = ('www.python.org', 80);#最好把元组看成一个由多个部分组成的对象,而不是可再其中插入或删除项的不同对象的集合
persion = ('X', 'Lv9', '13522921150');
# stock = 'GOOG', 100, 490, 10; 这样也可以初始化元组 只不过不太好
a = ();#0元素的元组
b = (1,);#1元素的元组
c = 1, ;#1元素的元组

name, shares, price = stock;#可以通过这种形式将元组中的数据提取出来初始化变量
host, port = address;#需要注意的是元组的元素必须与被初始化的变量数量一样
print(name, shares, price, sep=",");
print(host, port, sep=",");

data = "GOOG,100,490.1";
fields = data.split(',');#与Java中String对象的split查不多 只不过不知道字符是被作为正则分割还是字符去分割

name = fields[0];
shares = int(fields[1]);
price = float(fields[2]);

stock2 = (name, shares, price);


stocklist = (stock, stock2);

print('name', 'shares', 'prince');
#可以通过这种途径将数组中的数组(二维数组) 展开并赋值给一些属性
for name, shares, prince in stocklist:
    print(name, shares, prince);
