package monopoly;

//soy imbecilsadjj
import java.util.ArrayList;
import partida.*;

public class Casilla {

    // Atributos:
    private String nombre; // Nombre de la casilla
    private String tipo; // Tipo de casilla (Solar, Especial, Transporte, Servicios, Comunidad).
    private float valor; // Valor de esa casilla (en la mayoría será valor de compra, en la casilla
                         // parking se usará como el bote).
    private int posicion; // Posición que ocupa la casilla en el tablero (entero entre 0 y 40).
    private Jugador duenho; // Dueño de la casilla (por defecto sería la banca).
    private Grupo grupo; // Grupo al que pertenece la casilla (si es solar).
    private float impuesto; // Cantidad a pagar por caer en la casilla: el alquiler en
                            // solares/servicios/transportes o impuestos.
    private float hipoteca; // Valor otorgado por hipotecar una casilla
    private ArrayList<Avatar> avatares= new ArrayList<Avatar>(); // Avatares que están situados en la casilla.

    public static final int casillaWidth = 19;

    /*
     * Constructor para casillas tipo Solar, Servicios o Transporte:
     * Parámetros: nombre casilla, tipo (debe ser solar, serv. o transporte),
     * posición en el tablero, valor y dueño.
     */
    public Casilla(String nombre, String tipo, int posicion, float valor, Jugador duenho) {

        this.nombre = nombre;
        check(tipo == "solar" || tipo == "serv" || tipo == "transporte", "casilla.tipo unexpected value\n" + tipo);
        this.tipo = tipo;
        this.posicion = posicion;
        this.valor = valor;
        this.duenho = duenho;
    }

    /*
     * Constructor utilizado para inicializar las casillas de tipo IMPUESTOS.
     * Parámetros: nombre, posición en el tablero, impuesto establecido y dueño.
     */
    public Casilla(String nombre, int posicion, float impuesto, Jugador duenho) {
        this.nombre = nombre;
        this.tipo = "Impuestos";
        this.posicion = posicion;
        this.impuesto = impuesto;
        this.duenho = duenho;
    }

    /*
     * Constructor utilizado para crear las otras casillas (Suerte, Caja de
     * comunidad y Especiales):
     * Parámetros: nombre, tipo de la casilla (será uno de los que queda), posición
     * en el tablero y dueño.
     */
    public Casilla(String nombre, String tipo, int posicion, Jugador duenho) {
        this.nombre = nombre;
        check(tipo.equals("suerte") || tipo.equals("caja") || tipo.equals("especial"), "casilla.tipo unexpected value");
        this.tipo = tipo;
        this.posicion = posicion;
        this.duenho = duenho;
    }

    //GETTERS
    public String getNombre() {
        return this.nombre;
    }

    public float getValor(){
        return this.valor;
    }

    public String getTipo(){
        return this.tipo;
    }

    public int getPosicion() {

        return this.posicion;
    }

    public Grupo getGrupo(){
        return this.grupo;
    }

    public Jugador getDuenho() {
        return this.duenho;
    }

    public float getImpuesto(){
        return this.impuesto;
    }

    public float getHipoteca(){
        return this.hipoteca;
    }


    //SETTERS
    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setValor(float valor){
        this.valor = valor;
    }

    public void setTipo(String tipo){
        this.tipo = tipo;
    }

    public void setPosicion(int pos) {
        this.posicion = pos;
    }

    public void setDuenho(Jugador duenho){
        this.duenho = duenho;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public void setImpuesto(float impuesto){
        this.impuesto = impuesto;
    }

    public void setHipoteca(float hipoteca){
        this.hipoteca = hipoteca;
    }


    // Método utilizado para añadir un avatar al array de avatares en casilla.
    public void anhadirAvatar(Avatar av) {
        this.avatares.add(av);
    }

    // Método utilizado para eliminar un avatar del array de avatares en casilla.
    public void eliminarAvatar(Avatar av) {
        this.avatares.remove(av);
    }

    /*
     * Método para evaluar qué hacer en una casilla concreta. Parámetros:
     * - Jugador cuyo avatar está en esa casilla.
     * - La banca (para ciertas comprobaciones).
     * - El valor de la tirada: para determinar impuesto a pagar en casillas de
     * servicios.
     * Valor devuelto: true en caso de ser solvente (es decir, de cumplir las
     * deudas), y false
     * en caso de no cumplirlas.
     */
    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada) {
        return false;
    }

