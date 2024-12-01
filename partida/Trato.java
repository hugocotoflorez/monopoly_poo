package partida;

import monopoly.*;
import monopoly.Casilla.Propiedad.Propiedad;

// Queda por cambiar Propiedad por Propiedad

public class Trato {

    Jugador proponedor;
    Jugador receptor;

    Float dineroJ1;
    Float dineroJ2;

    Propiedad PropiedadJ1;
    Propiedad PropiedadJ2;

    int tipo;
    String ID;

    public Trato(Jugador proponedor, Jugador receptor, Propiedad PropiedadJ1, Propiedad PropiedadJ2) { // Propiedad
                                                                                                       // por
        // Propiedad
        if (proponedor != null && receptor != null && PropiedadJ1.getDuenho().equals(proponedor)
                && PropiedadJ2.getDuenho().equals(receptor)) {

            this.proponedor = proponedor;
            this.receptor = receptor;

            this.PropiedadJ1 = PropiedadJ1;
            this.dineroJ1 = 0f;

            this.PropiedadJ2 = PropiedadJ2;
            this.dineroJ2 = 0f;

            this.tipo = 0;

            this.ID = "trato" + Valor.NumeroTratos++;
        }

        else if (proponedor == null) {

            Juego.consola.imprimirError("El jugador proponedor no existe");

            this.receptor = null;
            this.proponedor = null;
        } 
        
        else if (receptor == null) {

            Juego.consola.imprimirError("El jugador receptor no existe");

            this.receptor = null;
            this.proponedor = null;
        }
    }

    public Trato(Jugador proponedor, Jugador receptor, Propiedad PropiedadJ1, Float dineroJ2) { // Propiedad por
                                                                                                // dinero
        if (proponedor != null && receptor != null && PropiedadJ1.getDuenho().equals(proponedor)) {

            this.proponedor = proponedor;
            this.receptor = receptor;

            this.PropiedadJ1 = PropiedadJ1;
            this.dineroJ1 = 0f;

            this.PropiedadJ2 = null;
            this.dineroJ2 = dineroJ2;

            this.tipo = 1;

            this.ID = "trato" + Valor.NumeroTratos++;
        } 
        
        else if (proponedor == null) {

            Juego.consola.imprimirError("El jugador proponedor no existe");

            this.receptor = null;
            this.proponedor = null;
        } 
        
        else if (receptor == null) {
            Juego.consola.imprimirError("El jugador receptor no existe");
            this.receptor = null;
            this.proponedor = null;
        } 
        
        else if (!PropiedadJ1.getDuenho().equals(proponedor)) {

            Juego.consola.imprimirError(
                    "El jugador " + proponedor.getNombre() + " no tiene la propiedad " + PropiedadJ1.getNombre());

            this.receptor = null;
            this.proponedor = null;
        }

    }

    public Trato(Jugador proponedor, Jugador receptor, Float dineroJ1, Propiedad PropiedadJ2) { // Dinero por
                                                                                                // propiedad
        if (proponedor != null && receptor != null && dineroJ1 < proponedor.getFortuna()
                && PropiedadJ2.getDuenho().equals(receptor)) {
            this.proponedor = proponedor;
            this.receptor = receptor;

            this.PropiedadJ1 = null;
            this.dineroJ1 = dineroJ1;

            this.PropiedadJ2 = PropiedadJ2;
            this.dineroJ2 = 0f;

            this.tipo = 2;
            this.ID = "trato" + Valor.NumeroTratos++;
        }

        else if (proponedor == null) {

            Juego.consola.imprimirError("El jugador proponedor no existe");

            this.receptor = null;
            this.proponedor = null;
        }

        else if (receptor == null) {

            Juego.consola.imprimirError("El jugador receptor no existe");

            this.receptor = null;
            this.proponedor = null;
        }

        else if (dineroJ1 > proponedor.getFortuna()) {

            Juego.consola
                    .imprimirError("No tienes suficiente dinero para ejecutar el trato: " + proponedor.getFortuna());

            this.proponedor = null;
            this.receptor = null;
        }

        else if (!PropiedadJ2.getDuenho().equals(receptor)) {

            Juego.consola.imprimirError(
                    "El jugador " + receptor.getNombre() + " no tiene la propiedad " + PropiedadJ2.getNombre());

            this.receptor = null;
            this.proponedor = null;
        }
    }

