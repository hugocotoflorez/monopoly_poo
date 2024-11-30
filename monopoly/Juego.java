package monopoly;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import monopoly.Casilla.Propiedad.*;
import partida.*;
import partida.Avatar.*;
import partida.Carta.*;
import monopoly.consola.*;
import monopoly.Casilla.*;

public class Juego {

    // Atributos
    private ArrayList<Jugador> jugadores = new ArrayList<Jugador>(); // Jugadores de la partida.
    private ArrayList<Avatar> avatares; // Avatares en la partida.
    private int turno; // Índice correspondiente a la posición en el arrayList del jugador (y el
                       // avatar) que tienen el turno
    private int lanzamientos = 0; // Variable para contar el número de lanzamientos de un jugador en un turno.
    private Tablero tablero; // Tablero en el que se juega.
    private Dado dado1; // Dos dados para lanzar y avanzar casillas.
    private Dado dado2;
    private Jugador banca; // El jugador banca.
    private boolean tirado = false; // Booleano para comprobar si el jugador que tiene el turno ha tirado o no.
    private boolean solvente = true; // Booleano para comprobar si el jugador que tiene el turno es solvente, es
    // decir, si ha pagado sus deudas.
    private boolean partida_empezada = false;
    private boolean partida_finalizada = false;
    private ArrayList<Boolean> movimientoAvanzado;
    /*
     * Las siguientes variables se movieron a coche (creo)
     * private boolean[] se_puede_tirar_en_el_siguiente_turno = { true, true, true,
     * true, true, true };
     * private boolean[] se_puede_tirar_en_el_siguiente_turno2 = { true, true, true,
     * true, true, true };
     * private int contadorTiradasCoche = 0;
     */
    private boolean es_coche_y_no_puede_tirar = false;
    private boolean movimientoAvanzadoSePuedeCambiar = true;
    private boolean jugador_puede_comprar = true;
    private int lanzamientos_dobles = 0;
    private ArrayList<Casilla> casillasVisitadas = new ArrayList<Casilla>();
    private ArrayList<Trato> tratos = new ArrayList<Trato>();

    /*
     * Poner un scanner nuevo para cada funcion en la que se necesita daba error
     * porque segun lo que la intuicion me dice no se pueden abrir dos scanners
     * para System.in, por lo que si abrias y cerrabas uno en una funcion que se
     * ejecutase en el medio de un ciclo de vida de un scanner en otra funcion esta
     * fallaria
     */
    public static ConsolaNormal consola = new ConsolaNormal();

    public Juego() {
        crearCartasComunidad();
        crearCartasSuerte();
        this.avatares = new ArrayList<Avatar>();
        this.jugadores = new ArrayList<Jugador>();
        this.movimientoAvanzado = new ArrayList<Boolean>();
        for (int i = 0; i < 6; i++)
            movimientoAvanzado.add(false);
        iniciarPartida();
    }

    private void crearCartasSuerte() {
        new Suerte();
        new Suerte();
        new Suerte();
        new Suerte();
        new Suerte();
        new Suerte();
    }

    private void crearCartasComunidad() {
        new Comunidad();
        new Comunidad();
        new Comunidad();
        new Comunidad();
        new Comunidad();
        new Comunidad();
    }

