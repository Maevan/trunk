# coding: utf-8
'''
Created on 2011-11-28

@author: Lv9
'''

a = 1;
b = 2;

product = "game";
productType = "pirate memory";
age = 5;

suffix = ".png";

s = "Lv9 is a cool kid"

hasLv9 = True;#Python的布尔值分别为True和False

if a < b:
    print("Computer says yes");
else:
    print("Computer says no");

if a < b:
    pass;#空子句是不允许的 要创建一条空子句可用pass关键字
else:
    print("Computer says no");


#使用or、and和not关键字可以建立布尔类型的表达式('\'可以在下一行继续书写上一条语句的内容 当单行语句过长时可以用这个将其分开)
if product == "game" and productType == "pirate memory" \
                     and not (age < 4 or age > 8):
    print("I'll take it");
else:
    pass;

#Python没有switch语句 只能用elif完成(相当于Java里的else if)
if suffix == ".htm":
    print("text/html");
elif suffix == ".jpg":
    print("image/jpeg");
elif suffix == ".png":
    print("image/png");
else:
    raise RuntimeError("Unknown content type");

'''
 in关键字可以在一个指定的数组、MAP、字符串中去寻找指定的关键字
 所有的关系运行符 返回的结果都是布尔类型结果(True或False)
'''
if 'Lv9' in s:
    hasLv9 = True;
else:
    hasLv9 = False;
