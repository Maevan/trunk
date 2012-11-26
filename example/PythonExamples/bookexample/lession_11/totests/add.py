#coding=utf-8
'''
Created on 2012-2-14

@author: zhaojp
'''
#Python将单元测试的代码写在注释中 下面的代码代表了单元测试中会被执行的操作 先调用add(1,1) 然后预计结果会输出2
#另外 也不一定非得单独启用一个测试类 也可以将测试操作放到被测试的文件中
def add(n1, n2):
    '''
        Add
        >>> add(1,1)
        2
    '''
    return n1 + n2;

if __name__ == '__main__':
    #test myself
    from doctest import testmod;
    nfails, ntests = testmod(verbose=True);#通过testmod函数使用单元测试 verbose属性会生成详细的测试报告
    
    print('fails:', nfails, 'ntests:', ntests);