    public void cargarArchivo(String archivo) {
        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                consola.imprimirln("[>]: " + linea);
                analizarComando(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Cuando se caiga en una casilla en la cual hay que lanzar cartas, se tiene que
     * llamar
     * a esta funcion.
     */
    // private boolean elegir_carta_suerte() {
    // int n;

    // do {
    // n = consola.leerInt("Elige una carta del 1 al 6: ");

    // } while (n < 1 || n > 6);

    //// Carta.barajar(baraja);
    // Suerte c = Suerte.obtenerCarta(n);
    // c.mostrarDescipcion();
    // return c.realizarAccion(avatares.get(turno), jugadores,
    //// tablero.getPosiciones());
    // }

    // private boolean elegir_carta_comunidad() {
    // int n;

    // do {
    // n = consola.leerInt("Elige una carta del 1 al 6: ");

    // } while (n < 1 || n > 6);

    //// Carta.barajar(baraja);
    // Comunidad c = Comunidad.obtenerCarta(n);
    // c.mostrarDescipcion();
    // return c.realizarAccion(avatares.get(turno), jugadores,
    //// tablero.getPosiciones());
    // }

    private void crear_jugador(String nombreJugador, String tipoAvatar) {

        if (!Avatar.esTipo(tipoAvatar)) {
            consola.imprimirln("Tipo invalido: " + tipoAvatar);
            return;
        }
        Jugador jugador = new Jugador(nombreJugador, tipoAvatar, this.tablero.posicion_salida(), this.avatares);
        this.jugadores.add(jugador);
        this.tablero.posicion_salida().anhadirAvatar(jugador.getAvatar());
    }

    /* No se porque no funciona */
    public static String generateColor(String s) {
        return "\u001B[%dm".formatted(30 + s.chars().sum() % 7);
    }

    private void iniciarPartida() {
        this.turno = 1;
        this.dado1 = new Dado();
        this.dado2 = new Dado();
        this.banca = new Jugador();
        String comando;
        this.avatares.add(null); // avatar banca
        this.jugadores.add(banca);
        this.tablero = new Tablero(banca);

        analizarComando("opciones");

        /* Bucle principal de la partida */
        while (!partida_finalizada) {

            // Para evitar que se lean comandos que no se de
            // donde salen y den error
            if (partida_empezada)
                comando = consola.leer("[" + generateColor(jugadores.get(turno).getNombre())
                        + jugadores.get(turno).getNombre() + "\033[0m]: ");
            else
                comando = consola.leer("[>]: ");

            analizarComando(comando);
        }
        /* Para que no explote si sales sin empezar la partida */
        if (jugadores.size() > 1)
            consola.imprimirln("\rEl jugador " + this.jugadores.get(turno).getNombre() + " ha ganado.");

        acabarPartida();
    }

    /* */
    /*--------------------------------------- FUNCIONES DE LINEA DE COMANDOS ---------------------------------------*/
    private void __analizarComandos_opciones() {

        /*
         * El color de fondo da problemas con la terminal de vscode si tiene que
         * desplazar hacia abajo el contenido de la consola porque no entra. En otras
         * terminales va bien.
         */

        consola.imprimirln(
                Valor.BGMENU + Valor.BOLD + "Opciones                                      " + Valor.RESET);
        consola.imprimirln(
                Valor.BGMENU + "crear jugador <nombre> <tipo_avatar>          " + Valor.BGBLACK + " " + Valor.RESET);
        consola.imprimirln(
                Valor.BGMENU + "jugador - jugador con el turno                " + Valor.BGBLACK + " " + Valor.RESET);
        consola.imprimirln(
                Valor.BGMENU + "listar enventa                                " + Valor.BGBLACK + " " + Valor.RESET);
        consola.imprimirln(
                Valor.BGMENU + "listar jugadores                              " + Valor.BGBLACK + " " + Valor.RESET);
        consola.imprimirln(
                Valor.BGMENU + "listar avatares                               " + Valor.BGBLACK + " " + Valor.RESET);
        consola.imprimirln(
                Valor.BGMENU + "lanzar dados                                  " + Valor.BGBLACK + " " + Valor.RESET);
        consola.imprimirln(
                Valor.BGMENU + "cambiar modo                                  " + Valor.BGBLACK + " " + Valor.RESET);
        consola.imprimirln(
                Valor.BGMENU + "acabar - acaba el turno                       " + Valor.BGBLACK + " " + Valor.RESET);
        consola.imprimirln(
                Valor.BGMENU + "salir - salir carcel)                         " + Valor.BGBLACK + " " + Valor.RESET);
        consola.imprimirln(
                Valor.BGMENU + "describir jugador  <nombre>                   " + Valor.BGBLACK + " " + Valor.RESET);
        consola.imprimirln(
                Valor.BGMENU + "describir avatar <letra                       " + Valor.BGBLACK + " " + Valor.RESET);
        consola.imprimirln(
                Valor.BGMENU + "comprar <casilla>                             " + Valor.BGBLACK + " " + Valor.RESET);
        consola.imprimirln(
                Valor.BGMENU + "bancarrota - acaba la partida para ese jugador" + Valor.BGBLACK + " " + Valor.RESET);
        consola.imprimirln(
                Valor.BGMENU + "ver - muestra el tablero                      " + Valor.BGBLACK + " " + Valor.RESET);
        consola.imprimirln(
                Valor.BGMENU + "clear - limpia la pantalla                    " + Valor.BGBLACK + " " + Valor.RESET);
        consola.imprimirln(
                Valor.BGMENU + "estadisticas <Jugador>                        " + Valor.BGBLACK + " " + Valor.RESET);
        consola.imprimirln(
                Valor.BGMENU + "estadisticas                                  " + Valor.BGBLACK + " " + Valor.RESET);
        consola.imprimirln(
                Valor.BGMENU + "hipotecar <casilla>                           " + Valor.BGBLACK + " " + Valor.RESET);
        consola.imprimirln(
                Valor.BGMENU + "deshipotecar <casilla>                        " + Valor.BGBLACK + " " + Valor.RESET);
        consola.imprimirln(
                Valor.BGMENU + "listar edificios                              " + Valor.BGBLACK + " " + Valor.RESET);
        consola.imprimirln(
                Valor.BGMENU + "listar edificios <grupo>                      " + Valor.BGBLACK + " " + Valor.RESET);
        consola.imprimirln(
                Valor.BGMENU + "edificar <tipo>                               " + Valor.BGBLACK + " " + Valor.RESET);
        consola.imprimirln(
                Valor.BGMENU + "vender <tipo> <solar> <cantidad>              " + Valor.BGBLACK + " " + Valor.RESET);
        consola.imprimirln(
                Valor.BGMENU + "----------------------------------------------" + Valor.BGBLACK + " " + Valor.RESET);
        consola.imprimirln(
                Valor.BGMENU + "opciones, ? -> Muestra las opciones           " + Valor.BGBLACK + " " + Valor.RESET);
        consola.imprimirln(
                Valor.BGMENU + "a -> acabar                                   " + Valor.BGBLACK + " " + Valor.RESET);
        consola.imprimirln(
                Valor.BGMENU + "empezar partida                               " + Valor.BGBLACK + " " + Valor.RESET);
        consola.imprimirln(
                Valor.BGMENU + "q, SALIR -> acaba la ejecucion del programa   " + Valor.BGBLACK + " " + Valor.RESET);
        consola.imprimirln(
                Valor.BGMENU + "c, clear -> limpia la pantalla                " + Valor.BGBLACK + " " + Valor.RESET);
        consola.imprimirln(
                Valor.BGMENU + "l x y -> lanzar dados, con resultado x e y    " + Valor.BGBLACK + " " + Valor.RESET);
        consola.imprimirln(
                Valor.BGMENU + "default -> crea dos jugadores                 " + Valor.BGBLACK + " " + Valor.RESET);
        consola.imprimirln(
                Valor.BGMENU + "archivo file -> ejecuta comandos en file      " + Valor.BGBLACK + " " + Valor.RESET);
        consola.imprimirln(
                Valor.BGMENU + "fortuna <valor>                               " + Valor.BGBLACK + " " + Valor.RESET);
        consola.imprimirln(" " + Valor.BGBLACK + "                                              " + Valor.RESET);

    }

    private void __analizarComandos_default() {

        analizarComando("crear jugador Jugador1 Coche");
        analizarComando("crear jugador Jugador2 Pelota");

    }

    private void __analizarComandos_listar(String[] com) {

        if (com.length == 2) {
            switch (com[1]) {
                case "enventa":

                    listarVenta();
                    break;

                case "jugadores":

                    listarJugadores();
                    break;

                case "avatares":

                    listarAvatares();
                    break;

                case "edificios":

                    listarEdificios();
                    break;

                default:
                    Juego.consola.imprimirError("Opcion incorrecta! <enventa> <jugadores> <avatares> <edificios>");
            }
        }
        if (com.length == 3) {
            if (com[1].equals("edificios"))
                listarEdificios(this.tablero.getGruposMap().get(com[2]));
        }

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

                __analizarComandos_opciones();
                break;

            case "default":

                __analizarComandos_default();
                break;

            case "archivo":
                if (com.length == 2) {
                    cargarArchivo(com[1]);
                }
                break;

            case "crear":
                if (partida_empezada) {
                    consola.imprimirln("La partida ya esta iniciada!");
                    break;
                } else if (com.length == 4 && com[1].equals("jugador")) {
                    if (jugadores.size() <= 6) {
                        crear_jugador(com[2], com[3]);
                        consola.imprimirln(this.tablero.toString());
                    } else
                        consola.imprimirln("Ya se ha alcanzado el número máximo de jugadores.");

                } else
                    consola.imprimirln("No se pudo crear el jugador");
                break;

            case "jugador":

                descJugador();
                break;

            case "listar":

                __analizarComandos_listar(com);
                break;

            case "empezar":
                if (jugadores.size() >= 3) // iniciar la partida
                    partida_empezada = true;
                break;

            case "lanzar":
                if (com.length == 2 && com[1].equals("dados")) {
                    if (jugadores.size() >= 3) { // iniciar la partida
                        partida_empezada = true;
                        lanzarDados();
                        consola.imprimirln(this.tablero.toString());
                    } else
                        consola.imprimirln("No tienes suficientes jugadores creados! (Mínimo 2).");
                }
                break;

            case "cambiar":
                if (com.length == 2 && com[1].equals("modo"))
                    cambairModo();
                break;

            case "l":
                if (com.length == 3) {
                    if (jugadores.size() >= 3) { // iniciar la partida
                        partida_empezada = true;
                        int valor = Integer.parseInt(com[1]);
                        int valor2 = Integer.parseInt(com[2]);
                        lanzarDados(valor, valor2);
                    } else
                        consola.imprimirln("No tienes suficientes jugadores creados! (Mínimo 2).");
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
                bancarrota(banca);
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
                }
                break;

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
                    consola.imprimirln("Opcion incorrecta. [? para ver las opciones]");
                break;

            case "hipotecar":
                if (com.length == 2) {
                    accionhipotecar(com[1]);
                }
                break;
            case "deshipotecar":
                if (com.length == 2) {
                    acciondeshipotecar(com[1]);
                }

            case "ver":
                consola.imprimirln(this.tablero.toString());
                break;

            case "q":
            case "SALIR":
                this.partida_finalizada = true;
                break;

            case "c":
            case "clear":
                consola.imprimir("\033[H\033[2J");
                break;

            case "edificar":
                if (com.length == 2)
                    edificar(com[1]);
                break;
            case "vender":
                if (com.length == 4) {
                    desedificar(com[2], com[1], com[3]);
                }
                break;
            case "fortuna":
                this.jugadores.get(turno).setFortuna(Float.parseFloat(com[1]));
                break;
            case "trato":
                trato(com);
                break;
            default:
                consola.imprimirln("Opcion incorrecta. [? para ver las opciones]");
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
                consola.imprimirln(J.toString());
                return;
            }
        }
        consola.imprimirln("No se ha encontrado este jugador.\n");
    }

