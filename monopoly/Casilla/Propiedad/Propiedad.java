package monopoly.Casilla.Propiedad;
import java.util.ArrayList;

import monopoly.Juego;
import partida.*;
import monopoly.Casilla.*;;

public abstract class Propiedad extends Casilla {
    private float valor;
    private float alquiler;
    private float hipoteca;
    private Jugador duenho;
    private boolean hipotecada = false;
    private float recaudado = 0;

    public Propiedad(String nombre, int posicion, float valor, Jugador duenho) {
        super(nombre, posicion);
        this.valor = valor;
        this.duenho = duenho;
        this.hipoteca = valor/2;
    }

    //GETTERS & SETTERS------------------------------------
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
    public boolean getHipotecada(){
        return hipotecada;
    }
    public float getRecaudado(){
        return recaudado;
    }
    //-------------------
    public void setValor(float valor){
        if(valor > 0)
        this.valor = valor;
    }
    public void setAlquiler(float alquiler){
        if(alquiler > 0)
        this.alquiler = alquiler;
    }
    public void setDuenho(Jugador duenho){
        this.duenho = duenho;
    }
    public void setHipotecada(boolean hipotecada){
        this.hipotecada = hipotecada;
    }
    public void setRecaudado(float recaudado){
        this.recaudado = recaudado;
    }
    //---------------------------------------------------------

    public void sumarValor(float suma){
        this.valor += suma;
    }

    public boolean esComprable(Jugador jugador) {
        return (this.duenho.esBanca() && jugador.getAvatar().getCasilla().equals(this));
    }
    public boolean esComprable(ArrayList<Casilla> casillas) {
        return (this.duenho.esBanca() && casillas.contains(this));
    }

    public boolean perteneceAJugador(Jugador jugador){
        return this.duenho.equals(jugador);
    }


    public void comprarCasilla(Jugador solicitante, Jugador banca) {
        /*
         * si llamas a esto no eres la pelota, por lo que el resto de argumentos
         * dan igual
         */
        comprarCasilla(solicitante, banca, false, null);
    }

    public void comprarCasilla(Jugador solicitante, Jugador banca, boolean movAv, ArrayList<Casilla> casVis){ //TODO excepcion
        if (solicitante.getFortuna() < this.valor){
            Juego.consola.imprimirln("No tienes suficiente fortuna. Necesitas " + this.valor);
        }
        else if (solicitante.getAvatar() instanceof partida.Avatar.Pelota && movAv && !esComprable(casVis)){
            Juego.consola.imprimirln("No caíste en esta propiedad.");
        }
        else if ( (!(solicitante.getAvatar() instanceof partida.Avatar.Pelota) || !movAv) && !this.esComprable(solicitante)){
            Juego.consola.imprimirln("No puedes comprar esta propiedad.");
        }
        else {
            solicitante.setFortuna(solicitante.getFortuna() - this.valor);
            solicitante.anhadirPropiedad(this);
            solicitante.setDineroInvertido(solicitante.getDineroInvertido() + this.valor);
            banca.eliminarPropiedad(this);
            this.duenho = solicitante;
            Juego.consola.imprimirln("El jugador " + solicitante.getNombre() + " ha comprado la propiedad " + this.getNombre() + " por " + this.valor);
            Juego.consola.imprimirln("Su fortuna actual es " + solicitante.getFortuna());
        }
    }



    //TODO Excepciones
    public void hipotecar(Jugador solicitante){
        if (this.perteneceAJugador(solicitante) && this.hipotecada == false && this.duenho.getEnCarcel() == false){
            solicitante.sumarFortuna(hipoteca);
            this.hipotecada = true;

            Juego.consola.imprimirln("El jugador " + solicitante.getNombre() + " hipoteca " + this.getNombre() + " por " + this.hipoteca);
            Juego.consola.imprimirln("Su fortuna actual es " + solicitante.getFortuna());
        }
        else if (!this.perteneceAJugador(solicitante)){
            Juego.consola.imprimirln("Esta propiedad no pertenece a " + solicitante.getNombre());
        }
        else if (this.hipotecada == true){
            Juego.consola.imprimirln("Esta propiedad ya está hipotecada.");
        }
        else if (this.duenho.getEnCarcel() == true){
            Juego.consola.imprimirln("¡Estás en la cárcel!");
        }
    }
    //TODO excepciones
    public void deshipotecar(Jugador solicitante){
        if(this.perteneceAJugador(solicitante) && this.hipotecada == true && this.duenho.getEnCarcel() == false){
            if (this.duenho.getFortuna() >= this.hipoteca * 1.10f){
                this.duenho.sumarFortuna(-hipoteca * 1.10f);
                this.hipotecada = false;

                Juego.consola.imprimirln("El jugador " + solicitante.getNombre() + " deshipoteca la casilla " + this.getNombre() + "por" + this.hipoteca * 1.10f);
                Juego.consola.imprimirln("Su fortuna actual es " + solicitante.getFortuna());
            }
            else {
                Juego.consola.imprimirln("No tienes suficiente fortuna. Necesitas " + this.hipoteca * 1.10f);
            }
        }
        else if (!this.perteneceAJugador(solicitante)){
            Juego.consola.imprimirln("Esta propiedad no pertenece a " + solicitante.getNombre());
        }
        else if (this.hipotecada == false){
            Juego.consola.imprimirln("Esta propiedad no está hipotecada.");
        }
        else if (this.duenho.getEnCarcel() == true){
            Juego.consola.imprimirln("¡Estás en la cárcel!");
        }
    }




    public abstract float calcularAlquiler(int tirada);

    public void cobrarAlquiler(Jugador actual){
        actual.sumarFortuna(-this.alquiler);
        this.getDuenho().sumarFortuna(this.alquiler);
        Juego.consola.imprimirln("El jugador " + actual.getNombre() + " paga " + this.alquiler + " de alquiler a " + this.getDuenho().getNombre());
        Juego.consola.imprimirln("Ahora el jugador " + actual.getNombre() + " tiene " + actual.getFortuna() + " y el jugador " + this.getDuenho().getNombre() + " tiene " + this.getDuenho().getFortuna());

        //Actualizar estadísticas
        actual.setPagoDeAlquileres(actual.getPagoDeAlquileres() + this.alquiler);
        this.getDuenho().setCobroDeAlquileres(this.getDuenho().getCobroDeAlquileres() + this.alquiler);
        this.recaudado += this.alquiler;
    }



    @Override
    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada){
        if(!this.duenho.esBanca() && !this.duenho.equals(actual)){
            if(!this.hipotecada){
                calcularAlquiler(tirada);
                this.setAlquiler(this.calcularAlquiler(tirada));
                cobrarAlquiler(actual);
                if (actual.estaBancarrota()) return false;
            }
            else Juego.consola.imprimirln("El jugador " + this.duenho.getNombre() + "no cobra el alquiler por " + this.getNombre() + "porque está hipotecada.");
        }
        else if (this.duenho.equals(actual)) Juego.consola.imprimirln("Esta propiedad te pertenece.");
        else Juego.consola.imprimirln("Se puede comprar la casilla " + this.getNombre());
        return true;
    }

    @Override
    public abstract String infoCasilla(Jugador banca);
}