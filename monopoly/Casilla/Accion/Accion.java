package monopoly.Casilla.Accion;

import monopoly.*;
import monopoly.Casilla.*;
import partida.*;

public class Accion extends Casilla {
    public Accion(String nombre, int posicion){
        super(nombre,posicion);
    }

    @Override 
    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada){
        Juego.consola.imprimirln("El jugador " + actual.getNombre() + " ha caído en una casilla de acción.");
        return true;
    }

    @Override
    public String infoCasilla(Jugador banca){
        String info = new String();
        info = """
                {
                Nombre: %s
                Tipo: acción
                }
                """.formatted(this.getNombre());
        return info;
    }
}
