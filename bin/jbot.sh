#!/bin/sh

java -Dfile.encoding=UTF-8 potix2.jbot.JBot | netcat chat.freenode.net 6667
