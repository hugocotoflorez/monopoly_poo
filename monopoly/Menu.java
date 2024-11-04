package monopoly;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


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
    private boolean movimientoAvanzadoSePuedeCambiar = true;
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
                System.out.println("cambiar modo");
                System.out.println("acabar - acaba el turno");
                System.out.println("salir (carcel)");
                System.out.println("describir jugador  <nombre>");
                System.out.println("describir avatar <letra");
                System.out.println("comprar <casilla>");
                System.out.println("bancarrota - acaba la partida para ese jugador");
                System.out.println("ver - muestra el tablero");
                System.out.println("clear - limpia la pantalla");
                System.out.println("estadisticas <Jugador>");
                System.out.println("estadisticas");
                System.out.println("hipotecar <casilla>");
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
                    else if (com[1].equals("edificios"))
                        listarEdificios();
                }
                if (com.length == 3) {
                    if (com[1].equals("edificios"))
                        listarEdificios(this.tablero.getGruposMap().get(com[2]));
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

            case "cambiar":
                if (com.length == 2 && com[1].equals("modo"))
                    cambairModo();
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
                    mostrarEstadisticasJugador(com[1]);
                } else if (com.length == 1) {
                    mostrarEstadisticasPartida();
                } else
                    System.out.println("Opcion incorrecta. [? para ver las opciones]");
                break;
            
            case "hipotecar":
                if(com.length==2){
                    
                }

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
        Casilla c = this.tablero.encontrar_casilla(nombre);
        if (c!=null) System.out.println(c.infoCasilla());
        else System.out.println("La casilla " + nombre + " no existe.");
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
            this.jugadores.get(turno).sumarNumeroTiradas();

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
            moverNormal(valor1, valor2);
        }
        else
        switch (this.avatares.get(turno).getTipo()) {
            case "Coche":
                moverCoche(valor1, valor2);
                break;
            case "Pelota":
                moverPelota(valor1, valor2);
                break;
            case "Esfinge":
                /* No para esta entrega */
                moverEsfinge(valor1, valor2);
                break;
            case "Somprero":
                /* No para esta entrega */
                moverSombrero(valor1, valor2);
                break;
        }
    }

    private void moverNormal(int valor1, int valor2) {
        int desplazamiento = valor1 + valor2;
        System.out
                .print("El avatar " + this.avatares.get(turno).getId() + " avanza " + desplazamiento + " desde "
                        + this.avatares.get(turno).getCasilla().getNombre());
        this.avatares.get(turno).moverAvatar(this.tablero.getPosiciones(), desplazamiento);
        System.out.println(" hasta" + avatares.get(turno).getCasilla().getNombre());
    }

    private void moverCoche(int valor1, int valor2) {
        /*
         * Coche: si el valor de los dados es mayor que 4, avanza tantas casillas como
         * dicho valor y puede seguir lanzando los dados tres veces más mientras el
         * valor sea mayor que 4. Durante el turno solo se puede realizar una sola
         * compra de propiedades, servicios o transportes, aunque se podría hacer en
         * cualesquiera de los 4 intentos posibles. Sin embargo, se puede edificar
         * cualquier tipo de edificio en cualquier intento. Si el valor de los dados es
         * menor que 4, el avatar retrocederá el número de casillas correspondientes y
         * además no puede volver a lanzar los dados en los siguientes dos turnos.
         */
        /*
         * DUDAS:
         * se vuelve a tirar cuando se sacan dobles? como afecta?
         */
        System.err.println("[!]: Estas usando codigo sin acabar!! (moverCoche)");
        int desplazamiento = valor1 + valor2;
        if (desplazamiento > 4) {
            moverNormal(valor1, valor2);
            this.tirado = false; // se puede seguir tirando
        }
    }

    private void moverPelota(int valor1, int valor2) {
        /*
         * Pelota: si el valor de los dados es mayor que 4, avanza tantas casillas como
         * dicho valor; mientras que, si el valor es menor o igual que 4, retrocede el
         * número de casillas correspondiente. En cualquiera de los dos casos, el avatar
         * se parará en las casillas por las que va pasando y cuyos valores son impares
         * contados desde el número 4. Por ejemplo, si el valor del dado es 9, entonces
         * el avatar avanzará hasta la casilla 5, de manera que si pertenece a otro
         * jugador y es una casilla de propiedad deberá pagar el alquiler, y después
         * avanzará hasta la casilla 7, que podrá comprar si no pertenece a ningún
         * jugador, y finalmente a la casilla 9, que podrá comprar o deberá pagar
         * alquiler si no pertenece al jugador. Si una de esas casillas es Ir a Cárcel,
         * entonces no se parará en las subsiguientes casillas
         */
        System.err.println("[!]: Estas usando codigo sin acabar!!(MoverPelota)");
        int desplazamiento = valor1 + valor2;
        if (desplazamiento > 4) {
            for (int i = 5; i < desplazamiento; i += 2) {
                /*
                 * Hay que hacer algo tipo evaluar casilla pero que si se puede comprar la pueda
                 * comprar y sino siga avanzando o no se algo raro. Puedo utilizar el valor de
                 * retorno de evaluar casilla y hacerle algo tipo un menu nuevo para comprar
                 * propiedades, no se
                 */
                if (i == 5)
                    moverNormal(5, 0);
                else
                    moverNormal(2, 0);
            }
        } else {
        }
    }

    /* No para esta entrega */
    private void moverEsfinge(int valor1, int valor2) {
    }

    /* No para esta entrega */
    private void moverSombrero(int valor1, int valor2) {
    }

    private void cambairModo() {
        if (!movimientoAvanzadoSePuedeCambiar) {
            /* Esto no se si deberia existir o si se puede cambiar DUDA */
            System.out.println("Ya cambiaste de modo en este turno!");
            return;
        }

        movimientoAvanzado = !movimientoAvanzado;
        movimientoAvanzadoSePuedeCambiar = false;

        if (movimientoAvanzado)
            System.out.println("Se ha activado el modo avanzado");
        else
            System.out.println("Se ha desactivado el modo avanzado");
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
            solvente = avatares.get(turno).getCasilla().evaluarCasilla(jugadores.get(turno), jugadores.get(0), desplazamiento);
            if(!solvente){
                System.out.println("El jugador " + jugadores.get(turno).getNombre() + " está en bancarrota. Su fortuna actual es " + jugadores.get(turno).getFortuna());
                char opcion;
                do{
                    System.out.println(("¿Deseas seguir jugando? (S/N): "));
                    opcion = this.scanner.next().charAt(0);
                    switch (opcion){
                        case 's':
                        case 'S':
                            System.out.println("Para saldar tus deudas, debes hipotecar tus propiedades antes de acabar tu turno.");
                            break;
                        case 'n':
                        case 'N':
                            bancarrota();
                            solvente = true;
                            break;
                        default:
                            System.out.println("Opción errónea.");
                            break;
                    }
                }while(opcion != 'S' && opcion != 's' && opcion != 'n' && opcion != 'n' && opcion != 'N'); 
            }
        }
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
            //TODO pasar edificios
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
            //TODO pasar edificios
        }

        this.jugadores.remove(turno);
    }

    private void accionhipotecar(String nombre){
        Casilla c = this.tablero.encontrar_casilla(nombre);
        if (c!=null){
            c.hipotecar(jugadores.get(turno));

            if(!jugadores.get(turno).estaBancarrota()){
                solvente = true;
            }

            else System.out.println("Aún no has saldado tus deudas.");
        }
        else System.out.println("La casilla " + nombre + " no existe.");
        
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

        for (ArrayList<Casilla> ac : tablero.getPosiciones())
            for (Casilla c : ac) {
                if (c.getDuenho().esBanca() &&
                        (c.getTipo().equals("solar") ||
                                c.getTipo().equals("transporte")
                                || c.getTipo().equals("servicios"))) {

                    System.out.println(c);
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

    // Método que muestra todos los edifcios construidos en la partida
    private void listarEdificios() {

        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < 10; j++) {

                this.tablero.getPosiciones().get(i).get(j).listar_edificios_casilla();

            }

        }
    }

    // Método que muestra todos los elementos de un grupo dado el color
    private void listarEdificios(Grupo grupo) {
        if (grupo == null) {
            System.out.println("Ese grupo no existe.");
            return;
        }
            grupo.toString();

    }

    private void mostrarEstadisticasJugador(String nombre) {
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
    private String buscarCasillasMasRentables() {
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

    private String buscarGruposMasRentables() {
        String ret = new String();
        float maxrecaudado = tablero.getGruposMap().get("Rojo").totalRecaudado();
        Set<Map.Entry<String, Grupo>> entradas = tablero.getGruposMap().entrySet();
        for (Map.Entry<String, Grupo> e : entradas) {
            Grupo g = e.getValue();
            if (g.totalRecaudado() >= maxrecaudado)
                maxrecaudado = g.totalRecaudado();
        }
        for (Map.Entry<String, Grupo> e : entradas) {
            Grupo g = e.getValue();
            if (g.totalRecaudado() == maxrecaudado) {
                ret += e.getKey();
                ret += ", ";
            }
        }
        return ret;
    }

    private String buscarCasillaMasFrecuentada() {
        String ret = new String();
        int maxvisitas = this.tablero.posicion_salida().totalVisitas();
        for (ArrayList<Casilla> Lado : this.tablero.getPosiciones()) {
            for (Casilla c : Lado) {
                if (c.totalVisitas() >= maxvisitas)
                    maxvisitas = c.totalVisitas();
            }
        }
        for (ArrayList<Casilla> Lado : this.tablero.getPosiciones()) {
            for (Casilla c : Lado) {
                if (c.totalVisitas() == maxvisitas) {
                    ret += c.getNombre();
                    ret += ", ";
                }
            }
        }
        return ret;
    }

    private String buscarJugadorMasVueltas(){
        String ret = new String();
        int maxvueltas = this.jugadores.get(1).getVueltas();
        for (Jugador j: this.jugadores){
            if(!j.esBanca() && j.getVueltas() >= maxvueltas){
                maxvueltas = j.getVueltas();
            }
        }
        for (Jugador j: this.jugadores){
            if(!j.esBanca() && j.getVueltas() == maxvueltas){
                ret += j.getNombre();
                ret += ", ";
            }
        }
        return ret;
    }

    private String buscarJugadorMasDados(){
        String ret = new String();
        int maxdados = this.jugadores.get(1).getNumeroTiradas();
        for (Jugador j: this.jugadores){
            if(!j.esBanca() && j.getNumeroTiradas() >= maxdados){
                maxdados = j.getNumeroTiradas();
            }
        }
        for (Jugador j: this.jugadores){
            if(!j.esBanca() && j.getNumeroTiradas() == maxdados){
                ret += j.getNombre();
                ret += ", ";
            }
        }
        return ret;
    }

    private String buscarJugadorEnCabeza(){
        String ret = new String();
        float maxscore = this.jugadores.get(1).score();
        Jugador ganador = this.jugadores.get(1);
        for (Jugador j: this.jugadores){
            if(!j.esBanca() && j.score() >= maxscore){
                maxscore = j.score();
            }
        }
        for (Jugador j: this.jugadores){
            if(!j.esBanca() && j.score() == maxscore){
                ret += j.getNombre();
                ret += ", ";
            }
        }
        return ret;
    }
    
    // FIN FUNCIONES PARA MOSTRAR ESTADISTICAS PARTIDA ------------------------

    private void mostrarEstadisticasPartida() {
        System.out.println("Casillas más rentables: " + this.buscarCasillasMasRentables());
        System.out.println("Grupos más rentables: " + this.buscarGruposMasRentables());
        System.out.println("Casillas más frecuentadas: " + this.buscarCasillaMasFrecuentada());
        System.out.println("Jugador que ha dado más vueltas: " + this.buscarJugadorMasVueltas());
        System.out.println("Jugador que ha lanzado los dados más veces: " + this.buscarJugadorMasDados());
        System.out.println("Jugador que va ganando: " + this.buscarJugadorEnCabeza());
    }

    // Método que realiza las acciones asociadas al comando 'acabar turno'.
    private void acabarTurno() {
        if (partida_empezada && this.tirado && this.solvente) {

            /* Esto no se donde meterlo, en cada turno se tiene que poner a true*/
            movimientoAvanzadoSePuedeCambiar = true;

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
        } else if(!this.solvente){
            System.out.println("No has saldado tus deudas, hipoteca tus propiedades.");
        }
    }

    // Método que finaliza la partida
    public static void acabarPartida() {
        System.out.println("Finalizando partida");
        /* Esto es un poco criminal */
        System.exit(0);
    }

    
}
