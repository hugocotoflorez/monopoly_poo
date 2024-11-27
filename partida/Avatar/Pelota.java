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
    public ArrayList<Casilla> getCasillasVisitadas(){
        return null;
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
        boolean solvente = true;

        if (desplazamiento > 4) {
            for (int i = 5; i <= desplazamiento + 1; i += 2) {

                if (i == 5) // primer salto
                    moverNormal(tablero, 5, 0, jugadores);
                else // saltos restantes
                if (i == desplazamiento)
                    moverNormal(tablero, 1, 0, jugadores);
                else
                    moverNormal(tablero, 2, 0, jugadores);

                // anade la casilla en la que cae a las que puede comprar
                casillasVisitadas.add(getCasilla());
                // evalua casilla o hace la accion que deba hacer

                solvente = evaluarAccion(valor1 + valor2, jugadores, tablero);
                // si va a la carcel deja de moverse
                if (getJugador().getEnCarcel())
                break;

                if (!solvente)
                return solvente;
            }
            return solvente;

        } else {
            // retroceder
            moverAtras(tablero, valor1, valor2);
            // Comprueba si pasa por salida hacia atras
            pasarPorSalidaHaciaAtras(valor1 + valor2);
            evaluarAccion(valor1 + valor2, jugadores, tablero);
            return solvente;
        }
    }
}
