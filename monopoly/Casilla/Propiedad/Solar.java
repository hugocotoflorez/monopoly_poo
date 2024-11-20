package monopoly.Casilla.Propiedad;

import partida.Jugador;

public class Solar extends Propiedad{

    @Override
    public void hipotecar(Jugador solicitante){
        if (this.edificios.size != 0){ //TODO excepcion
            Juego.consola.imprimir("Debes vender los edificios de esta propiedad antes de hipotecarla.");
            Juego.consola.imprimir("Edificios de esta propiedad: ");
            this.listar_edificios_casilla();
        }
        else super.hipotecar(solicitante);
    }

}
