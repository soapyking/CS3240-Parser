JFLAGS = -g -classpath $(CLASSPATH)
JC = javac
JARS = $(wildcard ./res/*.jar)
CLASSPATH_STR = . $(SRC_DIR) $(JARS)
CLASSPATH = $(subst $(space),:,$(CLASSPATH_STR))

RECENT_JAVA = $(shell ls -1t **/*.java | head -1)

SRC_DIR = ./src
BIN_DIR = ./bin

RUN = java -cp $(subst :./,:../,$(CLASSPATH))

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

test-fast: $(basename $(RECENT_JAVA)).run

%.run: %.class
	echo "#!/bin/bash\necho ---------\ncd src\n$(RUN) $(notdir $(basename $<)) \$$@" > test.sh

%.class: %.java
	$(JC) $(JFLAGS) -classpath $(CLASSPATH) $<

clean:
	$(RM) *.class
