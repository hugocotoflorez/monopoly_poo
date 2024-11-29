package monopoly.Edificio;

import monopoly.Casilla.Propiedad.*;
import monopoly.Valor;

public class Casa extends Edificio {

    public Casa(Solar casilla) {
        super("Casa-" + Valor.NumeroCasasConstruidas, casilla, casilla.getGrupo().getValor()*0.60f);
    }

}
