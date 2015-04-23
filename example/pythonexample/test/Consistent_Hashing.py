# coding=utf-8
'''
Created on 2015年1月29日

@author: zhaojp
'''
def allstatus(nodes):
    for node in nodes:
        print(node.status());

class Node(object):
    __slots__ = ('__next', '__before', '__name', '__hashfunc', '__range', '__routers', '__storage');
    
    # irange可以在初始化的时候不指定 交给分配给他数据的节点指定
    def __init__(self, name, hashfunc, irange=None):
        self.__name = name;
        self.__hashfunc = hashfunc;
        self.__range = irange;
        self.__storage = {};

    # node_change_type 0是init 1是add 2是remove
    def rebalance(self, nodes, size, node_change_type):
        to_clean = [];
        if node_change_type == 1:
            i = 0;
            for node in nodes:
                if node is self:break;
                i += 1;
            new_node = nodes[i + 1];
            imin, imax = self.__range;
            simax = int((imax - imin) / 2 if (imax + imin) & 1 != 1 else (imax - imin) / 2 + 1) + imin;
            
            for k, v in self.__storage.items():
                if imin <= self.__hashfunc(k) <= simax:
                    continue;
                
                new_node.__put(k, v);
                to_clean.append(k);
            
            new_node.__range = (simax + 1 , imax);
            new_node.__rebuild_linked(nodes);
            new_node.__rebuild_routers(nodes, size);
            self.__range = (imin, simax);  # TODO 由于这种方式会导致短期内无法正确路由　所以可以在热更改期间更改__ger_oper_node的方法实现 让其遵循旧的路由方式 在路由重建完毕后　再替换会原本实现
            
        elif node_change_type == 2:
            to_rm_node = self.__next;
            for k, v in to_rm_node.__storage:
                self.__put(k, v);
            self.__next = to_rm_node.__next;
            self.__range = (self.__range[0], to_rm_node[1]);
            
            
        self.__rebuild_linked(nodes);
        self.__rebuild_routers(nodes, size);
        self.__clean(to_clean);
    
    # 寻找下游节点的时候 与路由不是一个业务 在进行迁移过程中 不会影响到调用者使用
    def __rebuild_linked(self, nodes):
        i = 0;
        for node in nodes:
            if node is self:break;
            i += 1;
        node.__before = nodes[i - 1] if i > 0 else nodes[len(nodes) - 1];
        node.__next = nodes[i + 1] if i < len(nodes) - 1 else nodes[0];
    
    def __rebuild_routers(self, nodes, size):
        b = 1;
        _, mmax = self.__range;
        routers = [];
        for i in range(0, size):
            offset = b << i;
            for node in nodes:
                _, nnmax = node.__range;
                if nnmax > self.__hashfunc(mmax + offset):
                    routers.append((offset, node));
                    break;
        
        self.__routers = routers;        
    
    def __clean(self, keys):
        for key in keys:
            del self.__storage[key];
        pass;
    
    def status(self):
        return 'Name: %s, Before: %s, Next: %s, \r\n\t\tRange: %s, Routers: %s\n\t\tData: %s' % (self.__name, self.__before.__name, self.__next.__name, self.__range, [(offset, node.__name) for offset, node in self.__routers], self.__storage);
    
    def get(self, key):
        node = self.__ger_oper_node(key);
        if node is self:
            return self.__get(key);
        else:
            return node.get(key);
    
    def put(self, key, val):
        node = self.__ger_oper_node(key);
        if node is self:
            return self.__put(key, val);
        else:
            return node.put(key, val);
        
    @property
    def name(self):
        return self.__name;
    
    def count(self):
        return len(self.__storage);
    
    def __put(self, key, val):
        self.__storage[key] = val;
        
    def __get(self, key):
        return self.__storage.get(key);
    
    def __ger_oper_node(self, key):
        k = self.__hashfunc(hash(key));
        imin, imax = self.__range;
        if imin <= k <= imax:
            return self;
        
        if imin > k:
            return self.__routers[len(self.__routers) - 1];
        else:
            b = k - imax;
            lastest = None;
            for num, node in self.__routers:
                if b < num:
                    break;
                lastest = node;
            return lastest;
    def __str__(self):
        return self.__name;

class HashRing(object):
    __slots__ = ('__size', '__nodes');

    def __init__(self, size, node_names):
        slots = 1 << size;
        if node_names is None or len(node_names) == 0 or len(node_names) > slots:
            raise RuntimeError("节点数需大于0并小于size");
        
        self.__nodes = [];
        self.__size = size;
        
        region_length = int(slots / len(node_names));
        regions = [];
        remain = int(slots % len(node_names));
        start = 0;
        
        def hash_func(hashcode):
            return HashRing.ihash(slots - 1, hashcode);
        
        for node_name in node_names:
            end = start + region_length - (0 if remain != 0 else 1);
            regions.append((start, end));
            self.__nodes.append(Node(node_name, hash_func, (start, end)));
            start = end + 1;
            
            remain = remain - 1 if remain != 0 else 0;
        
        for node in self.__nodes:
            node.rebalance(self.__nodes, size, 0);

    @property
    def nodes(self):
        return self.__nodes;
    
    @staticmethod
    def ihash(mask, hashcode):
        return hashcode & mask;

    def join(self, node_name, after_nodename=None):
        to_insert = 0;
        if after_nodename is None:
            imax = 0;
            for i in range(len(self.__nodes)):
                if imax < self.nodes[i].count():
                    imax = self.nodes[i].count();
                    to_insert = i;
        else:
            for i in range(len(self.__nodes)):
                if self.__nodes[i].name != after_nodename:
                    continue;
                to_insert = i;
                break;
        slots = 1 << self.__size;
        
        def hash_func(hashcode):
            return HashRing.ihash(slots - 1, hashcode);
        
        self.__nodes.insert(to_insert + 1, Node(node_name, hash_func));
        self.__nodes[to_insert].rebalance(self.__nodes, self.__size, 1);
        
if __name__ == '__main__':
    r = HashRing(5, ['Node 1', 'Node 2', 'Node 3', 'Node 4', 'Node 5']);
    
    tnode = r.nodes[0];
    for i in range(31):
        tnode.put(i, 'V' + str(i));
    
    allstatus(r.nodes)
    r.join('Node 2.5', 'Node 2');
    r.join('Node 4.5', 'Node 4');
    print('-' * 100, '\n')
    tnode.put(39, 'V' + str(39));
    tnode.put(43, 'V' + str(43));
    allstatus(r.nodes)
