package partida;

import monopoly.Casilla.*;
import monopoly.Valor;

public class Trato {

    Jugador j1;
    Jugador j2;

    float dineroJ1;
    float dineroJ2;

    Casilla casillaJ1;
    Casilla casillaJ2;

    int tipo;
    String ID;

    public Trato(Jugador j1, Jugador j2, Casilla casillaJ1, Casilla casillaJ2) { // Casilla por Casilla

        this.j1 = j1;
        this.j2 = j2;

        this.casillaJ1 = casillaJ1;

        this.casillaJ2 = casillaJ2;

        this.tipo = 0;

        this.ID = "trato" + Valor.NumeroTratos++;
    }

    public Trato(Jugador j1, Jugador j2, Casilla casillaJ1, float dineroJ2) { // Propiedad por dinero

        this.j1 = j1;
        this.j2 = j2;

        this.casillaJ1 = casillaJ1;

        this.dineroJ2 = dineroJ2;

        this.tipo = 1;

        this.ID = "trato" + Valor.NumeroTratos++;

    }

    public Trato(Jugador j1, Jugador j2, float dineroJ1, Casilla casillaJ2) { // Dinero por propiedad

        this.j1 = j1;
        this.j2 = j2;

        this.dineroJ1 = dineroJ1;

        this.casillaJ2 = casillaJ2;

        this.tipo = 2;
        this.ID = "trato" + Valor.NumeroTratos++;
    }

    public Trato(Jugador j1, Jugador j2, Casilla casillaJ1, Casilla casillaJ2, float dineroJ2) {// Casilla por Casilla y
                                                                                                // dinero

        this.j1 = j1;
        this.j2 = j2;

        this.casillaJ1 = casillaJ1;

        this.casillaJ2 = casillaJ2;
        this.dineroJ2 = dineroJ2;

        this.tipo = 3;
        this.ID = "trato" + Valor.NumeroTratos++;
    }

    public Trato(Jugador j1, Jugador j2, Casilla casillaJ1, float dineroJ1, Casilla casillaJ2) { // Casilla y dinero por
        // Casilla

        this.j1 = j1;
        this.j2 = j2;

        this.casillaJ1 = casillaJ1;
        this.dineroJ1 = dineroJ1;

        this.casillaJ2 = casillaJ2;

        this.tipo = 4;
        this.ID = "trato" + Valor.NumeroTratos++;
    }

    public boolean aceptar() {

        switch (tipo) {

            case 1:
                break;

            case 2:
                break;

            case 3:
                break;

            case 4:
                break;
        }

    }

    // public boolean rechazaar(){}

}