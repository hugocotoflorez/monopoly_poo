package partida.Avatar;

import java.util.ArrayList;
import java.util.Random;
import partida.Carta.*;
import monopoly.Casilla.*;
import monopoly.Casilla.Accion.AccionCajaComunidad;
import monopoly.Casilla.Accion.AccionSuerte;
import monopoly.Casilla.Especial.Especial;
import partida.*;
import monopoly.*;

public abstract class Avatar {

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

    public ArrayList<Casilla> getCasillasVisitadas(){
        return null;
    }

    // GETTERS
    public String getId() {
        return this.id != null ? this.id : "";
    }

    public Jugador getJugador() {
        return this.jugador;
    }

    public int getTurno() {
        return this.turno;
    }

    public Casilla getCasilla() {
        return this.casilla;
    }

    public abstract String getTipo();

    // SETTERS
    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public void setLugar(Casilla lugar) {
        this.casilla = lugar;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

    public static Boolean esTipo(String tipo) {
        return tipo.equals("Coche") || tipo.equals("Esfinge") ||
                tipo.equals("Sombrero") || tipo.equals("Pelota");
    }

    public void desplazar(ArrayList<ArrayList<Casilla>> casillas, int valorTirada) {
        desplazar(Tablero.obtenerCasilla(casillas, valorTirada + this.casilla.getPosicion() - 1));
    }

    public void desplazar(Casilla casilla) {
        this.casilla.eliminarAvatarCasilla(this.id);
        this.casilla = casilla;
        this.casilla.anhadirAvatarCasilla(this);
        casilla.actualizarCaidasEnCasilla(this.turno);
    }

    private void pasarPorSalida(Tablero tablero, ArrayList<Jugador> jugadores) {
        // !!!!!! si se modifica algo de esto hay que modificarlo tambien en Carta
        Juego.consola.imprimirln("¡Has pasado por la Salida! Ganaste " + Valor.SUMA_VUELTA);
        jugador.sumarFortuna(Valor.SUMA_VUELTA);
        jugador.setVueltas(jugador.getVueltas() + 1);
        jugador.setPasarPorCasillaDeSalida(
                jugador.getPasarPorCasillaDeSalida() + Valor.SUMA_VUELTA);
        Juego.consola.imprimirln("Llevas " + jugador.getVueltas() + " vueltas.");

        int vueltasmin = jugador.getVueltas();

        for (Jugador j : jugadores) {
            if (!j.esBanca() && j.getVueltas() < vueltasmin) {
                vueltasmin = j.getVueltas();
            }
        }

        if ((jugador.getVueltas() == vueltasmin) && (vueltasmin % 4 == 0)) {
            Juego.consola.imprimirln(
                    "Todos los jugadores han dado un múltiplo de 4 vueltas, se va a incrementar el precio de los solares en un 10%.");
            tablero.actualizarValorSolares();
        }
    }

    public void moverNormal(Tablero tablero, int valor1, int valor2, ArrayList<Jugador> jugadores) {
        int desplazamiento = valor1 + valor2;
        Juego.consola.imprimir("El avatar " + id + " avanza " + desplazamiento + " desde "
                + casilla.getNombre());
        desplazar(tablero.getPosiciones(), desplazamiento);
        Juego.consola.imprimirln(" hasta " + casilla.getNombre());

        // Comprueba si pasa por salida
        comprobarSiPasasPorSalida(tablero, valor1 + valor2, jugadores);
    }

    private void comprobarSiPasasPorSalida(Tablero tablero, int desplazamiento, ArrayList<Jugador> jugadores) {
        int casillanueva = casilla.getPosicion();
        /*
         * Si estas en una casilla que la posicion de la casilla es menor que
         * la tirada quiere decir que pasaste por salida. Por ejemplo, si desde la
         * salida 0 me muevo 5 caigo en la casilla 5, por lo que para que sea menor tuve
         * que moverme desde una casilla de antes de la salida.
         */
        if ((casillanueva < desplazamiento)) {
            pasarPorSalida(tablero, jugadores);
        }
    }

    public void moverAtras(Tablero tablero, int valor1, int valor2) {
        int desplazamiento = valor1 + valor2;
        Juego.consola.imprimir("El avatar " + getId() + " avanza " + desplazamiento
                + " hacia atras desde "
                + getCasilla().getNombre());
        desplazar(tablero.getPosiciones(), 40 - desplazamiento);
        Juego.consola.imprimirln(" hasta " + getCasilla().getNombre());
    }

    public abstract boolean moverEnAvanzado(Tablero tablero, int valor1, int valor2, ArrayList<Jugador> jugadores) ;

    public abstract String getInfo();

    public void pasarPorSalidaHaciaAtras(int desplazamiento) {

        int casillanueva = casilla.getPosicion();
        /*
         * Si la casilla anterior, que se obtiene de sumarle el desplazamiento a la
         * casilla actual porque se va hacia atras, esta fuera de los indices del
         * tablero quiere decir que paso por salida
         */
        if ((casillanueva + desplazamiento >= 40)) {

            Juego.consola.imprimirln("¡Has pasado por la Salida hacia atras! Perdiste" + Valor.SUMA_VUELTA);
            jugador.sumarFortuna(-Valor.SUMA_VUELTA);
            if (jugador.getVueltas() != 0)
                jugador.setVueltas(jugador.getVueltas() - 1);
            jugador.setPasarPorCasillaDeSalida(
                    jugador.getPasarPorCasillaDeSalida() - Valor.SUMA_VUELTA);
            Juego.consola.imprimirln("Llevas " + jugador.getVueltas() + " vueltas.");

        }
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


    public boolean evaluarAccion(int desplazamiento, ArrayList<Jugador> jugadores, Tablero tablero) {
        /*
         * En estos casos no se evalua casilla, sino que la accion se realiza
         * desde aqui. Si esto es un error borrar los else-if pero el de caja y suerte
         * si que no puede ejecutarse evaluar casilla despues
         */
        /* TODO: marina (instanceof?)*/
        if (this.casilla instanceof AccionSuerte) {
            if (Suerte.elegirCarta(this, jugadores, tablero)) {
                pasarPorSalida(tablero, jugadores);
            }
        }

        /* TODO: marina (instanceof?)*/
        if (this.casilla instanceof AccionCajaComunidad) {
            if (Comunidad.elegirCarta(this, jugadores, tablero)) {
                pasarPorSalida(tablero,jugadores );
            }
        }

        if (casilla.getNombre().equals("IrCarcel")) {
            jugadores.get(turno).encarcelar(tablero.getPosiciones());
        }

        else {
            // evaluar casilla
            return casilla.evaluarCasilla(jugadores.get(turno), jugadores.get(0),
                    desplazamiento);
            }

        return true;
    }

    @Override
    public String toString() {
        return this.id;
    }

    /*
     * LEGACY CODE. TO BE REMOVED
     * TODO (Arreglar el resto sin borrar esto asi no peta y se puede probar)
     */
    static Casilla obtenerCasilla(ArrayList<ArrayList<Casilla>> casillas, int valor) {
        System.out.println("Estas usando Avatar.obtenerCasilla(deprecated)");
        valor = valor % 40;
        return casillas.get(valor / 10).get(valor % 10);
    }

    public void moverAvatar(ArrayList<ArrayList<Casilla>> casillas, int valorTirada) {
        moverAvatar(obtenerCasilla(casillas, valorTirada + this.casilla.getPosicion() - 1));
    }

    public void moverAvatar(Casilla casilla) {
        System.out.println("Estas usando Avatar.moverAvatar (deprecated)");
        this.casilla.eliminarAvatarCasilla(this.id);
        this.casilla = casilla;
        this.casilla.anhadirAvatarCasilla(this);
        casilla.actualizarCaidasEnCasilla(this.turno);
    }

}