    public Trato(Jugador proponedor, Jugador receptor, Propiedad PropiedadJ1, Propiedad PropiedadJ2, Float dineroJ2) { // Propiedad
        // por
        // Propiedad
        // +
        // Dinero
        if (proponedor != null && receptor != null && PropiedadJ1.getDuenho().equals(proponedor)
                && PropiedadJ2.getDuenho().equals(receptor)) {

            this.proponedor = proponedor;
            this.receptor = receptor;

            this.PropiedadJ1 = PropiedadJ1;
            this.dineroJ1 = 0f;

            this.PropiedadJ2 = PropiedadJ2;
            this.dineroJ2 = dineroJ2;

            this.tipo = 3;
        }

        else if (proponedor == null) {

            Juego.consola.imprimirError("El jugador proponedor no existe");

            this.receptor = null;
            this.proponedor = null;
        }

        else if (receptor == null) {

            Juego.consola.imprimirError("El jugador receptor no existe");

            this.receptor = null;
            this.proponedor = null;
        }

    }

    public Trato(Jugador proponedor, Jugador receptor, Propiedad PropiedadJ1, Float dineroJ1, Propiedad PropiedadJ2) { // Propiedad
                                                                                                                       // +
                                                                                                                       // Dinero
                                                                                                                       // por
                                                                                                                       // propiedad
        if (proponedor != null && receptor != null && PropiedadJ1.getDuenho().equals(proponedor)
                && PropiedadJ2.getDuenho().equals(receptor)) {

            this.proponedor = proponedor;
            this.receptor = receptor;

            this.PropiedadJ1 = PropiedadJ1;
            this.dineroJ1 = dineroJ1;

            this.PropiedadJ2 = PropiedadJ2;
            this.dineroJ2 = 0f;

            this.tipo = 4;
        }

        else if (proponedor == null) {

            Juego.consola.imprimirError("El jugador proponedor no existe");

            this.receptor = null;
            this.proponedor = null;

        }

        else if (receptor == null) {

            Juego.consola.imprimirError("El jugador receptor no existe");

            this.receptor = null;
            this.proponedor = null;

        }
    }

    private void trato0() {

        this.proponedor.anhadirPropiedad(PropiedadJ2);
        this.proponedor.eliminarPropiedad(PropiedadJ1);

        this.receptor.anhadirPropiedad(PropiedadJ1);
        this.receptor.eliminarPropiedad(PropiedadJ2);

        Juego.consola.imprimir("Se ha aceptado el siguiente trato con: " + this.proponedor + ": le doy "
                + this.PropiedadJ2.getNombre() + " a cambio de " + this.PropiedadJ1.getNombre());

    }

    private void trato1() {

        this.proponedor.sumarFortuna(dineroJ2);
        this.proponedor.eliminarPropiedad(PropiedadJ1);

        this.receptor.anhadirPropiedad(PropiedadJ1);
        this.receptor.sumarFortuna(-dineroJ2);

        Juego.consola.imprimir("Se ha aceptado el siguiente trato con: " + this.proponedor + ": le doy "
                + this.dineroJ2 + " a cambio de " + this.PropiedadJ1.getNombre());

    }

    private void trato2() {

        this.proponedor.anhadirPropiedad(PropiedadJ2);
        this.proponedor.sumarFortuna(-dineroJ1);

        this.receptor.eliminarPropiedad(PropiedadJ2);
        this.receptor.sumarFortuna(dineroJ1);

        Juego.consola.imprimir("Se ha aceptado el siguiente trato con: " + this.proponedor + ": le doy "
                + this.PropiedadJ2.getNombre() + " a cambio de " + this.dineroJ1);

    }

    private void trato3() {

        this.proponedor.anhadirPropiedad(PropiedadJ2);
        this.proponedor.eliminarPropiedad(PropiedadJ1);
        this.proponedor.sumarFortuna(dineroJ2);

        this.receptor.anhadirPropiedad(PropiedadJ1);
        this.receptor.eliminarPropiedad(PropiedadJ2);
        this.receptor.sumarFortuna(-dineroJ2);

        Juego.consola.imprimir("Se ha aceptado el siguiente trato con: " + this.proponedor + ": le doy "
                + this.PropiedadJ2.getNombre() + " y " + this.dineroJ2 + " a cambio de "
                + this.PropiedadJ1.getNombre());

    }

