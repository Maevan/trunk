# coding: utf-8
'''
Created on 2011-12-2

@author: Lv9
'''
import copy;

a = [1, 2, 3, 4, [5, 6]];
b = a;

b[0] = '1';

print(a, a is b);#当a和b引用指向同样的对象的时候 更改a同时也会更改b

b = list(a);

b[0] = 1;

print(a, a is b);#执行浅copy后 重新创建了一个对象 但是仅仅是对第一层进行了简单的copy 保存了a列表中元素的引用

b[4][0] = '5';

print(a, a is b);#a和b中的特定类型的元素的引用指向的依旧是同一个对象

b = copy.deepcopy(a);#执行深copy 创建一个新对象并且递归的复制它包含的所有对象 没有内置操作可以创建对象的深拷贝 但是可以使用标准库的copy.deepcopy()函数完成该工作 如下例所示:

b[4][0] = 5;

print(a, a is b);#a和b中的列表元素都被重新创建了 虽然b中列表元素的值在更改之前与a相同 但是深拷贝是全部重新创建一次
