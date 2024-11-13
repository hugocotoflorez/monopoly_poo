package partida.Avatar;

import java.util.ArrayList;
import java.util.Random;
import monopoly.*;
import partida.*;

public class Avatar {

    // Atributos
    private String id; // Identificador: una letra generada aleatoriamente.
    private String tipo; // Sombrero, Esfinge, Pelota, Coche
    private Jugador jugador; // Un jugador al que pertenece ese avatar.
    private Casilla lugar; // Los avatares se sitúan en casillas del tablero.
    private int turno;

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
        avCreados.add(this);

    }

    public Avatar(String tipo, Casilla lugar, ArrayList<Avatar> avCreados) {

        this.tipo = tipo;
        this.lugar = lugar;
        generarId(avCreados);
        avCreados.add(this);

    }

    public static Boolean esTipo(String tipo) {
        return tipo.equals("Coche") || tipo.equals("Esfinge") ||
                tipo.equals("Sombrero") || tipo.equals("Pelota");
    }

    // GETTERS
    public String getId() {
        return this.id != null ? this.id : "";
    }

    public Jugador getJugador() {
        return this.jugador;
    }

    public String getTipo() {
        return this.tipo;
    }

    public int getTurno(){
        return this.turno;
    }

    public Casilla getCasilla() {
        return this.lugar;
    }

    // SETTERS
    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setLugar(Casilla lugar) {
        this.lugar = lugar;
    }

    public void setTurno(int turno){
        this.turno = turno;
    }

    


    static Casilla obtenerCasilla(ArrayList<ArrayList<Casilla>> casillas, int valor) {
        valor = valor % 40;
        return casillas.get(valor / 10).get(valor % 10);
    }

    public void moverAvatar(ArrayList<ArrayList<Casilla>> casillas, int valorTirada) {
        moverAvatar(obtenerCasilla(casillas, valorTirada + this.lugar.getPosicion() - 1));
    }

    public void moverAvatar(Casilla casilla) {
        this.lugar.eliminarAvatarCasilla(this.id);
        this.lugar = casilla;
        this.lugar.anhadirAvatarCasilla(this);
        casilla.actualizarCaidasEnCasilla(this.turno);
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
        while ((aseguradodistinto == 0) && (avCreados.size() != 0)) {
            for (Avatar A : avCreados) {
                if (A != null && (A.id.equals(letra))) {
                    letra = String.valueOf((char) ('A' + rnd.nextInt(26)));
                    aseguradodistinto = 0;
                    break;
                }
                aseguradodistinto = 1;
            }
        }
        this.id = letra;
    }

    public String getInfo() {
        String ret = """
                id: %s,
                tipo: %s,
                casilla: %s,
                jugador: %s
                    """.formatted(this.id, this.tipo, this.lugar.getNombre(), this.jugador.getNombre());
        return ret;
    }

    @Override
    public String toString() {
        return this.id;
    }

}
