JFLAGS = -g -classpath $(CLASSPATH)
JC = javac
JARS = $(wildcard $(shell pwd)/src/*.jar)
CLASSPATH_STR = . $(SRC_DIR) $(JARS)
CLASSPATH = $(subst $(space),:,$(CLASSPATH_STR))

RECENT_JAVA = $(shell ls -1t **/*.java | head -1)

SRC_DIR = ./src
BIN_DIR = ./bin

RUN = java -classpath $(subst :./,:../,$(CLASSPATH))
DEBUG = jdb -classpath $(subst :./,:./,$(CLASSPATH))

JUNIT = org.junit.runner.JUnitCore

null:=
space:= $(null) $(null)


.PHONY: check-syntax
.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) -classpath $(CLASSPATH) $*.java


CLASSES = $(wildcard $(SRC_DIR)/*.java)


default: classes

classes: $(CLASSES:.java=.class)

check-syntax:
	$(JC) $(JFLAGS) -classpath $(CLASSPATH) -Xlint $(CHK_SOURCES)

run:
	echo "#!/bin/bash\necho ---------\ncd src\n$(RUN) \$$@" > run.sh
	chmod +x run.sh

debug:
	echo "#!/bin/bash\necho ---------\ncd src\n$(DEBUG) \$$@" > debug.sh
	chmod +x debug.sh

test-fast: $(basename $(RECENT_JAVA)).run

test:
	$(RUN) $(JUNIT) $(TFILE)


%.run: %.class
	echo "#!/bin/bash\necho ---------\ncd src\n$(RUN) $(notdir $(basename $<)) \$$@" > test.sh

%.class: %.java
	$(JC) $(JFLAGS) -classpath $(CLASSPATH) $<

clean:
	$(RM) *.class
