package partida;

import monopoly.Casilla.Propiedad.Propiedad;
import monopoly.Valor;

// Queda por cambiar Propiedad por Propiedad

public class Trato {

    Jugador proponiente;
    Jugador receptor;

    float dineroJ1;
    float dineroJ2;

    Propiedad PropiedadJ1;
    Propiedad PropiedadJ2;

    int tipo;
    String ID;

    public Trato(Jugador proponiente, Jugador receptor, Propiedad PropiedadJ1, Propiedad PropiedadJ2) { // Propiedad por Propiedad

        this.proponiente = proponiente;
        this.receptor = receptor;

        this.PropiedadJ1 = PropiedadJ1;

        this.PropiedadJ2 = PropiedadJ2;

        this.tipo = 0;

        this.ID = "trato" + Valor.NumeroTratos++;
    }

    public Trato(Jugador proponiente, Jugador receptor, Propiedad PropiedadJ1, float dineroJ2) { // Propiedad por dinero

        this.proponiente = proponiente;
        this.receptor = receptor;

        this.PropiedadJ1 = PropiedadJ1;

        this.dineroJ2 = dineroJ2;

        this.tipo = 1;

        this.ID = "trato" + Valor.NumeroTratos++;

    }

    public Trato(Jugador proponiente, Jugador receptor, float dineroJ1, Propiedad PropiedadJ2) { // Dinero por propiedad

        this.proponiente = proponiente;
        this.receptor = receptor;

        this.dineroJ1 = dineroJ1;

        this.PropiedadJ2 = PropiedadJ2;

        this.tipo = 2;
        this.ID = "trato" + Valor.NumeroTratos++;
    }

    public Trato(Jugador proponiente, Jugador receptor, Propiedad PropiedadJ1, Propiedad PropiedadJ2, float dineroJ2) {// Propiedad por Propiedad y
                                                                                                // dinero

        this.proponiente = proponiente;
        this.receptor = receptor;

        this.PropiedadJ1 = PropiedadJ1;

        this.PropiedadJ2 = PropiedadJ2;
        this.dineroJ2 = dineroJ2;

        this.tipo = 3;
        this.ID = "trato" + Valor.NumeroTratos++;
    }

    public Trato(Jugador proponiente, Jugador receptor, Propiedad PropiedadJ1, float dineroJ1, Propiedad PropiedadJ2) { // Propiedad y dinero por
        // Propiedad

        this.proponiente = proponiente;
        this.receptor = receptor;

        this.PropiedadJ1 = PropiedadJ1;
        this.dineroJ1 = dineroJ1;

        this.PropiedadJ2 = PropiedadJ2;

        this.tipo = 4;
        this.ID = "trato" + Valor.NumeroTratos++;
    }

    private void trato0() {

        this.proponiente.anhadirPropiedad(PropiedadJ2);
        this.proponiente.eliminarPropiedad(PropiedadJ1);

        this.receptor.anhadirPropiedad(PropiedadJ1);
        this.receptor.eliminarPropiedad(PropiedadJ2);

    }

    private void trato1() {

        this.proponiente.sumarFortuna(dineroJ2);
        this.proponiente.eliminarPropiedad(PropiedadJ1);

        this.receptor.anhadirPropiedad(PropiedadJ1);
        this.receptor.sumarFortuna(-dineroJ2);

    }

    private void trato2() {

        this.proponiente.anhadirPropiedad(PropiedadJ2);
        this.proponiente.sumarFortuna(-dineroJ1);

        this.receptor.eliminarPropiedad(PropiedadJ2);
        this.receptor.sumarFortuna(dineroJ1);

    }

    private void trato3() {

        this.proponiente.anhadirPropiedad(PropiedadJ2);
        this .proponiente.eliminarPropiedad(PropiedadJ1);
        this.proponiente.sumarFortuna(dineroJ2);

        this.receptor.anhadirPropiedad(PropiedadJ1);
        this.receptor.eliminarPropiedad(PropiedadJ2);
        this.receptor.sumarFortuna(-dineroJ2);

    }

    private void trato4() {

        this.proponiente.anhadirPropiedad(PropiedadJ2);
        this.proponiente.eliminarPropiedad(PropiedadJ1);
        this.proponiente.sumarFortuna(-dineroJ1);

        this.receptor.anhadirPropiedad(PropiedadJ1);
        this.receptor.eliminarPropiedad(PropiedadJ2);
        this.receptor.sumarFortuna(dineroJ1);

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

    public Jugador getReceptor(){

        return this.receptor;

    }

    @Override    
    public String toString(){

        String ret = new String();

        ret += ("id" + this.ID +"\n");
        ret +=("jugadorPropone" + this.proponiente.getNombre() +"\n");

        switch(tipo){

            case 0: // Propiedad por propiedad

            ret += ("cambiar (" + this.PropiedadJ1.getNombre() + ", "+ this.PropiedadJ2.getNombre() + ")\n");
                break;

            case 1: // Propiedad por dinero

            ret += ("cambiar (" + this.PropiedadJ1.getNombre() + ", " + this.dineroJ2+ ")\n");
                break;

            case 2: // Dinero por propiedad

                ret += ("cambiar (" + this.dineroJ1 + ", " + this.PropiedadJ2.getNombre() + ")\n");
                break;

            case 3: // Propiedad por propiedad y dinero

                ret += ("cambiar (" + this.PropiedadJ1.getNombre() + ", "+ this.PropiedadJ2.getNombre() + " y " + this.dineroJ2 + ")\n");
                break;

            case 4: // Propiedad y dinero por propiedad

                ret += ("cambiar (" + this.PropiedadJ1.getNombre() +" y " + this.dineroJ1 + ", "+ this.PropiedadJ2.getNombre() + ")\n");
                break;
        }

        return ret;
    }
}