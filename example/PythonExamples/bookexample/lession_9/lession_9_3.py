#coding= utf-8
'''
Created on 2012-2-1

@author: Lv9
'''
# encoding 属性用来指定字符集
# newline 可以控制通用换行符 可以通过这个指定换行符
# closed 控制在调用close()函数时 是否实际关闭底层文件描述符 默认情况下该值为True
# mode 操作模式
#             核心模式 :r 读取模式;w 输出模式;a 附加模式
#             修饰模式 :b 字节模式;t 字符模式;
#             读写模式 :+ r+或者w+ 如果是r+模式则可以读取文件并且可以通过write在read的位置写入内容 当然会清理掉read到的位置后的全部内容
#                             如果是w+模式可以写入文件 不过会清空文件内容 读取的位置从0开始
fw = open('foo', mode='w', encoding='utf-8');#创建foo并且写入(会覆盖掉原来的foo文件) 另外可以通过encoding指定字符集
fa = open('foo', mode='a');#附加模式 类似Java中的输出流的追加模式 该模式不会重新创建文件并且能够将新的内容追加到文件结尾
fwb = open('foo', mode='wb');#输出二进制数据时候追加模式b
# frt = open('foo',mode='wt');#强调按照字符输出数据 模式w默认貌似就是按照字符输出

# f = open('foo'); #打开foo以供读取
fr = open('foo', mode='r'); #同上 另外需要注意的一点是 如果文件不存在而且mode为r的时候 会出现No such file or director异常
frb = open('foo', mode='rb'); #如果尝试读取二进制数据的话需要追加模式b
# frt = open('foo',mode='rt'); #强调按照字符读取数据 模式r默认貌似就是按照字符读取

fw.write("Hello World!\nStudy ");#写入字符串
fa.write("Python!");#追加内容到结尾
fw.writelines(["Lv9!", "Lv15!"]);#写入序列中所有的字符串
fw.flush();#清除输出缓冲区
fw.truncate(100);#截断文件内容到指定长度 ("Hello World!"会被截取成"Hello" 我不太清楚是否对模式为a的file有用 输入流无法使用该函数)


fw.close();
fa.close();

print(fr.read(1));#读取N个字节 否则读取全部
print(fr.readline(3));#读取单行的N个字节 否则读取此行
print(fr.tell());#返回当前文件指针
print(fr.isatty());#如果file对象时交互式终端 则返回1
print(fr.readlines(12));#读取所有行 返回一个行序列 size是可选的 用来决定读取操作停止前在文件上读取的近似字符数(这个方法必须是按照行单位返回记录 也就是说返回的字符串中不存在"半行"这种效果的数据 比如一个文件有50个字符 每行25个字符 当size为1的时候只返回第一行数据 为25的时候也只返回第一行数据 一旦超过第一行拥有的字符数(比如26) 就会返回第二行的整行数据)
print(fr.fileno());#返回一个整数文件描述符
print(fr.seek(0));#指定文件指针位置

print(fr.encoding);#获得文件编码 
print(fr.name);#返回文件名称
print(fr.mode);#返回文件mode
print(fr.closed);#返回文件是否关闭
print(fr.newlines);#返回流中指定的换行符 
#标准迭代文件所有航的方式 会调用f.__next__()函数迭代
for l in fr:
    print(l);
#另外 如果通过read或者readline函数读取文件内容的时候 读取到了文件末尾 函数会返回一个空字符串 判断空字符串的方式如下
line = "";
if not line:
    print("line is a empty string");