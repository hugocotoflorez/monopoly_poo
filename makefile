# Definir el compilador de Java
JAVAC = javac
JAVA = java

# Definir las carpetas donde se encuentran los archivos .java
SRC_MONOPOLY = monopoly
SRC_PARTIDA = partida

# Definir las clases de cada carpeta
CLASSES_MONOPOLY = $(SRC_MONOPOLY)/Casilla.java $(SRC_MONOPOLY)/Grupo.java $(SRC_MONOPOLY)/Menu.java $(SRC_MONOPOLY)/MonopolyETSE.java $(SRC_MONOPOLY)/Tablero.java $(SRC_MONOPOLY)/Valor.java
CLASSES_PARTIDA = $(SRC_PARTIDA)/Avatar.java $(SRC_PARTIDA)/Dado.java $(SRC_PARTIDA)/Jugador.java

# Clase principal para ejecutar el proyecto
MAIN_CLASS = monopoly.MonopolyETSE

# Objetivo que compila y ejecuta el proyecto
all: compile run

# Regla para compilar todos los archivos .java
compile:
	$(JAVAC) $(CLASSES_MONOPOLY) $(CLASSES_PARTIDA)

# Regla para ejecutar el proyecto
run:
	$(JAVA) $(MAIN_CLASS)

# Objetivo para limpiar los archivos .class generados
clean:
	rm -f $(SRC_MONOPOLY)/*.class $(SRC_PARTIDA)/*.class