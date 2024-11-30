package monopoly.Casilla;

import java.util.ArrayList;
import java.util.Iterator;

import monopoly.Edificio.*;
import monopoly.Grupo;
import monopoly.Juego;
import monopoly.Valor;
import partida.*;
import partida.Avatar.*;

public abstract class Casilla {

    // Atributos:
    private String nombre; // Nombre de la casilla
    private int posicion; // Posición que ocupa la casilla en el tablero (entero entre 0 y 40).
    private ArrayList<Avatar> avatares; // Avatares que están situados en la casilla.
    private int[] caidasEnCasilla = { 0, 0, 0, 0, 0, 0 }; // Número de veces que el jugador iesimo cayó en la casilla.

    public static final int casillaWidth = 10;

    //Constructor general para todas las casillas
    public Casilla(String nombre, int posicion){
        this.nombre = nombre;
        this.posicion = posicion;
        this.avatares = new ArrayList<Avatar>();
    }

    /*
     * Constructor utilizado para crear las otras casillas (Suerte, Caja de
     * comunidad y Especiales):
     * Parámetros: nombre, tipo de la casilla (será uno de los que queda), posición
     * en el tablero y dueño.
     */
    /*public Casilla(String nombre, String tipo, int posicion, Jugador duenho) {
        this.nombre = nombre;
        check(tipo.equals("suerte") || tipo.equals("caja") || tipo.equals("especial"), "casilla.tipo unexpected value");
        this.tipo = tipo;
        this.posicion = posicion;
        this.duenho = duenho;
    }*/

    // GETTERS Y SETTERS ------------------------------------
    public String getNombre() {
        return this.nombre;
    }

    public int getPosicion() {

        return this.posicion;
    }

    public int[] getCaidasEnCasilla() {
        return this.caidasEnCasilla;
    }

    public ArrayList<Avatar> getAvatares(){
        return this.avatares;
    }

    //--------------------

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPosicion(int pos) {
        this.posicion = pos;
    }

    //No hay setters para caidas en casilla y avatares porque se manejan como arrays

    //--------------------------------------------------------

    // Método utilizado para añadir un avatar al array de avatares en casilla.
    public void anhadirAvatar(Avatar av) {
        this.avatares.add(av);
    }

    // Método utilizado para eliminar un avatar del array de avatares en casilla.
    public void eliminarAvatar(Avatar av) {
        this.avatares.remove(av);
    }

    // Método para incrementar en 1 el número de veces que se cayó en una casilla.
    public void actualizarCaidasEnCasilla(int jugador) {
        if (jugador < 6 && jugador >= 0)
            this.caidasEnCasilla[jugador] += 1;
    }

    // Método para que devuelve el total de visitas de la casilla
    public int totalVisitas() {
        int ret = 0;
        for (int i = 0; i < 6; i++)
            ret += caidasEnCasilla[i];
        return ret;
    }


