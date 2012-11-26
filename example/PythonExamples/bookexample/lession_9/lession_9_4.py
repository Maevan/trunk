#coding= utf-8
'''
Created on 2012-2-5

@author: Lv9
'''
# sys.stdin 标准输入
# sys.stdout 标准输出
# sys.stderr 错误输出

# sys.stdout.write("Enter your name:");
# name = sys.stdin.readline();

try:
    name = input("Enter your name:");#input()可以从标准输入中读取一行文本并可以打印一个提示符 等价于上面的两行代码
except (KeyboardInterrupt | RuntimeError) as e:
    print(e);#键盘中断(ctrl + c)会导致KeybordInterrupt异常

# sys.__stdin__ = otherin; #可以通过其他方式将标准输入、输出、错误流重定向到其他文件对象 另外 一些低级方法无法在标准流中使用 如 seek和read()