    // Sobrecarga: si no se pasa argumentos describe el jugador que tiene el turno
    // actual
    private void descJugador() {
        if ((jugadores.size() != 1) && !jugadores.get(turno).esBanca()) {
            consola.imprimirln("""
                    | Nombre: %s
                    | Avatar: %s
                    """.formatted(jugadores.get(turno).getNombre(), jugadores.get(turno).getAvatar().getId()));
            return;
        }
        consola.imprimirln("No se ha encontrado este jugador.\n");
    }

    /*
     * Método que realiza las acciones asociadas al comando 'describir avatar'.
     * Parámetro: id del avatar a describir.
     */
    private void descAvatar(String ID) {
        for (int i = 1; i < avatares.size(); i++) {
            if (avatares.get(i).getId().equals(ID)) {
                consola.imprimirln(avatares.get(i).getInfo());
                return;
            }
        }
        consola.imprimirln("No se ha encontrado ese avatar.");
    }

    /*
     * Método que realiza las acciones asociadas al comando 'describir
     * nombre_casilla'.
     * Parámetros: nombre de la casilla a describir.
     */
    private void descCasilla(String nombre) {
        Casilla c = this.tablero.encontrar_casilla(nombre);
        if (c != null)
            consola.imprimirln(c.infoCasilla(this.banca));
        else
            consola.imprimirln("La casilla " + nombre + " no existe.");
    }

    // Método que ejecuta todas las acciones relacionadas con el comando 'lanzar
    // dados'.
    private boolean dadosDobles(int valor1, int valor2) {
        return (valor1 == valor2);
    }

    public boolean getTirado() {
        return tirado;
    }

    private void lanzarDados() {

        this.dado1.hacerTirada();
        this.dado2.hacerTirada();
        lanzarDados(dado1.getValor(), dado2.getValor());
    }

