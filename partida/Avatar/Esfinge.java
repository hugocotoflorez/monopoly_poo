package partida.Avatar;

public class Esfinge extends Avatar {
    public String getInfo() {
        String ret = """
                id: %s,
                tipo: Esfinge
                casilla: %s,
                jugador: %s
                    """.formatted(this.getId(), this.getCasilla().getNombre(), this.getJugador().getNombre());
        return ret;
    }

    @Override
    public void moverEnAvanzado(){} //TODO moverEnAvanzado Esfinge
}
