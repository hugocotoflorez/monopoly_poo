package monopoly.Edificio;

import monopoly.Casilla;
import monopoly.Valor;

public class Hotel extends Edificio {

    public Hotel(Casilla casilla) {

        super("Hotel-" + Valor.NumeroHotelesConstruidos, casilla,
                casilla.getGrupo().getValor() * 0.60f);

    }

    @Override
    public String getTipo() {
        
        return "Hotel";
    }
}