    // sobrecarga de lanzar dados en la cual elegimos qué valor sacan los dados
    private void lanzarDados(int valor1, int valor2) {

        if (this.jugadores.get(turno).getEnCarcel()) {
            consola.imprimirln("Oh no! Estás en la cárcel!");

        } else if (this.tirado) {
            consola.imprimirln("Ya has tirado en este turno!");

        } else {

            this.tirado = true;
            this.lanzamientos += 1;
            this.jugadores.get(turno).sumarNumeroTiradas();
            consola.imprimirln("Tirada: " + valor1 + ", " + valor2);

            mover(valor1, valor2);

            // Pasar por salida ahora se hace en mover

            Coche av_coche = null;
            if (this.jugadores.get(turno).getAvatar() instanceof Coche)
                av_coche = (Coche) this.jugadores.get(turno).getAvatar();

            if (dadosDobles(valor1, valor2)
                    /* Si esta usando el movimiento avanzado del coche no cuenta */
                    && (!(av_coche != null && movimientoAvanzado.get(turno - 1))
                            || av_coche.getContadorTiradasCoche() == 4)) {

                /* TODO no se si se incrementa dos veces y esto sobra */
                av_coche.incContadorTiradasCoche(); // solo puede tirar una vez si saca dobles al final
                jugador_puede_comprar = true;
                this.tirado = false;
                this.lanzamientos_dobles++;
                consola.imprimirln(("Has sacado dobles! Puedes tirar otra vez."));

                if (this.lanzamientos_dobles == 3) {
                    this.jugadores.get(turno).encarcelar(this.tablero.getPosiciones());
                    consola.imprimirln("Has sacado tres dobles seguidos! Vas a la carcel sin pasar por salida.");
                    this.tirado = true;
                }
            }

            // evaluarAcceion ahora esta en mover
        }
    }

    private void mover(int valor1, int valor2) {
        /* Movimiento default */
        if (!movimientoAvanzado.get(turno - 1)) {
            avatares.get(turno).moverNormal(tablero, valor1, valor2, jugadores);
            solvente = avatares.get(turno).evaluarAccion(valor1 + valor2, jugadores, tablero);
            if (!solvente) {
                evaluarSolvente();
            }

        } else {
            /*
             * mover en avanzado tiene que devolver si es solvente. Si lo es y es la pelota,
             * debe llamar a evaluarSolvente() que hace lo que se hace en evaluarAccion.
             * TODO: Hugo
             * Creo que ya lo hice pero no me acuerdo. No lo probe asi que lo dejo aqui
             */
            boolean solvente = this.avatares.get(turno).moverEnAvanzado(tablero, valor1, valor2, jugadores);
            if (this.avatares.get(turno) instanceof Pelota) {
                if (!solvente)
                    // es solvente, hacer lo que sea
                    evaluarSolvente();
            } else {
                if (this.avatares.get(turno) instanceof Coche)
                    tirado = ((Coche) this.avatares.get(turno)).getTirado();
                avatares.get(turno).evaluarAccion(valor1 + valor2, jugadores, tablero);
            }
        }
    }

    // private void moverNormal(int valor1, int valor2) {
    // int desplazamiento = valor1 + valor2;
    // consola.imprimir("El avatar " + this.avatares.get(turno).getId() + " avanza "
    // + desplazamiento + " desde "
    // + this.avatares.get(turno).getCasilla().getNombre());
    // this.avatares.get(turno).moverNormal(this.tablero, valor1, valor2);
    // consola.imprimirln(" hasta " + avatares.get(turno).getCasilla().getNombre());
    //
    // // Comprueba si pasa por salida
    // comprobarSiPasasPorSalida(valor1 + valor2);
    //
    // }
    //
    // private void moverCoche(int valor1, int valor2) {
    // /*
    // * Coche: si el valor de los dados es mayor que 4, avanza tantas casillas como
    // * dicho valor y puede seguir lanzando los dados tres veces más mientras el
    // * valor sea mayor que 4. Durante el turno solo se puede realizar una sola
    // * compra de propiedades, servicios o transportes, aunque se podría hacer en
    // * cualesquiera de los 4 intentos posibles. Sin embargo, se puede edificar
    // * cualquier tipo de edificio en cualquier intento. Si el valor de los dados
    // es
    // * menor que 4, el avatar retrocederá el número de casillas correspondientes y
    // * además no puede volver a lanzar los dados en los siguientes dos turnos.
    // */
    // /*
    // * DUDAS:
    // * se vuelve a tirar cuando se sacan dobles? como afecta?
    // */
    // int desplazamiento = valor1 + valor2;
    // if (desplazamiento > 4) {
    // moverNormal(valor1, valor2);
    // // actualiza contador coche y si el contador es 4 se pone a 0 y
    // // this.tirado es false por lo que no se puede seguir tirando
    // contadorTiradasCoche++;
    // this.tirado = contadorTiradasCoche >= 4;
    //
    // consola.imprimirln("Se puede volver a tirar? " + !this.tirado);
    // consola.imprimirln("Tiradas coche = " + contadorTiradasCoche);
    //
    // } else {
    // contadorTiradasCoche = 1;
    // moverAtras(valor1, valor2);
    // // Comprueba si pasa por salida hacia atras
    // pasarPorSalidaHaciaAtras(valor1 + valor2);
    // se_puede_tirar_en_el_siguiente_turno[turno - 1] = false;
    // se_puede_tirar_en_el_siguiente_turno2[turno - 1] = false;
    // consola.imprimirln("No puedes mover en dos turnos!");
    // }
    // }

    // private void moverPelota(int valor1, int valor2) {
    // /*
    // * Pelota: si el valor de los dados es mayor que 4, avanza tantas casillas
    // como
    // * dicho valor; mientras que, si el valor es menor o igual que 4, retrocede el
    // * número de casillas correspondiente. En cualquiera de los dos casos, el
    // avatar
    // * se parará en las casillas por las que va pasando y cuyos valores son
    // impares
    // * contados desde el número 4. Por ejemplo, si el valor del dado es 9,
    // entonces
    // * el avatar avanzará hasta la casilla 5, de manera que si pertenece a otro
    // * jugador y es una casilla de propiedad deberá pagar el alquiler, y después
    // * avanzará hasta la casilla 7, que podrá comprar si no pertenece a ningún
    // * jugador, y finalmente a la casilla 9, que podrá comprar o deberá pagar
    // * alquiler si no pertenece al jugador. Si una de esas casillas es Ir a
    // Cárcel,
    // * entonces no se parará en las subsiguientes casillas
    // */
    // int desplazamiento = valor1 + valor2;
    // if (desplazamiento > 4) {
    // for (int i = 5; i <= desplazamiento + 1; i += 2) {
    //
    // if (i == 5) // primer salto
    // moverNormal(5, 0);
    // else // saltos restantes
    // if (i == desplazamiento)
    // moverNormal(1, 0);
    // else
    // moverNormal(2, 0);
    //
    // // anade la casilla en la que cae a las que puede comprar
    // casillasVisitadas.add(jugadores.get(turno).getAvatar().getCasilla());
    // // evalua casilla o hace la accion que deba hacer
    // evaluarAccion(valor1 + valor2);
    //
    // // si va a la carcel deja de moverse
    // if (jugadores.get(turno).getEnCarcel())
    // break;
    //
    // }
    // } else {
    // // retroceder
    // moverAtras(valor1, valor2);
    // // Comprueba si pasa por salida hacia atras
    // pasarPorSalidaHaciaAtras(valor1 + valor2);
    // evaluarAccion(valor1 + valor2);
    // }
    // }
    //
    /* No para esta entrega */
    // private void moverEsfinge(int valor1, int valor2) {
    // consola.imprimirln("Movimendo normal, no para esta entrega");
    // moverNormal(valor1, valor2);
    // }
    //
    // /* No para esta entrega */
    // private void moverSombrero(int valor1, int valor2) {
    // consola.imprimirln("Movimendo normal, no para esta entrega");
    // moverNormal(valor1, valor2);
    // }

