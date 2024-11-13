package partida;

import monopoly.Valor;
import monopoly.Casilla.Casilla;

import java.util.ArrayList;
import java.util.Collections;

import partida.Avatar.*;

public class Carta {
    String descripcion;
    String tipo;
    int accion = 0;

    /*
     * Elegir carta. Cuando el avatar cae en una casilla de Suerte o en una de Caja
     * de Comunidadautomáticamente se deberá de seguir el siguiente procedimiento:
     *
     * Barajar las cartas. Se barajarán las cartas, de modo que a cada carta se le
     * asignará una posición
     * aleatoria dentro de la baraja. Esto se deberá realizar con las cartas de
     * Suerte o de Caja de
     * Comunidad cada vez que el avatar cae en una casilla de Suerte o de Caja de
     * Comunidad,
     * respectivamente.
     * Elegir carta. Se le preguntará al jugador qué carta desea elegir, para lo
     * cual deberá indicar un
     * número del 1 al 6. Al elegir la carta se le deberá mostrar la descripción de
     * la carta para que
     * conozca qué acción se va a realizar.
     * Realizar acción. Se realizará la acción indicada en la carta, de modo que se
     * le comunicará al
     * jugador el resultado de dicha acción. Si esta acción lleva consigo el pago de
     * dinero, primero se
     * intentará cobrar de la fortuna actual del jugador, pero en el caso de que
     * ésta no sea suficiente, se
     * le indicará al jugador que hipoteque una propiedad, realizándose a
     * continuación el pago
     * correspondiente. Si la acción implica ir a una casilla dada, el jugador podrá
     * tomar las mismas
     * decisiones que tienen lugar cuando cae en dicha casilla dentro del modo
     * normal del juego.
     * En el apéndice A se muestran las cartas que se deberán utilizar en el juego.
     * Tal y como se ha indicado
     * en la memoria, deberá haber 6 cartas de tipo Suerte y 6 cartas de Caja de
     * Comunidad.
     */

    public final static String desc1 = "Ve al Transportes1 y coge un avión. Si pasas por la casilla de Salida, cobra la cantidad habitual.";
    public final static String desc2 = "Decides hacer un viaje de placer. Avanza hasta Solar15 directamente, sin pasar por la casilla de Salida y sin cobrar la cantidad habitual.";
    public final static String desc3 = "Vendes tu billete de avión para Solar17 en una subasta por Internet. Cobra 500000€.";
    public final static String desc4 = "Ve a Solar3. Si pasas por la casilla de Salida, cobra la cantidad habitual.";
    public final static String desc5 = "Los acreedores te persiguen por impago. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida y sin cobrar la cantidad habitual.";
    public final static String desc6 = "¡Has ganado el bote de la lotería! Recibe 1000000€.";
    public final static String desc7 = "Paga 500000€ por un fin de semana en un balneario de 5 estrellas.";
    public final static String desc8 = "Te investigan por fraude de identidad. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida y sin cobrar la cantidad habitual.";
    public final static String desc9 = "Colócate en la casilla de Salida. Cobra la cantidad habitual.";
    public final static String desc10 = "Tu compañía de Internet obtiene beneficios. Recibe 2000000€.";
    public final static String desc11 = "Paga 1000000€ por invitar a todos tus amigos a un viaje a Solar14.";
    public final static String desc12 = "Alquilas a tus compañeros una villa en Solar7 durante una semana. Paga 200000€ a cada jugador3.";

    public Carta(String descripcion, String tipo, int accion) {
        this.setAccion(accion);
        this.setTipo(tipo);
        this.descripcion = descripcion;
    }

    public void setAccion(int n) {
        if (n > 0 && n <= 6)
            this.accion = n;
    }

    public void setTipo(String tipo) {
        if (tipo.equals("suerte") || tipo.equals("comunidad"))
            this.tipo = tipo;
    }

    public static void barajar(ArrayList<Carta> baraja) {
        System.out.println("SE ESTAN BARAJANDO LAS CARTAS !!!!!!");
        Collections.shuffle(baraja);
    }

    public static Carta obtenerCarta(ArrayList<Carta> baraja, int n) {
        if (n <= 0 && n > 6)
            return null;

        return baraja.get(n - 1);
    }

    public void mostrarDescipcion() {
        System.out.println(this.descripcion);
    }

    public boolean realizarAccion(Avatar av, ArrayList<Jugador> jugadores,
            ArrayList<ArrayList<Casilla>> casillas) {
        if (this.tipo.equals("suerte"))
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
        else
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

    private boolean accSuerte1(Avatar av, ArrayList<ArrayList<Casilla>> casillas) {
        /**
         * Ve al Transportes1 y coge un avión. Si pasas por la casilla de Salida, cobra
         * la cantidad habitual.
         */
        /* 5 es la posicion de transportes1 */
        if (av.getCasilla().getPosicion() > 5) {
            return true;
        }
        av.moverAvatar(casillas.get(0).get(5));
        return false;
    }

    private boolean accSuerte2(Avatar av, ArrayList<ArrayList<Casilla>> casillas) {
        /*
         * Decides hacer un viaje de placer. Avanza hasta Solar15 directamente, sin
         * pasar por la casilla de Salida y sin cobrar la cantidad habitual.
         */
        av.moverAvatar(casillas.get(2).get(6));
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
            return true;
        }
        av.moverAvatar(casillas.get(0).get(6));
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
        av.moverAvatar(casillas.get(0).get(0));
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
