package monopoly.Edificio;

import monopoly.Casilla.Casilla;
import monopoly.Valor;

public class Casa extends Edificio {

    public Casa(Casilla casilla) {

        super("Casa-" + Valor.NumeroCasasConstruidas, casilla,
                casilla.getGrupo().getValor() * 0.60f);

    }

    @Override
    public String getTipo() {

        return "Casa";
    }

}
