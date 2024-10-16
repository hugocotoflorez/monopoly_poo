package partida;

import monopoly.Valor;

import java.util.ArrayList;
import java.util.Collections;

import monopoly.Casilla;

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
    public final static String desc3 = "Vendes tu billete de avión para Solar171 en una subasta por Internet. Cobra 500000€.";
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
        Collections.shuffle(baraja);
    }

    public static Carta obtenerCarta(ArrayList<Carta> baraja, int n) {
        if (n <= 0 && n > 6)
            return null;

        return baraja.get(n - 1);
    }

    public  void mostrarDescipcion() {
        System.out.println(this.descripcion);
    }

    public  void realizarAccion(Avatar av, ArrayList<Jugador> jugadores,
            ArrayList<ArrayList<Casilla>> casillas) {
        if (this.tipo.equals("suerte"))
            switch (this.accion) {
                case 1:
                    accSuerte1(av, casillas);
                    break;
                case 2:
                    accSuerte2(av, casillas);
                    break;
                case 3:
                    accSuerte3(av);
                    break;
                case 4:
                    accSuerte4(av, casillas);
                    break;
                case 5:
                    accSuerte5(av, casillas);
                    break;
                case 6:
                    accSuerte6(av);
                    break;
            }
        else
            switch (this.accion) {
                case 1:
                    accComm1(av, jugadores);
                    break;
                case 2:
                    accComm2(av, casillas);
                    break;
                case 3:
                    accComm3(av, casillas);
                    break;
                case 4:
                    accComm4(av);
                    break;
                case 5:
                    accComm5(av, jugadores);
                    break;
                case 6:
                    accComm6(av, jugadores);
                    break;
            }
    }

    private void accSuerte1(Avatar av, ArrayList<ArrayList<Casilla>> casillas) {// TODO
        /**
         * Ve al Transportes1 y coge un avión. Si pasas por la casilla de Salida, cobra
         * la cantidad habitual.
         */
        /* 5 es la posicion de transportes1 */
        if (av.getCasilla().getPosicion() > 5) {
            // Pasa por salida
            System.out.println("¡Has pasado por la Salida! Ganaste " + Valor.SUMA_VUELTA);
            av.getJugador().sumarFortuna(Valor.SUMA_VUELTA);
            av.getJugador().setVueltas(av.getJugador().getVueltas() + 1);
            av.getJugador().setPasarPorCasillaDeSalida(
                    av.getJugador().getPasarPorCasillaDeSalida() + Valor.SUMA_VUELTA);
            System.out.println("Llevas " + av.getJugador().getVueltas() + " vueltas.");
        }
        av.setLugar(casillas.get(0).get(5));
    }

    private void accSuerte2(Avatar av, ArrayList<ArrayList<Casilla>> casillas) {
        /*
         * Decides hacer un viaje de placer. Avanza hasta Solar15 directamente, sin
         * pasar por la casilla de Salida y sin cobrar la cantidad habitual.
         */
        av.setLugar(casillas.get(0).get(0));
    }

    private void accSuerte3(Avatar av) {
        /**
         * Vendes tu billete de avión para Solar17 en una subasta por Internet. Cobra
         * 500000€
         */
        av.getJugador().sumarFortuna(500000);
        av.getJugador().setPremiosInversionesOBote(av.getJugador().getPremiosInversionesOBote() + 500000);
    }

    private void accSuerte4(Avatar av, ArrayList<ArrayList<Casilla>> casillas) { // TODO
        /**
         * Ve a Solar3. Si pasas por la casilla de Salida, cobra la cantidad habitual.
         */
        // cobrar
        av.setLugar(casillas.get(0).get(6));
        System.out.println("¡Has pasado por la Salida! Ganaste " + Valor.SUMA_VUELTA);
        av.getJugador().sumarFortuna(Valor.SUMA_VUELTA);
        av.getJugador().setVueltas(av.getJugador().getVueltas() + 1);
        av.getJugador().setPasarPorCasillaDeSalida(
                av.getJugador().getPasarPorCasillaDeSalida() + Valor.SUMA_VUELTA);
        System.out.println("Llevas " + av.getJugador().getVueltas() + " vueltas.");
    }

    private void accSuerte5(Avatar av, ArrayList<ArrayList<Casilla>> casillas) {
        /**
         * Los acreedores te persiguen por impago. Ve a la Cárcel. Ve directamente sin
         * pasar por la casilla de Salida
         * y sin cobrar la cantidad habitual
         */
        av.getJugador().encarcelar(casillas);

    }

    private void accSuerte6(Avatar av) {
        /**
         * ¡Has ganado el bote de la lotería! Recibe 1000000€
         */
        av.getJugador().sumarFortuna(1000000);
        av.getJugador().setPremiosInversionesOBote(av.getJugador().getPremiosInversionesOBote() + 1000000);
    }

    private void accComm1(Avatar av, ArrayList<Jugador> jugadores) {
        /**
         * Paga 500000€ por un fin de semana en un balneario de 5 estrellas
         */
        av.getJugador().sumarFortuna(500000);
        av.getJugador().setPagoTasasEImpuestos(av.getJugador().getPagoTasasEImpuestos() + 500000);
        jugadores.get(0).sumarGastos(500000);
        System.out.println("El bote de la banca ahora es " + jugadores.get(0).getGastos());
    }

    private void accComm2(Avatar av, ArrayList<ArrayList<Casilla>> casillas) {
        /**
         * Te investigan por fraude de identidad. Ve a la Cárcel. Ve directamente sin
         * pasar por la casilla de Salida y
         * sin cobrar la cantidad habitual
         */
        av.getJugador().encarcelar(casillas);
    }

    private void accComm3(Avatar av, ArrayList<ArrayList<Casilla>> casillas) {
        /**
         * Colócate en la casilla de Salida. Cobra la cantidad habitual.
         */
        av.setLugar(casillas.get(0).get(0));
        // Cobrar Salida
        System.out.println("¡Has pasado por la Salida! Ganaste " + Valor.SUMA_VUELTA);
        av.getJugador().sumarFortuna(Valor.SUMA_VUELTA);
        av.getJugador().setVueltas(av.getJugador().getVueltas() + 1);
        av.getJugador().setPasarPorCasillaDeSalida(
                av.getJugador().getPasarPorCasillaDeSalida() + Valor.SUMA_VUELTA);
        System.out.println("Llevas " + av.getJugador().getVueltas() + " vueltas.");
    }

    private void accComm4(Avatar av) {
        /**
         * Tu compañía de Internet obtiene beneficios. Recibe 2000000€
         */
        av.getJugador().sumarFortuna(2000000);
        av.getJugador().setPremiosInversionesOBote(av.getJugador().getPremiosInversionesOBote() +2000000);
    }

    private void accComm5(Avatar av, ArrayList<Jugador> jugadores) {
        /**
         * Paga 1000000€ por invitar a todos tus amigos a un viaje a Solar14.
         */
        av.getJugador().sumarFortuna(-1000000);
        av.getJugador().setPagoTasasEImpuestos(av.getJugador().getPagoTasasEImpuestos() + 1000000);
        jugadores.get(0).sumarGastos(1000000);
        System.out.println("El bote de la banca ahora es " + jugadores.get(0).getGastos());
    }

    private void accComm6(Avatar av, ArrayList<Jugador> jugadores) {
        /**
         * Alquilas a tus compañeros una villa en Solar7 durante una semana. Paga
         * 200000€ a cada jugador
         */
        for (Jugador j : jugadores) {
            av.getJugador().sumarFortuna(-200000);
            av.getJugador().estaBancarrota();
            j.sumarFortuna(200000);
            av.getJugador().setPagoTasasEImpuestos(av.getJugador().getPagoTasasEImpuestos() + 200000);
            jugadores.get(0).sumarGastos(200000);
            System.out.println("El bote de la banca ahora es " + jugadores.get(0).getGastos());
        }
    }

}
