# coding: utf-8
'''
Created on 2011-11-25

@author: Lv9
'''

m = {'a' : 'annotation', 'b' : 'basic', 'c' : 'control'};

print('map[\'a\']            ' + m['a']);
print('map[\'c\']            ' + m['c']);
for key in m:
    print("Key is %s and value is %s" % (key, m[key]))
