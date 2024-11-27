package partida.Avatar;

import partida.*;
import monopoly.*;
import monopoly.Casilla.*;
import java.util.ArrayList;

public class Esfinge extends Avatar {

    public Esfinge(Jugador jugador, Casilla lugar, ArrayList<Avatar> avCreados) {
        super(jugador, lugar, avCreados);
    }

    public String getTipo() {
        return "Esfinge";
    }

    public String getInfo() {
        String ret = """
                id: %s,
                tipo: Esfinge
                casilla: %s,
                jugador: %s
                    """.formatted(this.getId(), this.getCasilla().getNombre(), this.getJugador().getNombre());
        return ret;
    }

    public boolean moverEnAvanzado(Tablero tablero, int valor1, int valor2, ArrayList<Jugador> jugadores) {
        moverNormal(tablero, valor1, valor2, jugadores);
        return true;
    }
}
