# coding: utf-8
'''
Created on 2011-12-14

@author: Lv9
'''
class ListTransaction(object):
    def __init__(self, thelist):
        self.thelist = thelist
    
    #初始化函数 当对这个类的对象使用with的时候会执行这个函数 
    #另外"with obj as var"的var是这个函数返回的 需要强调的是 obj不一定是赋给这个var的值(比如本例子返回的是一个列表 而不是ListTransaction对象)
    def __enter__(self):
        self.workingcopy = list(self.thelist);
        return self.workingcopy;
    
    #清理函数 结果可以返回True或者False 分别指示被引发的异常得到了还是没有得到处理 如果返回False 引发的任何异常都将会传递出上下文
    def __exit__(self, etype, value, tb):
        if etype is None:
            self.thelist[:] = self.workingcopy;
        return False;

items = [1, 2, 3];

#with块里没有发生异常 操作生效
with ListTransaction(items) as working:
    working.append(4);
    working.append(5);

print(items);

try:
    #with块里发生异常 操作无效
    with ListTransaction(items) as working:
        working.append(6);
        working.append(7);
        raise RuntimeError("We're hosed");
except BaseException as e:
    pass;

print(items);
