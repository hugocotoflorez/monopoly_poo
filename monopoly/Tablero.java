package monopoly;

//
import partida.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;

public class Tablero {
    // Atributos.
    private ArrayList<ArrayList<Casilla>> posiciones; // Posiciones del tablero: se define como un arraylist de
                                                      // arraylists de casillas (uno por cada lado del tablero).
    private HashMap<String, Grupo> grupos; // Grupos del tablero, almacenados como un HashMap con clave String (será el
                                           // color del grupo).
    private Jugador banca; // Un jugador que será la banca.

    private static final boolean USAR_NERD_FONT = true;
    private char char_top_left = USAR_NERD_FONT ? '┏' : '+';
    private char char_top_right = USAR_NERD_FONT ? '┓' : '+';
    private char char_bottom_left = USAR_NERD_FONT ? '┗' : '+';
    private char char_bottom_right = USAR_NERD_FONT ? '┛' : '+';
    private char char_vertical = USAR_NERD_FONT ? '┃' : '|';
    private char char_horizontal = USAR_NERD_FONT ? '━' : '-';
    private char char_vertical_right = USAR_NERD_FONT ? '┣' : '|';
    private char char_vertical_left = USAR_NERD_FONT ? '┫' : '|';
    private char char_horizontal_up = USAR_NERD_FONT ? '┻' : '-';
    private char char_horizontal_down = USAR_NERD_FONT ? '┳' : '-';
    private char char_full_intersection = USAR_NERD_FONT ? '╋' : '+';

    // Constructor: únicamente le pasamos el jugador banca (que se creará desde el
    // menú).
    public Tablero(Jugador banca) {
        this.banca = banca;
        generarCasillas();
    }

    public Casilla posicion_salida() {
        return posiciones.get(0).get(0);
    }

    public ArrayList<ArrayList<Casilla>> getPosiciones() {
        return posiciones;
    }

    // Método para crear todas las casillas del tablero. Formado a su vez por cuatro
    // métodos (1/lado).
    private void generarCasillas() {
        this.posiciones = new ArrayList<ArrayList<Casilla>>();
        this.insertarLadoSur();
        this.insertarLadoOeste();
        this.insertarLadoNorte();
        this.insertarLadoEste();
    }

    // Dado un entero y las casillas del tablero, devuelve la casilla que está en
    // esa posición
    public Casilla obtenerCasilla(int posicion) {
        return this.posiciones.get(posicion / 10).get(posicion % 10);
    }

    // Método para insertar las casillas del lado norte.
    private void insertarLadoNorte() {
        ArrayList<Casilla> lado = new ArrayList<Casilla>();
        lado.add(new Casilla("Parking", "especial", 21, banca));
        lado.add(new Casilla("Solar12", "solar", 22, Valor.GRUPO_5, banca));
        lado.add(new Casilla("Suerte2", "suerte", 23, banca));
        lado.add(new Casilla("Solar13", "solar", 24, Valor.GRUPO_5, banca));
        lado.add(new Casilla("Solar14", "solar", 25, Valor.GRUPO_5, banca));
        lado.add(new Casilla("Trans3", "transporte", 26, Valor.SUMA_VUELTA, banca));
        lado.add(new Casilla("Solar15", "solar", 27, Valor.GRUPO_6, banca));
        lado.add(new Casilla("Solar16", "solar", 28, Valor.GRUPO_6, banca));
        lado.add(new Casilla("Serv2", "serv", 29, Valor.SUMA_VUELTA * 0.75f, banca));
        lado.add(new Casilla("Solar17", "solar", 30, Valor.GRUPO_6, banca));
        new Grupo(lado.get(1), lado.get(3), lado.get(4), Valor.COLOR_G5);
        new Grupo(lado.get(6), lado.get(7), lado.get(9), Valor.COLOR_G6);

        posiciones.add(lado);
        /* Se anade en el indice 0 */
    }

    // Método para insertar las casillas del lado sur.
    private void insertarLadoSur() {
        ArrayList<Casilla> lado = new ArrayList<Casilla>();
        lado.add(new Casilla("Salida", "especial", 1, banca));
        lado.add(new Casilla("Solar1", "solar", 2, Valor.GRUPO_1, banca));
        lado.add(new Casilla("Caja", "caja", 3, banca));
        lado.add(new Casilla("Solar2", "solar", 4, Valor.GRUPO_1, banca));
        lado.add(new Casilla("Impt1", 5, Valor.SUMA_VUELTA, banca));
        lado.add(new Casilla("Trans1", "transporte", 6, Valor.SUMA_VUELTA, banca));
        lado.add(new Casilla("Solar3", "solar", 7, Valor.GRUPO_2, banca));
        lado.add(new Casilla("Suerte", "suerte", 8, banca));
        lado.add(new Casilla("Solar4", "solar", 9, Valor.GRUPO_2, banca));
        lado.add(new Casilla("Solar5", "solar", 10, Valor.GRUPO_2, banca));
        new Grupo(lado.get(1), lado.get(3), Valor.COLOR_G1);
        new Grupo(lado.get(6), lado.get(8), lado.get(9), Valor.COLOR_G2);

        posiciones.add(lado);
    }

