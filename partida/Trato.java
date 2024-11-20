package partida;

import monopoly.Casilla.Propiedad.Propiedad;
import monopoly.Valor;

// Queda por cambiar Propiedad por Propiedad

public class Trato {

    Jugador j1;
    Jugador j2;

    float dineroJ1;
    float dineroJ2;

    Propiedad PropiedadJ1;
    Propiedad PropiedadJ2;

    int tipo;
    String ID;

    public Trato(Jugador j1, Jugador j2, Propiedad PropiedadJ1, Propiedad PropiedadJ2) { // Propiedad por Propiedad

        this.j1 = j1;
        this.j2 = j2;

        this.PropiedadJ1 = PropiedadJ1;

        this.PropiedadJ2 = PropiedadJ2;

        this.tipo = 0;

        this.ID = "trato" + Valor.NumeroTratos++;
    }

    public Trato(Jugador j1, Jugador j2, Propiedad PropiedadJ1, float dineroJ2) { // Propiedad por dinero

        this.j1 = j1;
        this.j2 = j2;

        this.PropiedadJ1 = PropiedadJ1;

        this.dineroJ2 = dineroJ2;

        this.tipo = 1;

        this.ID = "trato" + Valor.NumeroTratos++;

    }

    public Trato(Jugador j1, Jugador j2, float dineroJ1, Propiedad PropiedadJ2) { // Dinero por propiedad

        this.j1 = j1;
        this.j2 = j2;

        this.dineroJ1 = dineroJ1;

        this.PropiedadJ2 = PropiedadJ2;

        this.tipo = 2;
        this.ID = "trato" + Valor.NumeroTratos++;
    }

    public Trato(Jugador j1, Jugador j2, Propiedad PropiedadJ1, Propiedad PropiedadJ2, float dineroJ2) {// Propiedad por Propiedad y
                                                                                                // dinero

        this.j1 = j1;
        this.j2 = j2;

        this.PropiedadJ1 = PropiedadJ1;

        this.PropiedadJ2 = PropiedadJ2;
        this.dineroJ2 = dineroJ2;

        this.tipo = 3;
        this.ID = "trato" + Valor.NumeroTratos++;
    }

    public Trato(Jugador j1, Jugador j2, Propiedad PropiedadJ1, float dineroJ1, Propiedad PropiedadJ2) { // Propiedad y dinero por
        // Propiedad

        this.j1 = j1;
        this.j2 = j2;

        this.PropiedadJ1 = PropiedadJ1;
        this.dineroJ1 = dineroJ1;

        this.PropiedadJ2 = PropiedadJ2;

        this.tipo = 4;
        this.ID = "trato" + Valor.NumeroTratos++;
    }

    private void trato0() {

        this.j1.anhadirPropiedad(PropiedadJ2);
        this.j1.eliminarPropiedad(PropiedadJ1);

        this.j2.anhadirPropiedad(PropiedadJ1);
        this.j2.eliminarPropiedad(PropiedadJ2);

    }

    private void trato1() {

        this.j1.sumarFortuna(dineroJ2);
        this.j1.eliminarPropiedad(PropiedadJ1);

        this.j2.anhadirPropiedad(PropiedadJ1);
        this.j2.sumarFortuna(-dineroJ2);

    }

    private void trato2() {

        this.j1.anhadirPropiedad(PropiedadJ2);
        this.j1.sumarFortuna(-dineroJ1);

        this.j2.eliminarPropiedad(PropiedadJ2);
        this.j2.sumarFortuna(dineroJ1);

    }

    private void trato3() {

        this.j1.anhadirPropiedad(PropiedadJ2);
        this.j1.eliminarPropiedad(PropiedadJ1);
        this.j1.sumarFortuna(dineroJ2);

        this.j2.anhadirPropiedad(PropiedadJ1);
        this.j2.eliminarPropiedad(PropiedadJ2);
        this.j2.sumarFortuna(-dineroJ2);

    }

    private void trato4() {

        this.j1.anhadirPropiedad(PropiedadJ2);
        this.j1.eliminarPropiedad(PropiedadJ1);
        this.j1.sumarFortuna(-dineroJ1);

        this.j2.anhadirPropiedad(PropiedadJ1);
        this.j2.eliminarPropiedad(PropiedadJ2);
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