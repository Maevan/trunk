# coding: utf-8
'''
Created on 2011-12-16

@author: Lv9
'''

import gzip, bz2, fnmatch, os;

def find_files(topdir, pattern):
    for path, dirname, filelist in os.walk(topdir):
        for name in filelist:
            if fnmatch.fnmatch(name, pattern):
                yield os.path.join(path.name);

def opener(filenames):
    for name in filenames:
        if name.endswith(".gz"):
            f = gzip.open(name);
        elif name.endswith('.bz2'):
            f = bz2.BZ2File(name);
        else:
            f = open(name);
        yield f;
def cat(filelist):
    for f in filelist:
        for line in f:
            yield line;

def grep(pattern, lines):
    for line in lines:
        if(pattern in line):
            yield line;
