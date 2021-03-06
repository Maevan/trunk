# coding: utf-8
'''
Created on 2011-12-28

@author: Lv9
'''
import weakref
import sys

class Account(object):
    def __init__(self, name, balance):
        self.name = name;
        self.balance = balance;
        self.observers = set();

    def __del__(self):
        for ob in self.observers:
            ob.close();
        
        del self.observers;
    
    def register(self, observer):
        self.observers.add(observer);
    
    def unregister(self, observer):
        self.observers.remove();

    def notify(self):
        for ob in self.observers:
            ob.update();
    
    def withdraw(self, amt):
        self.balance -= amt;
        self.notify();
        
class AccountObserver(object):
    def __init__(self, theaccount):
        self.accountref = weakref.ref(theaccount);
        theaccount.register(self);
    
    def __del__(self):
        acc = self.accountref;
        if acc:
            acc.unregister(self);
    
    def update(self):
        print("Balance is %0.2f" % self.accountref().balance);
    
    def close(self):
        print("Account no longer in use");
        
a = Account('Dave', 1000.00);
b = AccountObserver(a);

print(a.__dict__.keys());
print(Account.__dict__.keys());
del b;
del a;
print(sys.getrefcount(Account));
print(sys.getrefcount(AccountObserver));

