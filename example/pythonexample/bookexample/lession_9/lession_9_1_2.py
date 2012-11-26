#coding= utf-8
'''
Created on 2012-1-30

@author: Lv9
'''
import optparse;
from optparse import Option;

#也可以通过optparse获取执行脚本时的参数和选项的参数
#可以在Run Configurations中增加如下参数  -o a.txt -d arg1 arg2 arg3
p = optparse.OptionParser();

actionAndDest = {'action' : 'store', 'dest' : 'outfile'};

#可以向一个OptionParser对象中添加选项参数的描述 比如选项名称 选项参数的值类型等信息 也可以给相同的选项名称起多个别名
#另外 将action设置为store则表示必须为这个选项提供一个参数
p.add_options([
    Option('-o', action='store', dest='outfile', type='string'),
    Option('--output', action='store', dest='outfile', type='string')
]);

#将action设置为store_true表示当选项没有参数的时候 为true
#顺带一提 如果设置为store_true 则不必再为这个选项提供任何参数 即使你提供了也会算作argv里去..
p.add_options([
    Option('-d', action='store_true', dest='debug'),
    Option('--debug', action='store_true', dest='debug')
]);

#指定某个选项的默认参数的值
p.set_default('debug', False);

opts, args = p.parse_args();

print('debug:', opts.debug);
print('output:', opts.outfile);

print('args:', end=' ');
for arg in args:
    print(arg, end=' ');
print();
