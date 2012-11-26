# coding: utf-8
'''
Created on 2011-12-14

@author: Lv9
'''

#默认定义函数的方式
def add(x, y):
    return x + y;

#可以指定函数参数列表的默认值
def split(line, delimiter=','):
    return line.split(delimiter);

#但是这个操作有的时候比较危险 默认参数保留了前面调用进行的修改 为了防止出现这种情况最好使用None值 
def foo(x, items=[]):
    items.append(x);
    return items;

#如果在参数前面加上*号 就可以传递任意数量的参数 函数内部这些参数则成为了元组
def fprintf(fmt, *args):
    return 'type is ' + str(type(args)) + ':' + fmt % args;

#如果在参数前面加上**号 也可以传递任何数量的参数 并且为每个参数提供一个键去获得 函数内部这些参数成为了字典
def make_table(data, **params):
    return data + str(params['age']) + "  " + str(params['name']) + "  " + str(params['height']);

#可变参数和关键字参数可以一起使用 关键字参数放到后面即可
def bar(*args, **kwargs):
    pass;

print(foo(1));
print(foo(2));
print(foo(3));#结果是[1,2,3]..把头两次进行的操作都给保存了
print(fprintf("%d %s %f", 20, 'Lv9', 1.85));
print(fprintf("%d %s %f", *(20, 'Lv9', 1.85)));#也可以直接传递元组进这个函数 只要在元组前面增加*号即可 比如 在元组变量args前加上 *args
print(add(x=10, y=10));#调用普通函数的时候也可以通过传递这种名值对来强调自己为哪个参数进行赋值
print(make_table('字典输出 ', age=20, name='Lv9', height=1.85));
print(make_table('字典输出 ', **{'age':20, 'name':'Lv9', 'height':1.85}));#也可以直接传递字典进这个函数 只要在字典前面增加**号即可 比如 在字典变量args前加上* *args
