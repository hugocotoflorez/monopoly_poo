package monopoly;

//
import partida.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Tablero {
    // Atributos.
    private ArrayList<ArrayList<Casilla>> posiciones; // Posiciones del tablero: se define como un arraylist de
                                                      // arraylists de casillas (uno por cada lado del tablero).
    private HashMap<String, Grupo> grupos; // Grupos del tablero, almacenados como un HashMap con clave String (será el
                                           // color del grupo).
    private Jugador banca; // Un jugador que será la banca.

    private static final boolean USAR_NERD_FONT = true;;
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

    // Método para crear todas las casillas del tablero. Formado a su vez por cuatro
    // métodos (1/lado).
    private void generarCasillas() {
        this.posiciones = new ArrayList<ArrayList<Casilla>>();
        this.insertarLadoNorte();
        this.insertarLadoEste();
        this.insertarLadoSur();
        this.insertarLadoOeste();
    }

    // ESTO LO HIZO CHAT-GPT . ESTA MAL!!!!

    // Método para insertar las casillas del lado norte.
    private void insertarLadoNorte() {
        ArrayList<Casilla> lado = new ArrayList<Casilla>();
        lado.add(new Casilla("Parking", "especial", 21, banca));
        lado.add(new Casilla("Solar12", "solar", 22, 420000, banca));
        lado.add(new Casilla("Suerte2", "suerte", 23, banca));
        lado.add(new Casilla("Solar13", "solar", 24, 440000, banca));
        lado.add(new Casilla("Solar14", "solar", 25, 460000, banca));
        lado.add(new Casilla("Trans3", "transporte", 26, Valor.SUMA_VUELTA, banca));
        lado.add(new Casilla("Solar15", "solar", 27, 480000, banca));
        lado.add(new Casilla("Serv2", "serv", 28, 130000, banca));
        lado.add(new Casilla("Solar16", "solar", 29, 500000, banca));
        lado.add(new Casilla("Ir a Cárcel", "especial", 30, banca));

        posiciones.add(lado);
        /* Se anade en el indice 0 */
    }

    // Método para insertar las casillas del lado sur.
    private void insertarLadoSur() {
        ArrayList<Casilla> lado = new ArrayList<Casilla>();
        lado.add(new Casilla("Salida", "especial", 1, banca));
        lado.add(new Casilla("Solar1", "solar", 2, 600000, banca));
        lado.add(new Casilla("Caja", "caja", 3, banca));
        lado.add(new Casilla("Solar2", "solar", 4, 600000, banca));
        lado.add(new Casilla("Impt1", 5, Valor.SUMA_VUELTA, banca));
        lado.add(new Casilla("Trans1", "transporte", 6, Valor.SUMA_VUELTA, banca));
        lado.add(new Casilla("Solar3", "solar", 7, 520000, banca));
        lado.add(new Casilla("Suerte", "suerte", 8, banca));
        lado.add(new Casilla("Solar4", "solar", 9, 520000, banca));
        lado.add(new Casilla("Solar5", "solar", 10, 520000, banca));

        posiciones.add(lado);
        /* Se anade en el indice 2 */
    }

    // Método que inserta casillas del lado oeste.
    private void insertarLadoOeste() {
        ArrayList<Casilla> lado = new ArrayList<Casilla>();
        lado.add(new Casilla("Carcel", "especial", 11, banca));
        lado.add(new Casilla("Solar6", "solar", 12, 300000, banca));
        lado.add(new Casilla("Serv1", "serv", 13, 120000, banca));
        lado.add(new Casilla("Solar7", "solar", 14, 320000, banca));
        lado.add(new Casilla("Solar8", "solar", 15, 340000, banca));
        lado.add(new Casilla("Trans2", "transporte", 16, Valor.SUMA_VUELTA, banca));
        lado.add(new Casilla("Solar9", "solar", 17, 360000, banca));
        lado.add(new Casilla("Caja", "caja", 18, banca));
        lado.add(new Casilla("Solar10", "solar", 19, 380000, banca));
        lado.add(new Casilla("Solar11", "solar", 20, 400000, banca));

        posiciones.add(lado);
        /* Se anade en el indice 3 */
    }

    // Método que inserta las casillas del lado este.
    private void insertarLadoEste() {
        ArrayList<Casilla> lado = new ArrayList<Casilla>();
        lado.add(new Casilla("Solar17", "solar", 31, 520000, banca));
        lado.add(new Casilla("Caja", "caja", 32, banca));
        lado.add(new Casilla("Solar18", "solar", 33, 540000, banca));
        lado.add(new Casilla("Trans4", "transporte", 34, Valor.SUMA_VUELTA, banca));
        lado.add(new Casilla("Suerte3", "suerte", 35, banca));
        lado.add(new Casilla("Solar19", "solar", 36, 560000, banca));
        lado.add(new Casilla("Solar20", "solar", 37, 580000, banca));
        lado.add(new Casilla("Impuesto2", 38, (Valor.SUMA_VUELTA)/2, banca));
        lado.add(new Casilla("Solar21", "solar", 39, 600000, banca));
        lado.add(new Casilla("Solar22", "solar", 40, 620000, banca));

        posiciones.add(lado);
        /* Se anade en el indice 1 */
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
        for (Casilla c : posiciones.get(0))
            ret += char_vertical + String.format("%s", c.printTablero());

        ret += char_vertical + String.format("%s", posiciones.get(1).get(0).printTablero());
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
            ret += char_vertical + String.format("%s", posiciones.get(1).get(i + 1).printTablero());
            ret += char_vertical;
            ret += String.valueOf(' ').repeat(Casilla.casillaWidth * 9 - 1);
            ret += char_vertical + String.format("%s", posiciones.get(3).get(i + 1).printTablero());
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
        ret += char_vertical + String.format("%s", posiciones.get(3).get(0).printTablero());
        for (Casilla c : posiciones.get(2))
            ret += char_vertical + String.format("%s", c.printTablero());
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

/**
 * String pai
 *
 * es Fr *
 */
