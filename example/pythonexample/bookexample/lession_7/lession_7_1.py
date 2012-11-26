# coding: utf-8
'''
Created on 2011-12-20

@author: Lv9
'''
class Account(object):
    num_account = 0;
    
    def __init__(self, name, balance):
        self.name = name;
        self.balance = balance;
        Account.num_account += 1;

    def __del__(self):
        Account.num_account -= 1;
    
    def deposit(self, amt):
        self.balance += amt;

    def withdraw(self, amt):
        self.balance -= amt;
    
    def foo(self):
        self.deposit(10);
        self.withdraw(10);
    
    def inquiry(self):
        return self.balance;
    
    @classmethod
    def get_account(cls):
        return cls.num_account;

print(Account.get_account());
print(dir(Account));#注意两种方式的输出 一个是查看类的结构 一个是查看类实例的结构 查看类实例的结构时会多出这个实例的属性
print(dir(Account('Lv9', 100)));