    private void cambairModo() {
        if (!movimientoAvanzadoSePuedeCambiar) {
            consola.imprimirln("Ya cambiaste de modo en este turno!");
            return;
        }
        if (lanzamientos > 0) {
            consola.imprimirln("No puedes cambiar de modo despues de lanzar los dados!");
            return;
        }

        movimientoAvanzado.set(turno - 1, !movimientoAvanzado.get(turno - 1));
        movimientoAvanzadoSePuedeCambiar = false;

        if (movimientoAvanzado.get(turno - 1))
            consola.imprimirln("Se ha activado el modo avanzado");
        else
            consola.imprimirln("Se ha desactivado el modo avanzado");
    }

    // private void pasarPorSalida() {
    // // !!!!!! si se modifica algo de esto hay que modificarlo tambien en Carta
    // consola.imprimirln("¡Has pasado por la Salida! Ganaste " +
    // Valor.SUMA_VUELTA);
    // jugadores.get(turno).sumarFortuna(Valor.SUMA_VUELTA);
    // jugadores.get(turno).setVueltas(jugadores.get(turno).getVueltas() + 1);
    // jugadores.get(turno).setPasarPorCasillaDeSalida(
    // jugadores.get(turno).getPasarPorCasillaDeSalida() + Valor.SUMA_VUELTA);
    // consola.imprimirln("Llevas " + jugadores.get(turno).getVueltas() + "
    // vueltas.");
    //
    // int vueltasmin = this.jugadores.get(turno).getVueltas();
    //
    // for (Jugador j : this.jugadores) {
    // if (!j.esBanca() && j.getVueltas() < vueltasmin) {
    // vueltasmin = j.getVueltas();
    // }
    // }
    //
    // if ((this.jugadores.get(turno).getVueltas() == vueltasmin) && (vueltasmin % 4
    // == 0)) {
    // consola.imprimirln(
    // "Todos los jugadores han dado un múltiplo de 4 vueltas, se va a incrementar
    // el precio de los solares en un 10%.");
    // this.tablero.actualizarValorSolares();
    // }
    // }
    //
    // private void evaluarAccion(int desplazamiento) {
    /// *
    // * En estos casos no se evalua casilla, sino que la accion se realiza
    // * desde aqui. Si esto es un error borrar los else-if pero el de caja y suerte
    // * si que no puede ejecutarse evaluar casilla despues
    // */
    // if (avatares.get(turno).getCasilla().getTipo().equals("suerte")) {
    // if (Suerte.elegirCarta(avatares.get(turno), jugadores, tablero)) {
    // pasarPorSalida();
    // }
    // }

    // if (avatares.get(turno).getCasilla().getTipo().equals("caja")) {
    // if (Comunidad.elegirCarta(avatares.get(turno), jugadores, tablero)) {
    // pasarPorSalida();
    // }
    // }

    // if (avatares.get(turno).getCasilla().getNombre().equals("IrCarcel")) {
    // jugadores.get(turno).encarcelar(this.tablero.getPosiciones());
    // }

    // else {
    //// evaluar casilla
    // solvente =
    // avatares.get(turno).getCasilla().evaluarCasilla(jugadores.get(turno), banca,
    // desplazamiento);
    // if (!solvente) {
    // evaluarSolvente();
    // }
    // }
    // }

    private void evaluarSolvente() {
        consola.imprimirln("El jugador " + jugadores.get(turno).getNombre()
                + " está en bancarrota. Su fortuna actual es " + jugadores.get(turno).getFortuna());
        char opcion;
        do {
            opcion = consola.leerChar("¿Deseas seguir jugando? (S/N): ");
            switch (opcion) {
                case 's':
                case 'S':
                    consola.imprimirln(
                            "Para saldar tus deudas, debes hipotecar tus propiedades antes de acabar tu turno.");
                    consola.imprimirln(
                            "Si te quedas sin propiedades que hipotecar y aún no has saldado tus deudas, tendrás que declararte en bancarrota.");
                    break;
                case 'n':
                case 'N':
                    bancarrota(this.banca);
                    solvente = true;

                    break;
                default:
                    consola.imprimirln("Opción errónea.");
                    break;
            }
        } while (opcion != 'S' && opcion != 's' && opcion != 'n' && opcion != 'N');
    }

