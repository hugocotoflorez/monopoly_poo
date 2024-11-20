package monopoly.Casilla;

import java.util.ArrayList;
import java.util.Iterator;

import monopoly.Edificio.*;
import monopoly.Grupo;
import monopoly.Juego;
import monopoly.Valor;
import partida.*;
import partida.Avatar.*;

public abstract class Casilla {

    // Atributos:
    private String nombre; // Nombre de la casilla
    //NO EXISTE private String tipo; // Tipo de casilla (Solar, Especial, Transporte, Servicios, Comunidad).
    //PROPIEDAD private float valor; // Valor de esa casilla (en la mayoría será valor de compra, en la casilla
                         // parking se usará como el bote).
    private int posicion; // Posición que ocupa la casilla en el tablero (entero entre 0 y 40).
    //PROPIEDAD private Jugador duenho; // Dueño de la casilla (por defecto sería la banca).
    //SOLAR private Grupo grupo; // Grupo al que pertenece la casilla (si es solar).
    //PROPIEDAD private float impuesto; // Cantidad a pagar por caer en la casilla: el alquiler en
                            // solares/servicios/transportes o impuestos.
    //PROPIEDADprivate float hipoteca; // Valor otorgado por hipotecar una casilla
    private ArrayList<Avatar> avatares; // Avatares que están situados en la casilla.
    //SOLAR private ArrayList<Edificio> edificios;
    private int[] caidasEnCasilla = { 0, 0, 0, 0, 0, 0 }; // Cuenta el numero de veces que el jugador iesimo cayó en la
                                                          // casilla
    //PROPIEDAD public float recaudado = 0;

    //PROPIEDAD private boolean hipotecada;

    public static final int casillaWidth = 10;

    //Constructor general para todas las casillas
    public Casilla(String nombre, int posicion){
        this.nombre = nombre;
        this.posicion = posicion;
        this.avatares = new ArrayList<Avatar>();
    }

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
        this.hipoteca = valor / 2;
        this.duenho = duenho;
        this.impuesto = valor * 0.1f;
        this.hipotecada = false;
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

    public int[] getCaidasEnCasilla() {
        return this.caidasEnCasilla;
    }

    public boolean getHipotecada() {

        return this.hipotecada;
    }

