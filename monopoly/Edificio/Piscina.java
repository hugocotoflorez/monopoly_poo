package monopoly.Edificio;

import monopoly.Casilla.Propiedad.Solar;
import monopoly.Valor;

public class Piscina extends Edificio {

    public Piscina(Solar casilla) {
        super("piscina-" + Valor.NumeroPiscinasConstruidas, casilla, casilla.getGrupo().getValor()*0.40f);
    }
    
}
