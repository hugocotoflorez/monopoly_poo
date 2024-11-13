package partida.Avatar;

public class Coche extends Avatar{
    public String getInfo() {
        String ret = """
                id: %s,
                tipo: Coche
                casilla: %s,
                jugador: %s
                    """.formatted(this.getId(), this.getCasilla().getNombre(), this.getJugador().getNombre());
        return ret;
    }
}
