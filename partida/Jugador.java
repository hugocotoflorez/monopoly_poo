package partida;

import java.util.ArrayList;
import monopoly.*;
import monopoly.Casilla.*;
import monopoly.Casilla.Propiedad.*;
import monopoly.Edificio.*;
import partida.Avatar.*;

public class Jugador {

    // Atributos:
    private String nombre; // Nombre del jugador
    private Avatar avatar; // Avatar que tiene en la partida.
    private float fortuna; // Dinero que posee.
    private float gastos = 0f; // Gastos realizados a lo largo del juego.
    private boolean enCarcel; // Será true si el jugador está en la carcel
    /* Lo comento porque esta en el esqueleto pero para que no salga el warning */
    //private int tiradasCarcel; // Cuando está en la carcel, contará las tiradas sin éxito que ha hecho allí
                               // para intentar salir (se usa para limitar el numero de intentos).
    private int turnoscarcel;
    private int vueltas = 0; // Cuenta las vueltas dadas al tablero.
    private ArrayList<Propiedad> propiedades = new ArrayList<Propiedad>(); // Propiedades que posee el jugador.
    private int tirada;
    private int numerotiradas = 0;

    private float dineroInvertido = 0;
    private float pagoTasasEImpuestos = 0;
    private float pagoDeAlquileres = 0;
    private float cobroDeAlquileres = 0;
    private float pasarPorCasillaDeSalida = 0;
    private float premiosInversionesOBote = 0;
    private int vecesEnLaCarcel = 0;

    // Constructor vacío. Se usará para crear la banca.
    public Jugador() {

        this.fortuna = 1000000000; // Valor elevado para que la banca nunca se quede sin dinero
        this.avatar = null;
        this.nombre = "banca";
        this.gastos = 0;
        this.enCarcel = false;
        this.turnoscarcel = 0;
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

        switch (tipoAvatar) {
            case "Pelota":
                this.avatar = new Pelota(this, inicio, avCreados);
                break;
            case "Coche":
                this.avatar = new Coche(this, inicio, avCreados);
                break;
            case "Esfinge":
                this.avatar = new Esfinge(this, inicio, avCreados);
                break;
            case "Sombrero":
                this.avatar = new Sombrero(this, inicio, avCreados);
                break;
        }

        this.avatar.setTurno(avCreados.size()-1);
        this.avatar.setLugar(inicio);
        this.fortuna = Valor.FORTUNA_INICIAL;
        this.enCarcel = false;
        this.turnoscarcel = 0;
        this.vueltas = 0;
        this.avatar.setJugador(this);

    }

    public float getGastos() {
        return this.gastos;
    }

