package partida.Avatar;

import partida.*;
import monopoly.Casilla.*;
import java.util.ArrayList;

public class Coche extends Avatar {

    public Coche(Jugador jugador, Casilla lugar, ArrayList<Avatar> avCreados) {
        super(jugador, lugar, avCreados);
    }

    public String getTipo() {
        return "Coche";
    }

    public String getInfo() {
        String ret = """
                id: %s,
                tipo: Coche
                casilla: %s,
                jugador: %s
                    """.formatted(this.getId(), this.getCasilla().getNombre(), this.getJugador().getNombre());
        return ret;
    }

    @Override
    public void moverEnAvanzado() {
    } // TODO moverEnAvanzado Coche
}
