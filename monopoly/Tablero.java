package monopoly;

import partida.*;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashMap;

public class Tablero {
    // Atributos.
    private ArrayList<ArrayList<Casilla>> posiciones; // Posiciones del tablero: se define como un arraylist de
                                                      // arraylists de casillas (uno por cada lado del tablero).
    private HashMap<String, Grupo> grupos; // Grupos del tablero, almacenados como un HashMap con clave String (será el
                                           // color del grupo).
    private Jugador banca; // Un jugador que será la banca.

    private int casillaWidth = 6;
    private int casillaHeight = 2;
    private char borderChar = '#';

    // Constructor: únicamente le pasamos el jugador banca (que se creará desde el
    // menú).
    public Tablero(Jugador banca) {
        generarCasillas();
    }

    // Método para crear todas las casillas del tablero. Formado a su vez por cuatro
    // métodos (1/lado).
    private void generarCasillas() {
        this.posiciones= new ArrayList<ArrayList<Casilla>>();
        this.insertarLadoSur();
        this.insertarLadoOeste();
        this.insertarLadoNorte();
        this.insertarLadoEste();
    }

    // Método para insertar las casillas del lado norte.
    private void insertarLadoNorte() {
        ArrayList<Casilla> lado = new ArrayList<Casilla>();
        Casilla c;
        for (int i = 0; i < 10; i++)
        {
            c = new Casilla();
            lado.add(c);
        }
        posiciones.add(lado);
    }

    // Método para insertar las casillas del lado sur.
    private void insertarLadoSur() {
    }

    // Método que inserta casillas del lado oeste.
    private void insertarLadoOeste() {
    }

    // Método que inserta las casillas del lado este.
    private void insertarLadoEste() {
    }

    // Para imprimir el tablero, modificamos el método toString().
    @Override
    public String toString() {
        String ret = new String();
        ret += String.valueOf(borderChar).repeat(casillaWidth*10+1);
        ret += '\n';
        for (Casilla c : posiciones.get(0))
            ret += borderChar + String.format("%s", c.toString());
        ret += borderChar;
        ret += '\n';
        ret += String.valueOf(borderChar).repeat(casillaWidth*10+1);
        ret += '\n';
        return ret;
    }

    // Método usado para buscar la casilla con el nombre pasado como argumento:
    public Casilla encontrar_casilla(String nombre) {
    }
}

/**
 * String pai
 *
 * es Fr *
 */