all: compile clean

compile:
	javac monopoly/MonopolyETSE.java
	java monopoly.MonopolyETSE

clean:
	rm -f {$(SRC_MONOPOLY),$(SRC_PARTIDA)}/{*,*/*}.class
