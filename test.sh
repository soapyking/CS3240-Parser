#!/bin/bash
echo ---------
cd src
java -cp .:../src:../res/junit-4.11-SNAPSHOT-20120416-1530.jar:../res/regexp.jar $@
