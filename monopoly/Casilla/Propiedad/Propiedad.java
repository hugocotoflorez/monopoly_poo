package monopoly.Casilla.Propiedad;
import java.util.ArrayList;

import monopoly.Grupo;
import monopoly.Juego;
import partida.*;
import partida.Avatar.*;
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
        this.valor = valor;
    }
    public void setAlquiler(float alquiler){
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

   /*  boolean perteneceAJugador(Jugador jugador)
 alquiler
 valor
 comprar*/

    public boolean esComprable(Jugador jugador) {
        return (this.duenho.esBanca() && jugador.getAvatar().getCasilla().equals(this));
    }
    public boolean esComprable(ArrayList<Casilla> casillas) {
        return (this.duenho.esBanca() && casillas.contains(this));
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
            Juego.consola.imprimir("No tienes suficiente fortuna. Necesitas " + this.valor);
        }
        else if (solicitante.getAvatar() instanceof partida.Avatar.Pelota && movAv && !esComprable(casVis)){
            Juego.consola.imprimir("No caíste en esta propiedad.");
        }
        else if ( (!(solicitante.getAvatar() instanceof partida.Avatar.Pelota) || !movAv) && !this.esComprable(solicitante)){
            Juego.consola.imprimir("No puedes comprar esta propiedad.");
        }
        else {
            solicitante.setFortuna(solicitante.getFortuna() - this.valor);
            solicitante.anhadirPropiedad(this);
            solicitante.setDineroInvertido(solicitante.getDineroInvertido() + this.valor);
            banca.eliminarPropiedad(this);
            this.duenho = solicitante;
            Juego.consola.imprimir("El jugador " + solicitante.getNombre() + " ha comprado la propiedad " + this.getNombre() + " por " + this.valor);
            Juego.consola.imprimir("Su fortuna actual es " + solicitante.getFortuna());
        }
    }

    //TODO Excepciones
    public void hipotecar(Jugador solicitante){
        if (this.perteneceAJugador(solicitante) && this.hipotecada == false && this.duenho.getEnCarcel() == false){
            this.duenho.sumarFortuna(hipoteca);
            this.hipotecada = true;
            Juego.consola.imprimir("El jugador " + solicitante.getNombre() + " hipoteca " + this.getNombre() + " por " + this.hipoteca);
            Juego.consola.imprimir("Su fortuna actual es " + solicitante.getFortuna());
        }
        else if (!this.perteneceAJugador(solicitante)){
            Juego.consola.imprimir("Esta propiedad no pertenece a " + solicitante.getNombre());
        }
        else if (this.hipotecada == true){
            Juego.consola.imprimir("Esta propiedad ya está hipotecada.");
        }
        else if (this.duenho.getEnCarcel() == true){
            Juego.consola.imprimir("¡Estás en la cárcel!");
        }
    }

    //TODO excepciones
    //TODO te tiene que hacer solvente
    public void deshipotecar(Jugador solicitante){
        if(this.perteneceAJugador(solicitante) && this.hipotecada == true && this.duenho.getEnCarcel() == false){
            if (this.duenho.getFortuna() >= this.hipoteca * 1.10f){
                this.duenho.sumarFortuna(-hipoteca * 1.10f);
                this.hipotecada = false;
                Juego.consola.imprimir("El jugador " + solicitante.getNombre() + " deshipoteca la casilla " + this.getNombre() + "por" + this.hipoteca * 1.10f);
                Juego.consola.imprimir("Su fortuna actual es " + solicitante.getFortuna());
            }
            else {
                Juego.consola.imprimir("No tienes suficiente fortuna. Necesitas " + this.hipoteca * 1.10f);
            }
        }
        else if (!this.perteneceAJugador(solicitante)){
            Juego.consola.imprimir("Esta propiedad no pertenece a " + solicitante.getNombre());
        }
        else if (this.hipotecada == false){
            Juego.consola.imprimir("Esta propiedad no está hipotecada.");
        }
        else if (this.duenho.getEnCarcel() == true){
            Juego.consola.imprimir("¡Estás en la cárcel!");
        }
    }


    public abstract float calcularAlquiler();

    public boolean perteneceAJugador(Jugador jugador){
        return this.duenho.equals(jugador);
    }

    @Override
    public String toString() {
        return super.toString() + " Propiedad{" +
                "valor = " + valor +
                ", alquiler = " + alquiler +
                ", hipoteca = " + hipoteca +
                ", duenho ="  + (duenho.esBanca() ? duenho.getNombre() : "Sin dueño") +
                '}';
    }

    public abstract void cobrarAlquiler();

    @Override
    public abstract String infoCasilla();
}