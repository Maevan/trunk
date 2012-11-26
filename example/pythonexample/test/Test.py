# coding: utf-8
'''
Created on 2011-11-25

@author: Lv9
'''
files = ['Main.java', 'JavaBean.java', 'PersonProtos.java'];
print([f for f in files if not str(f).endswith('Protos.java')]);