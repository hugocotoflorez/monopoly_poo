package partida;

import java.util.ArrayList;
import java.util.Collections;

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

    // El avatar es el jugador que esta en suerte y sobre el que se aplican las
    // acciones
    public void elegirCarta(ArrayList<Carta> baraja, int n, Avatar av) {
        barajar(baraja);
        obtenerCarta(baraja, n);
        Carta c = obtenerCarta(baraja, n);
        mostrarDescipcion(c);
        realizarAccion(c, av);
    }

    private void barajar(ArrayList<Carta> baraja) {
        Collections.shuffle(baraja);
    }

    private Carta obtenerCarta(ArrayList<Carta> baraja, int n) {
        if (n <= 0 && n > 6)
            return null;

        return baraja.get(n - 1);
    }

    private void mostrarDescipcion(Carta c) {
        System.out.println(c.descripcion);
    }

    private void realizarAccion(Carta c, Avatar av) {
        if (c.tipo.equals("suerte"))
            switch (c.accion) {
                case 1:
                    accSuerte1(av);
                    break;
                case 2:
                    accSuerte2(av);
                    break;
                case 3:
                    accSuerte3(av);
                    break;
                case 4:
                    accSuerte4(av);
                    break;
                case 5:
                    accSuerte5(av);
                    break;
                case 6:
                    accSuerte6(av);
                    break;
            }
        else
            switch (this.accion) {
                case 1:
                    accComm1(av);
                    break;
                case 2:
                    accComm2(av);
                    break;
                case 3:
                    accComm3(av);
                    break;
                case 4:
                    accComm4(av);
                    break;
                case 5:
                    accComm5(av);
                    break;
                case 6:
                    accComm6(av);
                    break;
            }
    }

    private void accSuerte1(Avatar av) {
    }

    private void accSuerte2(Avatar av) {
    }

    private void accSuerte3(Avatar av) {
    }

    private void accSuerte4(Avatar av) {
    }

    private void accSuerte5(Avatar av) {
    }

    private void accSuerte6(Avatar av) {
    }

    private void accComm1(Avatar av) {
    }

    private void accComm2(Avatar av) {
    }

    private void accComm3(Avatar av) {
    }

    private void accComm4(Avatar av) {
    }

    private void accComm5(Avatar av) {
    }

    private void accComm6(Avatar av) {
    }

}
