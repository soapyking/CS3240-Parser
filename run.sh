#!/bin/bash
cd src
java -classpath .:../src:/home/stewart/git/CS3240-Parser/src/commons-colletions.jar:/home/stewart/git/CS3240-Parser/src/junit.jar:/home/stewart/git/CS3240-Parser/src/regexp.jar $@
