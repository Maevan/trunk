# coding: utf-8
'''
Created on 2011-11-25

@author: Lv9
'''
words = ['a', 'b', 'c', 'd', 'e'];
print('words                    ' + str(words));
print('words[2]                 ' + str(words[2]));
print('words[1:]                ' + str(words[1:]));
print('words[:4]                ' + str(words[:4]));
print('words[0:1]               ' + str(words[0:1]));
print('words[-1]                ' + str(words[-1]));#The last element of word
print('words[-2]                ' + str(words[-2]));
print('words[-3:-1]             ' + str(words[-3:-1]));
print('words + [\'f\', \'g\']       ' + str(words + ['f', 'g']));

del words[0];
print('del words[0]             ' + str(words));
del words[0:2];
print('del words[0:2]           ' + str(words));

