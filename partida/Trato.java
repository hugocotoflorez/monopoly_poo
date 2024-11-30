package partida;

import monopoly.Casilla.Casilla;
import monopoly.Casilla.Propiedad.Propiedad;
import monopoly.Juego;
import monopoly.Tablero;
import monopoly.Valor;

// Queda por cambiar Propiedad por Propiedad

public class Trato {

    Jugador proponiente;
    Jugador receptor;

    Float dineroJ1;
    Float dineroJ2;

    Propiedad PropiedadJ1;
    Propiedad PropiedadJ2;

    int tipo;
    String ID;

    private void trato(Jugador proponiente, Jugador receptor, Propiedad PropiedadJ1, Propiedad PropiedadJ2) { // Propiedad
                                                                                                              // por
        // Propiedad

        this.proponiente = proponiente;
        this.receptor = receptor;

        this.PropiedadJ1 = PropiedadJ1;

        this.PropiedadJ2 = PropiedadJ2;

        this.tipo = 0;

        this.ID = "trato" + Valor.NumeroTratos++;
    }

    private void trato(Jugador proponiente, Jugador receptor, Propiedad PropiedadJ1, Float dineroJ2) { // Propiedad por
                                                                                                       // dinero

        this.proponiente = proponiente;
        this.receptor = receptor;

        this.PropiedadJ1 = PropiedadJ1;

        this.dineroJ2 = dineroJ2;

        this.tipo = 1;

        this.ID = "trato" + Valor.NumeroTratos++;

    }

    private void trato(Jugador proponiente, Jugador receptor, Float dineroJ1, Propiedad PropiedadJ2) { // Dinero por
                                                                                                       // propiedad

        this.proponiente = proponiente;
        this.receptor = receptor;

        this.dineroJ1 = dineroJ1;

        this.PropiedadJ2 = PropiedadJ2;

        this.tipo = 2;
        this.ID = "trato" + Valor.NumeroTratos++;
    }

    private void trato(Jugador proponiente, Jugador receptor, Propiedad PropiedadJ1, Propiedad PropiedadJ2,
            Float dineroJ2) {// Propiedad por Propiedad y dinero

        this.proponiente = proponiente;
        this.receptor = receptor;

        this.PropiedadJ1 = PropiedadJ1;

        this.PropiedadJ2 = PropiedadJ2;
        this.dineroJ2 = dineroJ2;

        this.tipo = 3;
        this.ID = "trato" + Valor.NumeroTratos++;
    }

    private void trato(Jugador proponiente, Jugador receptor, Propiedad PropiedadJ1, Float dineroJ1,
            Propiedad PropiedadJ2) { // Propiedad y dinero por Propiedad

        this.proponiente = proponiente;
        this.receptor = receptor;

        this.PropiedadJ1 = PropiedadJ1;
        this.dineroJ1 = dineroJ1;

        this.PropiedadJ2 = PropiedadJ2;

        this.tipo = 4;
        this.ID = "trato" + Valor.NumeroTratos++;
    }

    public Trato(Jugador proponedor, Jugador receptor, String of1, String of2, Tablero tablero) {

        Casilla c1 = tablero.encontrar_casilla(of1);
        Casilla c2 = tablero.encontrar_casilla(of2);

        if (receptor == null) {
            Juego.consola.imprimirError("No se ha encontrado el jugador!\n");
            return;
        }

        if (c1 != null && c2 != null) {
            if (!(c1 instanceof Propiedad)){
                Juego.consola.imprimirError("La casilla " + c1.getNombre() + "no es una propiedad");
                return;
            }
            if (!(c2 instanceof Propiedad)){
                Juego.consola.imprimirError("La casilla " + c2.getNombre() + "no es una propiedad");
                return;
            }

            Propiedad p1 = (Propiedad) c1;
            Propiedad p2 = (Propiedad) c2;

            if(!p1.getDuenho().equals(proponedor)){
                Juego.consola.imprimirError("El jugador " + proponedor.getNombre() + " no es dueño de la Casilla\n");
                return;
            }
            if(!p1.getDuenho().equals(receptor)){

                Juego.consola.imprimirError("El jugador " + receptor.getNombre() + " no es dueño de la Casilla\n");
                return;

            }

            trato(proponedor, receptor, p1, p2);

        } else if (c1 != null && c2 == null) {
            if (!(c1 instanceof Propiedad)){
                Juego.consola.imprimirError("La casilla " + c1.getNombre() + "no es una propiedad");
                return;
            }

            trato(proponedor, receptor, Float.parseFloat(of1), (Propiedad) c2);

        }

        else if (c1 == null && c2 != null) {
            if (!(c2 instanceof Propiedad)){
                /* TODO: GUILLE c1 es null no puedes acceder a su nombre */
                Juego.consola.imprimirError("La casilla " + c1.getNombre() + "no es una propiedad");
                return;
            }

            trato(proponedor, receptor, (Propiedad) c1, Float.parseFloat(of2));

        }

        else
            Juego.consola.imprimirError("Uso incorrecto < trato Maria: cambiar ( Solar1, 300000 ) >");

    }

    public Trato(Jugador proponedor, Jugador receptor, String of1, String of2, String of3, Tablero tablero){

        Casilla c1 = tablero.encontrar_casilla(of1);
        Casilla c2 = tablero.encontrar_casilla(of2);
        Casilla c3 = tablero.encontrar_casilla(of3);

        if(c1 != null && c2 != null && c3 == null) // Propiedad por propiedad y dinero
            trato(proponedor, receptor, (Propiedad) c1, (Propiedad) c2, Float.parseFloat(of3));

        else if(c1 != null && c2 == null && c3 != null) // Propiedad y dinero por propieadad
            trato(proponedor,receptor, (Propiedad) c1, Float.parseFloat(of2), (Propiedad) c3);
        else
            Juego.consola.imprimirError("Uso incorrecto < trato Maria: cambiar ( Solar1, Solar14 y 300000 ) >");
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
        this.proponiente.eliminarPropiedad(PropiedadJ1);
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

    public Jugador getReceptor() {

        return this.receptor;

    }

    @Override
    public String toString() {

        String ret = new String();

        ret += ("id" + this.ID + "\n");
        ret += ("jugadorPropone" + this.proponiente.getNombre() + "\n");

        switch (tipo) {

            case 0: // Propiedad por propiedad

                ret += ("cambiar (" + this.PropiedadJ1.getNombre() + ", " + this.PropiedadJ2.getNombre() + ")\n");
                break;

            case 1: // Propiedad por dinero

                ret += ("cambiar (" + this.PropiedadJ1.getNombre() + ", " + this.dineroJ2 + ")\n");
                break;

            case 2: // Dinero por propiedad

                ret += ("cambiar (" + this.dineroJ1 + ", " + this.PropiedadJ2.getNombre() + ")\n");
                break;

            case 3: // Propiedad por propiedad y dinero

                ret += ("cambiar (" + this.PropiedadJ1.getNombre() + ", " + this.PropiedadJ2.getNombre() + " y "
                        + this.dineroJ2 + ")\n");
                break;

            case 4: // Propiedad y dinero por propiedad

                ret += ("cambiar (" + this.PropiedadJ1.getNombre() + " y " + this.dineroJ1 + ", "
                        + this.PropiedadJ2.getNombre() + ")\n");
                break;
        }

        return ret;
    }
}