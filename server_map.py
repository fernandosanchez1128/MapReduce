#!/usr/bin/python

import sys

# input comes from STDIN (standard input)
for line in sys.stdin:
    # remove leading and trailing whitespace
    line = line.strip()
    # split the line into words
    words = line.split()
    # increase counters
    index =0
    for word in words:
		if (index==0):
			print '%s\t%s' % (word, 1)
		index = index + 1
