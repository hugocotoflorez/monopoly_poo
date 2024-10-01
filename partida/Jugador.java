package partida;

import java.util.ArrayList;
import monopoly.*;


public class Jugador {

    //Atributos:
    private String nombre; //Nombre del jugador
    private Avatar avatar; //Avatar que tiene en la partida.
    private float fortuna; //Dinero que posee.
    private float gastos; //Gastos realizados a lo largo del juego.
    private boolean enCarcel; //Será true si el jugador está en la carcel
    private int tiradasCarcel; //Cuando está en la carcel, contará las tiradas sin éxito que ha hecho allí para intentar salir (se usa para limitar el numero de intentos).
    private int vueltas; //Cuenta las vueltas dadas al tablero.
    private ArrayList<Casilla> propiedades; //Propiedades que posee el jugador.

    //Constructor vacío. Se usará para crear la banca.
    public Jugador() {
        
        this.fortuna = 100000; // Valor elevado para que la banca nunca se quede sin dinero
        this.avatar = null;
        this.nombre = null;
        this.gastos = 0;
        this.enCarcel = false;
        this.tiradasCarcel = 0;
        this.tiradasCarcel = 0;
        this.vueltas = 0;
        this.propiedades = null; //la banca no tendría que tener al principio todas las propiedades del tablero?
    }

    /*Constructor principal. Requiere parámetros:
    * Nombre del jugador, tipo del avatar que tendrá, casilla en la que empezará y ArrayList de
    * avatares creados (usado para dos propósitos: evitar que dos jugadores tengan el mismo nombre y
    * que dos avatares tengan mismo ID). Desde este constructor también se crea el avatar.
     */
    public Jugador(String nombre, String tipoAvatar, Casilla inicio, ArrayList<Avatar> avCreados) {

        this.nombre = nombre;
        this.avatar = new Avatar(tipoAvatar,inicio,avCreados);
        this.avatar.setTipo(tipoAvatar);
        this.avatar.setLugar(inicio);
        this. fortuna = Valor.FORTUNA_INICIAL;
        this.enCarcel = false;
        this.tiradasCarcel = 0;
        this.vueltas = 0;
        
    }

    //GETTERS
    public float getFortuna(){
        return this.fortuna;
    }
    public String getNombre(){
        return  this.nombre;
    }

    //SETTERS
    public void setFortuna(float fortuna){
        this.fortuna = fortuna;
        
    }
    
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public void setEnCarcel(boolean enCarcel){

        this.enCarcel = enCarcel;
    }
    public boolean getEnCarcel(){
        
        return this.enCarcel;
    }
    //Método para añadir una propiedad al jugador. Como parámetro, la casilla a añadir.
    public void anhadirPropiedad(Casilla casilla) {
        this.propiedades.add(casilla);
    }

    //Método para eliminar una propiedad del arraylist de propiedades de jugador.
    public void eliminarPropiedad(Casilla casilla) {
        if(this.propiedades.contains(casilla)){
            this.propiedades.remove(casilla);
        }
    }

    //Método para añadir fortuna a un jugador
    //Como parámetro se pide el valor a añadir. Si hay que restar fortuna, se pasaría un valor negativo.
    public void sumarFortuna(float valor) {
        this.fortuna += valor;
    }


    //Método para sumar gastos a un jugador.
    //Parámetro: valor a añadir a los gastos del jugador (será el precio de un solar, impuestos pagados...).
    public void sumarGastos(float valor) {
        this.gastos += valor;
    }

    /*Método para establecer al jugador en la cárcel. 
    * Se requiere disponer de las casillas del tablero para ello (por eso se pasan como parámetro).*/
    public void encarcelar(ArrayList<ArrayList<Casilla>> pos) {
    }
}
