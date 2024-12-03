run: compile
	java monopoly.MonopolyETSE
	make clean

compile: clean
	javac monopoly/MonopolyETSE.java

clean:
	find . -name "*.class" -type f -delete