    private void bancarrota(Jugador banca) {
        if(!partida_empezada){
            consola.imprimirln("No puedes declararte en bancarrota antes de empezar la partida.");
            return;
        }
        Jugador actual = this.jugadores.get(turno); // Jugador actual
        Casilla casillaactual = actual.getAvatar().getCasilla();
        if ((!(casillaactual instanceof Propiedad)) || !actual.estaBancarrota()) { // Está en bancarrota por banca o se
                                                                                   // declaró voluntariamente
            for (Propiedad c : actual.getPropiedades()) {
                banca.anhadirPropiedad(c);
                c.setDuenho(banca);
                c.setHipotecada(false);
                if (c instanceof Solar)
                    ((Solar) c).desedificar();
            }

            actual.getPropiedades().clear();

            consola.imprimirln("El jugador " + actual.getNombre()
                    + " se ha declarado en bancarrota. Sus propiedades pasan a estar de nuevo en venta al precio al que estaban y todos sus edificios serán demolidos.");
        }

        if ((casillaactual instanceof Propiedad && !((Propiedad) casillaactual).getDuenho().esBanca())) {// Es por otro
                                                                                                         // jugador
            for (Propiedad c : actual.getPropiedades()) {
                ((Propiedad) casillaactual).getDuenho().anhadirPropiedad(c);
                c.setDuenho(((Propiedad) casillaactual).getDuenho());
                if (c instanceof Solar)
                    ((Solar) c).getEdificios().clear();
                c.setHipotecada(false);
            }
            actual.getPropiedades().clear();
            consola.imprimirln("El jugador " + actual.getNombre()
                    + " se ha declarado en bancarrota. Sus propiedades y fortuna pasan a "
                    + ((Propiedad) casillaactual).getDuenho().getNombre());
        }

        this.jugadores.remove(turno);
        this.avatares.remove(turno);
        this.movimientoAvanzado.remove(turno);
        movimientoAvanzado.set(turno - 1, false);
        this.turno--;

        if (this.jugadores.size() == 2) {
            partida_finalizada = true;
            consola.imprimirln("Sólo queda un jugador. La partida ha finalizado.");
            /* Esta linea creo que sobra */
            this.turno = 1; //Es para q el mensaje del q gane salga bien (creo)
            return;
        }
        /* Si esto esta a true se puede acabar turno evitando todas las restricciones */
        es_coche_y_no_puede_tirar = true;
        acabarTurno();
    }

    private void accionhipotecar(String nombre) {
        Casilla c = this.tablero.encontrar_casilla(nombre);
        if (c != null && c instanceof Propiedad) {
            Propiedad p = (Propiedad) c;
            p.hipotecar(jugadores.get(turno));

            if (!jugadores.get(turno).estaBancarrota()) {
                solvente = true;
            }

            else
                consola.imprimirln("Aún no has saldado tus deudas.");
        } else
            consola.imprimirln("La casilla " + nombre + " no existe.");

    }

    private void acciondeshipotecar(String nombre) {
        Casilla c = this.tablero.encontrar_casilla(nombre);
        if (c != null && c instanceof Propiedad) {
            Propiedad p = (Propiedad) c;
            p.deshipotecar(jugadores.get(turno));
        } else
            consola.imprimirln("La casilla " + nombre + " no existe.");

    }

    private void lanzarDadosCarcel() {

        this.dado1.hacerTirada();
        this.dado2.hacerTirada();
        this.jugadores.get(turno).sumarNumeroTiradas();
        if (dadosDobles(dado1.getValor(), dado2.getValor()) && this.jugadores.get(turno).getTurnosCarcel() < 3
                && !this.tirado) {
            int desplazamiento = dado1.getValor() + dado2.getValor();
            consola.imprimirln("Has sacado dobles! Sales de la Cárcel y avanzas hasta");
            this.jugadores.get(turno).setEnCarcel(false);
            this.avatares.get(turno).desplazar(this.tablero.getPosiciones(), desplazamiento);
            consola.imprimirln(this.avatares.get(turno).getCasilla().toString());
            this.lanzamientos += 1;
            return;
        } else if (this.jugadores.get(turno).getTurnosCarcel() >= 3) {
            consola.imprimirln("No has sacado dobles! Dado1: " + dado1.getValor() + " Dado2: " + dado2.getValor());
            consola.imprimirln("Oh no! Llevas tres turnos en la cárcel. Paga " + Valor.PAGO_SALIR_CARCEL);
            this.tirado = false;
            pagarCarcel();
            if (this.jugadores.get(turno).getFortuna() < Valor.PAGO_SALIR_CARCEL) {
                consola.imprimirln("Como no puedes pagar para salir de la cárcel, se te declara en bancarrota.");
                bancarrota(this.banca);
            }
            return;
        } else if (this.tirado) {
            consola.imprimirln("Ya has tirado este turno! ");
            return;
        } else if (!dadosDobles(dado1.getValor(), dado2.getValor())) {
            consola.imprimirln("No has sacado dobles! Dado1: " + dado1.getValor() + " Dado2: " + dado2.getValor());
            this.lanzamientos += 1;
        }
        this.jugadores.get(turno).setTurnosCarcel(this.jugadores.get(turno).getTurnosCarcel() + 1);
        this.tirado = true;

    }

    private void pagarCarcel() {
        if (!this.tirado && this.jugadores.get(turno).getFortuna() >= Valor.PAGO_SALIR_CARCEL) {
            this.tirado = false;
            this.jugadores.get(turno).setEnCarcel(false);
            this.jugadores.get(turno).sumarFortuna(-Valor.PAGO_SALIR_CARCEL);
            consola.imprimirln(
                    "Has pagado " + Valor.PAGO_SALIR_CARCEL + " para salir de la carcel. Puedes lanzar los dados.");
            this.jugadores.get(turno).setPagoTasasEImpuestos(
                    this.jugadores.get(turno).getPagoTasasEImpuestos() + Valor.PAGO_SALIR_CARCEL);
            this.banca.sumarGastos(Valor.PAGO_SALIR_CARCEL);
            consola.imprimirln("El bote de la banca ahora es " + this.banca.getGastos());
        } else if (this.tirado) {
            consola.imprimirln("Ya has tirado este turno!");
        } else if (this.jugadores.get(turno).getFortuna() < Valor.PAGO_SALIR_CARCEL)
            consola.imprimirln("No tienes fortuna suficiente. Necesitas " + Valor.PAGO_SALIR_CARCEL);

    }

