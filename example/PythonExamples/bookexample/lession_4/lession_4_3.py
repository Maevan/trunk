# coding: utf-8
'''
Created on 2011-12-12

@author: Lv9
'''
print('{0} {1} {2}'.format('GOOG', 100, 490.10));#可以根据位置填充
print('{name} {shares} {price}'.format(name='GOOG', shares=100, price=490.10));#也可以根据键来填充
print('Hello {0},your age is {age}'.format('Lv9', age=20));#也可以混着来- -!
print('Use {{ and }} to output single curly braces'.format());#由于大括号在format里有其他意义 所以如果想输出大括号 就要连续输出两次{{}}

class T(object):
    def __init__(self):
        self.a = 'A!!!!!';
        self.b = 'B!!!!!';

stock = {
         'name':"Lv9",
         'shares':'100',
         'price' : 490.10
};


print('{0[name]} {0[shares]} {0[price]}'.format(stock)); #也可以直接获取字典参数里的某个值..
print('{0.a} {0.b}'.format(T()));#也可以获取对象的属性..