    /*
     * Método para evaluar qué hacer en una casilla concreta. Parámetros:
     * - Jugador cuyo avatar está en esa casilla.
     * - La banca (para ciertas comprobaciones).
     * - El valor de la tirada: para determinar impuesto a pagar en casillas de
     * servicios.KO
     * Valor devuelto: true en caso de ser solvente (es decir, de cumplir las
     * deudas), y false
     * en caso de no cumplirlas.
     */
    //Sólo dios sabe dónde vamos a meter todo esto
    public abstract boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada);
    /*public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada) {
        Casilla c = this;
        switch (c.getTipo()) {
            // supuestamente acabado
            case "solar":

                if (!c.esComprable(actual)) {
                    // se le resta el impuesto y se lo da al jugador que tiene
                    // la casilla
                    if (!this.hipotecada && !this.duenho.equals(actual)) {

                        if (this.grupo.esDuenhoGrupo(this.duenho)) {
                            Juego.consola.imprimir("El jugador " + this.duenho.getNombre()
                                    + " ya tiene todos los solares del grupo. Se va a duplicar su alquiler.");
                            this.grupo.actualizarAlquilerGrupo();
                        }
                        c.actualizarValorCasilla();

                        actual.sumarFortuna(-c.getImpuesto());// revisar
                        c.getDuenho().sumarFortuna(c.getImpuesto());

                        Juego.consola.imprimir("El jugador " + actual.getNombre() + " paga " +
                                c.getImpuesto() + " a " + c.getDuenho().getNombre());

                        actual.setPagoDeAlquileres(actual.getPagoDeAlquileres() + c.getImpuesto());
                        c.getDuenho().setCobroDeAlquileres(c.getDuenho().getCobroDeAlquileres() + c.impuesto);
                        c.recaudado += c.impuesto;
                    } else if (this.hipotecada)
                        Juego.consola.imprimir("El jugador " + this.getDuenho().getNombre()
                                + "no cobra alquiler porque la casilla está hipotecada.");

                    break;
                }
                /*
                 * La opcion de comprar se lleva a cabo
                 * desde el menu, esta funciona unicamente
                 * devuelve si se puede comprar o no
                 */
                /*Juego.consola.imprimir("Se puede comprar la casilla " + c.getNombre());
                return true; // se puede comprar

            case "especial":
                if (this.nombre.equals("Carcel")) {
                    // no pasa nada
                    Juego.consola.imprimir("Estás de visita en la cárcel.");
                }
                if (this.nombre.equals("Parking")) {
                    Juego.consola.imprimir("El jugador " + actual.getNombre() + " consigue el bote de la banca de "
                            + banca.getGastos());
                    actual.sumarFortuna(banca.getGastos());
                    actual.setPremiosInversionesOBote(actual.getPremiosInversionesOBote() + banca.getGastos());
                    banca.resetGastos();
                    this.setValor(0);
                }
                if (this.nombre.equals("IrCarcel")) {
                    Juego.consola.imprimir("Oh no! Has ido a la Cárcel!");
                }
                if (this.nombre.equals("Salida")) {
                    Juego.consola.imprimir("Has pasado por Salida! Cobra " + Valor.SUMA_VUELTA);
                }

                break;
            case "transporte":
                if (!c.esComprable(actual)) {
                    if (!this.hipotecada && !this.duenho.equals(actual)) {
                        float p = c.getDuenho().cuantostransportes() * 0.25f * Valor.IMPUESTOS_TRANSPORTES;
                        c.setImpuesto(p);

                        actual.sumarFortuna(-c.impuesto);
                        this.getDuenho().sumarFortuna(c.impuesto);
                        Juego.consola.imprimir("El jugador " + actual.getNombre() + " paga " +
                                c.impuesto + " a " + c.getDuenho().getNombre());

                        actual.setPagoDeAlquileres(actual.getPagoDeAlquileres() + c.getImpuesto());
                        c.getDuenho().setCobroDeAlquileres(c.getDuenho().getCobroDeAlquileres() + c.impuesto);
                        c.recaudado += c.impuesto;
                    } else
                        Juego.consola.imprimir("El jugador " + this.getDuenho()
                                + "no cobra alquiler porque la casilla está hipotecaada.");

                    break;
                }
                Juego.consola.imprimir("Se puede comprar la casilla " + c.getNombre());
                return true; // se puede comprar

            case "caja":
            case "suerte":
                break;

            case "serv":
                if (!c.esComprable(actual)) {
                    if (!this.hipotecada && !this.duenho.equals(actual)) {
                        int s = (c.getDuenho().cuantosservicios() >= 2) ? 10 : 4;
                        float p = Valor.IMPUESTO_SERVICIOS * s * tirada;
                        c.setImpuesto(p);

                        actual.sumarFortuna(-c.impuesto);
                        c.getDuenho().sumarFortuna(c.impuesto);
                        Juego.consola.imprimir("El jugador " + actual.getNombre() + " paga " +
                                c.impuesto + " a " + c.getDuenho().getNombre());

                        actual.setPagoDeAlquileres(actual.getPagoDeAlquileres() + c.getImpuesto());
                        c.getDuenho().setCobroDeAlquileres(c.getDuenho().getCobroDeAlquileres() + c.impuesto);
                        c.recaudado += c.impuesto;
                    } else
                        Juego.consola.imprimir("El jugador " + this.getDuenho()
                                + "no cobra alquiler porque la casilla está hipotecaada.");

                    break;
                }
                Juego.consola.imprimir("Se puede comprar la casilla " + c.getNombre());
                return true; // se puede comprar

            case "impuestos":
                Juego.consola.imprimir("Has caído en una casilla de impuestos. Se te va a cobrar " + c.impuesto);
                actual.sumarFortuna(-c.impuesto);
                banca.sumarGastos(c.impuesto);

                Juego.consola.imprimir("El bote de la banca ahora es " + banca.getGastos());

                actual.setPagoTasasEImpuestos(actual.getPagoTasasEImpuestos() + c.impuesto);
                break;

            default:
                System.err.println("Hugo no añadio el tipo %s a evaluarCasilla");

        }
        return actual.getFortuna() > 0;

    }*/

    public void eliminarAvatarCasilla(String ID) { // Elimina un avatar de la lista de avatares dado su ID

        for (int i = 0; i < avatares.size(); i++) {
            if (this.avatares.get(i).getId().equals(ID)) {
                this.avatares.remove(i);
            }
        }
    }

    public void anhadirAvatarCasilla(Avatar avatar) {
        this.avatares.add(avatar);
    }

    public abstract String infoCasilla(Jugador banca);

    /*
     * Método para mostrar información de una casilla en venta.
     * Valor devuelto: texto con esa información.
     */
    public String casEnVenta() {
        return "";
    }

    public String printCasilla() {
        String data = new String();
        data += this.nombre;
        /*
         * Esta funcion se usa para obtener los datos de la casilla al pintar
         * el tablero. Se necesita que sea del mismo tamano que CasillaWidth-1
         */
        if (this instanceof monopoly.Casilla.Propiedad.Solar){
            return (((monopoly.Casilla.Propiedad.Solar) this).getGrupo().getColor() + Valor.BOLD + data + " ".repeat(casillaWidth - data.length() - 1) + Valor.RESET);
        }
        else
            return (Valor.WHITE+ Valor.BOLD + data + " ".repeat(casillaWidth - data.length() - 1) + Valor.RESET);
    }

    public String printAvatares() {
        String data = new String();
        for (Avatar av : this.avatares)
            data +=Juego.generateColor(av.getJugador().getNombre())+ av.getId() + " \033[0m";
        return Valor.BOLD + data + " ".repeat(casillaWidth - data.length() + 9*avatares.size()- 1) + Valor.RESET;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
}