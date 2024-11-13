package monopoly.Edificio;

import monopoly.Casilla.Casilla;
import monopoly.Valor;

public class PistaDeporte extends Edificio {

    public PistaDeporte(Casilla casilla) {

        super("PistaDeporte-" + Valor.NumeroPistasConstruidos, casilla,
                casilla.getGrupo().getValor() * 1.25f);

    }

    @Override
    public String getTipo() {

        return "Pista de deportes";
    }
}
