package partida.Avatar;

import partida.*;
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

    @Override
    public void moverEnAvanzado() {
    } // TODO moverEnAvanzado Esfinge
}
