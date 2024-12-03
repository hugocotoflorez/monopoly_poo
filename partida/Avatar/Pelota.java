package partida.Avatar;

import partida.*;
import monopoly.*;
import monopoly.Casilla.*;
import java.util.ArrayList;

public class Pelota extends Avatar {

    private ArrayList<Casilla> casillasVisitadas = new ArrayList<Casilla>();

    public Pelota(Jugador jugador, Casilla lugar, ArrayList<Avatar> avCreados) {
        super(jugador, lugar, avCreados);
    }

    public String getTipo() {
        return "Pelota";
    }

    public String getInfo() {
        String ret = """
                id: %s,
                tipo: Pelota
                casilla: %s,
                jugador: %s
                    """.formatted(this.getId(), this.getCasilla().getNombre(), this.getJugador().getNombre());
        return ret;
    }

    @Override
    public ArrayList<Casilla> getCasillasVisitadas() {
        return casillasVisitadas;
    }

    public boolean moverEnAvanzado(Tablero tablero, int valor1, int valor2, ArrayList<Jugador> jugadores) {
        /*
         * Pelota: si el valor de los dados es mayor que 4, avanza tantas casillas
         * como
         * dicho valor; mientras que, si el valor es menor o igual que 4, retrocede el
         * número de casillas correspondiente. En cualquiera de los dos casos, el
         * avatar
         * se parará en las casillas por las que va pasando y cuyos valores son
         * impares
         * contados desde el número 4. Por ejemplo, si el valor del dado es 9,
         * entonces
         * el avatar avanzará hasta la casilla 5, de manera que si pertenece a otro
         * jugador y es una casilla de propiedad deberá pagar el alquiler, y después
         * avanzará hasta la casilla 7, que podrá comprar si no pertenece a ningún
         * jugador, y finalmente a la casilla 9, que podrá comprar o deberá pagar
         * alquiler si no pertenece al jugador. Si una de esas casillas es Ir a
         * Cárcel,
         * entonces no se parará en las subsiguientes casillas
         */
        int desplazamiento = valor1 + valor2;

        /* Se borran todas las casillas visitadas anteriormente */
        casillasVisitadas.removeAll(casillasVisitadas);

        if (desplazamiento > 4) {
            for (int i = 5; i <= desplazamiento + 1; i += 2) {

                if (i == 5) // primer salto
                    moverNormal(tablero, 5, 0, jugadores);

                // saltos restantes
                else {

                    /*
                     * Si el desplazamiento cumple esa condicion, la i esta fuera del rando de
                     * movimiento por lo que se desplaza solo 1. Este caso lo cumple el ultimo salto
                     * de todas l tiradas con desplazamiento par
                     */
                    if (i == desplazamiento + 1)
                        moverNormal(tablero, 1, 0, jugadores);

                    /* En el resto de los casos el salto es de 2 casillas */
                    else
                        moverNormal(tablero, 2, 0, jugadores);
                }
                // anade la casilla en la que cae a las que puede comprar
                casillasVisitadas.add(getCasilla());

                // evalua casilla o hace la accion que deba hacer
                evaluarAccion(valor1 + valor2, jugadores, tablero);

                // si va a la carcel deja de moverse
                if (getJugador().getEnCarcel())
                    break;

                /*
                 * Si esta en bancarrota deja de moverse y devuelve el valor necesario para que
                 * se pueda interpretar que esta en bancarrota desde el metodo invocante
                 */
                if (getJugador().estaBancarrota())
                    return false;
            }
            return true;

        } else {

            /*
             * Este bucle sigue la misma logica que el anterior, solamente se diferencia en
             * que llama a moverAtras() en vez de mover()
             */
            for (int i = 1; i <= desplazamiento + 1; i += 2) {

                if (i == 1) // primer salto
                    moverAtras(tablero, 1, 0);

                // saltos restantes
                else {

                    // Ultimo salto desplazamientos pares
                    if (i == desplazamiento + 1)
                        moverAtras(tablero, 1, 0);
                    else
                        moverAtras(tablero, 2, 0);
                }
                // anade la casilla en la que cae a las que puede comprar
                casillasVisitadas.add(getCasilla());
                // evalua casilla o hace la accion que deba hacer

                evaluarAccion(valor1 + valor2, jugadores, tablero);

                // si va a la carcel deja de moverse
                if (getJugador().getEnCarcel())
                    break;

                // Si esta en bancarrota deja de moverse y devuelve el valor esperado
                if (getJugador().estaBancarrota())
                    return false;
            }
            return true;

        }
    }
}
