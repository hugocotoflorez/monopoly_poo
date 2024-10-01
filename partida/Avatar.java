package partida;

import java.util.ArrayList;
import java.util.Random;
import monopoly.*;

public class Avatar {

    // Atributos
    private String id; // Identificador: una letra generada aleatoriamente.
    private String tipo; // Sombrero, Esfinge, Pelota, Coche
    private Jugador jugador; // Un jugador al que pertenece ese avatar.
    private Casilla lugar; // Los avatares se sitúan en casillas del tablero.

    // Constructor vacío
    public Avatar() {
    }

    public Avatar(String tipo, Jugador jugador, Casilla lugar) {

        this.tipo = tipo;
        this.jugador = jugador;
        this.lugar = lugar;
        // this.id = generarId(avCreados);
    }

    /*
     * Constructor principal. Requiere éstos parámetros:
     * Tipo del avatar, jugador al que pertenece, lugar en el que estará ubicado, y
     * un arraylist con los
     * avatares creados (usado para crear un ID distinto del de los demás avatares).
     */
    public Avatar(String tipo, Jugador jugador, Casilla lugar, ArrayList<Avatar> avCreados) {

        this.tipo = tipo;
        this.jugador = jugador;
        this.lugar = lugar;
        generarId(avCreados);

    }
    public Avatar(String tipo, Casilla lugar, ArrayList<Avatar> avCreados) {

        this.tipo = tipo;
        this.lugar= lugar;
        generarId(avCreados);

    }
    //GETTERS
    public String getId()
    {
        return this.id;
    }
    public Jugador getJugador(Jugador jugador){
        return this.jugador;
    }

    //SETTERS
    public void setJugador(Jugador jugador){
        this.jugador = jugador;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public void setLugar(Casilla lugar) {
        this.lugar = lugar;
    }


    public Casilla obtenerCasilla(ArrayList<ArrayList<Casilla>> casillas, int valorTirada)
    {
        return casillas.get(lugar.getPosicion()%10).get(lugar.getPosicion()/10);
    }

    public void moverAvatar(ArrayList<ArrayList<Casilla>> casillas, int valorTirada) {
        this.lugar = obtenerCasilla(casillas, valorTirada);
    }

    /*
     * Método que permite generar un ID para un avatar. Sólo lo usamos en esta clase
     * (por ello es privado).
     * El ID generado será una letra mayúscula. Parámetros:
     * - Un arraylist de los avatares ya creados, con el objetivo de evitar que se
     * generen dos ID iguales.
     */
    private void generarId(ArrayList<Avatar> avCreados) {

        Random rnd = new Random();
        String letra = String.valueOf((char) ('A' + rnd.nextInt(26)));
        int aseguradodistinto = 0;
        while ((aseguradodistinto == 0) &&  (avCreados.size()!=0)) {
            for (Avatar A : avCreados) {
                if (A.id == letra) {
                    letra = String.valueOf((char) ('A' + rnd.nextInt(26)));
                    aseguradodistinto = 0;
                    break;
                }
                aseguradodistinto = 1;
            }
        }
        this.id = letra;
    }

    @Override
    public String toString(){
        String ret = """
                id: %s,
                tipo: %s,
                casilla: %s,
                jugador: %s
                """.formatted(this.id,this.tipo,this.lugar.getNombre(),this.jugador.getNombre())
    }

}
