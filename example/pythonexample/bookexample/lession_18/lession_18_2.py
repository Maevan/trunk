#coding=utf-8
'''
Created on 2012-6-5

@author: zhaojp
'''

from filecmp import cmp;
from filecmp import cmpfiles;
from filecmp import dircmp;

print(cmp('lession_18_2.py', '__init__.py')); #cmp(f1, f2[, shallow]) 比较f1与f2文件内容是否相等 默认情况下 如果os.stat()返回的文件属性相同 就认为文件相等 如果shallow为False 将通过比较两个文件的内容来确定这两个文件是否相等
print(cmpfiles('../lession_17', '../lession_18', ['__init__.py', ], True));# cmpfiles(a, b, common[, shallow]) 比较a与b两个目录中的common列表中的文件内容 返回一个包含三个文件名的列表的元组(match,mismatch,errors) match列出的是两个目录相同的文件 mismatch是两个目录不同的文件 errors是无法进行比较的文件

d = dircmp('../lession_17', '../lession_18', ['RCS', 'CVS', 'tags', '.svn']) #dircmp(dir1, dir2[, ignore[, hide]]) 创建一个目录比较对象 用于执行目录dir1和目录dir2的各种比较操作 ignore是要忽略的文件名列表,默认值为['RCS','CVS','tags'] hide是要隐藏的文件名列表 它默认在[os.curdir,os.pardir]列表中(在UNIX中是['.','..'])

d.report();#比较dir1和dir2 并把比较结果输出到控制台中
d.report_partial_closure();#比较dir1和dir2共同的直接子目录 结果输出到控制台中

print(d.left_list);#列出dir1的文件和子目录 内容根据hide和ignore筛选
print(d.left_only);#列出仅在dir1中能找到的目录和文件
print(d.right_list);#列出dir2的文件和子目录 内容根据hide和ignore筛选
print(d.right_only);#列出仅在dir2中能找到的目录和文件
print(d.common);#列出dir1和dir2中都能找到的文件和子目录
print(d.common_dirs);#列出dir1和dir2共同的子目录
print(d.common_files);#列出dir1和dir2共同的文件
print(d.common_funny);#列出dir1和dir2中类型不同的文件 或者是无法通过os.stat()获得信息的文件
print(d.same_files);#列出在dir1和dir2中内容相同的文件
print(d.diff_files);#列出在dir1和dir2中内容不同的文件(内容不同我估计是同名不同内容的文件 不是求文件交集)
print(d.funny_files);#列出在dir1和dir2中都存在 但是由于某种原因不能进行比较(例如访问权限不足)的文件
print(d.subdirs);#将d.common_dirs中的文件名映射到其他dircmp对象中的字典
