package monopoly.Edificio;

import monopoly.Casilla.Propiedad.Solar;
import monopoly.Valor;

public class PistaDeporte extends Edificio {

    public PistaDeporte(Solar casilla) {
        super("pista-" + Valor.NumeroCasasConstruidas, casilla, casilla.getGrupo().getValor()*1.25f);
    }

}
