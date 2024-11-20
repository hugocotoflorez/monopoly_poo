package monopoly.Casilla.Propiedad;
import monopoly.Grupo;
import partida.*;
import monopoly.Casilla.*;;

public abstract class Propiedad extends Casilla {
    private float valor;
    private float alquiler;
    private float hipoteca;
    private Jugador duenho;
    private boolean hipotecada = false;
    private float recaudado = 0;

    public Propiedad(String nombre, int posicion, float valor, float alquiler, float hipoteca, Jugador duenho) {
        super(nombre, posicion);
        this.valor = valor;
        this.alquiler = alquiler;
        this.hipoteca = hipoteca;
    }

    //GETTERS ------------------------------------
    public float getValor(){
        return valor;
    }
    public float getAlquiler(){
        return alquiler;
    }
    public float getHipoteca(){
        return hipoteca;
    }
    public Jugador getDuenho(){
        return duenho;
    }
    public

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