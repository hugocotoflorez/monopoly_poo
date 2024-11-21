package partida.Avatar;

import partida.*;
import monopoly.Casilla.*;
import java.util.ArrayList;

public class Coche extends Avatar {

    public Coche(Jugador jugador, Casilla lugar, ArrayList<Avatar> avCreados) {
        super(jugador, lugar, avCreados);
    }

    public String getTipo() {
        return "Coche";
    }

    public String getInfo() {
        String ret = """
                id: %s,
                tipo: Coche
                casilla: %s,
                jugador: %s
                    """.formatted(this.getId(), this.getCasilla().getNombre(), this.getJugador().getNombre());
        return ret;
    }

    @Override
    public void moverEnAvanzado(int valor1, int valor2) {
        /*
         * Coche: si el valor de los dados es mayor que 4, avanza tantas casillas como
         * dicho valor y puede seguir lanzando los dados tres veces más mientras el
         * valor sea mayor que 4. Durante el turno solo se puede realizar una sola
         * compra de propiedades, servicios o transportes, aunque se podría hacer en
         * cualesquiera de los 4 intentos posibles. Sin embargo, se puede edificar
         * cualquier tipo de edificio en cualquier intento. Si el valor de los dados es
         * menor que 4, el avatar retrocederá el número de casillas correspondientes y
         * además no puede volver a lanzar los dados en los siguientes dos turnos.
         */
        /*
         * DUDAS:
         * se vuelve a tirar cuando se sacan dobles? como afecta?
         */
        int desplazamiento = valor1 + valor2;
        if (desplazamiento > 4) {
            moverEnBasico(valor1, valor2);
            // actualiza contador coche y si el contador es 4 se pone a 0 y
            // this.tirado es false por lo que no se puede seguir tirando
            contadorTiradasCoche++;
            this.tirado = contadorTiradasCoche >= 4;

            consola.imprimirln("Se puede volver a tirar? " + !this.tirado);
            consola.imprimirln("Tiradas coche = " + contadorTiradasCoche);

        } else {
            contadorTiradasCoche = 1;
            moverAtras(valor1, valor2);
            // Comprueba si pasa por salida hacia atras
            pasarPorSalidaHaciaAtras(valor1 + valor2);
            se_puede_tirar_en_el_siguiente_turno[turno - 1] = false;
            se_puede_tirar_en_el_siguiente_turno2[turno - 1] = false;
            consola.imprimirln("No puedes mover en dos turnos!");
        }
    }
    // TODO moverEnAvanzado Coche
}