    public float getRecaudado() {
        return this.recaudado;
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

    public void setHipotecada(boolean hipotecada) {

        this.hipotecada = hipotecada;
    }

    public void setRecaudado(float valor) {
        this.recaudado = valor;
    }

    public ArrayList<Edificio> getEdificios() {

        return this.edificios;

    }

    public String listar_edificios_grupo(String tipo) {

        String ret = new String();

        ret += "[ ";

        for (Edificio e : this.edificios)
            if (e.getTipo().equals(tipo))
                ret += e.getID() + ", ";

        ret += " ]\n";

        return ret;

    }

    // Método utilizado para añadir un avatar al array de avatares en casilla.
    public void anhadirAvatar(Avatar av) {
        this.avatares.add(av);
    }

    // Método utilizado para eliminar un avatar del array de avatares en casilla.
    public void eliminarAvatar(Avatar av) {
        this.avatares.remove(av);
    }

    // Método para incrementar en 1 el número de veces que se cayó en una casilla.
    public void actualizarCaidasEnCasilla(int jugador) {
        if (jugador < 6 && jugador >= 0)
            this.caidasEnCasilla[jugador] += 1;
    }

    // Método para que me diga el total de visitas de la casilla
    public int totalVisitas() {
        int ret = 0;
        for (int i = 0; i < 6; i++)
            ret += caidasEnCasilla[i];
        return ret;
    }

    public void hipotecar(Jugador actual) {

        if (this.duenho.equals(actual)
                && (this.tipo.equals("solar") || this.tipo.equals("serv") || this.tipo.equals("transporte"))
                && this.hipotecada == false && this.edificios.size() == 0) {

            this.duenho.sumarFortuna(hipoteca);
            Juego.consola.imprimir("Hipotecas " + this.getNombre() + " por " + this.hipoteca);
            this.hipotecada = true;
        } else if (this.edificios.size() != 0)
            Juego.consola.imprimir("Vende los edificios de esta propiedad antes de hipotecarla.");
        else
            Juego.consola.imprimir("No puedes hipotecar esa casilla.");

    }

    public void deshipotecar(Jugador actual) {
        if (this.duenho.equals(actual)
                && (this.tipo.equals("solar") || this.tipo.equals("serv") || this.tipo.equals("transporte"))
                && this.hipotecada == true) {
            if (this.duenho.getFortuna() >= hipoteca * 1.10f) {
                this.duenho.sumarFortuna(-hipoteca * 1.10f);
                this.hipotecada = false;
            } else
                Juego.consola.imprimir("No tienes suficiente fortuna. Necesitas " + hipoteca * 1.10f);
        } else
            Juego.consola.imprimir("No puedes deshipotecar esa casilla.");
    }

    /*
     * Método para evaluar qué hacer en una casilla concreta. Parámetros:
     * - Jugador cuyo avatar está en esa casilla.
     * - La banca (para ciertas comprobaciones).
     * - El valor de la tirada: para determinar impuesto a pagar en casillas de
     * servicios.KO
     * Valor devuelto: true en caso de ser solvente (es decir, de cumplir las
     * deudas), y false
     * en caso de no cumplirlas.
     */
    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada) {
        Casilla c = this;
        switch (c.getTipo()) {
            // supuestamente acabado
            case "solar":

                if (!c.esComprable(actual)) {
                    // se le resta el impuesto y se lo da al jugador que tiene
                    // la casilla
                    if (!this.hipotecada && !this.duenho.equals(actual)) {

                        if (this.grupo.esDuenhoGrupo(this.duenho)) {
                            Juego.consola.imprimir("El jugador " + this.duenho.getNombre()
                                    + " ya tiene todos los solares del grupo. Se va a duplicar su alquiler.");
                            this.grupo.actualizarAlquilerGrupo();
                        }
                        c.actualizarValorCasilla();

                        actual.sumarFortuna(-c.getImpuesto());// revisar
                        c.getDuenho().sumarFortuna(c.getImpuesto());

                        Juego.consola.imprimir("El jugador " + actual.getNombre() + " paga " +
                                c.getImpuesto() + " a " + c.getDuenho().getNombre());

                        actual.setPagoDeAlquileres(actual.getPagoDeAlquileres() + c.getImpuesto());
                        c.getDuenho().setCobroDeAlquileres(c.getDuenho().getCobroDeAlquileres() + c.impuesto);
                        c.recaudado += c.impuesto;
                    } else if (this.hipotecada)
                        Juego.consola.imprimir("El jugador " + this.getDuenho().getNombre()
                                + "no cobra alquiler porque la casilla está hipotecada.");

                    break;
                }
                /*
                 * La opcion de comprar se lleva a cabo
                 * desde el menu, esta funciona unicamente
                 * devuelve si se puede comprar o no
                 */
                Juego.consola.imprimir("Se puede comprar la casilla " + c.getNombre());
                return true; // se puede comprar

            case "especial":
                if (this.nombre.equals("Carcel")) {
                    // no pasa nada
                    Juego.consola.imprimir("Estás de visita en la cárcel.");
                }
                if (this.nombre.equals("Parking")) {
                    Juego.consola.imprimir("El jugador " + actual.getNombre() + " consigue el bote de la banca de "
                            + banca.getGastos());
                    actual.sumarFortuna(banca.getGastos());
                    actual.setPremiosInversionesOBote(actual.getPremiosInversionesOBote() + banca.getGastos());
                    banca.resetGastos();
                    this.setValor(0);
                }
                if (this.nombre.equals("IrCarcel")) {
                    Juego.consola.imprimir("Oh no! Has ido a la Cárcel!");
                }
                if (this.nombre.equals("Salida")) {
                    Juego.consola.imprimir("Has pasado por Salida! Cobra " + Valor.SUMA_VUELTA);
                }

                break;
            case "transporte":
                if (!c.esComprable(actual)) {
                    if (!this.hipotecada && !this.duenho.equals(actual)) {
                        float p = c.getDuenho().cuantostransportes() * 0.25f * Valor.IMPUESTOS_TRANSPORTES;
                        c.setImpuesto(p);

                        actual.sumarFortuna(-c.impuesto);
                        this.getDuenho().sumarFortuna(c.impuesto);
                        Juego.consola.imprimir("El jugador " + actual.getNombre() + " paga " +
                                c.impuesto + " a " + c.getDuenho().getNombre());

                        actual.setPagoDeAlquileres(actual.getPagoDeAlquileres() + c.getImpuesto());
                        c.getDuenho().setCobroDeAlquileres(c.getDuenho().getCobroDeAlquileres() + c.impuesto);
                        c.recaudado += c.impuesto;
                    } else
                        Juego.consola.imprimir("El jugador " + this.getDuenho()
                                + "no cobra alquiler porque la casilla está hipotecaada.");

                    break;
                }
                Juego.consola.imprimir("Se puede comprar la casilla " + c.getNombre());
                return true; // se puede comprar

            case "caja":
            case "suerte":
                break;

            case "serv":
                if (!c.esComprable(actual)) {
                    if (!this.hipotecada && !this.duenho.equals(actual)) {
                        int s = (c.getDuenho().cuantosservicios() >= 2) ? 10 : 4;
                        float p = Valor.IMPUESTO_SERVICIOS * s * tirada;
                        c.setImpuesto(p);

                        actual.sumarFortuna(-c.impuesto);
                        c.getDuenho().sumarFortuna(c.impuesto);
                        Juego.consola.imprimir("El jugador " + actual.getNombre() + " paga " +
                                c.impuesto + " a " + c.getDuenho().getNombre());

                        actual.setPagoDeAlquileres(actual.getPagoDeAlquileres() + c.getImpuesto());
                        c.getDuenho().setCobroDeAlquileres(c.getDuenho().getCobroDeAlquileres() + c.impuesto);
                        c.recaudado += c.impuesto;
                    } else
                        Juego.consola.imprimir("El jugador " + this.getDuenho()
                                + "no cobra alquiler porque la casilla está hipotecaada.");

                    break;
                }
                Juego.consola.imprimir("Se puede comprar la casilla " + c.getNombre());
                return true; // se puede comprar

            case "impuestos":
                Juego.consola.imprimir("Has caído en una casilla de impuestos. Se te va a cobrar " + c.impuesto);
                actual.sumarFortuna(-c.impuesto);
                banca.sumarGastos(c.impuesto);

                Juego.consola.imprimir("El bote de la banca ahora es " + banca.getGastos());

                actual.setPagoTasasEImpuestos(actual.getPagoTasasEImpuestos() + c.impuesto);
                break;

            default:
                System.err.println("Hugo no añadio el tipo %s a evaluarCasilla");

        }
        return actual.getFortuna() > 0;

    }

