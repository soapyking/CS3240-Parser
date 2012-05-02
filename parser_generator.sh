#!/bin/bash

if readlink /proc/$$/fd/0 | grep -q "^pipe:"; then
  ./run.sh ParseGen $@ < cat
else
  ./run.sh ParseGen $@
fi



