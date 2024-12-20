package monopoly.Casilla;

import java.util.ArrayList;

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
        if(posicion >= 0 && posicion < 40)
        this.posicion = pos;
    }

    //No hay setters para caidas en casilla y avatares porque se manejan como arrays

    //--------------------------------------------------------

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


    public abstract boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada);

    // Método utilizado para eliminar un avatar del array de avatares en casilla.
    public void eliminarAvatar(Avatar av) {
        this.avatares.remove(av);
    }

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
        data += "\033[0;1m";
        for (Avatar av : this.avatares)
            data +=Juego.generateColor(av.getJugador().getNombre())+ av.getId() + "\033[1m";
        return Valor.BOLD + data + " ".repeat(casillaWidth - data.length() + 9*avatares.size()+ 5) + Valor.RESET;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
}