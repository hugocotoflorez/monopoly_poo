package partida.Avatar;

import java.util.ArrayList;
import java.util.Random;
import monopoly.Casilla.Casilla;
import partida.*;
/*Los avatares deberán estar jerarquizados. Habrá una clase raíz llamada Avatar
que definirá los métodos y atributos comunes (por ejemplo, el nombre del avatar, el jugador al que
está asociado, etc.); y un segundo nivel que está compuesto por las clases Pelota, Coche, Esfinge y
Sombrero. Además, la clase raíz Avatar deberá de tener, por lo menos, los siguientes métodos (no se
incluyen los argumentos):
 moverEnBasico
 moverEnAvanzado */
public class Avatar {

    // Atributos
    private String id; // Identificador: una letra generada aleatoriamente.
    private Jugador jugador; // Un jugador al que pertenece ese avatar.
    private Casilla casilla; // Los avatares se sitúan en casillas del tablero.
    private int turno;

    // Constructor vacío
    public Avatar() {
    }

    /*
     * Constructor principal. Requiere éstos parámetros:
     * Tipo del avatar, jugador al que pertenece, lugar en el que estará ubicado, y
     * un arraylist con los
     * avatares creados (usado para crear un ID distinto del de los demás avatares).
     */
    public Avatar(Jugador jugador, Casilla lugar, ArrayList<Avatar> avCreados) {
        this.jugador = jugador;
        this.casilla = lugar;
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

    public int getTurno(){
        return this.turno;
    }

    public Casilla getCasilla() {
        return this.casilla;
    }

    // SETTERS
    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public void setLugar(Casilla lugar) {
        this.casilla = lugar;
    }

    public void setTurno(int turno){
        this.turno = turno;
    }


    public void moverAvatar(ArrayList<ArrayList<Casilla>> casillas, int valorTirada) {
        moverAvatar(obtenerCasilla(casillas, valorTirada + this.casilla.getPosicion() - 1));
    }

    public void moverAvatar(Casilla casilla) {
        this.casilla.eliminarAvatarCasilla(this.id);
        this.casilla = casilla;
        this.casilla.anhadirAvatarCasilla(this);
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

    @Override
    public String toString() {
        return this.id;
    }

}
