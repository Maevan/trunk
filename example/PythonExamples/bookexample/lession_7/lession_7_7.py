# coding: utf-8
'''
Created on 2011-12-22

@author: Lv9
'''
import math
#通常 访问实例或类的属性时 将返回所存储的相关值 特性(property) 是一种特殊的属性,
#访问它时会计算它的值 下面是一个简单的例子:
class Circle(object):
    def __init__(self, radius):
        self.radius = radius;
    
    #Circle的一些附加特性
    @property    
    def area(self):
        return math.pi * self.radius ** 2;

    @property
    def perimeter(self):
        return 2 * math.pi * self.radius;
    
c = Circle(4.0);

#在这个例子中 Circle实例存储了一个实例变量c.radius c.area和c.perimeter是根据
#该值计算得来的 @property装饰器支持以简单属性的形式访问后面的方法 无需像平常一
#样添加额外的()来调用该方法 对象的使用者很难发现正在计算一个属性 除非在试图重新
#定义该属性时生成了错误消息
print("radius:", c.radius);
print("area:", c.area);
print("perimeter:", c.perimeter);

#特性还可以拦截操作,以设置和删除属性 这是通过向特性附加其他setter和deleter方法
#来实现的 如下所示:

class Foo(object):
    def __init__(self, name, age):
        self.__name = name;
        self.__age = age;
        
    @property
    def name(self):
        return self.__name;

    @property
    def age(self):
        return self.__age;

    #这些方法的名称必须与存在的特性名称关联
    @name.setter
    def name(self, value):
        if not isinstance(value, str):
            raise TypeError("Must be a string");
    
    @age.setter
    def age(self, value):
        if not isinstance(value, int):
            raise TypeError("Must be a int");
        
    @name.deleter
    def name(self):
        raise TypeError("Can't delete name")

    @age.deleter
    def age(self):
        raise TypeError("Can't delete age");
    
#我个人的看法是 这个特性类似Java的访问器(get/set方法) 虽然我不清楚Python有没有
#访问权限修饰符 但是可以通过这个来实现那种效果 可以在设置、获取、删除的时候进行
#一些操作 比如上面拦截使用者直接对__name变量进行操作而是通过name()特性来赋值
#可以在name函数中对用户赋值的类型进行判断 由于Python是动态类型语言 所以在赋值的
#时候没有编译型语言的类型检查 通过这个可以有效的实现类型检查 虽然用户可以通过其他
#途径去找到隐藏起来 的__name属性 并直接对其赋值 不过也可以通过修改__dict__函数来
#隐藏这些..
f = Foo("Lv9", 20);
# f.name = 9; 会出现异常 验证有效
# f.age = '9'; 会出现异常 验证有效
# del f.name; 会出现异常 验证有效
# del f.age; 会出现异常 验证有效

