#coding= utf-8
'''
Created on 2012-1-29

@author: Lv9
'''
import sys;

#读取命令行选项参数1默认为程序所在位置(通过python命令运行指定脚本 一般都是 python xxx.py arg1 arg2.... 所以第一个参数必然是程序所在位置)
print(len(sys.argv));
if len(sys.argv) != 3:
    sys.stderr.write("Usage : python %s inputfile outputfile\n" % sys.argv[0]);
    raise SystemExit(1);

inputfile = sys.argv[1];
outputfile = sys.argv[2];

print("inputfile:%s,outputfile:%s" % (inputfile, outputfile));
