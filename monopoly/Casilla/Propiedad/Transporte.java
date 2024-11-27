package monopoly.Casilla.Propiedad;

import monopoly.Valor;
import partida.Jugador;

public class Transporte extends Propiedad{
    public Transporte(String nombre, int posicion, float valor, float hipoteca, Jugador duenho) {
        super(nombre, posicion, valor, hipoteca, duenho);
    }

    @Override
    public float calcularAlquiler(int tirada){
        return this.getDuenho().cuantostransportes() * 0.25f * Valor.IMPUESTO_TRANSPORTES;
    }

    @Override
    public String infoCasilla(){
        String info = new String();
        info = """
                {
                Nombre: %s
                Tipo: transporte
                Propietario: %s
                Valor: %f
                Alquiler: %f
                }
                """.formatted(this.getNombre(), this.getDuenho().getNombre(), this.getValor(), calcularAlquiler(0));
        return info;
    }
}
