package partida.Avatar;

import partida.*;
import monopoly.*;
import monopoly.Casilla.*;
import java.util.ArrayList;

public class Sombrero extends Avatar {

    public Sombrero(Jugador jugador, Casilla lugar, ArrayList<Avatar> avCreados) {
        super(jugador, lugar, avCreados);
    }

    public String getTipo() {
        return "Sombrero";
    }

    public String getInfo() {
        String ret = """
                id: %s,
                tipo: Sombrero
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
