package partida.Avatar;

import partida.*;
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

    @Override
    public void moverEnAvanzado() {
    } // TODO moverEnAvanzado Sombrero
}
