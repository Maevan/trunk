#coding=utf-8
'''
Created on 2012-2-14

@author: zhaojp
'''
import doctest;
import bookexample.lession_11.totests.add as totest;

nfails, ntests = doctest.testmod(totest, verbose=True);#通过testmod函数使用单元测试 verbose属性会生成详细的测试报告

print('fails:', nfails, 'ntests:', ntests);