    /*
     * Método usado para comprar una casilla determinada. Parámetros:
     * - Jugador que solicita la compra de la casilla.
     * - Banca del monopoly (es el dueño de las casillas no compradas aún).
     */
    public void comprarCasilla(Jugador solicitante, Jugador banca) { // Añade un avatar a la lista de avatares
        float fortuna_solicitante = solicitante.getFortuna();
        float fortuna_banca = banca.getFortuna();
        if (fortuna_solicitante >= this.valor) {
            solicitante.setFortuna(fortuna_solicitante - this.valor);
            banca.setFortuna(fortuna_banca + this.valor);
            System.out.println("El jugador" + solicitante.getNombre() + "ha comprado la casilla" + this.nombre + "Su fortuna actual es" + solicitante.getFortuna());
        } else { // TODO
            System.out.println("No tienes suficiente cash.");
        }
    }
    public void eliminarAvatarCasilla(String ID){ // Elimina un avatar de la lista de avatares dado su ID

        for(int i = 0; i < avatares.size(); i++){
            if(this.avatares.get(i).getId().equals(ID)){
                this.avatares.remove(i);
            }
        }
    }
    public void anhadirAvatarCasilla(Avatar avatar){
        this.avatares.add(avatar);
    }
    /*
     * Método para añadir valor a una casilla. Utilidad:
     * - Sumar valor a la casilla de parking.
     * - Sumar valor a las casillas de solar al no comprarlas tras cuatro vueltas de
     * todos los jugadores.
     * Este método toma como argumento la cantidad a añadir del valor de la casilla.
     */
    public void sumarValor(float suma) {
        this.valor += suma;
    }

    /*
     * Método para mostrar información sobre una casilla.
     * Devuelve una cadena con información específica de cada tipo de casilla.
     */
    public String infoCasilla() { // TODO --> eliminar aquellos atributos que no tengan sentido para cada tipo de
                                  // casilla
                                  // Si se pone suerte o ir a cárcel no hacer nada
        String info = new String();
        if (this.tipo.equals("Solar")) {
            info = """
                    {
                    Nombre: %s
                    Grupo: %s
                    Propietario: %s
                    Valor: %f
                    Alquiler: %f
                    Valor hotel: %f
                    Valor casa: %f
                    
                    Tipo: %s
                    Valor: %f
                    Posición en el tablero: %d
                    Dueño: %s
                    Color del grupo: %s
                    Impuestos a pagar: %f
                    Valor de hipoteca: %f
                    }""".formatted(nombre, tipo, posicion, duenho.getNombre(), grupo.getColor(), impuesto, hipoteca);
        }
        if (this.tipo.equals("Especial")) { //Aquí hay que poner el bote en el Parking, qué jugadores están en la cárcel, cuánto te dan en la salida
            info = """
                    {
                    Nombre: %s
                    Tipo: %s
                    Valor: %f
                    Posición en el tablero: %d
                    Dueño: %s
                    Color del grupo: %s
                    Impuestos a pagar: %f
                    Valor de hipoteca: %f
                    }""".formatted(nombre, tipo, posicion, duenho.getNombre(), grupo.getColor(), impuesto, hipoteca);
        }
        if (this.tipo.equals("Transporte")) { //Aquí hay que poner si tiene dueño. Poner valor y cuánto cuesta caer ahí
            info = """
                    {
                    Nombre: %s
                    Tipo: %s
                    Valor: %f
                    Posición en el tablero: %d
                    Dueño: %s
                    Color del grupo: %s
                    Impuestos a pagar: %f
                    Valor de hipoteca: %f
                    }""".formatted(nombre, tipo, posicion, duenho.getNombre(), grupo.getColor(), impuesto, hipoteca);
        }
        if (this.tipo.equals("Servicios")) { //Idem transportes
            info = """
                    {
                    Nombre: %s
                    Tipo: %s
                    Valor: %f
                    Posición en el tablero: %d
                    Dueño: %s
                    Color del grupo: %s
                    Impuestos a pagar: %f
                    Valor de hipoteca: %f
                    }""".formatted(nombre, tipo, posicion, duenho.getNombre(), grupo.getColor(), impuesto, hipoteca);
        }
        return info;
    }

    /*
     * Método para mostrar información de una casilla en venta.
     * Valor devuelto: texto con esa información.
     */
    public String casEnVenta() {
        return "";
    }

    public String printCasilla() {
        String data = new String();
        data += this.nombre;
        data += " ";
        for (Avatar av: this.avatares) data += av.getId();
        //data = data.substring(0, data.length()-this.avatares.size());
        
        /*
         * Esta funcion se usa para obtener los datos de la casilla al pintar
         * el tablero. Se necesita que sea del mismo tamano que CasillaWidth-1
         */
        return (this.grupo != null ? this.grupo.getColor() : Valor.WHITE) +
                Valor.BOLD + data + " ".repeat(casillaWidth - data.length() - 1) + Valor.RESET;
    }

    private void check(Boolean x) {
    if (!x) {
        System.exit(1);
    }
}
    /* check return if x is true, else print message to stderr and raise an error */
    private void check(Boolean x, String msg) {
        if (!x) {
            System.err.println(msg);
            System.exit(1);
        }
    }

    @Override
    public String toString()
  {
        return """
        | Nombre: %s
        | Tipo: %s
        | Valor: %s
        | Duenho: %s
        | Grupo: %s
        | Impuesto: %s
        """.formatted(nombre, tipo, valor, duenho, grupo, impuesto);
    }

}