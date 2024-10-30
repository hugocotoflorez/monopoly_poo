package monopoly;

import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.transform.stax.StAXResult;

import partida.*;

public class Menu {

    // Atributos
    private ArrayList<Jugador> jugadores = new ArrayList<Jugador>(); // Jugadores de la partida.
    private ArrayList<Avatar> avatares = new ArrayList<Avatar>(); // Avatares en la partida.
    private int turno; // Índice correspondiente a la posición en el arrayList del jugador (y el
                       // avatar) que tienen el turno
    private int lanzamientos = 0; // Variable para contar el número de lanzamientos de un jugador en un turno.
    private Tablero tablero; // Tablero en el que se juega.
    private Dado dado1; // Dos dados para lanzar y avanzar casillas.
    private Dado dado2;
    private Jugador banca; // El jugador banca.
    private boolean tirado; // Booleano para comprobar si el jugador que tiene el turno ha tirado o no.
    private boolean solvente; // Booleano para comprobar si el jugador que tiene el turno es solvente, es
                              // decir, si ha pagado sus deudas.
    private boolean partida_empezada = false;
    private boolean partida_finalizada = false;
    private boolean movimientoAvanzado = false;
    /*
     * Poner un scanner nuevo para cada funcion en la que se necesita daba error
     * porque segun lo que la intuicion me dice no se pueden abrir dos scanners
     * para System.in, por lo que si abrias y cerrabas uno en una funcion que se
     * ejecutase en el medio de un ciclo de vida de un scanner en otra funcion esta
     * fallaria
     */
    private Scanner scanner = new Scanner(System.in);

    private boolean debeActualizar = false;

    private ArrayList<Carta> suerte;
    private ArrayList<Carta> comunidad;

    public Menu() {
        crear_cartas_comunidad();
        crear_cartas_suerte();
        iniciarPartida();
    }

    private void crear_cartas_suerte() {
        this.suerte = new ArrayList<Carta>();
        suerte.add(new Carta(Carta.desc1, "suerte", 1));
        suerte.add(new Carta(Carta.desc2, "suerte", 2));
        suerte.add(new Carta(Carta.desc3, "suerte", 3));
        suerte.add(new Carta(Carta.desc4, "suerte", 4));
        suerte.add(new Carta(Carta.desc5, "suerte", 5));
        suerte.add(new Carta(Carta.desc6, "suerte", 6));
    }

    private void crear_cartas_comunidad() {
        this.comunidad = new ArrayList<Carta>();
        comunidad.add(new Carta(Carta.desc7, "comunidad", 1));
        comunidad.add(new Carta(Carta.desc8, "comunidad", 2));
        comunidad.add(new Carta(Carta.desc9, "comunidad", 3));
        comunidad.add(new Carta(Carta.desc10, "comunidad", 4));
        comunidad.add(new Carta(Carta.desc11, "comunidad", 5));
        comunidad.add(new Carta(Carta.desc12, "comunidad", 6));
    }

    /*
     * Cuando se caiga en una casilla en la cual hay que lanzar cartas, se tiene que
     * llamar
     * a esta funcion.
     */
    private void elegir_carta(ArrayList<Carta> baraja) {
        int n;

        do {
            System.out.println("Elige una carta del 1 al 6: ");
            n = Integer.parseInt(this.scanner.next());

        } while (n < 1 || n > 6);

        Carta.barajar(baraja);
        Carta c = Carta.obtenerCarta(baraja, n);
        c.mostrarDescipcion();
        c.realizarAccion(avatares.get(turno), jugadores, tablero.getPosiciones());
    }

    private void crear_jugador(String nombreJugador, String tipoAvatar) {

        if (!Avatar.esTipo(tipoAvatar)) {
            System.out.println("Tipo invalido: " + tipoAvatar);
            return;
        }
        Jugador jugador = new Jugador(nombreJugador, tipoAvatar, this.tablero.posicion_salida(), this.avatares);
        this.jugadores.add(jugador);
        this.tablero.posicion_salida().anhadirAvatar(jugador.getAvatar());
    }