    /*
     * Método que ejecuta todas las acciones realizadas con el comando 'comprar
     * nombre_casilla'.
     * Parámetro: cadena de caracteres con el nombre de la casilla.
     */
    private void comprar(String nombre) {

        Casilla casilla = tablero.encontrar_casilla(nombre);

        if (jugadores.get(turno).getEnCarcel()) {
            consola.imprimirln("No puedes comprar desde la carcel!");
            return;
        }

        /* Para la mierda del coche y la pelota */
        if (!jugador_puede_comprar && movimientoAvanzado.get(turno - 1)) {
            consola.imprimirln("Ya has comprado en este turno!");
            return;
        }

        if (casilla == null) {
            consola.imprimirln("La casilla no existe.");
            return;

        }
        if (casilla instanceof Propiedad) {
            Propiedad p = (Propiedad) casilla;
            if (lanzamientos > 0) {
                p.comprarCasilla(this.jugadores.get(turno), banca, movimientoAvanzado.get(turno - 1),
                        avatares.get(turno).getCasillasVisitadas());
                /*
                 * Esta variable se pone a true si los dados son dobles, asi para los que
                 * se mueven mas de una vez o pueden comprar mas de una no les dejan
                 */
                jugador_puede_comprar = false;
            }
        }
    }

    // Método que ejecuta todas las acciones relacionadas con el comando 'salir
    // carcel'.
    private void salirCarcel() {

        if (this.jugadores.get(turno).getEnCarcel() == true && this.tirado == false) {
            consola.imprimirln("Como quieres salir de la cárcel?");
            consola.imprimirln("1) Lanzar dados (sacando dobles)");
            consola.imprimirln("2) Pagando el impuesto " + Valor.PAGO_SALIR_CARCEL);
            char opcion = consola.leerChar("[" + jugadores.get(turno).getNombre() + "] ");
            switch (opcion) {
                case '1':
                    lanzarDadosCarcel();
                    break;
                case '2':
                    pagarCarcel();
                    break;
                default:
                    consola.imprimirln("Opcion incorrecta");
                    break;
            }
        } else if (this.jugadores.get(turno).getEnCarcel() == false) {
            consola.imprimirln("El jugador " + this.jugadores.get(turno).getNombre() + " no puede en la cárcel.");
        } else if (this.tirado == true) {
            consola.imprimirln("Ya has tirado en este turno!");
        }
    }

    // Método que realiza las acciones asociadas al comando 'listar enventa'.
    private void listarVenta() {

        for (ArrayList<Casilla> ac : tablero.getPosiciones())
            for (Casilla c : ac) {
                if ((c instanceof Propiedad) && ((Propiedad) c).getDuenho().esBanca()) {
                    /*
                     * Si es instancia de Propiedad es instancia de lo que hay dentro
                     * no? @marina si :)
                     */
                    consola.imprimirln(((Propiedad) c).toString());
                }
            }
    }

    // Método que realiza las acciones asociadas al comando 'listar jugadores'.
    private void listarJugadores() {
        consola.imprimirln("Jugadores:");
        if (jugadores != null)
            for (Jugador j : jugadores) {
                if (!j.esBanca()) {
                    consola.imprimirln(j.toString());
                }
            }
    }

    // Método que realiza las acciones asociadas al comando 'listar avatares'.
    private void listarAvatares() {
        for (Avatar A : avatares) {
            if (A != null) {
                consola.imprimirln(A.getInfo());
                consola.imprimirln("\n");
            }
        }
    }