    /*
     * Método usado para comprar una casilla determinada. Parámetros:
     * - Jugador que solicita la compra de la casilla.
     * - Banca del monopoly (es el dueño de las casillas no compradas aún).
     */
    public void comprarCasilla(Jugador solicitante, Jugador banca) {
        /*
         * si llamas a esto no eres la pelota, por lo que el resto de argumentos
         * dan igual
         */
        comprarCasilla(solicitante, banca, false, null);
    }

    public void comprarCasilla(Jugador solicitante, Jugador banca, boolean movAv, ArrayList<Casilla> casVis) {

        float fortuna_solicitante = solicitante.getFortuna();

        if (fortuna_solicitante < this.valor) {
            Juego.consola.imprimir("No tienes suficiente fortuna.");

            /* Si es la pelota llama al metodo relacionado con la pelota */
        } else if (solicitante.getAvatar().getTipo().equals("Pelota") && movAv && !esComprable(casVis))
            Juego.consola.imprimir("No caiste en esta casilla.");

        else if ((!solicitante.getAvatar().getTipo().equals("Pelota") || !movAv) && !this.esComprable(solicitante))
            Juego.consola.imprimir("Esta casilla no se puede comprar.");

        else {
            solicitante.setFortuna(fortuna_solicitante - this.valor);
            solicitante.anhadirPropiedad(this);
            solicitante.setDineroInvertido(solicitante.getDineroInvertido() + this.valor);

            banca.eliminarPropiedad(this); /* No se porque estaba comentado */
            this.duenho = solicitante;

            Juego.consola.imprimir("El jugador " + solicitante.getNombre() +
                    " ha comprado la casilla " + this.nombre +
                    ". Su fortuna actual es " + solicitante.getFortuna());
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
            float alquilerbase = this.getGrupo().getValor();
            if (this.grupo.esDuenhoGrupo(this.duenho))
                alquilerbase *= 2;
            info = """
                    {
                    Nombre: %s
                    Grupo: %s
                    Propietario: %s
                    Valor: %f
                    Alquiler: %f
                    Edificios: %s
                    Valor hotel: %s
                    Valor casa: %s
                    Valor piscina: %s
                    Valor pista de deporte: %s
                    Alquiler una casa: %s
                    ALquiler dos casas: %s
                    Alquiler tres casas: %s
                    Alquiler cuatro casas: %s
                    Alquiler hotel: %s
                    Alquiler piscina: %s
                    Alquiler pista de deporte: %s
                    }""".formatted(nombre, this.grupo.getID(), duenho.getNombre(), this.getValor(),
                    impuesto, this.listar_nombres_edificios(), alquilerbase * 0.60f, alquilerbase * 0.60f,
                    alquilerbase * 0.40f,
                    alquilerbase * 1.25f, alquilerbase * 5f, alquilerbase * 15f, alquilerbase * 35f, alquilerbase * 50f,
                    alquilerbase * 70f, alquilerbase * 25f, alquilerbase * 25f);

        } else if (this.tipo.equals("especial")) { // Aquí hay que poner el bote en el Parking, qué jugadores están en
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
                        """.formatted(this.duenho.getGastos(), "[" + jugenparking + "]");
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
                    }""".formatted(nombre, duenho.getNombre(), valor, impuesto);
        } else if (this.tipo.equals("serv")) { // Idem transportes
            info = """
                    {
                    Nombre: %s
                    Tipo: servicios
                    Propietario: %s
                    Valor: %f
                    Impuesto: %f
                    }""".formatted(nombre, duenho.getNombre(), valor, impuesto);
        } else if (this.tipo.equals("caja")) {
            info = """
                    Nombre: %s
                    Tipo: comunidad
                    """.formatted(nombre);
        } else if (this.tipo.equals("suerte")) {
            info = """
                    Nombre: %s
                    Tipo: suerte
                    """.formatted(nombre);
        } else if (this.tipo.equals("impuestos")) {
            info = """
                    Nombre: %s
                    Tipo: impuestos
                    Impuestos: %f
                    """.formatted(nombre, impuesto);
        } else {
            Juego.consola.imprimir("Esa casilla no existe.");
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
        /*
         * En printAvatares
         * data += " ";
         * for (Avatar av : this.avatares)
         * data += av.getId();
         */
        // data = data.substring(0, data.length()-this.avatares.size());

        /*
         * Esta funcion se usa para obtener los datos de la casilla al pintar
         * el tablero. Se necesita que sea del mismo tamano que CasillaWidth-1
         */
        return (this.grupo != null ? this.grupo.getColor() : Valor.WHITE) +
                Valor.BOLD + data + " ".repeat(casillaWidth - data.length() - 1) + Valor.RESET;
    }

    public String printAvatares() {
        String data = new String();
        for (Avatar av : this.avatares)
            data += av.getId() + " ";
        return Valor.BOLD + data + " ".repeat(casillaWidth - data.length() - 1) + Valor.RESET;
    }

    /* check return if x is true, else print message to stderr and raise an error */
    private void check(Boolean x, String msg) {
        if (!x) {
            System.err.println(msg);
            System.exit(1);
        }
    }

    public boolean esComprable(Jugador jugador) {

        return (this.duenho.esBanca()
                && (this.tipo.equals("solar") || this.tipo.equals("transporte") || this.tipo.equals("serv"))
                && jugador.getAvatar().getCasilla().equals(this));
    }

    /*
     * Esta funcion permite comprobar si la casilla que se quiere comprar pertenece
     * al array de casillas por las que la pelota cayo
     */
    public boolean esComprable(ArrayList<Casilla> casillas) {

        return (this.duenho.esBanca()
                && (this.tipo.equals("solar") || this.tipo.equals("transporte") || this.tipo.equals("serv")) &&
                casillas.contains(this));
    }

    @Override
    public String toString() {
        return this.nombre;
    }

    public int obtenerNumeroCasas() {
        int ret = 0;
        for (Edificio e : this.edificios) {
            if (e.getTipo().equals("Casa"))
                ret += 1;
        }
        return ret;
    }

    public int obtenerNumeroHoteles() {
        int ret = 0;
        for (Edificio e : this.edificios) {
            if (e.getTipo().equals("Hotel"))
                ret += 1;
        }
        return ret;
    }

    public int obtenerNumeroPiscinas() {
        int ret = 0;
        for (Edificio e : this.edificios) {
            if (e.getTipo().equals("Piscina"))
                ret += 1;
        }
        return ret;
    }

    public int obtenerNumeroPistasDeporte() {
        int ret = 0;
        for (Edificio e : this.edificios) {
            if (e.getTipo().equals("Pista de deportes"))
                ret += 1;
        }
        return ret;
    }

    private boolean getCasillaEdificable() {

        if (this.grupo.getNumCasillas() == 2) {

            return !(this.grupo.obtenerNumCasasGrupo() == 2 && this.grupo.obtenerNumHotelesGrupo() == 2
                    && this.grupo.obtenerNumPiscinasGrupo() == 2 && this.grupo.obtenerNumPistasGrupo() == 2);

        }

        return !(this.grupo.obtenerNumCasasGrupo() == 3 && this.grupo.obtenerNumHotelesGrupo() == 3
                && this.grupo.obtenerNumPiscinasGrupo() == 3 && this.grupo.obtenerNumPistasGrupo() == 3);
    }

    private boolean esConstruible(Jugador duenhoGrupo) {

        int numero_veces_caidas = this.caidasEnCasilla[duenhoGrupo.getAvatar().getTurno()];
        return ((this.grupo.esDuenhoGrupo(duenhoGrupo) || numero_veces_caidas > 2)
                && this.getDuenho().equals(duenhoGrupo));

    }
    // Se cumplen los requisitos para construir?

    private boolean esCasaEdificable() {

        if (this.obtenerNumeroHoteles() < 2)
            return (this.obtenerNumeroCasas() < 4 && this.getCasillaEdificable());

        return (this.obtenerNumeroCasas() < 2 && this.getCasillaEdificable());
    }

    private boolean esHotelEdificable() {

        if (this.grupo.getNumCasillas() == 2)
            return (this.obtenerNumeroCasas() == 4 && this.getCasillaEdificable() && this.obtenerNumeroHoteles() < 2);

        return (this.obtenerNumeroCasas() == 4 && this.getCasillaEdificable() && this.obtenerNumeroHoteles() < 3);

    }

    private boolean esPiscinaEdificable() {

        if (this.grupo.getNumCasillas() == 2)
            return (this.obtenerNumeroCasas() >= 2 && this.obtenerNumeroHoteles() >= 1 && this.getCasillaEdificable()
                    && this.obtenerNumeroPiscinas() < 2);

        return (this.obtenerNumeroCasas() >= 2 && this.obtenerNumeroHoteles() >= 1 && this.getCasillaEdificable()
                && this.obtenerNumeroPiscinas() < 3);
    }

    private boolean esPistaEdificable() {

        if (this.grupo.getNumCasillas() == 2)
            return (this.obtenerNumeroHoteles() >= 2 && this.getCasillaEdificable()
                    && this.obtenerNumeroPistasDeporte() < 2);

        return (this.obtenerNumeroHoteles() >= 2 && this.getCasillaEdificable()
                && this.obtenerNumeroPistasDeporte() < 3);
    }

    public void listar_edificios_casilla() {

        for (Edificio e : this.edificios) {

            Juego.consola.imprimir(e.toString());

        }

    }

    public String listar_nombres_edificios() {
        String ret = new String();
        for (Edificio e : this.edificios) {
            ret += e.getID();
            ret += ", ";
        }
        return ret;
    }

    // Para casillas edificadas, recalcula el impuesto cada vez que se conestruye un
    // edificio
    public void actualizarValorCasilla() {

        if (this.edificios.size() == 0) {
            return;
        }

        int numeroCasas = this.obtenerNumeroCasas();
        int numeroHoteles = this.obtenerNumeroHoteles();
        int numeroPiscinas = this.obtenerNumeroPiscinas();
        int numeroPistas = this.obtenerNumeroPistasDeporte();

        float nuevoImpuestoCasas = 0f;
        float nuevoImpuestoHoteles = 0f;
        float nuevoImpuestoPiscinas = 0f;
        float nuevoImpuestoPistas = 0f;

        float alquilerinicial = this.grupo.getValor();

        if (this.grupo.esDuenhoGrupo(this.getDuenho()))
            alquilerinicial *= 2;

        if (numeroCasas == 1) {
            nuevoImpuestoCasas = 5 * alquilerinicial;
        }

        if (numeroCasas == 2) {
            nuevoImpuestoCasas = 15 * alquilerinicial;

        }
        if (numeroCasas == 3) {
            nuevoImpuestoCasas = 35 * alquilerinicial;
        }

        if (numeroCasas == 4) {
            nuevoImpuestoCasas = 50 * alquilerinicial;
        }

        if (numeroHoteles >= 1) {
            nuevoImpuestoHoteles = 70 * alquilerinicial * numeroHoteles;
        }

        if (numeroPiscinas >= 1) {
            nuevoImpuestoPiscinas = 25 * alquilerinicial * numeroPiscinas;
        }

        if (numeroPistas >= 1) {
            nuevoImpuestoPistas = 25 * alquilerinicial * numeroPistas;
        }

        this.setImpuesto(alquilerinicial + nuevoImpuestoCasas + nuevoImpuestoHoteles + nuevoImpuestoPiscinas
                + nuevoImpuestoPistas);
    }

    public void edificar(String tipo, Jugador duenhoGrupo) {
        // Aumentar el alquiler de la casilla dependiendo de la
        // edificación
        if (!this.tipo.equals("solar")) {

            Juego.consola.imprimir("Solo se puede edificar en Solares!");
            return;
        }

        if (esConstruible(duenhoGrupo)) {

            switch (tipo) {
                case "Casa": // Como máximo puede haver 4 casas

                    if (this.esCasaEdificable()) {

                        Edificio Casa = new Casa(this);
                        if (duenhoGrupo.getFortuna() >= Casa.getPrecio()) {
                            this.edificios.add(Casa);

                            duenhoGrupo.sumarFortuna(-Casa.getPrecio());

                            Valor.NumeroCasasConstruidas++;

                            duenhoGrupo.setDineroInvertido(duenhoGrupo.getDineroInvertido() + Casa.getPrecio());

                            Juego.consola
                                    .imprimir("Se ha edificado una casa en " + this.getNombre() + ". La fortuna de "
                                            + duenhoGrupo.getNombre() + " se reduce en " + Casa.getPrecio());
                        } else
                            Juego.consola.imprimir("No tienes suficiente fortuna, necesitas " + Casa.getPrecio());
                        return;

                    }

                    Juego.consola.imprimir("No puedes edificar una Casa en estos momentos.");
                    break;

                case "Hotel": // Solo se puede construir un hotel si hay 4 casas
                              // Además las casas serán eliminadas

                    int removed = 0;
                    if (this.esHotelEdificable()) {
                        Edificio Hotel = new Hotel(this);
                        if (duenhoGrupo.getFortuna() >= Hotel.getPrecio()) {
                            do
                                for (Edificio e : this.edificios) {
                                    if (e.getTipo().equals("Casa")) {
                                        this.edificios.remove(e);
                                        removed++;
                                        break;
                                    }

                                }
                            while (removed < 4);

                            this.edificios.add(Hotel);
                            duenhoGrupo.sumarFortuna(-Hotel.getPrecio());

                            Valor.NumeroHotelesConstruidos++;

                            duenhoGrupo.setDineroInvertido(duenhoGrupo.getDineroInvertido() + Hotel.getPrecio());

                            Juego.consola.imprimir("Se ha edificado un hotel en " + this.getNombre() + "La fortuna de "
                                    + duenhoGrupo.getNombre() + " se reduce en " + Hotel.getPrecio());
                        } else
                            Juego.consola.imprimir("No tienes suficiente fortuna, necesitas " + Hotel.getPrecio());
                        return;
                    }
                    Juego.consola.imprimir("No puedes edificar un Hotel en estos momentos.");
                    break;

                case "Piscina": // Numero de hoteles >= 1
                                // Numero de casas >= 2
                    if (this.esPiscinaEdificable()) {

                        Edificio Piscina = new Piscina(this);
                        if (duenhoGrupo.getFortuna() >= Piscina.getPrecio()) {

                            this.edificios.add(Piscina);
                            duenhoGrupo.sumarFortuna(-Piscina.getPrecio());

                            Valor.NumeroPiscinasConstruidas++;

                            duenhoGrupo.setDineroInvertido(duenhoGrupo.getDineroInvertido() + Piscina.getPrecio());

                            Juego.consola
                                    .imprimir("Se ha edificado una piscina en " + this.getNombre() + "La fortuna de "
                                            + duenhoGrupo.getNombre() + " se reduce en " + Piscina.getPrecio());
                        } else
                            Juego.consola.imprimir("No tienes suficiente fortuna, necesitas " + Piscina.getPrecio());
                        return;

                    }
                    Juego.consola.imprimir("No puedes edificar una Piscina en estos momentos.");
                    break;

                case "Pista": // Numero de hoteles >= 2
                    if (this.esPistaEdificable()) {

                        Edificio Pista = new PistaDeporte(this);
                        if (duenhoGrupo.getFortuna() >= Pista.getPrecio()) {
                            this.edificios.add(Pista);
                            duenhoGrupo.sumarFortuna(-Pista.getPrecio());

                            Valor.NumeroPistasConstruidos++;

                            duenhoGrupo.setDineroInvertido(duenhoGrupo.getDineroInvertido() + Pista.getPrecio());

                            Juego.consola.imprimir(
                                    "Se ha edificado una Pista de Deportes en " + this.getNombre() + "La fortuna de "
                                            + duenhoGrupo.getNombre() + " se reduce en " + Pista.getPrecio());
                        } else
                            Juego.consola.imprimir("No tienes suficiente fortuna, necesitas " + Pista.getPrecio());
                        return;
                    }

                    Juego.consola.imprimir("No puedes edificar una Pista de deportes en estos momentos.");
                    break;

                default:

                    Juego.consola.imprimir("Tipo incorrecto!");
                    break;
            }

        }

    }

    public void desEdificar(String tipoEdificio, Jugador duenhoEdificio, String n) {

        switch (tipoEdificio) {
            case "Casa":
                if (Integer.parseInt(n) > this.obtenerNumeroCasas()) {

                    Juego.consola
                            .imprimir("No tienes suficientes casas construidas! Total: " + this.obtenerNumeroCasas());
                    return;
                }
                break;

            case "Hotel":
                if (Integer.parseInt(n) > this.obtenerNumeroHoteles()) {
                    Juego.consola.imprimir(
                            "No tienes suficientes Hoteles construidao! Total: " + this.obtenerNumeroHoteles());
                    return;
                }
                break;

            case "Piscina":
                if (Integer.parseInt(n) > this.obtenerNumeroPiscinas()) {
                    Juego.consola.imprimir(
                            "No tienes suficientes Piscinas construidas! Total: " + this.obtenerNumeroPiscinas());
                    return;
                }
                break;

            case "Pista":
                tipoEdificio = "Pista de deportes";
                if (Integer.parseInt(n) > this.obtenerNumeroPistasDeporte()) {
                    Juego.consola.imprimir(
                            "No tienes suficientes Pistas construidas! Total: " + this.obtenerNumeroPistasDeporte());
                    return;
                }
                break;

            default:
                Juego.consola.imprimir("Tipo incorrecto! (Es con mayuscula)");
                return;
        }

        float fortuna_anhadida = 0f;

        Iterator<Edificio> it = this.edificios.iterator();
        int numeroeliminados = 0;

        while (it.hasNext() && numeroeliminados < Integer.parseInt(n)) {

            Edificio e = it.next();
            if (e.getTipo().equals(tipoEdificio)) {

                fortuna_anhadida += (e.getPrecio() / 2f);
                it.remove();
                numeroeliminados++;

            }

        }

        duenhoEdificio.sumarFortuna(fortuna_anhadida);

        Juego.consola.imprimir(
                duenhoEdificio.getNombre() + " ha vendido " + n + " " + tipoEdificio + " en " + this.getNombre()
                        + ", recibiendo " + fortuna_anhadida + " Su fortuna actual es " + duenhoEdificio.getFortuna());
    }

    public void desEdificar() {

        this.edificios.clear();
    }
}