    // Método que inserta casillas del lado oeste.
    private void insertarLadoOeste() {
        ArrayList<Casilla> lado = new ArrayList<Casilla>();
        lado.add(new Casilla("Carcel", "especial", 11, banca));
        lado.add(new Casilla("Solar6", "solar", 12, Valor.GRUPO_3, banca));
        lado.add(new Casilla("Serv1", "serv", 13, Valor.SUMA_VUELTA * 0.75f, banca));
        lado.add(new Casilla("Solar7", "solar", 14, Valor.GRUPO_3, banca));
        lado.add(new Casilla("Solar8", "solar", 15, Valor.GRUPO_3, banca));
        lado.add(new Casilla("Trans2", "transporte", 16, Valor.SUMA_VUELTA, banca));
        lado.add(new Casilla("Solar9", "solar", 17, Valor.GRUPO_4, banca));
        lado.add(new Casilla("Caja", "caja", 18, banca));
        lado.add(new Casilla("Solar10", "solar", 19, Valor.GRUPO_4, banca));
        lado.add(new Casilla("Solar11", "solar", 20, Valor.GRUPO_4, banca));
        new Grupo(lado.get(1), lado.get(3), lado.get(4), Valor.COLOR_G3);
        new Grupo(lado.get(6), lado.get(8), lado.get(9), Valor.COLOR_G4);

        posiciones.add(lado);
    }

    // Método que inserta las casillas del lado este.
    private void insertarLadoEste() {
        ArrayList<Casilla> lado = new ArrayList<Casilla>();
        lado.add(new Casilla("IrCarcel", "especial", 31, banca));
        lado.add(new Casilla("Solar18", "solar", 32, Valor.GRUPO_7, banca));
        lado.add(new Casilla("Solar19", "solar", 33, Valor.GRUPO_7, banca));
        lado.add(new Casilla("Caja", "caja", 34, banca));
        lado.add(new Casilla("Solar20", "solar", 35, Valor.GRUPO_7, banca));
        lado.add(new Casilla("Trans4", "transporte", 36, Valor.SUMA_VUELTA, banca));
        lado.add(new Casilla("Suerte3", "suerte", 37, banca));
        lado.add(new Casilla("Solar21", "solar", 38, Valor.GRUPO_8, banca));
        lado.add(new Casilla("Impuesto2", 39, (Valor.SUMA_VUELTA) / 2, banca));
        lado.add(new Casilla("Solar22", "solar", 40, Valor.GRUPO_8, banca));
        new Grupo(lado.get(1), lado.get(2), lado.get(4), Valor.COLOR_G7);
        new Grupo(lado.get(7), lado.get(9), Valor.COLOR_G8);

        posiciones.add(lado);
    }

    // Para imprimir el tablero, modificamos el método toString().
    @Override
    public String toString() {
        String ret = new String();

        ret += "\033[H\033[2J";

        // borde superior del tablero
        ret += char_top_left;
        for (int i = 0; i < 11; i++) {
            ret += String.valueOf(char_horizontal).repeat(Casilla.casillaWidth - 1);
            if (i < 10)
                ret += char_horizontal_down;
        }
        ret += char_top_right;
        ret += '\n';

        // casillas del lado norte
        for (Casilla c : posiciones.get(2))
            ret += char_vertical + String.format("%s", c.printCasilla());

        ret += char_vertical + String.format("%s", posiciones.get(3).get(0).printCasilla());
        ret += char_vertical;
        ret += '\n';

        // borde inferior del lado norte
        ret += char_vertical_right;
        for (int i = 0; i < 11; i++) {
            ret += String.valueOf(char_horizontal).repeat(Casilla.casillaWidth - 1);
            if (i == 0)
                ret += char_full_intersection;
            else if (i < 9)
                ret += char_horizontal_up;
            else if (i == 9)
                ret += char_full_intersection;
        }
        ret += char_vertical_left;
        ret += '\n';

        // casillas del lado este y oeste
        for (int i = 0; i < 9; i++) {
            ret += char_vertical + String.format("%s", posiciones.get(1).get(9 - i).printCasilla());
            ret += char_vertical;
            ret += String.valueOf(' ').repeat(Casilla.casillaWidth * 9 - 1);
            ret += char_vertical + String.format("%s", posiciones.get(3).get(i + 1).printCasilla());
            ret += char_vertical;
            ret += '\n';
            if (i != 8) {
                ret += char_vertical_right;
                ret += String.valueOf(char_horizontal).repeat(Casilla.casillaWidth - 1);
                ret += char_vertical_left;
                ret += String.valueOf(' ').repeat(Casilla.casillaWidth * 9 - 1);
                ret += char_vertical_right;
                ret += String.valueOf(char_horizontal).repeat(Casilla.casillaWidth - 1);
                ret += char_vertical_left;
                ret += '\n';
            }

        }
        // borde superior del lado sur
        ret += char_vertical_right;
        for (int i = 0; i < 11; i++) {
            ret += String.valueOf(char_horizontal).repeat(Casilla.casillaWidth - 1);
            if (i == 0)
                ret += char_full_intersection;
            else if (i < 9)
                ret += char_horizontal_down;
            else if (i == 9)
                ret += char_full_intersection;
        }
        ret += char_vertical_left;
        ret += '\n';

        // lado sur
        ret += char_vertical + String.format("%s", posiciones.get(1).get(0).printCasilla());
        for (int i = 9; i>=0; i--)
            ret += char_vertical + String.format("%s", posiciones.get(0).get(i).printCasilla());
        ret += char_vertical;
        ret += '\n';

        // borde inferor del tablero
        ret += char_bottom_left;
        for (int i = 0; i < 11; i++) {
            ret += String.valueOf(char_horizontal).repeat(Casilla.casillaWidth - 1);
            if (i < 10)
                ret += char_horizontal_up;
        }
        ret += char_bottom_right;
        ret += '\n';

        // ret contiene todo el tablero
        return ret;
    }

    // Método usado para buscar la casilla con el nombre pasado como argumento:
    public Casilla encontrar_casilla(String nombre) {
        // Solucion O(n) (busqueda lineal)
        for (ArrayList<Casilla> arr : posiciones) {
            for (Casilla c : arr) {
                if (c.getNombre() == nombre)
                    return c;
            }
        }
        return null;
    }
}
