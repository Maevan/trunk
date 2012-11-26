# coding: utf-8
'''
Created on 2011-11-28

@author: Lv9
'''
names = ["Dave", "Make", "Ann", "Phil"];#列表 相当于JAVA中的列表(List) 先说明 这个不是数组 用中括号('[]')包起来是列表
names[0] = "Jeff";#可以通过位置获取元素或者设置元素 另外 这个列表本身没有类型限制 与变量名称一样 一个列表内可以存储多种类型的值
names.append("Paula");#将指定元素追加到列表末尾
names.insert(2, "Thomas");#将指定元素插入到指定位置

#使用切片运算符可以提取一个子列表或者对子列表重新赋值
b = names[0:2];#返回['Jeff', 'Make']
c = names[2:];#返回['Thomas', 'Ann', 'Phil', 'Paula']
names[0:2] = ['Dave', 'Mark', 'Jeff'];#将列表的头2项替换成右边的集合
names = names + ['Bob', 'Tony'];#通过(+)运算符可以连接列表

# names = list(); 创建一个空列表
# names = [];#创建一个空列表
# names = [1, "Dave", 3.14, ["Mark", 7, 9, [100, 101]], 10];#列表可以包含任意种类的Python对象 也包括其他的对象(访问的时候类似Java的二维数组)

print(b);
print(c);
print("The minimum value is ", min(names));#打印列表中的最小值
print("The maximum value is ", max(names));#打印列表中的最大值

strs = ["1", "2", "3", "4"];

print("The minimum value is ", min(strs));#打印列表中的最小值
print("The maximum value is ", max(strs));#打印列表中的最大值

integers = [int(line) for line in strs];#将strs中的字符串转换为整型并添加到一个集合中 这种强大的构造方式叫列表包含
print(strs);
print(integers);
