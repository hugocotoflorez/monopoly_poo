package monopoly.Edificio;

import monopoly.Casilla.Casilla;
import monopoly.Valor;

public class Piscina extends Edificio {

    public Piscina(Casilla casilla) {

        super("Piscina-" + Valor.NumeroPiscinasConstruidas, casilla,
                casilla.getGrupo().getValor() * 0.40f);

    }

    @Override
    public String getTipo() {

        return "Piscina";
    }
}
