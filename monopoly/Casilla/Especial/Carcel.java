package monopoly.Casilla.Especial;

import monopoly.*;
import partida.*;
import partida.Avatar.Avatar;

public class Carcel extends Especial {
    public Carcel(int posicion){
        super("Carcel", posicion);
    }

    @Override
    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada){
        Juego.consola.imprimirln("Estás de visita en la cárcel.");
        return true;
    }

    @Override
    public String infoCasilla(Jugador banca){
        String jugadores = new String();
        for (Avatar av : this.getAvatares()){
            if (av.getJugador().getEnCarcel()){
                jugadores += av.getJugador().getNombre() + " lleva " + av.getJugador().getTurnosCarcel() + "turnos,";
            }
        }

        String info = new String();
        info = """
                {
                Nombre: %s
                Tipo: especial
                Pago por salir: %f
                Jugadores: [%s]
                }
                """.formatted(this.getNombre(), Valor.PAGO_SALIR_CARCEL, jugadores);
        return info;
    }
}
