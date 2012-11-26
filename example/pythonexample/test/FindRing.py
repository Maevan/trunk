#coding=utf-8
'''
Created on 2012-6-21

@author: zhaojp
'''
class Linked(object):
    def __init__(self, value, nextlinked=None):
        self.value = value;
        self.nextlinked = nextlinked;
    def __str__(self):
        return str(self.value);

def isRing(linked, detect_interval=20):
    '''
          判断指定的链表是否有环
    '''
    destruction_linkeds = [];
    i = 0;
    result = False;
    
    while linked.nextlinked is not None:
        i += 1;
        previous_linked = linked;
        linked = linked.nextlinked;
        
        if linked.value is 'Fuck':
            result = True;
            break;
        
        if i >= detect_interval:
            previous_linked.nextlinked = Linked('Fuck');
            destruction_linkeds.append((previous_linked, linked));
            i = 0;
            
    for linked, next_linked in destruction_linkeds:
        linked.nextlinked = next_linked;
        
    return result;

def generate(count, is_ring=False, previous_linked=None, startvalue=0):
    '''
            生成指定数量的链表
    '''
    first = None;
    
    if previous_linked is None:
        first = Linked(startvalue);
    else:
        first = previous_linked;
    
    last = first;
    startvalue += 1;
    
    for i in range(startvalue, startvalue + count):
        last.nextlinked = Linked(i);
        last = last.nextlinked;
    if is_ring:
        last.nextlinked = first;
        
    return first;

linkedsize = 200;
ring_start = int(linkedsize / 2);

first = generate(linkedsize, False);
pointor = first;

print(isRing(first));
for i in range(ring_start):pointor = pointor.nextlinked;

generate(int(linkedsize / 3), True, pointor, linkedsize);
pointor = first;
print(isRing(first));
#while pointor.nextlinked is not None:
#    print(pointor);
#    pointor = pointor.nextlinked;
