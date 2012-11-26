#coding=utf-8
'''
Created on 2012-8-8

@author: zhaojp
'''
from os import listdir;
from re import findall;

print(listdir('F:/Libs/FusionCharts_Evaluation'))
print([f for f in listdir('F:/Libs/FusionCharts_Evaluation') if len(findall('FusionCharts', f))])