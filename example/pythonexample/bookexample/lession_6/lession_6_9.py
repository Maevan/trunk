# coding: utf-8
'''
Created on 2011-12-16

@author: Lv9
'''
import math

#    l = [];
#    for n in range(1, 10):
#        l.append(n);

print([n * n for n in range(1, 10)]);#这种操作叫做列表推导 用来代替上面那种传统的赋值形式
print([n * n for n in range(1, 10) if n % 2 != 0]); #可以根据判断来决定是否将这次循环的结果添加
print([(x, y) for x in range(1, 10) for y in range(11, 20) if x % 2 != 0]);#也可以实现嵌套循环赋值的效果
print([math.sqrt(x * x + y * y) for x, y in zip(range(1, 10), range(11, 20))]);#也可以在循环赋值过程中对for每个阶段循环返回的结果用方法进行处理

generator = (n for n in range(1, 10)); #外面用中括号返回的是列表 如果用圆括号则是一个生成器

for y in generator:
    print(y, end=' ');

#用这种列表推导的方式 可以很轻松的完成声明式编程 如果注意上面的代码 你会发现可以用这种方式模拟SQL select语句的行为
