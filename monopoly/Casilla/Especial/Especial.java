package monopoly.Casilla.Especial;

import monopoly.*;
import monopoly.Casilla.*;
import partida.*;

public abstract class Especial extends Casilla{
    public Especial (String nombre, int posicion){
        super(nombre,posicion);
    }

    @Override
    public String infoCasilla(Jugador banca){
        String info = new String();
        info = """
                {
                Nombre: %s
                Tipo: especial
                }
                """.formatted(this.getNombre());
        return info;
    }

    @Override
    public abstract boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada);

}