    // Método para inciar una partida: crea los jugadores y avatares.
    private void iniciarPartida() {
        this.turno = 1;
        this.dado1 = new Dado();
        this.dado2 = new Dado();
        Jugador banca = new Jugador();
        this.avatares.add(null); // avatar banca
        this.jugadores.add(banca);
        this.tablero = new Tablero(banca);
        analizarComando("opciones");
        while (!partida_finalizada) {
            System.out.print("\n[>]: ");
            analizarComando(this.scanner.nextLine());
        }
        this.scanner.close();
        acabarPartida();
    }

    /*
     * Método que interpreta el comando introducido y toma la accion
     * correspondiente.
     * Parámetro: cadena de caracteres (el comando).
     */
    private void analizarComando(String comando) {

        String[] com = comando.split(" ");
        switch (com[0]) {
            case "opciones":
            case "?":
                System.out.println(Valor.BOLD + "Opciones" + Valor.RESET);
                System.out.println("crear jugador <nombre> <tipo_avatar>");
                System.out.println("jugador - jugador con el turno");
                System.out.println("listar enventa");
                System.out.println("listar jugadores");
                System.out.println("listar avatares");
                System.out.println("lanzar dados");
                System.out.println("acabar - acaba el turno");
                System.out.println("salir (carcel)");
                System.out.println("describir jugador  <nombre>");
                System.out.println("describir avatar <letra");
                System.out.println("comprar <casilla>");
                System.out.println("bancarrota - acaba la partida para ese jugador");
                System.out.println("ver - muestra el tablero");
                System.out.println("clear - limpia la pantalla");
                System.out.println("estadisticas <Jugador>");
                break;

            case "default":
                analizarComando("crear jugador Jugador1 Coche");
                analizarComando("crear jugador Jugador2 Esfinge");
                break;

            case "crear":
                if (partida_empezada) {
                    System.out.println("La partida ya esta iniciada!");
                    break;
                } else if (com.length == 4 && com[1].equals("jugador")) {
                    if (jugadores.size() <= 6) {
                        crear_jugador(com[2], com[3]);
                        System.out.println(this.tablero);
                    } else
                        System.out.println("Ya se ha alcanzado el número máximo de jugadores.");

                } else
                    System.out.println("No se pudo crear el jugador");
                break;

            case "jugador":
                descJugador();
                break;

            case "listar":
                if (com.length == 2) {
                    if (com[1].equals("enventa"))
                        listarVenta();
                    else if (com[1].equals("jugadores"))
                        listarJugadores();
                    else if (com[1].equals("avatares"))
                        listarAvatares();
                }
                break;

            case "lanzar":
                if (com.length == 2 && com[1].equals("dados")) {
                    if (jugadores.size() >= 3) { // iniciar la partida
                        partida_empezada = true;
                        lanzarDados();
                        System.out.println(this.tablero);
                    } else
                        System.out.println("No tienes suficientes jugadores creados! (Mínimo 2).");
                }
                break;

            case "l":
                if (com.length == 3) {
                    partida_empezada = true;
                    int valor = Integer.parseInt(com[1]);
                    int valor2 = Integer.parseInt(com[2]);
                    lanzarDados(valor, valor2);
                }
                break;

            case "a":
            case "acabar":
                acabarTurno();
                break;

            case "salir":
                salirCarcel();
                break;
            case "bancarrota":
                bancarrota();
                break;

            case "describir":
                if (com.length >= 2) {
                    switch (com[1]) {
                        case "jugador": // describir jugador
                            descJugador(com);
                            break;
                        case "avatar": // describir avatar
                            if (com.length == 3)
                                descAvatar(com[2]);
                            break;
                        default: // describir casilla
                            descCasilla(com[1]);
                            break;
                    }
                    break;
                }

            case "comprar":
                if (com.length == 2) {
                    comprar(com[1]);
                }
                break;

            case "estadisticas":
                if (com.length == 2) {
                    mostrarestadisticasjugador(com[1]);
                } else if (com.length == 1) {
                    mostrarestadisticaspartida();
                } else
                    System.out.println("Opcion incorrecta. [? para ver las opciones]");
                break;

            case "ver":
                System.out.println(this.tablero);
                break;

            case "q":
            case "SALIR":
                this.partida_finalizada = true;
                break;

            case "c":
            case "clear":
                System.out.print("\033[H\033[2J");
                break;

            default:
                System.out.println("Opcion incorrecta. [? para ver las opciones]");
                break;
        }
    }

