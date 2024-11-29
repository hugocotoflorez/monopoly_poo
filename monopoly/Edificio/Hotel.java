package monopoly.Edificio;

import monopoly.Casilla.Propiedad.Solar;
import monopoly.Valor;

public class Hotel extends Edificio {

    public Hotel(Solar casilla) {
        super("hotel-" + Valor.NumeroHotelesConstruidos, casilla, casilla.getGrupo().getValor()*0.60f);
    }

}