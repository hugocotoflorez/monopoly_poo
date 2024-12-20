package partida.Carta;

import monopoly.Casilla.*;
import monopoly.*;
import java.util.ArrayList;
import partida.Jugador;
import partida.Avatar.*;

public class Suerte extends Carta {

    private static ArrayList<Suerte> cartas = null;

    public Suerte() { //TODO que?? 2.0
        super();

        if (cartas == null)
            cartas = new ArrayList<Suerte>(6);
        cartas.add(this);
        super.setAccion(cartas.indexOf(this)+1);
        super.setDescripcion(cartas.indexOf(this));
    }

    public static Suerte obtenerCarta( int n) {
        if (n <= 0 && n > 6)
            return null;

        return cartas.get(n - 1);
    }

    public boolean realizarAccion(Avatar av, ArrayList<Jugador> jugadores,
            ArrayList<ArrayList<Casilla>> casillas) {
        switch (this.accion) {
            case 1:
                return accSuerte1(av, casillas);
            case 2:
                return accSuerte2(av, casillas);
            case 3:
                return accSuerte3(av);
            case 4:
                return accSuerte4(av, casillas);
            case 5:
                return accSuerte5(av, casillas);
            case 6:
                return accSuerte6(av);
        }
        return false;
    }

    public static boolean elegirCarta(Avatar avatar, ArrayList<Jugador>jugadores, Tablero tablero) {
        int n;

        do {
            n = Juego.consola.leerInt("Elige una carta del 1 al 6: ");

        } while (n < 1 || n > 6);

        // Carta.barajar(baraja);
        Suerte c = obtenerCarta(n);
        c.mostrarDescipcion();
        return c.realizarAccion(avatar, jugadores, tablero.getPosiciones());
    }

    private boolean accSuerte1(Avatar av, ArrayList<ArrayList<Casilla>> casillas) {
        /**
         * Ve al Transportes1 y coge un avión. Si pasas por la casilla de Salida, cobra
         * la cantidad habitual.
         */
        /* 5 es la posicion de transportes1 */
        if (av.getCasilla().getPosicion() > 5) {
            av.desplazar(casillas.get(0).get(5));
            return true;
        }
        av.desplazar(casillas.get(0).get(5));
        return false;
    }

    private boolean accSuerte2(Avatar av, ArrayList<ArrayList<Casilla>> casillas) {
        /*
         * Decides hacer un viaje de placer. Avanza hasta Solar15 directamente, sin
         * pasar por la casilla de Salida y sin cobrar la cantidad habitual.
         */
        av.desplazar(casillas.get(2).get(6));
        return false;
    }

    private boolean accSuerte3(Avatar av) {
        /**
         * Vendes tu billete de avión para Solar17 en una subasta por Internet. Cobra
         * 500000€
         */
        av.getJugador().sumarFortuna(500000);
        av.getJugador().setPremiosInversionesOBote(av.getJugador().getPremiosInversionesOBote() + 500000);
        return false;
    }

    private boolean accSuerte4(Avatar av, ArrayList<ArrayList<Casilla>> casillas) {
        /**
         * Ve a Solar3. Si pasas por la casilla de Salida, cobra la cantidad habitual.
         */
        // cobrar
        if (av.getCasilla().getPosicion() > 6) {
            av.desplazar(casillas.get(0).get(6));
            return true;
        }
        av.desplazar(casillas.get(0).get(6));
        return false;
    }

    private boolean accSuerte5(Avatar av, ArrayList<ArrayList<Casilla>> casillas) {
        /**
         * Los acreedores te persiguen por impago. Ve a la Cárcel. Ve directamente sin
         * pasar por la casilla de Salida
         * y sin cobrar la cantidad habitual
         */
        av.getJugador().encarcelar(casillas);
        return false;

    }

    private boolean accSuerte6(Avatar av) {
        /**
         * ¡Has ganado el bote de la lotería! Recibe 1000000€
         */
        av.getJugador().sumarFortuna(1000000);
        av.getJugador().setPremiosInversionesOBote(av.getJugador().getPremiosInversionesOBote() + 1000000);
        return false;
    }

}