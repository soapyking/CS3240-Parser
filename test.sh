#!/bin/bash
echo ---------
cd src
java -classpath .:../src:/home/stewart/git/CS3240-Parser/src/junit.jar:/home/stewart/git/CS3240-Parser/src/regexp.jar $@
