#!/bin/bash

if readlink /proc/$$/fd/0 | grep -q "^pipe:"; then
  ./run.sh Lexer $@ < cat
else
  ./run.sh Lexer $@
fi



