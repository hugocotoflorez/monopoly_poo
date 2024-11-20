package partida;

import monopoly.Casilla.*;

public class Trato {

    Jugador j1;
    Jugador j2;

    float dineroJ1;
    float dineroJ2;

    Casilla casillaJ1;
    Casilla c2;

    public Trato(Jugador j1, Jugador j2, Casilla casillaJ1, Casilla c2) { // Casilla por Casilla

        this.j1 = j1;
        this.j2 = j2;

        this.casillaJ1 = casillaJ1;

        this.c2 = c2;

    }

    public Trato(Jugador j1, Jugador j2, Casilla casillaJ1, float dineroJ2) { // Propiedad por dinero

        this.j1 = j1;
        this.j2 = j2;

        this.casillaJ1 = casillaJ1;

        this.dineroJ2 = dineroJ2;
    }

    public Trato(Jugador j1, Jugador j2, float dineroJ1, Casilla c2) { // Dinero por propiedad

        this.j1 = j1;
        this.j2 = j2;

        this.dineroJ1 = dineroJ1;

        this.c2 = c2;

    }

    public Trato(Jugador j1, Jugador j2, Casilla casillaJ1, Casilla casillaJ2, float dineroJ2) {// Casilla por Casilla y
                                                                                                // dinero

        this.j1 = j1;
        this.j2 = j2;

        this.casillaJ1 = casillaJ1;

        this.c2 = casillaJ2;
        this.dineroJ2 = dineroJ2;

    }

    public Trato(Jugador j1, Jugador j2, Casilla casillaJ1, float dineroJ1, Casilla c2) { // Casilla y dinero por
                                                                                          // Casilla

        this.j1 = j1;
        this.j2 = j2;

        this.casillaJ1 = casillaJ1;
        this.dineroJ1 = dineroJ1;

        this.c2 = c2;

    }

}