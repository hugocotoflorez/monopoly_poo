package monopoly.Casilla.Especial;

import monopoly.*;
import monopoly.Casilla.*;
import partida.*;

public class Salida extends Especial {
    public Salida(int posicion){
        super("Salida", posicion);
    }

    @Override
    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada){
        Juego.consola.imprimir("Estás en la casilla de salida.");
        return true;
    }
}
