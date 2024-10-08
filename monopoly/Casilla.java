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
    private ArrayList<Avatar> avatares = new ArrayList<Avatar>(); // Avatares que están situados en la casilla.

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
        this.tipo = "impuestos";
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

    // GETTERS
    public String getNombre() {
        return this.nombre;
    }

    public float getValor() {
        return this.valor;
    }

    public String getTipo() {
        return this.tipo;
    }

    public int getPosicion() {

        return this.posicion;
    }

    public Grupo getGrupo() {
        return this.grupo;
    }

    public Jugador getDuenho() {
        return this.duenho;
    }

    public float getImpuesto() {
        return this.impuesto;
    }

    public float getHipoteca() {
        return this.hipoteca;
    }

    // SETTERS
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setPosicion(int pos) {
        this.posicion = pos;
    }

    public void setDuenho(Jugador duenho) {
        this.duenho = duenho;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public void setImpuesto(float impuesto) {
        this.impuesto = impuesto;
    }

    public void setHipoteca(float hipoteca) {
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
        Casilla c = this;// sorry
        switch (c.getTipo()) {
            // supuestamente acabado
            case "solar":
                if ((!c.getDuenho().esBanca()) && (!c.getDuenho().equals(actual))) {
                    // se le resta el impuesto y se lo da al jugador que tiene
                    // la casilla
                    actual.sumarFortuna(-c.getImpuesto());// revisar
                    c.getDuenho().sumarFortuna(c.getImpuesto());
                    System.out.println("El jugador " + actual.getNombre() + " paga " +
                            c.getImpuesto() + " a " + c.getDuenho().getNombre());
                    break;
                }
                /*
                 * La opcion de comprar se lleva a cabo
                 * desde el menu, esta funciona unicamente
                 * devuelve si se puede comprar o no
                 */
                System.out.println("Se puede comprar la casilla " + c.getNombre());
                return true; // se puede comprar

            case "especial":
                if (this.nombre.equals("Carcel")) {
                    // no pasa nada
                    System.out.println("Estás de visita en la cárcel.");
                }
                if (this.nombre.equals("Parking")) {
                    System.out.println("El jugador " + actual.getNombre() + " consigue el bote de la banca de " + banca.getGastos());
                    banca.sumarFortuna(-banca.getGastos());
                    this.getDuenho().sumarFortuna(banca.getGastos());
                    banca.resetGastos();
                }
                if (this.nombre.equals("IrCarcel")) {
                    /* No se como pasarle el array de casillas desde aqui */
                    actual.encarcelar(null); // TODO
                }
                if (this.nombre.equals("Salida")) {
                    // Desde aqui no se hace nada
                }

                break;
            case "transporte":
                if (c.getDuenho() != banca && c.getDuenho() != actual) {
                    float p = 0.25f * c.getDuenho().transportes() * c.impuesto;
                    actual.sumarFortuna(-p);
                    this.getDuenho().sumarFortuna(p);
                    System.out.println("El jugador " + actual.getNombre() + " paga " +
                            p + " a " + c.getDuenho());
                    // pagar
                    break;
                }
                System.out.println("Se puede comprar la casilla " + c.getNombre());
                return true; // se puede comprar

            case "caja":
            case "suerte":
                System.out.println("No implementado");
                // hacer lo que haya que hacer (no para esta entrega)
                break;

            case "serv":
                if (c.getDuenho() != banca && c.getDuenho() != actual) {
                    // se le resta el impuesto y se lo da al jugador que tiene
                    // la casilla
                    int s = (c.getDuenho().servicios() >= 2) ? 10 : 4;
                    float precio = c.getImpuesto() * s * actual.getTirada(); // REVISAR
                    actual.sumarFortuna(-precio);// revisar
                    c.getDuenho().sumarFortuna(precio);
                    System.out.println("El jugador " + actual.getNombre() + " paga " +
                            precio + " a " + c.getDuenho());
                    break;
                }
                System.out.println("Se puede comprar la casilla " + c.getNombre());
                return true;

            case "impuesto":
                System.out.println("Has caído en una casilla de impuestos. Se te va a cobrar "+c.impuesto);
                actual.sumarFortuna(-c.impuesto);
                banca.sumarGastos(c.impuesto);
                System.out.println("El bote de la banca ahora es "+banca.getGastos());
                break;

            default:
                System.err.println("Hugo no añadio el tipo %s a evaluarCasilla");

        }
        return false;

    }

    /*
     * Método usado para comprar una casilla determinada. Parámetros:
     * - Jugador que solicita la compra de la casilla.
     * - Banca del monopoly (es el dueño de las casillas no compradas aún).
     */
    public void comprarCasilla(Jugador solicitante, Jugador banca) {

        float fortuna_solicitante = solicitante.getFortuna();
        if (fortuna_solicitante >= this.valor && this.esComprable()) {
            solicitante.setFortuna(fortuna_solicitante - this.valor);
            banca.sumarGastos(this.valor);
            banca.eliminarPropiedad(this);
            solicitante.anhadirPropiedad(this);
            this.duenho = solicitante;
            System.out.println("El jugador " + solicitante.getNombre() + " ha comprado la casilla " + this.nombre
                    + ". Su fortuna actual es " + solicitante.getFortuna());
            System.out.println("El bote de la banca es ahora "+banca.getGastos());
        } else if (fortuna_solicitante < this.valor) { // TODO
            System.out.println("No tienes suficiente fortuna.");
        } else if (!this.esComprable()) {
            System.out.println("Esta casilla no se puede comprar.");
        }
    }

    public void eliminarAvatarCasilla(String ID) { // Elimina un avatar de la lista de avatares dado su ID

        for (int i = 0; i < avatares.size(); i++) {
            if (this.avatares.get(i).getId().equals(ID)) {
                this.avatares.remove(i);
            }
        }
    }

    public void anhadirAvatarCasilla(Avatar avatar) {
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
    public String infoCasilla() {
        String info = new String();
        if (this.tipo.equals("solar")) {
            info = """
                    {
                    Nombre: %s
                    Propietario: %s
                    Valor: %f
                    Alquiler: %f
                    Valor hotel: %s
                    Valor casa: %s
                    Valor piscina: %s
                    Valor pista de deporte: %s
                    Alquiler una casa: %s
                    ALquiler dos casas: %s
                    Alquiler tres casas: %s
                    Alquiler cuatro casas: %s
                    Alquiler piscina: %s
                    Alquiler pista de deporte: %s
                    }""".formatted(this.grupo.getColor() + nombre + Valor.RESET, duenho.getNombre(), this.getValor(),
                    impuesto, "No implementado.", "No implementado.", "No implementado.", "No implementado.",
                    "No implementado.", "No implementado.", "No implementado.", "No implementado.", "No implementado.",
                    "No implementado.");
        } 
        else if (this.tipo.equals("especial")) { // Aquí hay que poner el bote en el Parking, qué jugadores están en
                                                   // la cárcel, cuánto te dan en la salida
            if (this.nombre.equals("Salida"))
                info = "{Casilla de salida.}";
            else if (this.nombre.equals("IrCarcel"))
                info = "{Casilla de ir a la cárcel.}";
            else if (this.nombre.equals("Parking")) {
                String jugenparking = new String();
                for (Avatar av : avatares)
                    jugenparking += av.getJugador().getNombre() + " ";
                info = """
                        Bote: %f
                        Jugadores: %s
                        """.formatted(valor, "[" + jugenparking + "]");
            } else if (this.nombre.equals("Carcel")) {
                String jugencarcel = new String();
                for (Avatar av : avatares) {
                    if (av.getJugador().getEnCarcel()) {
                        jugencarcel += av.getJugador().getNombre() + " lleva " + av.getJugador().getTurnosCarcel()
                                + " turnos aquí";
                    }
                }
                info = """
                        Salir: %.2f
                        Jugadores: %s
                        """.formatted(Valor.PAGO_SALIR_CARCEL, "[" + jugencarcel + "]");
            }
        } else if (this.tipo.equals("transporte")) { // Aquí hay que poner si tiene dueño. Poner valor y cuánto cuesta
                                                     // caer ahí
            info = """
                    {
                    Nombre: %s
                    Tipo: transporte
                    Propietario: %s
                    Valor: %f
                    Impuesto: %f
                    }""".formatted(nombre, duenho.getNombre(), valor, impuesto); // TODO
        } else if (this.tipo.equals("serv")) { // Idem transportes
            info = """
                    {
                    Nombre: %s
                    Tipo: servicios
                    Propietario: %s
                    Valor: %f
                    Impuesto: %f
                    }""".formatted(nombre, duenho.getNombre(), valor, impuesto); // TODO
        } else if (this.tipo.equals("caja")) {
            info = """
                    Nombre: %s
                    Tipo: comunidad
                    """.formatted(nombre);
        }else if (this.tipo.equals("suerte")){
                info = """
                    Nombre: %s
                    Tipo: suerte
                    """.formatted(nombre);
        }
        else if  (this.tipo.equals("impuestos")){
            info = """
                    Nombre: %s
                    Tipo: impuestos
                    Impuestos: %f
                    """.formatted(nombre,impuesto);
        }
        else {
            System.out.println("Esa casilla no existe.");
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
        for (Avatar av : this.avatares)
            data += av.getId();
        // data = data.substring(0, data.length()-this.avatares.size());

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
    private boolean encontrarAvatarCasilla(){
        for (Avatar a: avatares){
            if(a.getCasilla().equals(this))
                return true;
        }
        return false;
    }
    public boolean esComprable() {

        return (this.duenho.esBanca()
                && (this.tipo.equals("solar") || this.tipo.equals("transporte") || this.tipo.equals("serv")) && this.encontrarAvatarCasilla());
    }

    @Override
    public String toString() {
        return this.nombre;
    }

}