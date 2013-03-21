#coding=utf-8
'''
Created on 2013-2-16

@author: zhaojp
'''
import time
start = [int(time.strftime('%H')), int(time.strftime('%M')), int(time.strftime('%S'))]

#to find the FRACATION closest to pi with denominator and molecular both in 1000000

# ran = 1000000
ran = 1000000;
pi = 3.141592653

denoRange = range(1, int((ran / 4)) + 1);

rec = pi
Rmole = 0
Rdeno = 0

for deno in denoRange:
    print(deno);
    fdeno = float(deno);
    for mole in range(deno * 3, deno * 4 + 1):
        n = float(mole) / fdeno
        tole = abs(n - pi)
        if rec > tole:
            rec = tole
            Rmole = mole
            Rdeno = fdeno
#    for mole in range(deno * 3, deno * 4 + 1):
#        mole = mole * qtime;
#        n = mole / deno
#        tole = abs(n - pi_qt)
#        if rec > tole:
#            rec = tole
#            Rmole = mole / qtime
#            Rdeno = deno


    
end = start = [int(time.strftime('%H')), int(time.strftime('%M')), int(time.strftime('%S'))]

print(Rmole, '''/''', Rdeno, '\nfound in', end[0] - start[0], ':', end[1] - start[1], ':', end[2] - start[2]);
