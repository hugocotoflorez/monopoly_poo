package monopoly;

public class Valor {
    // Se incluyen una serie de constantes útiles para no repetir valores.
    public static final float FORTUNA_BANCA = 500000;
    public static final float FORTUNA_INICIAL = 9543076.28f;
    public static final float SUMA_VUELTA = 1301328.584f; // Se aproxima a la media de los precios de los solares del
                                                          // tablero.

    public static float PAGO_SALIR_CARCEL = 0.25f*SUMA_VUELTA;

    public static final float GRUPO_1 = 600000;
    public static final float GRUPO_2 = 520000;
    public static final float GRUPO_3 = 676000;
    public static final float GRUPO_4 = 878800;
    public static final float GRUPO_5 = 1142440;
    public static final float GRUPO_6 = 1485172;
    public static final float GRUPO_7 = 1930723.6f;
    public static final float GRUPO_8 = 3764911.02f;
    public static final float IMPUESTOS_TRANSPORTES = SUMA_VUELTA;
    public static final float IMPUESTO_SERVICIOS = SUMA_VUELTA/200f;
    public static final float IMPUESTOS1 = SUMA_VUELTA;
    public static final float IMPUESTOS2 = SUMA_VUELTA/2;
    public static final float TRANSPORTES = SUMA_VUELTA;
    public static final float SERVICIOS = SUMA_VUELTA*0.75f;

    // Colores del texto:
    public static final String BOLD = "\u001B[1m";
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    public static final String COLOR_G1 = BLACK;
    public static final String COLOR_G2 = CYAN;
    public static final String COLOR_G3 = "\u001B[38;2;255;192;203m";
    public static final String COLOR_G4 = "\u001B[38;2;255;255;0m";
    public static final String COLOR_G5 = RED;
    public static final String COLOR_G6 = "\u001B[38;2;128;64;0m";
    public static final String COLOR_G7 = GREEN;
    public static final String COLOR_G8 = BLUE;

    public static int NumeroCasasConstruidas = 0;
    public static int NumeroHotelesConstruidos = 0;
    public static int NumeroPiscinasConstruidas = 0;
    public static int NumeroPistasConstruidos = 0;

}
