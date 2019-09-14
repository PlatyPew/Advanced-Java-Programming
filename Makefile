TARGET = build
JFLAGS = -g -d $(TARGET) -cp $(TARGET)
JC = javac

JRFLAGS = -cp build
JCR= java

CLASSES = $(shell find src -type f -name '*.java')

MAIN = Main

default: $(CLASSES)
	$(JC) $(JFLAGS) $^

run:
	$(JCR) $(JRFLAGS) $(MAIN)

clean:
	rm -rf $(TARGET)
