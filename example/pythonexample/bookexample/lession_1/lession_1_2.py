# coding: utf-8
'''
Created on 2011-11-28

@author: Lv9
'''
principal = 1000;
rate = 0.05;
numyears = 5;
year = 1;
f = "{0:3d} {1:0.2f}";# :前的数字代表具体的参数 比如0代表参数0,1代表参数1 :后面代表格式
while year <= numyears:
    principal = principal * (1 + rate);
    # print(year, principal); 
    # print("%3d %0.2f" % (year, principal));
    # print(format(year, "3d"), format(principal, "0.2f"))
    print(f.format(year, principal));
    year += 1;
print("in loop");#Python的代码块是根据缩进来表示的 不像Java是用大括号 可以通过调整这行代码的缩进选择代码是属于while循环的代码块还是外围的代码  
