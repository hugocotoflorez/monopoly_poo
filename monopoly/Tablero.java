package monopoly;

import partida.*;
import monopoly.Casilla.*;
import monopoly.Casilla.Accion.*;
import monopoly.Casilla.Especial.*;
import monopoly.Casilla.Impuesto.*;
import monopoly.Casilla.Propiedad.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Tablero {
    // Atributos.
    private ArrayList<ArrayList<Casilla>> posiciones; // Posiciones del tablero: se define como un arraylist de arraylists de casillas (uno por cada lado del tablero).
    private HashMap<String, Grupo> grupos = new HashMap<String, Grupo>(); // Grupos del tablero, almacenados como HashMap con clave String (será el color del grupo).
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

    String[] tablero_text = {
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣠⣤⣤⣤⡀⠀⠀⠀⠀⠀⠀⠀⡤⠚⣉⠉⠲⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀",
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣠⠶⠛⠉⠀⠀⢻⣿⣿⡀⠀⠀⠀⠀⠀⢸⠀⡞⠉⠙⠒⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀",
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣴⣿⣿⣦⠀⠀⠀⠀⠀⢻⣿⣷⡄⠀⠀⠀⠀⠘⣄⠹⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀",
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⡿⣿⣿⣿⣿⣷⣀⠤⠒⠊⠉⠱⣶⣿⣆⠀⣀⣴⠂⠈⠢⡈⠳⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀",
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⢧⡈⢿⣿⡿⣋⣴⣀⣀⣀⣠⣤⡬⠭⠼⠻⣏⠀⠀⠀⠀⠈⠲⣌⠳⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀",
            "⠀⠀⠀⠀⠀⠀⠀⠀⢀⡴⣶⠀⠀⠀⠀⠀⠀⠀⠀⠻⣬⢋⣾⠿⠛⣋⠍⢁⠤⠀⠀⠀⠀⠈⠉⠳⡀⠀⠀⠀⠀⠈⠳⣌⠳⣄⢀⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀",
            "⠀⠀⠀⠀⠀⣀⣠⣤⡾⠀⢻⡀⠀⠀⠀⠀⠀⠀⠀⢀⣵⠟⣡⠖⠋⠀⠀⠁⠀⠀⠀⠀⠀⠀⠰⠂⢳⠀⠀⠀⠀⠀⠀⠈⣳⠾⠃⡷⠈⠒⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀",
            "⠀⣀⡤⠞⡋⠉⠀⠀⠇⠀⠀⣿⠲⣤⣤⣄⡀⠀⠀⣼⠁⣼⡏⢐⠆⠀⠀⠀⠘⠃⠀⠀⠀⣄⠀⠀⢸⡇⢀⠀⠀⠀⠀⠀⡇⠠⡶⠁⡀⠀⠙⡄⠀⠀⠀⠀⠀⠀⠀⠀",
            "⣼⠁⢀⠩⠔⠀⣀⣀⣀⣀⡼⢁⣾⣿⣿⣿⣿⣶⣤⣹⣆⣻⠓⠀⠀⠀⠀⠀⠀⠀⡠⠤⠐⠋⠉⠑⠚⠓⣻⠀⠀⠀⠀⠀⣧⡀⢧⣞⣠⢂⡰⣄⠀⠀⠀⠀⠀⠀⠀⠀",
            "⠳⠤⠴⠚⠛⠉⠉⠉⠉⠛⢳⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣍⡦⢄⣤⠀⢢⡄⠠⠎⠀⠀⠀⣠⣀⣀⣠⠔⠁⠀⠀⠀⢀⣿⣆⠻⢦⣌⡽⠋⠙⢮⡳⣄⠀⠀⠀⠀⠀⠀",
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠙⠻⢿⣿⣿⣿⣿⣿⣿⣿⣾⣿⣷⣄⠑⠢⠤⠤⠴⠋⠉⣀⡞⠁⠀⠀⠀⣀⣴⣿⣿⣿⣷⣤⡽⠃⠀⠀⠀⠙⢮⡳⣄⠀⠀⠀⠀",
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠻⣿⣿⣿⣿⣿⣿⣿⣿⡏⠙⢒⠦⠤⠤⠴⢚⣿⣿⣶⣶⣾⣿⣿⣿⣿⣿⣿⣿⣿⡿⠃⠀⠀⠀⠀⠀⠙⢮⡳⣄⠀⠀",
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⢿⣿⣿⣿⣿⣿⣿⣿⡄⣸⡤⠖⠢⣤⠇⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⢮⡳⣄",
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⣿⣿⣿⣿⣿⣿⣿⣷⠉⠀⠀⠀⠀⢰⣿⣿⣿⣿⣿⣿⣿⣿⠿⠛⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠊",
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⠀⠀⠀⠀⠀⠀⠀⠀⢰⣿⢟⣵⣶⣶⣝⣿⣿⣆⠀⠀⠀⠀⢸⣿⣿⣿⡿⠛⠋⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀",
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣆⠀⠀⠀⠀⠀⠀⣰⣟⣵⣿⣿⣿⣿⣿⣿⡿⠛⢷⡀⠀⠀⢸⣿⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀",
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢿⣷⣶⣤⣤⣶⣿⣿⣿⣿⣿⣿⣿⣿⣿⠏⠀⠀⠈⣧⣴⡄⢀⣿⣿⣿⣆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀",
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠀⠀⠀⠀⣿⣿⣿⣿⣿⣿⣿⣿⣷⣤⣴⠞⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀",
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠛⠻⣿⣿⣿⣿⣿⣿⣿⣿⡇⠀⠀⠀⢀⣿⣿⣿⣿⣿⣿⣿⡿⠛⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀",
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢻⣿⣿⣿⡙⢿⣿⡟⠛⠀⠀⠀⣾⣿⣿⣿⣿⣿⣿⣿⣷⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀",
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⢿⣿⣿⣿⣌⢿⡇⠀⢀⣤⣾⡍⢻⣿⣿⣿⣿⣿⣿⣿⣧⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀",
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠻⠿⠟⠀⠳⠀⡀⠀⣼⠃⢀⣿⣿⣿⣿⣿⣿⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀",
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠉⠁⣀⣼⣿⣿⣿⣿⣿⣿⣿⡟⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀",
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣠⣀⣰⣿⣿⣿⣿⣿⣿⣿⣿⠿⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀",
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⡾⠁⠀⢹⣿⣿⣿⣿⣿⡿⠋⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀",
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡳⠀⠀⢸⣿⣿⣿⡿⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀",
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡇⠀⠀⢸⢿⣿⠏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀",
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡇⠀⠀⠀⠀⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀",
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢻⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀",
            "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠻⠦⣤⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀",
    };

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
    public static Casilla obtenerCasilla(ArrayList<ArrayList<Casilla>> array, int posicion) {
        posicion = posicion % 40;
        return array.get(posicion / 10).get(posicion % 10);
    }

    public HashMap<String, Grupo> getGruposMap() {
        return this.grupos;
    }

    // Método para insertar las casillas del lado norte.
    private void insertarLadoNorte() {
        ArrayList<Casilla> lado = new ArrayList<Casilla>();
        lado.add(new Parking(21));
        lado.add(new Solar("Solar12", 22, Valor.GRUPO_5, banca));
        lado.add(new AccionSuerte("Suerte2", 23));
        lado.add(new Solar("Solar13", 24, Valor.GRUPO_5, banca));
        lado.add(new Solar("Solar14", 25, Valor.GRUPO_5, banca));
        lado.add(new Transporte("Trans3", 26, Valor.TRANSPORTES, banca));
        lado.add(new Solar("Solar15", 27, Valor.GRUPO_6, banca));
        lado.add(new Solar("Solar16", 28, Valor.GRUPO_6, banca));
        lado.add(new Servicio("Serv2", 29, Valor.SERVICIOS, banca));
        lado.add(new Solar("Solar17", 30, Valor.GRUPO_6, banca));

        Grupo G5 = new Grupo(lado.get(1), lado.get(3), lado.get(4), Valor.COLOR_G5, "Rojo");
        Grupo G6 = new Grupo(lado.get(6), lado.get(7), lado.get(9), Valor.COLOR_G6, "Marron");
        this.grupos.put("Rojo", G5);
        this.grupos.put("Marron", G6);
        posiciones.add(lado);
        /* Se anade en el indice 0 */
    }

    // Método para insertar las casillas del lado sur.
    private void insertarLadoSur() {
        ArrayList<Casilla> lado = new ArrayList<Casilla>();
        lado.add(new Salida(1));
        lado.add(new Solar("Solar1", 2, Valor.GRUPO_1, banca));
        lado.add(new AccionCajaComunidad("Caja", 3));
        lado.add(new Solar("Solar2", 4, Valor.GRUPO_1, banca));
        lado.add(new Impuesto("Impt1", 5, Valor.IMPUESTOS1));
        lado.add(new Transporte("Trans1", 6, Valor.TRANSPORTES, banca));
        lado.add(new Solar("Solar3", 7, Valor.GRUPO_2, banca));
        lado.add(new AccionSuerte("Suerte", 8));
        lado.add(new Solar("Solar4", 9, Valor.GRUPO_2, banca));
        lado.add(new Solar("Solar5", 10, Valor.GRUPO_2, banca));

        Grupo G1 = new Grupo(lado.get(1), lado.get(3), Valor.COLOR_G1, "Negro");
        Grupo G2 = new Grupo(lado.get(6), lado.get(8), lado.get(9), Valor.COLOR_G2, "Cyan");
        this.grupos.put("Negro", G1);
        this.grupos.put("Cyan", G2);

        posiciones.add(lado);
    }

    // Método que inserta casillas del lado oeste.
    private void insertarLadoOeste() {
        ArrayList<Casilla> lado = new ArrayList<Casilla>();
        lado.add(new Carcel(11));
        lado.add(new Solar("Solar6", 12, Valor.GRUPO_3, banca));
        lado.add(new Servicio("Serv1", 13, Valor.SERVICIOS, banca));
        lado.add(new Solar("Solar7", 14, Valor.GRUPO_3, banca));
        lado.add(new Solar("Solar8", 15, Valor.GRUPO_3, banca));
        lado.add(new Transporte("Trans2", 16, Valor.TRANSPORTES, banca));
        lado.add(new Solar("Solar9", 17, Valor.GRUPO_4, banca));
        lado.add(new AccionCajaComunidad("Caja", 18));
        lado.add(new Solar("Solar10", 19, Valor.GRUPO_4, banca));
        lado.add(new Solar("Solar11", 20, Valor.GRUPO_4, banca));

        Grupo G3 = new Grupo(lado.get(1), lado.get(3), lado.get(4), Valor.COLOR_G3, "Rosa");
        Grupo G4 = new Grupo(lado.get(6), lado.get(8), lado.get(9), Valor.COLOR_G4, "Amarillo");
        this.grupos.put("Rosa", G3);
        this.grupos.put("Amarillo", G4);

        posiciones.add(lado);
    }

    // Método que inserta las casillas del lado este.
    private void insertarLadoEste() {
        ArrayList<Casilla> lado = new ArrayList<Casilla>();
        lado.add(new IrCarcel(31));
        lado.add(new Solar("Solar18", 32, Valor.GRUPO_7, banca));
        lado.add(new Solar("Solar19", 33, Valor.GRUPO_7, banca));
        lado.add(new AccionCajaComunidad("Caja", 34));
        lado.add(new Solar("Solar20", 35, Valor.GRUPO_7, banca));
        lado.add(new Transporte("Trans4", 36, Valor.TRANSPORTES, banca));
        lado.add(new AccionSuerte("Suerte3", 37));
        lado.add(new Solar("Solar21", 38, Valor.GRUPO_8, banca));
        lado.add(new Impuesto("Impt2", 39, Valor.IMPUESTOS2));
        lado.add(new Solar("Solar22", 40, Valor.GRUPO_8, banca));

        Grupo G7 = new Grupo(lado.get(1), lado.get(2), lado.get(4), Valor.COLOR_G7, "Verde");
        Grupo G8 = new Grupo(lado.get(7), lado.get(9), Valor.COLOR_G8, "Azul");
        this.grupos.put("Verde", G7);
        this.grupos.put("Azul", G8);

        posiciones.add(lado);
    }

    // Para imprimir el tablero, modificamos el método toString().
    @Override
    public String toString() {
        String ret = new String();

        // ret += "\033[H\033[2J"; LIMPIAR PANTALLA

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

        // avatares del lado norte
        for (Casilla c : posiciones.get(2))
            ret += char_vertical + String.format("%s", c.printAvatares());

        ret += char_vertical + String.format("%s", posiciones.get(3).get(0).printAvatares());
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
            ret += tablero_text[i * 3];
            ret += String.valueOf(' ').repeat(Casilla.casillaWidth * 9 - 1 - tablero_text[i * 3].length());
            ret += char_vertical + String.format("%s", posiciones.get(3).get(i + 1).printCasilla());
            ret += char_vertical;
            ret += '\n';
            ret += char_vertical + String.format("%s", posiciones.get(1).get(9 - i).printAvatares());
            ret += char_vertical;
            ret += tablero_text[i * 3 + 1];
            ret += String.valueOf(' ').repeat(Casilla.casillaWidth * 9 - 1 - tablero_text[i * 3 + 1].length());
            ret += char_vertical + String.format("%s", posiciones.get(3).get(i + 1).printAvatares());
            ret += char_vertical;
            ret += '\n';
            if (i != 8) {
                ret += char_vertical_right;
                ret += String.valueOf(char_horizontal).repeat(Casilla.casillaWidth - 1);
                ret += char_vertical_left;
                ret += tablero_text[i * 3 + 2];
                ret += String.valueOf(' ').repeat(Casilla.casillaWidth * 9 - 1 - tablero_text[i * 3 + 2].length());
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
        for (int i = 9; i >= 0; i--)
            ret += char_vertical + String.format("%s", posiciones.get(0).get(i).printCasilla());
        ret += char_vertical;
        ret += '\n';
        ret += char_vertical + String.format("%s", posiciones.get(1).get(0).printAvatares());
        for (int i = 9; i >= 0; i--)
            ret += char_vertical + String.format("%s", posiciones.get(0).get(i).printAvatares());
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
                if (c.getNombre().equals(nombre))
                    return c;
            }
        }
        return null;
    }

    public void actualizarValorSolares() {
        for (Grupo g : this.grupos.values()) {
            for (Casilla c : g.getMiembros()) {
                if (c instanceof Solar){
                    Solar solar = (Solar) c;
                    if(solar.getDuenho().esBanca())
                        solar.setValor(solar.getValor() + solar.getValor()*0.05f);
                }
            }
        }
    }
}
