#coding=utf-8
'''
Created on 2013-1-10

@author: zhaojp
'''

def foo(years, can_breed_age, initcows, births):
    can_breed = 1;
    can_not_breed = 0;
    kraal = {};
    
    for i in range(0, years):
        for j in range(can_breed_age, 1, -1):
            kraal[j] = kraal.get(j - 1, 0);
        new_can_breed = kraal.get(can_breed_age, 0);
        can_breed += new_can_breed;
        kraal[1] = can_breed * births;
        can_not_breed += kraal[1] - new_can_breed;
        
    return can_breed + can_not_breed;

print(foo(20, 5, 1, 1));


