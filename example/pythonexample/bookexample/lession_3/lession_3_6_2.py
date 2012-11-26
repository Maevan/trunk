# coding: utf-8
'''
Created on 2011-12-5

@author: Lv9
'''

print('hello'.capitalize());#首字母大写
print('center'.center(50, '*'));#在指定长度的情况下将字符串居中 第一个参数是指定的长度 第二个参数是填充的字符
print('left'.ljust(50, '*'));#在指定长度的情况下将字符串左对齐 第一个参数是指定的长度 第二个参数是填充的字符
print('right'.rjust(50, '*'));#在指定长度的情况下将字符串右对齐 第一个参数是指定的长度 第二个参数是填充的字符
print('aaabbbbccc'.count('b'));#计算子字符串的出现次数
print('美丽风景.jpg'.endswith('.jpg'));#判断字符串是否以指定suffix结尾
print('        d'.expandtabs());#使用空格替换制表符
print('abcdefg'.find('c'));#找到指定字符串出现的位置 否则返回-1
print('abcdefg'.index('c'));#找到指定字符串出现的位置 否则抛出异常
print('{0:3d} {1:3.2f}'.format(1, 1.111));#格式化s
print('11223344aaa'.isalnum());#判断字符串内是否全部是字母或者数字 PS.小数无效
print('aabbccdd'.isalpha());#判断字符串内是否全部是字母
print('11111111'.isdigit());#检查字符串内是否全部是数字 PS.小数无效
print('aabbccdd1'.islower());#检查字符串内的字母是否都是小写 PS.可以包含数字、或者其他符号
print('AABBCCDD1'.isupper());#检查字符串内的字母是否都是大写 PS.可以包含数字、或者其他符号
print('    '.isspace());#检查字符串内是否都是空白 PS.如果字符串为空居然返回False..
print('Pigger'.istitle());#检查字符串是否为标题字符串(首字母大写)
print('_'.join(["a", "b"]));#使用指定字符串作为分隔符将某个序列的元素连接起来并返回个字符串 PS.字符串可以作为参数 但是数字类型就不行 理由是没办法自动转型
print("AAAAAA".lower());#将字符串中的字母转换为小写
print("aaaaaa".upper());#将字符串中的字母转换为大写
print("  aaa".lstrip());#将字符串左边的空格清理掉
print("aaa   ".rstrip());#将字符串右边的空格清理掉
print("a:b and c".partition(":"));#使用指定的分隔符分隔字符串并返回一个元组 PS.不是split 他只是将第一个发现的分隔符之前的字符作为一个元素 分隔符本身做一个元素 分隔符之后的字符串作为一个元素 分成三个..
print("a:b:c:d:e".split(":", 3));#使用指定的分隔符分隔字符串并返回一个列表 可以指定划分次数
print("abbc".replace("b", "a"));#将字符窜内容替换为指定的内容

#剩下的不写了 像just index find partition 这些函数还有正则方式的 只需要方法名前加r 比如rfind