    public void resetGastos() {
        this.gastos = 0;
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

    public int getTurnosCarcel() {
        return this.turnoscarcel;
    }

    public int getVueltas() {
        return this.vueltas;
    }

    public float getDineroInvertido() {
        return this.dineroInvertido;
    }

    public float getPagoTasasEImpuestos() {
        return this.pagoTasasEImpuestos;
    }

    public float getPagoDeAlquileres() {
        return this.pagoDeAlquileres;
    }

    public float getCobroDeAlquileres() {
        return this.cobroDeAlquileres;
    }

    public float getPasarPorCasillaDeSalida() {
        return this.pasarPorCasillaDeSalida;
    }

    public float getPremiosInversionesOBote() {
        return this.premiosInversionesOBote;
    }

    public int getVecesEnLaCarcel() {
        return this.vecesEnLaCarcel;
    }

    public int getNumeroTiradas() {
        return this.numerotiradas;
    }

    public int getTirada() {
        return this.tirada;
    }

    public ArrayList<Propiedad> getPropiedades() {
        return this.propiedades;
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

    public void setTurnosCarcel(int turnos) {
        if (turnos >= 0)
        this.turnoscarcel = turnos;
    }

    public void setVueltas(int vueltas) {
        if (vueltas >= 0)
        this.vueltas = vueltas;
    }

    public void setDineroInvertido(float valor) {
        if (valor >= 0)
        this.dineroInvertido = valor;
    }

    public void setPagoTasasEImpuestos(float valor) {
        if (valor >= 0)
        this.pagoTasasEImpuestos = valor;
    }

    public void setPagoDeAlquileres(float valor) {
        if (valor >= 0)
        this.pagoDeAlquileres = valor;
    }

    public void setCobroDeAlquileres(float valor) {
        if (valor >= 0)
        this.cobroDeAlquileres = valor;
    }

    public void setPasarPorCasillaDeSalida(float valor) {
        if (valor >= 0)
        this.pasarPorCasillaDeSalida = valor;
    }

    public void setPremiosInversionesOBote(float valor) {
        if (valor >= 0)
        this.premiosInversionesOBote = valor;
    }

    public void setVecesEnLaCarcel(int valor) {
        if (valor >= 0)
        this.vecesEnLaCarcel = valor;
    }

    public void setNumeroTiradas(int valor) {
        if (valor >= 0)
        this.numerotiradas = valor;
    }

    public void setTirada(int tirada) {
        if (tirada >= 0)
        this.tirada = tirada;
    }

    // Método para añadir una propiedad al jugador. Como parámetro, la casilla a
    // añadir.
    public void anhadirPropiedad(Propiedad casilla) {
        this.propiedades.add(casilla);
    }

    // Método para eliminar una propiedad del arraylist de propiedades de jugador.
    public void eliminarPropiedad(Propiedad casilla) {
        if (this.propiedades.contains(casilla)) {
            this.propiedades.remove(casilla);
        }
    }

    // Método para añadir fortuna a un jugador
    // Como parámetro se pide el valor a añadir. Si hay que restar fortuna, se
    // pasaría un valor negativo.
    public void sumarFortuna(float valor) {
        this.fortuna += valor;
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

        this.avatar.getCasilla().eliminarAvatar(this.avatar);
        this.avatar.setLugar(pos.get(1).get(0));
        this.enCarcel = true;
        this.avatar.getCasilla().anhadirAvatarCasilla(this.avatar);
        this.vecesEnLaCarcel += 1;

    }

    public boolean esBanca() {
        return this.avatar == null;
    }

    public void sumarNumeroTiradas() {
        this.numerotiradas++;
    }

    /*
     * Obtiene el numero de casillas de tipo servicio que
     * tiene el jugador. Es ineficiente pero funciona
     */
    public int cuantosservicios() {
        int i = 0;
        for (Propiedad c : this.propiedades) {
            if (c instanceof Servicio)
                ++i;
        }
        return i;
    }

    /*
     * Obtiene el numero de casillas de tipo transporte que
     * tiene el jugador. Es ineficiente pero funciona
     */
    public int cuantostransportes() {
        int i = 0;
        for (Propiedad c : this.propiedades) {
            if (c instanceof Transporte)
                ++i;
        }
        return i;
    }

    public String estadisticasJugador() {
        String ret = """
                {
                    Dinero invertido en compra de propiedades y edificaciones: %f
                    Pago de tasas y/o impuestos: %f
                    Pago de alquileres y uso de servicios y transportes: %f
                    Cobro de alquileres y uso de servicios y transportes: %f
                    Cobro por pasar por la casilla de salida: %f
                    Cobro por premios, inversiones o  bote: %f
                    Veces en la cárcel: %d
                }
                """.formatted(
                    this.dineroInvertido,
                    this.pagoTasasEImpuestos,
                    this.pagoDeAlquileres,
                    this.cobroDeAlquileres,
                    this.pasarPorCasillaDeSalida,
                    this.premiosInversionesOBote,
                    this.vecesEnLaCarcel);
        return ret;
    }

    private String printEdificios() {
        String ret = new String();
        ret += "[";

        for (Propiedad c : this.propiedades) {
            if (c instanceof Solar){
                Solar s = (Solar) c;
                for (Edificio e : s.getEdificios()){
                    ret += e.getID();
                    ret += ", ";
                }

            }

        }
        ret += "]";

        return ret;

    }

    private String printHipotecas() {
        String ret = new String();
        ret += "[";
        for (Propiedad c : this.propiedades) {
            if (c instanceof Solar){
                Solar s = (Solar) c;
                if (s.getHipotecada()){
                    ret += s.getNombre();
                    ret += ", ";
                }
            }
        }
        ret += "]";
        return ret;
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

                """.formatted(this.nombre, avatar != null ? this.avatar.getId() : "", fortuna,
                propiedades != null ? this.propiedades : "[]", this.printHipotecas(), this.printEdificios());
    }

    public float score() {
        float ret = 0;
        ret += this.fortuna;
        for (Propiedad c : propiedades) {
            ret += c.getValor();
            if (c instanceof Solar){
                Solar s = (Solar) c;
                for (Edificio e: s.getEdificios())
                    ret += e.getPrecio();
            }
        }
        return ret;
    }

    public boolean puedeHacerTrato(Propiedad casilla){

        return this.propiedades.contains(casilla);

    }

    public boolean puedeHacerTrato(float dinero){

        return (this.fortuna >= dinero);

    }
    public boolean puedeHacerTrato(Propiedad casilla, float dinero){

        return (puedeHacerTrato(casilla) && puedeHacerTrato(dinero));

    }
}