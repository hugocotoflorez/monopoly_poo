package monopoly.Casilla.Impuesto;

import monopoly.Juego;
import partida.*;
import monopoly.Casilla.*;;

public class Impuesto extends Casilla{
    private float impuesto;

    public Impuesto(String nombre, int posicion, float impuesto){
        super(nombre,posicion);
        this.impuesto = impuesto;
    }

    public float getImpuesto(){
        return this.impuesto;
    }

    public void setImpuesto(float impuesto){
        this.impuesto = impuesto;
    }

    @Override
    public String infoCasilla(Jugador banca){
        String info = new String();
        info = """
                {
                Nombre: %s
                Tipo: impuestos
                Impuesto: %f
                }
                """.formatted(this.getNombre(), this.impuesto);
        return info;
    }
    
    @Override
    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada){
        actual.sumarFortuna(-this.impuesto);
        banca.sumarGastos(this.impuesto);

        actual.setPagoTasasEImpuestos(actual.getPagoTasasEImpuestos() + this.impuesto);

        Juego.consola.imprimir("El jugador " + actual.getNombre() + " paga " + this.impuesto + " de impuestos a la banca.");
        Juego.consola.imprimir("Ahora el jugador " + actual.getNombre() + " tiene " + actual.getFortuna());
        Juego.consola.imprimir("El bote de la banca es " + banca.getGastos());
        
        if (actual.estaBancarrota()) return true;
        return false;
    }
}