    /*
     * Método que realiza las acciones asociadas al comando 'describir jugador'.
     * Parámetro: comando introducido
     */
    private void descJugador(String[] partes) {

        for (Jugador J : this.jugadores) {
            if (J.getNombre().equals(partes[2])) {
                System.out.println(J);
                return;
            }
        }
        System.out.println("No se ha encontrado este jugador.\n");
    }

    // Sobrecarga: si no se pasa argumentos describe el jugador que tiene el turno
    // actual
    private void descJugador() {
        if ((jugadores.size() != 1) && !jugadores.get(turno).esBanca()) {
            System.out.println("""
                    | Nombre: %s
                    | Avatar: %s
                    """.formatted(jugadores.get(turno).getNombre(), jugadores.get(turno).getAvatar().getId()));
            return;
        }
        System.out.println("No se ha encontrado este jugador.\n");
    }

    /*
     * Método que realiza las acciones asociadas al comando 'describir avatar'.
     * Parámetro: id del avatar a describir.
     */
    private void descAvatar(String ID) {
        for (int i = 1; i < avatares.size(); i++) {
            if (avatares.get(i).getId().equals(ID)) {
                System.out.println(avatares.get(i).getInfo());
                return;
            }
        }
        System.out.println("No se ha encontrado ese avatar.");
    }

