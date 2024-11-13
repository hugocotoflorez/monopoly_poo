package partida.Avatar;

public class Sombrero extends Avatar{
    public String getInfo() {
        String ret = """
                id: %s,
                tipo: Sombrero
                casilla: %s,
                jugador: %s
                    """.formatted(this.getId(), this.getCasilla().getNombre(), this.getJugador().getNombre());
        return ret;
    }
}