    // Método que muestra todos los edifcios construidos en la partida
    private void listarEdificios() {

        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < 10; j++) {
                if (this.tablero.getPosiciones().get(i).get(j) instanceof Solar) {
                    Solar s = (Solar) this.tablero.getPosiciones().get(i).get(j);
                    s.listar_info_edificios();
                }
            }
        }
    }

    // Método que muestra todos los elementos de un grupo dado el color
    private void listarEdificios(Grupo grupo) {
        if (grupo == null) {
            consola.imprimirln("Ese grupo no existe.");
            return;
        }
        consola.imprimirln(grupo.toString());

    }

    private void mostrarEstadisticasJugador(String nombre) {
        for (Jugador J : this.jugadores) {
            if (J.getNombre().equals(nombre)) {
                consola.imprimirln(J.estadisticasJugador());
                return;
            }
        }
        consola.imprimirln("No se ha encontrado este jugador.\n");
    }

    // FUNCIONES PARA MOSTRAR ESTADISTICAS
    // PARTIDA------------------------------------
    private String buscarCasillasMasRentables() {
        String ret = new String();
        float maxrecaudado = 0f;
        for (ArrayList<Casilla> Lado : this.tablero.getPosiciones()) {
            for (Casilla c : Lado) {
                if (c instanceof Propiedad)
                    if (((Propiedad) c).getRecaudado() >= maxrecaudado)
                        maxrecaudado = ((Propiedad) c).getRecaudado();
            }
        }
        for (ArrayList<Casilla> Lado : this.tablero.getPosiciones()) {
            for (Casilla c : Lado) {
                if (c instanceof Propiedad)
                    if (((Propiedad) c).getRecaudado() == maxrecaudado) {
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

    private String buscarJugadorMasVueltas() {
        String ret = new String();
        int maxvueltas = 0;
        for (Jugador j : this.jugadores) {
            if ((!j.esBanca()) && j.getVueltas() >= maxvueltas)
                maxvueltas = j.getVueltas();
        }
        for (Jugador j : this.jugadores) {
            if ((!j.esBanca()) && j.getVueltas() == maxvueltas) {
                ret += j.getNombre();
                ret += ", ";
            }
        }
        return ret;
    }

    private String buscarJugadorMasVecesDados() {
        String ret = new String();
        int maxtiradas = 0;
        for (Jugador j : this.jugadores) {
            if ((!j.esBanca()) && j.getNumeroTiradas() >= maxtiradas)
                maxtiradas = j.getNumeroTiradas();
        }
        for (Jugador j : this.jugadores) {
            if ((!j.esBanca()) && j.getNumeroTiradas() == maxtiradas) {
                ret += j.getNombre();
                ret += ", ";
            }
        }
        return ret;
    }

    private String buscarJugadorEnCabeza() {
        String ret = new String();
        float maxscore = 0.0f;
        for (Jugador j : this.jugadores) {
            if ((!j.esBanca()) && j.score() >= maxscore)
                maxscore = j.score();
        }
        for (Jugador j : this.jugadores) {
            if ((!j.esBanca()) && j.score() == maxscore) {
                ret += j.getNombre();
                ret += ", ";
            }
        }
        return ret;
    }
    // FIN FUNCIONES PARA MOSTRAR ESTADISTICAS PARTIDA ------------------------

    private void mostrarEstadisticasPartida() {
        consola.imprimirln("Casillas más rentables: " + this.buscarCasillasMasRentables());
        consola.imprimirln("Grupos más rentables: " + this.buscarGruposMasRentables());
        consola.imprimirln("Casillas más frecuentadas: " + this.buscarCasillaMasFrecuentada());
        consola.imprimirln("Jugador con más vueltas: " + this.buscarJugadorMasVueltas());
        consola.imprimirln("Jugador que ha tirado más veces los dados: " + this.buscarJugadorMasVecesDados());
        consola.imprimirln("Jugador en cabeza: " + this.buscarJugadorEnCabeza());
    }

    // Método que realiza las acciones asociadas al comando 'acabar turno'.
    private void acabarTurno() {
        // es_coche_y_no_puede_tirar es lo mismo que this.tirado (creo). No lo cambio
        // para no joder nada (mas)
        if (partida_empezada && ((lanzamientos > 0 && this.tirado) || es_coche_y_no_puede_tirar) && this.solvente) {

            /* Esto no se donde meterlo, en cada turno se tiene que poner a true */
            movimientoAvanzadoSePuedeCambiar = true;
            jugador_puede_comprar = true;
            lanzamientos_dobles = 0;
            casillasVisitadas.removeAll(casillasVisitadas); // se borran todas las casillas

            this.lanzamientos = 0;

            int numero_jugadores = this.jugadores.size() - 1; // La banca no cuenta
            if (this.turno < numero_jugadores) {
                this.turno += 1;
                consola.imprimirln("El jugador actual es: " + this.jugadores.get(turno).getNombre());
            } else {
                this.turno = 1; // Por la banca
                consola.imprimirln("El jugador actual es: " + this.jugadores.get(turno).getNombre());
            }

            /*
             * : esto se movio a avatar. Pero tiene que cambiarse el del coche si
             * es el coche el que acaba de mover supongo. Va mal de momento. En el
             * coche no se actualizan los valores al acabar el turno creo.
             * Actualizacion: Deberia funcionar ya
             */

            if (avatares.get(turno) instanceof Coche) {
                Coche c = (Coche) avatares.get(turno);
                c.setContadorTiradasCoche(0);
                this.tirado = !c.getse_puede_tirar_en_el_siguiente_turno();
                c.setse_puede_tirar_en_el_siguiente_turno(c.getse_puede_tirar_en_el_siguiente_turno2());
                c.setse_puede_tirar_en_el_siguiente_turno2(true);
                es_coche_y_no_puede_tirar = this.tirado;

            } else
                this.tirado = false;

        } else if (!partida_empezada) {
            consola.imprimirln("La partida todavia no ha empezado. ");
        } else if (es_coche_y_no_puede_tirar) {
            consola.imprimirln("No puedes tirar en este turno");
        } else if (!this.tirado) {
            consola.imprimirln("No has lanzado los dados este turno");
        } else if (!this.solvente) {
            consola.imprimirln("No has saldado tus deudas, hipoteca tus propiedades.");
        } else {
            consola.imprimirln("No puedes acabar turno");
        }
    }

    // Método que finaliza la partida (a la fuerza)
    public static void acabarPartida() {
        consola.imprimirln("Finalizando partida");
        /* Esto es un poco criminal */
        System.exit(0);
    }

    // FUNCIONES PARA EDIFICAR
    private void edificar(String tipo) {
        Casilla c = this.jugadores.get(turno).getAvatar().getCasilla();
        if (c instanceof Solar) {
            ((Solar) c).edificar(tipo, this.jugadores.get(this.turno));
        } else
            consola.imprimirln("No puedes edificar en esta casilla.");
    }

    private void desedificar(String casilla, String tipoedificio, String n) {

        Casilla c = this.tablero.encontrar_casilla(casilla);
        if (c == null) {

            consola.imprimirln("Nombre de casilla incorrecto!");
            return;
        }

        if (c instanceof Solar) {
            ((Solar) c).desedificar(tipoedificio, this.jugadores.get(turno), n);
            if (!jugadores.get(turno).estaBancarrota()) {
                solvente = true;
            } else
                consola.imprimirln("Aún no has saldado tus deudas.");
        } else
            consola.imprimirln("No puedes desedificar en esta casilla.");
    }

    Jugador obtenerJugadorDadoNombre(String nombreJugador) {

        for (Jugador j : this.jugadores) {

            if (j.getNombre().equals(nombreJugador))
                return j;
        }
        return null;

    }

    private void trato(String[] com) { // TODO comprobar dueños de las Propiedades. Hacer cuando se acabe casilla
                                       // (DENTRO DE TRATO)

        if (com.length == 7) {

            Jugador j2 = obtenerJugadorDadoNombre(com[1]);
            Trato trato = new Trato(this.jugadores.get(turno), j2, com[4], com[5], com[7], this.tablero);
            this.tratos.add(trato);

        } else if (com.length == 5) {

            Jugador j2 = obtenerJugadorDadoNombre(com[1]);
            Trato trato = new Trato(this.jugadores.get(turno), j2, com[4], com[5], this.tablero);
            this.tratos.add(trato);

        }

    }

    // private void listarTratos() {

    // for (Trato t : tratos) {

    // if (t.getReceptor().equals(this.jugadores.get(turno))) {

    // t.toString();
    // // TODO: creo que falta el printf @guille

    // }

    // }

    // }

}
