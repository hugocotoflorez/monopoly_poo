package partida;

import monopoly.Casilla.*;
import monopoly.Valor;

// Queda por cambiar Casilla por Propiedad

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

    private void trato0() {

        this.j1.anhadirPropiedad(casillaJ2);
        this.j1.eliminarPropiedad(casillaJ1);

        this.j2.anhadirPropiedad(casillaJ1);
        this.j2.eliminarPropiedad(casillaJ2);

    }

    private void trato1() {

        this.j1.sumarFortuna(dineroJ2);
        this.j1.eliminarPropiedad(casillaJ1);

        this.j2.anhadirPropiedad(casillaJ1);
        this.j2.sumarFortuna(-dineroJ2);

    }

    private void trato2() {

        this.j1.anhadirPropiedad(casillaJ2);
        this.j1.sumarFortuna(-dineroJ1);

        this.j2.eliminarPropiedad(casillaJ2);
        this.j2.sumarFortuna(dineroJ1);

    }

    private void trato3() {

        this.j1.anhadirPropiedad(casillaJ2);
        this.j1.eliminarPropiedad(casillaJ1);
        this.j1.sumarFortuna(dineroJ2);

        this.j2.anhadirPropiedad(casillaJ1);
        this.j2.eliminarPropiedad(casillaJ2);
        this.j2.sumarFortuna(-dineroJ2);

    }

    private void trato4() {

        this.j1.anhadirPropiedad(casillaJ2);
        this.j1.eliminarPropiedad(casillaJ1);
        this.j1.sumarFortuna(-dineroJ1);

        this.j2.anhadirPropiedad(casillaJ1);
        this.j2.eliminarPropiedad(casillaJ2);
        this.j2.sumarFortuna(dineroJ1);

    }

    public void aceptar() {

        switch (tipo) {

            case 0:

                this.trato0();
                break;

            case 1:

                this.trato1();
                break;

            case 2:

                this.trato2();
                break;

            case 3:

                this.trato3();
                break;

            case 4:

                this.trato4();
                break;

        }

    }

    // public boolean rechazaar(){}

}