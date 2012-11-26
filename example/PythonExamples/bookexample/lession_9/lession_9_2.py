#coding= utf-8
'''
Created on 2012-1-30

@author: Lv9
'''
# 可以通过os.environ访问环境变量

import os;

print(os.environ['path']);
print(os.environ['java_home']);
print(os.environ['class_path']);

# 顺带一提 可以通过os.environ修改环境变量 修改环境变量会影响到当前运行的Python程序和此程序创建的子进程
# os.environ['FOO'] = 'BAR';
