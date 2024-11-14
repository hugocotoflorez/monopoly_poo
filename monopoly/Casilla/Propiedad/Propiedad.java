package monopoly.Casilla.Propiedad;
import monopoly.Grupo;
import partida.*;
import monopoly.*;
import monopoly.Casilla.*;;

public abstract class Propiedad extends Casilla {
    private float valor;
    private Jugador duenho;
    private Grupo grupo;

    public Propiedad(String nombre, int posicion, float valor, Grupo grupo) {
        // TODO el constructor de Propiedad esta mal, cambie los argumentos
        // de super para que np de error pero va mal
        super(nombre, posicion, valor, null);
        this.valor = valor;
        this.grupo = grupo;
        this.duenho = null; // Por defecto, no tiene dueño
    }

    public float getValor() {
        return valor;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public Jugador getDuenho() {
        return duenho;
    }

    public void setDuenho(Jugador duenho) {
        this.duenho = duenho;
    }

    public boolean perteneceAJugador(Jugador jugador) {
        return duenho != null && duenho.equals(jugador);
    }

    public abstract float calcularAlquiler();

    @Override
    public String toString() {
        return super.toString() + " Propiedad{" +
                "valor=" + valor +
                ", duenho=" + (duenho != null ? duenho.getNombre() : "Sin dueño") +
                '}';
    }
}