    private void trato4() {

        this.proponedor.anhadirPropiedad(PropiedadJ2);
        this.proponedor.eliminarPropiedad(PropiedadJ1);
        this.proponedor.sumarFortuna(-dineroJ1);

        this.receptor.anhadirPropiedad(PropiedadJ1);
        this.receptor.eliminarPropiedad(PropiedadJ2);
        this.receptor.sumarFortuna(dineroJ1);

        Juego.consola.imprimir("Se ha aceptado el siguiente trato con: " + this.proponedor + ": le doy "
                + this.PropiedadJ2.getNombre() + " a cambio de " + this.PropiedadJ1.getNombre() + " y "
                + this.dineroJ1);

    }

    public void aceptar() {

        switch (tipo) {

            case 0: // Propiedad por propiedad

                if (!this.PropiedadJ1.getDuenho().equals(this.proponedor)) {

                    // TODO Gestion de errores
                    return;
                }

                if (!this.PropiedadJ2.getDuenho().equals(this.receptor)) {

                    // TODO Gestion de errores
                    return;

                }
                this.trato0();
                break;

            case 1: // Propiedad por dinero

                if (!this.PropiedadJ1.getDuenho().equals(this.proponedor)) {

                    // TODO Gestion de errores
                    return;
                }

                if (this.dineroJ2 > this.receptor.getFortuna()) {
                    // TODO Gestion de errores
                    return;
                }

                this.trato1();
                break;

            case 2: // Dinero por proiedad

                if (!this.PropiedadJ2.getDuenho().equals(this.receptor)) {

                    // TODO Gestion de errores
                    return;
                }

                if (this.dineroJ1 > this.proponedor.getFortuna()) {
                    // TODO Gestion de errores
                    return;
                }

                this.trato2();
                break;

            case 3: // Propiedad + Dinero por propiedad

                if (!this.PropiedadJ1.getDuenho().equals(this.proponedor)) {

                    // TODO Gestion de errores
                    return;
                }

                if (this.dineroJ1 > this.proponedor.getFortuna()) {
                    // TODO Gestion de errores
                    return;
                }

                if (!this.PropiedadJ2.getDuenho().equals(this.receptor)) {

                    // TODO Gestion de errores
                    return;
                }

                this.trato3();
                break;

            case 4: // Propiedad por Propiedad + Dinero

                if (!this.PropiedadJ1.getDuenho().equals(this.proponedor)) {

                    // TODO Gestion de errores
                    return;
                }

                if (this.dineroJ2 > this.receptor.getFortuna()) {
                    // TODO Gestion de errores
                    return;
                }

                if (!this.PropiedadJ2.getDuenho().equals(this.receptor)) {

                    // TODO Gestion de errores
                    return;
                }

                this.trato4();
                break;

        }

    }

    public Jugador getReceptor() {

        return this.receptor;

    }

    public Jugador getProponedor() {

        return this.proponedor;

    }

    public String getID() {

        return this.ID;

    }

    @Override
    public String toString() {

        String ret = new String();
        ret += "{\n";
        ret += ("\tid " + this.ID + "\n");
        ret += ("\tjugadorPropone " + this.proponedor.getNombre() + "\n");

        switch (tipo) {

            case 0: // Propiedad por propiedad

                ret += ("\tcambiar (" + this.PropiedadJ1.getNombre() + ", " + this.PropiedadJ2.getNombre() + ")\n");
                break;

            case 1: // Propiedad por dinero

                ret += ("\tcambiar (" + this.PropiedadJ1.getNombre() + ", " + this.dineroJ2 + ")\n");
                break;

            case 2: // Dinero por propiedad

                ret += ("\tcambiar (" + this.dineroJ1 + ", " + this.PropiedadJ2.getNombre() + ")\n");
                break;

            case 3: // Propiedad por propiedad y dinero

                ret += ("\tcambiar (" + this.PropiedadJ1.getNombre() + ", " + this.PropiedadJ2.getNombre() + " y "
                        + this.dineroJ2 + ")\n");
                break;

            case 4: // Propiedad y dinero por propiedad

                ret += ("\tcambiar (" + this.PropiedadJ1.getNombre() + " y " + this.dineroJ1 + ", "
                        + this.PropiedadJ2.getNombre() + ")\n");
                break;
        }
        ret += "}\n";
        return ret;
    }

    public boolean esTratoValido(){

        return this.proponedor != null && this.receptor != null;
        
    }
}