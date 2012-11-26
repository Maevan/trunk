#coding= utf-8
'''
Created on 2012-2-5

@author: Lv9
'''
import string;

#不同的变量插入方式

#格式化:

form = """
    Dear %(name)s,
    Please send back my %(item)s or pay me $%(amount)0.2f.
                                        Sincerely yours,
                                        Joe Python User.
""";

print(form % {'name':'Lv9', 'item':'blender', 'amount':5.0})

#格式化(format()):
form = """
    Dear {name},
    Please send back my {item} or pay me ${amount:0.2f}.
                                        Sincerely yours,
                                        Joe Python User.
""";

print(form.format(name="Lv9", item="blender", amount=5.0));

#Template
form = string.Template("""
    Dear $name,
    Please send back my $item or pay me $amount.
                                        Sincerely yours,
                                        Joe Python User.
""");
form.substitute({'name':'Lv9', 'item':'blender', 'amount':"$%0.2f" % 50.0})