    /*
     * Método que realiza las acciones asociadas al comando 'describir
     * nombre_casilla'.
     * Parámetros: nombre de la casilla a describir.
     */
    private void descCasilla(String nombre) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                if (tablero.getPosiciones().get(i).get(j).getNombre().equals(nombre)) {
                    System.out.println(tablero.getPosiciones().get(i).get(j).infoCasilla());
                    return;
                }
            }
        }
        System.out.println("La casilla " + nombre + " no existe.");
    }

    // Método que ejecuta todas las acciones relacionadas con el comando 'lanzar
    // dados'.
    private boolean dadosDobles(int valor1, int valor2) {
        return (valor1 == valor2);
    }

    private void lanzarDados() {

        this.dado1.hacerTirada();
        this.dado2.hacerTirada();
        lanzarDados(dado1.getValor(), dado2.getValor());
    }

    /*
     * private Jugador jugadorMenosVueltas(ArrayList<Jugador> jugadores) {
     * Jugador min_j;
     * min_j = jugadores.get(0);
     * for (Jugador a : jugadores)
     * if (a.getVueltas() < min_j.getVueltas())
     * min_j = a;
     * return min_j;
     * }
     */

    // sobrecarga de lanzar dados en la cual elegimos qué valor sacan los dados
    private void lanzarDados(int valor1, int valor2) {
        if (this.lanzamientos < 2 && !this.jugadores.get(turno).getEnCarcel() && !this.tirado) {

            int casillaantes = avatares.get(turno).getCasilla().getPosicion();
            this.tirado = true;
            this.lanzamientos += 1;
            System.out.println("Tirada: " + valor1 + ", " + valor2);

            mover(valor1, valor2);

            // Comprueba si pasa por salida
            pasarPorSalida(casillaantes);

            if (dadosDobles(valor1, valor2)) {
                this.tirado = false;
                System.out.println("Has sacado dobles! Puedes volver a lanzar los dados. ");
            }

            evaluarAccion(valor1 + valor2);

        } else if (this.lanzamientos >= 2 && !this.tirado) {
            this.jugadores.get(turno).encarcelar(this.tablero.getPosiciones());
            System.out.println("Has sacado tres dobles seguidos! Vas a la carcel sin pasar por salida.");
            this.tirado = true;
        } else if (this.jugadores.get(turno).getEnCarcel()) {
            System.out.println("Oh no! Estás en la cárcel!");
        }
    }

    private void mover(int valor1, int valor2) {
        /* Movimiento default */
        if (!movimientoAvanzado) {
            int desplazamiento = valor1 + valor2;
            System.out
                    .print("El avatar " + this.avatares.get(turno).getId() + " avanza " + desplazamiento + " desde "
                            + this.avatares.get(turno).getCasilla().getNombre());
            this.avatares.get(turno).moverAvatar(this.tablero.getPosiciones(), desplazamiento);
            System.out.println(" hasta" + avatares.get(turno).getCasilla().getNombre());
            return;
        }
        switch(this.avatares.get(turno).getTipo())
        {
            case "Coche":
            break;
            case "Esfinge":
            break;
            case "Somprero":
            break;
            case "Pelota":
            break;
        }
        movimientoAvanzado = false;

    }

    private void pasarPorSalida(int casillaantes) {
        int casillanueva = avatares.get(turno).getCasilla().getPosicion();
        if ((casillaantes > casillanueva)) {

            // !!!!!! si se modifica algo de esto hay que modificarlo tambien en Carta
            System.out.println("¡Has pasado por la Salida! Ganaste " + Valor.SUMA_VUELTA);
            jugadores.get(turno).sumarFortuna(Valor.SUMA_VUELTA);
            jugadores.get(turno).setVueltas(jugadores.get(turno).getVueltas() + 1);
            jugadores.get(turno).setPasarPorCasillaDeSalida(
                    jugadores.get(turno).getPasarPorCasillaDeSalida() + Valor.SUMA_VUELTA);
            System.out.println("Llevas " + jugadores.get(turno).getVueltas() + " vueltas.");

            int vueltasmin = this.jugadores.get(turno).getVueltas();

            for (Jugador j : this.jugadores) {
                if (!j.esBanca() && j.getVueltas() < vueltasmin) {
                    vueltasmin = j.getVueltas();
                }
            }

            if ((this.jugadores.get(turno).getVueltas() == vueltasmin) && (vueltasmin % 4 == 0)) {
                System.out.println(
                        "Todos los jugadores han dado un múltiplo de 4 vueltas, se va a incrementar el precio de los solares en un 10%.");
                this.tablero.actualizarValorSolares();
            }
        }
    }

    private void evaluarAccion(int desplazamiento) {
        /*
         * En estos casos no se evalua casilla, sino que la accion se realiza
         * desde aqui. Si esto es un error borrar los else-if pero el de caja y suerte
         * si que no puede ejecutarse evaluar casilla despues
         */
        if (avatares.get(turno).getCasilla().getNombre().equals("IrCarcel")) {
            jugadores.get(turno).encarcelar(this.tablero.getPosiciones());
        }

        else if (avatares.get(turno).getCasilla().getTipo().equals("suerte")) {
            elegir_carta(suerte);
        }

        else if (avatares.get(turno).getCasilla().getTipo().equals("caja")) {
            elegir_carta(suerte);
        }

        else{
            avatares.get(turno).getCasilla().actualizarCaidasEnCasilla(turno-1); //-1 para ignorar la banca;
            avatares.get(turno).getCasilla().evaluarCasilla(jugadores.get(turno), jugadores.get(0), desplazamiento);
        }
    }

    private void lanzarDadosCarcel() {

        this.dado1.hacerTirada();
        this.dado2.hacerTirada();
        if (dadosDobles(dado1.getValor(), dado2.getValor()) && this.jugadores.get(turno).getTurnosCarcel() < 3
                && !this.tirado) {
            int desplazamiento = dado1.getValor() + dado2.getValor();
            System.out.println("Has sacado dobles! Sales de la Cárcel y avanzas hasta");
            this.jugadores.get(turno).setEnCarcel(false);
            this.avatares.get(turno).moverAvatar(this.tablero.getPosiciones(), desplazamiento);
            System.out.println(this.avatares.get(turno).getCasilla());
            this.lanzamientos += 1;
            return;
        } else if (this.jugadores.get(turno).getTurnosCarcel() >= 3) {
            System.out.println("No has sacado dobles! Dado1: " + dado1.getValor() + " Dado2: " + dado2.getValor());
            System.out.println("Oh no! Llevas tres turnos en la cárcel paga " + Valor.PAGO_SALIR_CARCEL);
            this.tirado = false;
            pagarCarcel();
            return;
        } else if (this.tirado) {
            System.out.println("Ya has tirado este turno! ");
            return;
        } else if (!dadosDobles(dado1.getValor(), dado2.getValor())) {
            System.out.println("No has sacado dobles! Dado1: " + dado1.getValor() + " Dado2: " + dado2.getValor());
        }
        this.jugadores.get(turno).setTurnosCarcel(this.jugadores.get(turno).getTurnosCarcel() + 1);
        this.tirado = true;

    }

    private void pagarCarcel() {
        if (!this.tirado) {
            this.tirado = false;
            this.jugadores.get(turno).setEnCarcel(false);
            this.jugadores.get(turno).sumarFortuna(-Valor.PAGO_SALIR_CARCEL);
            System.out.println(
                    "Has pagado " + Valor.PAGO_SALIR_CARCEL + " para salir de la carcel. Puedes lanzar los dados.");
            this.jugadores.get(turno).setPagoTasasEImpuestos(
                    this.jugadores.get(turno).getPagoTasasEImpuestos() + Valor.PAGO_SALIR_CARCEL);
            this.jugadores.get(0).sumarGastos(Valor.PAGO_SALIR_CARCEL);
            System.out.println("El bote de la banca ahora es " + this.jugadores.get(0).getGastos());
        } else {
            System.out.println("Ya has tirado este turno!");
        }
    }

    /*
     * Método que ejecuta todas las acciones realizadas con el comando 'comprar
     * nombre_casilla'.
     * Parámetro: cadena de caracteres con el nombre de la casilla.
     */
    private void comprar(String nombre) {

        Casilla casilla = tablero.encontrar_casilla(nombre);

        if (casilla == null) {
            System.out.println("La casilla no existe");
            return;

        }
        if (this.tirado || lanzamientos > 0) {
            casilla.comprarCasilla(this.jugadores.get(turno), banca);
        }
    }

    // Método que ejecuta todas las acciones relacionadas con el comando 'salir
    // carcel'.
    private void salirCarcel() {

        if (this.jugadores.get(turno).getEnCarcel() == true && this.tirado == false) {
            System.out.println("Como quieres salir de la cárcel?");
            System.out.println("1) Lanzar dados (sacando dobles)");
            System.out.println("2) Pagando el impuesto " + Valor.PAGO_SALIR_CARCEL);
            System.out.printf("[>]: ");
            char opcion = this.scanner.next().charAt(0);
            switch (opcion) {
                case '1':
                    lanzarDadosCarcel();
                    break;
                case '2':
                    pagarCarcel();
                    break;
                default:
                    System.out.println("Opcion incorrecta");
                    break;
            }
        } else if (this.jugadores.get(turno).getEnCarcel() == false) {
            System.out.println("El jugador " + this.jugadores.get(turno).getNombre() + " no puede en la cárcel.");
        } else if (this.tirado == true) {
            System.out.println("Ya has tirado en este turno!");
        }
    }

    // Método que realiza las acciones asociadas al comando 'listar enventa'.
    private void listarVenta() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                if (tablero.getPosiciones().get(i).get(j).getDuenho().esBanca() &&
                        (tablero.getPosiciones().get(i).get(j).getTipo().equals("solar") ||
                                tablero.getPosiciones().get(i).get(j).getTipo().equals("transporte")
                                || tablero.getPosiciones().get(i).get(j).getTipo().equals("servicios"))) {
                    System.out.println(tablero.getPosiciones().get(i).get(j));
                }
            }
        }

        for (int i = 0; i < 40; i++) {
            if (this.tablero.obtenerCasilla(i).getDuenho().esBanca()
                    && (this.tablero.obtenerCasilla(i).getTipo().equals("solar")
                            || this.tablero.obtenerCasilla(i).getTipo().equals("transporte")
                            || this.tablero.obtenerCasilla(i).getTipo().equals("serv"))) {
                System.out.println(this.tablero.obtenerCasilla(i).infoCasilla());
            }

        }
    }

    // Método que realiza las acciones asociadas al comando 'listar jugadores'.
    private void listarJugadores() {
        System.out.println("Jugadores:");
        if (jugadores != null)
            for (Jugador j : jugadores) {
                if (!j.esBanca()) {
                    System.out.println(j);
                }
            }
    }

    // Método que realiza las acciones asociadas al comando 'listar avatares'.
    private void listarAvatares() {
        for (Avatar A : avatares) {
            if (A != null) {
                System.out.println(A.getInfo());
                System.out.println("\n");
            }
        }
    }

    private void mostrarestadisticasjugador(String nombre) {
        for (Jugador J : this.jugadores) {
            if (J.getNombre().equals(nombre)) {
                System.out.println(J.estadisticasJugador());
                return;
            }
        }
        System.out.println("No se ha encontrado este jugador.\n");
    }

    // FUNCIONES PARA MOSTRAR ESTADISTICAS
    // PARTIDA------------------------------------

    //FUNCIONES PARA MOSTRAR ESTADISTICAS PARTIDA------------------------------------
    private String buscarCasillasMasRentables(){
        String ret = new String();
        float maxrecaudado = tablero.posicion_salida().getRecaudado();
        for (ArrayList<Casilla> Lado : this.tablero.getPosiciones()) {
            for (Casilla c : Lado) {
                if (c.getRecaudado() >= maxrecaudado)
                    maxrecaudado = c.getRecaudado();
            }
        }
        for (ArrayList<Casilla> Lado : this.tablero.getPosiciones()) {
            for (Casilla c : Lado) {
                if (c.getRecaudado() == maxrecaudado) {
                    ret += c.getNombre();
                    ret += ", ";
                }
            }
        }
        return ret;
    }
    
    private String buscarCasillaMasFrecuentada(){
        String ret = new String();
        int maxvisitas = this.tablero.posicion_salida().totalVisitas();
        for(ArrayList<Casilla> Lado: this.tablero.getPosiciones()){
            for(Casilla c: Lado){
                if(c.totalVisitas() >= maxvisitas ) maxvisitas = c.totalVisitas();
            }
        }
        for(ArrayList<Casilla> Lado: this.tablero.getPosiciones()){
            for(Casilla c: Lado){
                if(c.totalVisitas() == maxvisitas ){
                    ret += c.getNombre();
                    ret+= ", ";
                }
            }
        }
        return ret;
    }
    //FIN FUNCIONES PARA MOSTRAR ESTADISTICAS PARTIDA ------------------------
    private void mostrarestadisticaspartida() {
        System.out.println("Casillas más rentables: " + this.buscarCasillasMasRentables() );
        System.out.println("Casillas más frecuentadas: " + this.buscarCasillaMasFrecuentada());
    }

    // Método que realiza las acciones asociadas al comando 'acabar turno'.
    private void acabarTurno() {
        if (partida_empezada && this.tirado) {
            int numero_jugadores = this.jugadores.size() - 1; // La banca no cuenta
            this.tirado = false;
            this.lanzamientos = 0;
            if (this.turno < numero_jugadores) {
                this.turno += 1;
                System.out.println("El jugador actual es: " + this.jugadores.get(turno).getNombre());
            } else {
                this.turno = 1; // Por la banca
                System.out.println("El jugador actual es: " + this.jugadores.get(turno).getNombre());
            }
        } else if (!partida_empezada) {
            System.out.println("La partida todavia no ha empezado. ");
        } else if (!this.tirado) {
            System.out.println("No has lanzado los dados este turno");
        }
    }

    // Método que finaliza la partida
    public static void acabarPartida() {
        System.out.println("Finalizando partida");
        /* Esto es un poco criminal */
        System.exit(0);
    }

    private void bancarrota() {

        Jugador actual = this.jugadores.get(turno); // Jugador actual
        if (actual.getAvatar().getCasilla().getDuenho().esBanca() || actual.getFortuna() > 0) { // Si la banca lo deja
                                                                                                // en bancarrota
            for (Casilla c : actual.getPropiedades()) {
                actual.eliminarPropiedad(c);
                banca.anhadirPropiedad(c);
                c.setDuenho(banca);
            }
            System.out.println("El jugador " + actual.getNombre()
                    + "se ha declarado en bancarrota. Sus propiedades pasan a estar de nuevo en venta al precio al que estaban.");
        }

        if (!actual.getAvatar().getCasilla().getDuenho().esBanca()) { // Si es otro jugador
            for (Casilla c : actual.getPropiedades()) {
                actual.eliminarPropiedad(c);
                actual.getAvatar().getCasilla().getDuenho().anhadirPropiedad(c);
                c.setDuenho(actual.getAvatar().getCasilla().getDuenho());
            }
            System.out.println("El jugador " + actual.getNombre()
                    + " se ha declarado en bancarrota. Sus propiedades y fortuna pasan a "
                    + actual.getAvatar().getCasilla().getDuenho().getNombre());
        }

        this.jugadores.remove(turno);
    }
}
