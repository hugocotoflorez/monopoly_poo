run: compile
	java monopoly.MonopolyETSE
	make clean

compile: clean
	javac monopoly/MonopolyETSE.java

clean:
	rm monopoly/*.class; rm monopoly/{Casilla,consola,Edificio}/*.class; rm partida/*.class; rm partida/{Avatar,Carta}/*.class; rm monopoly/Casilla/{Accion,Especial,Impuesto,Propiedad}/*.class; echo Done!