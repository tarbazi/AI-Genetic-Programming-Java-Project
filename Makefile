# Makefile for Assignment 1
# 13/08/2022
# Bazi Mathangeni

# Configurable Variables
JAVAC := /usr/bin/javac
SRCDIR := src
BINDIR := bin
DOCDIR := docs

# Makefile for compiling Java files
# Author: Your Name

# Configurable Variables
JAVAC := /usr/bin/javac
SRCDIR := src
BINDIR := bin

# Define Java source files and class files
JAVA_FILES := $(wildcard $(SRCDIR)/*.java)
CLASS_FILES := $(patsubst $(SRCDIR)/%.java,$(BINDIR)/%.class,$(JAVA_FILES))

# Default target
default: $(CLASS_FILES)

# Compile Java files
$(BINDIR)/%.class: $(SRCDIR)/%.java
	$(JAVAC) -d $(BINDIR) $<

# Clean target
clean:
	rm -rf $(BINDIR)/*.class