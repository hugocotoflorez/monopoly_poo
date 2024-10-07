package partida;

import java.util.ArrayList;
import monopoly.*;

public class Jugador {

    // Atributos:
    private String nombre; // Nombre del jugador
    private Avatar avatar; // Avatar que tiene en la partida.
    private float fortuna; // Dinero que posee.
    private float gastos; // Gastos realizados a lo largo del juego.
    private boolean enCarcel; // Será true si el jugador está en la carcel
    private int tiradasCarcel; // Cuando está en la carcel, contará las tiradas sin éxito que ha hecho allí
                               // para intentar salir (se usa para limitar el numero de intentos).
    private int turnoscarcel;
    private int vueltas = 0; // Cuenta las vueltas dadas al tablero.
    private ArrayList<Casilla> propiedades = new ArrayList<Casilla>(); // Propiedades que posee el jugador.
    private int tirada;

    // Constructor vacío. Se usará para crear la banca.
    public Jugador() {

        this.fortuna = 100000; // Valor elevado para que la banca nunca se quede sin dinero
        this.avatar = null;
        this.nombre = "banca";
        this.gastos = 0;
        this.enCarcel = false;
        this.tiradasCarcel = 0;
        this.tiradasCarcel = 0;
        this.vueltas = 0;
    }

    /*
     * Constructor principal. Requiere parámetros:
     * Nombre del jugador, tipo del avatar que tendrá, casilla en la que empezará y
     * ArrayList de
     * avatares creados (usado para dos propósitos: evitar que dos jugadores tengan
     * el mismo nombre y
     * que dos avatares tengan mismo ID). Desde este constructor también se crea el
     * avatar.
     */
    public Jugador(String nombre, String tipoAvatar, Casilla inicio, ArrayList<Avatar> avCreados) {

        this.nombre = nombre;
        this.avatar = new Avatar(tipoAvatar, inicio, avCreados);
        this.avatar.setTipo(tipoAvatar);
        this.avatar.setLugar(inicio);
        this.fortuna = Valor.FORTUNA_INICIAL;
        this.enCarcel = false;
        this.tiradasCarcel = 0;
        this.turnoscarcel = 0;
        this.vueltas = 0;
        this.avatar.setJugador(this);

    }

    // GETTERS
    public float getFortuna() {
        return this.fortuna;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Avatar getAvatar() {
        return this.avatar;
    }

    public boolean getEnCarcel() {

        return this.enCarcel;
    }

    public int getTurnosCarcel(){
        return this.turnoscarcel;
    }

    // SETTERS
    public void setFortuna(float fortuna) {
        this.fortuna = fortuna;

    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEnCarcel(boolean enCarcel) {

        this.enCarcel = enCarcel;
    }

    public int setTurnosCarcel(int turnos){
        return this.turnoscarcel = turnos;
    }

    // Método para añadir una propiedad al jugador. Como parámetro, la casilla a
    // añadir.
    public void anhadirPropiedad(Casilla casilla) {
        this.propiedades.add(casilla);
    }

    // Método para eliminar una propiedad del arraylist de propiedades de jugador.
    public void eliminarPropiedad(Casilla casilla) {
        if (this.propiedades.contains(casilla)) {
            this.propiedades.remove(casilla);
        }
    }

    public int getTirada(){
        return this.tirada;
    }
    public void setTirada(int tirada){
        this.tirada = tirada;
    }

    // Método para añadir fortuna a un jugador
    // Como parámetro se pide el valor a añadir. Si hay que restar fortuna, se
    // pasaría un valor negativo.
    public void sumarFortuna(float valor) {
        this.fortuna += valor;
        if (this.estaBancarrota()){
            System.out.println("El jugador " + this.nombre + " se ha quedado en bancarrota. La partida se acabará");
            Menu.acabarPartida();
        }
    }

    // Método para comprobar si un jugador está en bancarrota
    public boolean estaBancarrota() {
        return this.fortuna < 0;
    }

    // Método para sumar gastos a un jugador.
    // Parámetro: valor a añadir a los gastos del jugador (será el precio de un
    // solar, impuestos pagados...).
    public void sumarGastos(float valor) {
        this.gastos += valor;
    }

    /*
     * Método para establecer al jugador en la cárcel.
     * Se requiere disponer de las casillas del tablero para ello (por eso se pasan
     * como parámetro).
     */
    public void encarcelar(ArrayList<ArrayList<Casilla>> pos) {

        this.avatar.setLugar(pos.get(1).get(0));

    }

    public boolean esBanca() {
        return this.avatar == null;
    }

@Override
    public String toString() {
        return """
            | Nombre: %s
            | Avatar: %s
            | - Fortuna %s
            | - propiedades: %s
            | - Hipotecas: %s
            | - Edificios: %s

            """.formatted(this.nombre, avatar!=null?this.avatar.getId():"", fortuna, propiedades!=null?this.propiedades:"", "Sin implementar", "Sin implementar");

    }
}
