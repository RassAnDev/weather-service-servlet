setup:
	gradle wrapper --gradle-version 8.2
	
start:
	gradle run

clean:
	gradle clean

build:
	gradle clean build

lint:
	gradle checkstyleMain checkstyleTest

test:
	gradle test

.PHONY: build
