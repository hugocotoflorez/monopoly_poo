package monopoly.Casilla.Especial;

import monopoly.*;
import monopoly.Casilla.*;
import partida.*;

public class IrCarcel extends Especial {
    public IrCarcel(int posicion){
        super("IrCarcel", posicion);
    }

    @Override
    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada){
        Juego.consola.imprimirln("Oh no! Vas a la cárcel.");
        return true;
    }
}
