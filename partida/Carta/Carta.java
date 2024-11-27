package partida.Carta;

import monopoly.Casilla.Casilla;

import java.util.ArrayList;
import java.util.Collections;

import partida.Jugador;
import partida.Avatar.*;

public abstract class Carta {
    String descripcion;
    int accion;

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

    public final static String[] desc = {
            "Ve al Transportes1 y coge un avión. Si pasas por la casilla de Salida, cobra la cantidad habitual.\n",
            "Decides hacer un viaje de placer. Avanza hasta Solar15 directamente, sin pasar por la casilla de Salida y sin cobrar la cantidad habitual.\n",
            "Vendes tu billete de avión para Solar17 en una subasta por Internet. Cobra 500000€.\n",
            "Ve a Solar3. Si pasas por la casilla de Salida, cobra la cantidad habitual.\n",
            "Los acreedores te persiguen por impago. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida y sin cobrar la cantidad habitual.\n",
            "¡Has ganado el bote de la lotería! Recibe 1000000€.\n",
            "Paga 500000€ por un fin de semana en un balneario de 5 estrellas.\n",
            "Te investigan por fraude de identidad. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida y sin cobrar la cantidad habitual.\n",
            "Colócate en la casilla de Salida. Cobra la cantidad habitual.\n",
            "Tu compañía de Internet obtiene beneficios. Recibe 2000000€.\n",
            "Paga 1000000€ por invitar a todos tus amigos a un viaje a Solar14.\n",
            "Alquilas a tus compañeros una villa en Solar7 durante una semana. Paga 200000€ a cada jugador3.\n"
    };

    public Carta() {
    }

    public void setAccion(int n) {
        if (n > 0 && n <= 6)
            this.accion = n;
    }

    public void setDescripcion(int n) {
        this.descripcion = desc[n];
    }

    public static void barajar(ArrayList<Carta> baraja) {
        System.out.println("SE ESTAN BARAJANDO LAS CARTAS !!!!!!");
        Collections.shuffle(baraja);
    }

    public void mostrarDescipcion() {
        System.out.println(this.descripcion);
    }

    public abstract boolean realizarAccion(Avatar av, ArrayList<Jugador> jugadores,
            ArrayList<ArrayList<Casilla>> casillas);

}
