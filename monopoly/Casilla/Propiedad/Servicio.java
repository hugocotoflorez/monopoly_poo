package monopoly.Casilla.Propiedad;

import monopoly.Valor;
import partida.Jugador;

public class Servicio extends Propiedad{
    public Servicio(String nombre, int posicion, float valor, float hipoteca, Jugador duenho) {
        super(nombre, posicion, valor, hipoteca, duenho);
    }

    @Override
    public float calcularAlquiler(int tirada){
        int s = (this.getDuenho().cuantosservicios() >= 2) ? 10 : 4;
        return Valor.IMPUESTO_SERVICIOS * s * tirada;
    }

    @Override
    public String infoCasilla(){
        String info = new String();
        info = """
                {
                Nombre: %s
                Tipo: servicios
                Propietario: %s
                Valor: %f
                Alquiler: %f
                }
                """.formatted(this.getNombre(), this.getDuenho().getNombre(), this.getValor(), this.calcularAlquiler(1) + " * el valor de la tirada");
        return info;
    }
}
