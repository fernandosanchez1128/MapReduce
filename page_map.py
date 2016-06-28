#!/usr/bin/python

import sys

# input comes from STDIN (standard input)
for line in sys.stdin:
    # remove leading and trailing whitespace
    line = line.strip()
    # split the line into words
    words = line.split()
    # increase counters
    server =""
    page = "" 
    index =0
    for word in words:
		if (index==0):
			server = word
		if (index==4):
			page = word
	index = index + 1
	
	if (server=="edams.ksc.nasa.gov"):
		print '%s\t%s' % (page, 1)
	 
