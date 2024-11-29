package monopoly.Casilla.Especial;

import monopoly.*;
import monopoly.Casilla.*;
import partida.*;

public class Parking extends Especial {
    public Parking(int posicion){
        super("Parking", posicion);
    }

    @Override
    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada){
        Juego.consola.imprimirln("El jugador " + actual.getNombre() + " consigue el bote de la banca de " + banca.getGastos());
        Juego.consola.imprimirln("Su fortuna pasa de " + actual.getFortuna() + " a " + (actual.getFortuna() + banca.getGastos()));
        
        actual.sumarFortuna(banca.getGastos());
        actual.setPremiosInversionesOBote(actual.getPremiosInversionesOBote() + banca.getGastos());
        banca.resetGastos();
        return true;
    }

    @Override
    public String infoCasilla(Jugador banca){
        String info = new String();
        info = """
                {
                Nombre: %s
                Tipo: especial
                Bote: %f
                }
                """.formatted(this.getNombre(), banca.getGastos());
        return info;
    }
}
