# coding: utf-8
'''
Created on 2011-11-29

@author: Lv9
'''

#初始化字典 用大括号包起来是MAP
stock = {
         "name" : "GOOG",
         "shares" : 100,
         "price" : 490.10
}
# stock = {} 一个空字典
# stock = dict() 一个空字典

name = stock["name"];#访问字典成员 用中括号("[]") 包关键字
value = stock["shares"] * stock["price"];

print(name, value);

stock["shares"] = 75;#修改
stock["date"] = "June 7,2007"#插入

#如果指定的key不存在字典中会出现错误 可以通过in运算符测试某个内容项是不是字典成员
if "shares" in stock:
    print(stock["shares"]);
else:
    print(0.0);
#也可以通过get避免错误 当无法通过KEY找到元素的时候返回指定的默认值
print(stock.get("sharess", 0.0));
print(stock.get("sharess"));#不指定默认值的话就返回None

key_set = list(stock);#通过list函数获得一个关键字列表
for key in key_set:
    print(key);


del stock["date"];#删除字典中的元素
