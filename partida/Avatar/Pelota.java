package partida.Avatar;

public class Pelota extends Avatar{
    public String getInfo() {
        String ret = """
                id: %s,
                tipo: Pelota
                casilla: %s,
                jugador: %s
                    """.formatted(this.getId(), this.getCasilla().getNombre(), this.getJugador().getNombre());
        return ret;
    }
}
