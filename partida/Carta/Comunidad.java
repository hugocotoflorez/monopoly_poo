package partida.Carta;

import java.util.ArrayList;

import monopoly.Casilla.*;
import monopoly.*;
import partida.Jugador;
import partida.Avatar.*;

public class Comunidad extends Carta {

    private static ArrayList<Comunidad> cartas = null; //TODO que??

    public Comunidad() {
        super();

        if (cartas == null)
            cartas = new ArrayList<Comunidad>(6);

        cartas.add(this);
        super.setAccion(cartas.indexOf(this)+1);
        super.setDescripcion(cartas.indexOf(this)+6); // las 6 primeras son de suerte

    }

    public static Comunidad obtenerCarta(int n) {
        if (n <= 0 && n > 6)
            return null;

        return cartas.get(n - 1);
    }

    public boolean realizarAccion(Avatar av, ArrayList<Jugador> jugadores,
            ArrayList<ArrayList<Casilla>> casillas) {
        switch (this.accion) {
            case 1:
                return accComm1(av, jugadores);
            case 2:
                return accComm2(av, casillas);
            case 3:
                return accComm3(av, casillas);
            case 4:
                return accComm4(av);
            case 5:
                return accComm5(av, jugadores);
            case 6:
                return accComm6(av, jugadores);
        }
        return false;
    }


    public static boolean elegirCarta(Avatar avatar, ArrayList<Jugador>jugadores, Tablero tablero) {
        int n;

        do {
            n = Juego.consola.leerInt("Elige una carta del 1 al 6: ");

        } while (n < 1 || n > 6);

        // Carta.barajar(baraja);
        Comunidad c = obtenerCarta(n);
        c.mostrarDescipcion();
        return c.realizarAccion(avatar, jugadores, tablero.getPosiciones());
    }

    private boolean accComm1(Avatar av, ArrayList<Jugador> jugadores) {
        /**
         * Paga 500000€ por un fin de semana en un balneario de 5 estrellas
         */
        av.getJugador().sumarFortuna(-500000);
        av.getJugador().setPagoTasasEImpuestos(av.getJugador().getPagoTasasEImpuestos() + 500000);
        jugadores.get(0).sumarGastos(500000);
        System.out.println("El bote de la banca ahora es " + jugadores.get(0).getGastos());
        return false;
    }

    private boolean accComm2(Avatar av, ArrayList<ArrayList<Casilla>> casillas) {
        /**
         * Te investigan por fraude de identidad. Ve a la Cárcel. Ve directamente sin
         * pasar por la casilla de Salida y
         * sin cobrar la cantidad habitual
         */
        av.getJugador().encarcelar(casillas);
        return false;
    }

    private boolean accComm3(Avatar av, ArrayList<ArrayList<Casilla>> casillas) {
        /**
         * Colócate en la casilla de Salida. Cobra la cantidad habitual.
         */
        av.desplazar(casillas.get(0).get(0));
        /* Se podria pasar por salida desde aqui porque ahora esta en avatar */
        return true;
    }

    private boolean accComm4(Avatar av) {
        /**
         * Tu compañía de Internet obtiene beneficios. Recibe 2000000€
         */
        av.getJugador().sumarFortuna(2000000);
        av.getJugador().setPremiosInversionesOBote(av.getJugador().getPremiosInversionesOBote() + 2000000);
        return false;
    }

    private boolean accComm5(Avatar av, ArrayList<Jugador> jugadores) {
        /**
         * Paga 1000000€ por invitar a todos tus amigos a un viaje a Solar14.
         */
        av.getJugador().sumarFortuna(-1000000);
        av.getJugador().setPagoTasasEImpuestos(av.getJugador().getPagoTasasEImpuestos() + 1000000);
        jugadores.get(0).sumarGastos(1000000);
        System.out.println("El bote de la banca ahora es " + jugadores.get(0).getGastos());
        return false;
    }

    private boolean accComm6(Avatar av, ArrayList<Jugador> jugadores) {
        /**
         * Alquilas a tus compañeros una villa en Solar7 durante una semana. Paga
         * 200000€ a cada jugador
         */
        for (Jugador j : jugadores) {
            av.getJugador().sumarFortuna(-200000);
            j.sumarFortuna(200000);
            av.getJugador().setPagoTasasEImpuestos(av.getJugador().getPagoTasasEImpuestos() + 200000);
        }
        return false;
    }

}