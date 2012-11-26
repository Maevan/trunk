# coding: utf-8
'''
Created on 2011-12-14

@author: Lv9
'''
#下面这两行代码都是在以普通模式运行的时候才会执行 而最优模式则不会执行(这些代码在最优模式的时候会被忽略 而在DEBUG模式则会内联 比如'if __debug__'这样的判断会消失)
#开启最优模式的方法 在启动的时候加上参数选项 -O
#可以通过__debug__这个内置变量来判断
if __debug__:
    print('is debug');

#类似Java的断言
def write_data(file, data):
    assert file, 'write_data: file not defined!'; 

