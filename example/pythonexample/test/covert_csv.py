#coding=utf-8
'''
Created on 2012-7-13

@author: zhaojp
'''

writer = open("D:/rtz.csv", mode='w');
reader = open("D:/rtz", mode='r');

for line in reader:
    print(line)
    print(dir(line))
    break;
    