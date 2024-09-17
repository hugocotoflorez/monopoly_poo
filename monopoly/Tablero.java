package monopoly;
//
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
        this.insertarLadoNorte();
        this.insertarLadoOeste();
        this.insertarLadoEste();
        this.insertarLadoSur();
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
        /* Se anade en el indice 0 */
    }

    // Método para insertar las casillas del lado sur.
    private void insertarLadoSur() {
        ArrayList<Casilla> lado = new ArrayList<Casilla>();
        Casilla c;
        for (int i = 0; i < 10; i++)
        {
            c = new Casilla();
            lado.add(c);
        }
        posiciones.add(lado);
        /* Se anade en el indice 3 */
    }

    // Método que inserta casillas del lado oeste.
    private void insertarLadoOeste() {
        ArrayList<Casilla> lado = new ArrayList<Casilla>();
        Casilla c;
        for (int i = 0; i < 8; i++)
        {
            c = new Casilla();
            lado.add(c);
        }
        posiciones.add(lado);
        /* Se anade en el indice 2 */
    }

    // Método que inserta las casillas del lado este.
    private void insertarLadoEste() {
        ArrayList<Casilla> lado = new ArrayList<Casilla>();
        Casilla c;
        for (int i = 0; i < 10; i++)
        {
            c = new Casilla();
            lado.add(c);
        }
        posiciones.add(lado);
        /* Se anade en el indice 3 */
    }

    // Para imprimir el tablero, modificamos el método toString().
    @Override
    public String toString() {
        /* Primera parte: Lado norte
         * El lado norte contiene las 10 primeras casillas del tablero
         *
         * Routine
         * 1) Se pinta el borde superior del tamano necesario
         * 2) Se pinta cada casilla de la parte superior
         * 3) Se pinta el borde inferior
         */
        // 1
        String ret = new String();
        ret += String.valueOf(borderChar).repeat(casillaWidth*10+1);
        ret += '\n';
        // 2
        for (Casilla c : posiciones.get(0))
            ret += borderChar + String.format("%s", c.toString());
        ret += borderChar;
        ret += '\n';
        // 3
        ret += String.valueOf(borderChar).repeat(casillaWidth*10+1);
        ret += '\n';
        /**
         * Pinta los dos laterales a la vez (8 casillas)
         */
        for (int i = 0; i < 8; i++)
        {
            ret += borderChar + String.format("%s", posiciones.get(1).get(i).toString());
            ret += '#';
            ret += String.valueOf(' ').repeat(casillaWidth*8-1);
            ret += borderChar + String.format("%s", posiciones.get(2).get(i).toString());
            ret += '#';
            ret += '\n';
            if (i != 7){
                ret += String.valueOf(borderChar).repeat(casillaWidth+1);
                ret += String.valueOf(' ').repeat(casillaWidth*8-1);
                ret += String.valueOf(borderChar).repeat(casillaWidth+1);
                ret += '\n';
            }

        }
        /* Ultima parte: Lado sur
         * El lado sur contiene las 10 primeras casillas del tablero
         *
         * Routine
         * 1) Se pinta el borde superior del tamano necesario
         * 2) Se pinta cada casilla de la parte superior
         * 3) Se pinta el borde inferior
         */
        // 1
        ret += String.valueOf(borderChar).repeat(casillaWidth*10+1);
        ret += '\n';
        // 2
        for (Casilla c : posiciones.get(3))
            ret += borderChar + String.format("%s", c.toString());
        ret += borderChar;
        ret += '\n';
        // 3
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
