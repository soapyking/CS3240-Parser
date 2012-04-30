JFLAGS = -g
JC = javac
CLASSPATH = -classpath regexp.jar
RECENT_JAVA := $(zsh ls -lt **/*.java | head -1)

.PHONY: check-syntax
.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $(CLASSPATH) $*.java


CLASSES = \
	source_lexer.java

default: classes

classes: $(CLASSES:.java=.class)

run: default
	java -cp .:regexp.jar source_lexer

check-syntax:
	$(JC) $(JFLAGS) $(CLASSPATH) -Xlint $(CHK_SOURCES)

#test-fast: $(basename $(RECENT_JAVA)).run
#
#%.run: %.class
#	java $(basename $<)
#
#%.class: %.java
#	javac $<

clean:
	$(RM) *.class
