package monopoly;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import monopoly.Casilla.Propiedad.*;
import monopoly.MonopolyException.MonopolyException;
import monopoly.MonopolyException.AccionException.AccionIncompatibleException;
import monopoly.MonopolyException.AccionException.CarcelException;
import monopoly.MonopolyException.AccionException.EstadoPartidaException;
import monopoly.MonopolyException.ComandoException.ComandoIncorrectoException;
import monopoly.MonopolyException.AccionException.FortunaInsuficienteException;
import monopoly.MonopolyException.AccionException.NoSolventeException;
import monopoly.MonopolyException.AccionException.NumeroJugadoresException;
import monopoly.MonopolyException.ComandoException.NoExisteElementoException;
import monopoly.MonopolyException.ComandoException.TipoIncorrectoException;
import monopoly.MonopolyException.PropiedadException.TipoPropiedadException;
import monopoly.MonopolyException.PropiedadException.EdificioException.CasaEdificableException;
import monopoly.MonopolyException.PropiedadException.EdificioException.HotelEdificableException;
import monopoly.MonopolyException.PropiedadException.EdificioException.NumeroEdificiosException;
import monopoly.MonopolyException.PropiedadException.EdificioException.PiscinaEdificableException;
import monopoly.MonopolyException.PropiedadException.EdificioException.PistaEdificableException;
import partida.*;
import partida.Avatar.*;
import partida.Carta.*;
import monopoly.consola.*;
import monopoly.Casilla.*;

import java.util.Iterator;

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

    private void crear_jugador(String nombreJugador, String tipoAvatar) {
        try {
            if (!Avatar.esTipo(tipoAvatar))
                throw new TipoIncorrectoException(
                        "Tipo de avatar incorrecto. Tipos correctos: <Coche>, <Sombrero>, <Pelota>, <Esfinge>.");

            Jugador jugador = new Jugador(nombreJugador, tipoAvatar, this.tablero.posicion_salida(), this.avatares);
            this.jugadores.add(jugador);
            this.tablero.posicion_salida().anhadirAvatarCasilla(jugador.getAvatar());
        } catch (TipoIncorrectoException tie) {
            consola.imprimirError(tie.getMessage());
        }
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
    private void analizarComandosOpciones() {

        /*
         * El color de fondo da problemas con la terminal de vscode si tiene que
         * desplazar hacia abajo el contenido de la consola porque no entra. En otras
         * terminales va bien.
         */

        consola.imprimirln(" " +
                Valor.BGMENU + Valor.BOLD + "  Opciones                                                                                 "
                + Valor.RESET);
        consola.imprimirln(" " +
                Valor.BGMENU + "  crear jugador <nombre> <tipo_avatar>                                                     " + Valor.BGBLACK + " "
                + Valor.RESET);
        consola.imprimirln(" " +
                Valor.BGMENU + "  listar enventa|jugadores|avatares                                                        " + Valor.BGBLACK + " "
                + Valor.RESET);
        consola.imprimirln(" " +
                Valor.BGMENU + "  lanzar dados                                                                             " + Valor.BGBLACK + " "
                + Valor.RESET);
        consola.imprimirln(" " +
                Valor.BGMENU + "  cambiar modo                                                                             " + Valor.BGBLACK + " "
                + Valor.RESET);
        consola.imprimirln(" " +
                Valor.BGMENU + "  acabar - acaba el turno                                                                  " + Valor.BGBLACK + " "
                + Valor.RESET);
        consola.imprimirln(" " +
                Valor.BGMENU + "  salir - salir carcel                                                                     " + Valor.BGBLACK + " "
                + Valor.RESET);
        consola.imprimirln(" " +
                Valor.BGMENU + "  describir jugador|avatar <nombre|letra>                                                  " + Valor.BGBLACK + " "
                + Valor.RESET);
        consola.imprimirln(" " +
                Valor.BGMENU + "  comprar <casilla>                                                                        " + Valor.BGBLACK + " "
                + Valor.RESET);
        consola.imprimirln(" " +
                Valor.BGMENU + "  bancarrota - acaba la partida para ese jugador                                           " + Valor.BGBLACK + " "
                + Valor.RESET);
        consola.imprimirln(" " +
                Valor.BGMENU + "  ver - muestra el tablero                                                                 " + Valor.BGBLACK + " "
                + Valor.RESET);
        consola.imprimirln(" " +
                Valor.BGMENU + "  estadisticas [<Jugador>]                                                                 " + Valor.BGBLACK + " "
                + Valor.RESET);
        consola.imprimirln(" " +
                Valor.BGMENU + "  edificar <tipo> [<solar>]                                                                " + Valor.BGBLACK + " "
                + Valor.RESET);
        consola.imprimirln(" " +
                Valor.BGMENU + "  hipotecar|deshipotecar <casilla>                                                         " + Valor.BGBLACK + " "
                + Valor.RESET);
        consola.imprimirln(" " +
                Valor.BGMENU + "  vender <tipo> <solar> <cantidad>                                                         " + Valor.BGBLACK + " "
                + Valor.RESET);
        consola.imprimirln(" " +
                Valor.BGMENU + "  listar edificios [<grupo>]                                                               " + Valor.BGBLACK + " "
                + Valor.RESET);
        consola.imprimirln(" " +
                Valor.BGMENU + "  listar tratos                                                                            " + Valor.BGBLACK + " "
                + Valor.RESET);
//        consola.imprimirln(" " +
//                Valor.BGMENU + "  trato <jugador> cambiar <propiedad> <propiedad>             " + Valor.BGBLACK + " "
//                + Valor.RESET);
//        consola.imprimirln(" " +
//                Valor.BGMENU + "  trato <jugador> cambiar <propiedad> <dinero>                " + Valor.BGBLACK + " "
//                + Valor.RESET);
//        consola.imprimirln(" " +
//                Valor.BGMENU + "  trato <jugador> cambiar <dinero> <propiedad>                " + Valor.BGBLACK + " "
//                + Valor.RESET);
//        consola.imprimirln(" " +
//                Valor.BGMENU + "  trato <jugador> cambiar <propiedad> <propiedad> y <dinero>  " + Valor.BGBLACK + " "
//                + Valor.RESET);
//        consola.imprimirln(" " +
//                Valor.BGMENU + "  trato <jugador> cambiar <propiedad> y <dinero> <propiedad>  " + Valor.BGBLACK + " "
//                + Valor.RESET);
        consola.imprimirln(" " +
                Valor.BGMENU + "  trato <jugador> cambiar <propiedad|dinero> [y <dinero>] <propiedad|dinero> [y <dinero>]  " + Valor.BGBLACK + " "
                + Valor.RESET);
//        consola.imprimirln(" " +
//                Valor.BGMENU + "  ----------------------------------------------------------  " + Valor.BGBLACK + " "
//                + Valor.RESET);
//        consola.imprimirln(" " +
//                Valor.BGMENU + "  opciones, ? -> Muestra las opciones                         " + Valor.BGBLACK + " "
//                + Valor.RESET);
//        consola.imprimirln(" " +
//                Valor.BGMENU + "  a -> acabar                                                 " + Valor.BGBLACK + " "
//                + Valor.RESET);
//        consola.imprimirln(" " +
//                Valor.BGMENU + "  empezar partida                                             " + Valor.BGBLACK + " "
//                + Valor.RESET);
//        consola.imprimirln(" " +
//                Valor.BGMENU + "  q, SALIR -> acaba la ejecucion del programa                 " + Valor.BGBLACK + " "
//                + Valor.RESET);
//        consola.imprimirln(" " +
//                Valor.BGMENU + "  c, clear -> limpia la pantalla                              " + Valor.BGBLACK + " "
//                + Valor.RESET);
//        consola.imprimirln(" " +
//                Valor.BGMENU + "  l x y -> lanzar dados, con resultado x e y                  " + Valor.BGBLACK + " "
//                + Valor.RESET);
//        consola.imprimirln(" " +
//                Valor.BGMENU + "  default -> crea dos jugadores                               " + Valor.BGBLACK + " "
//                + Valor.RESET);
//        consola.imprimirln(" " +
//                Valor.BGMENU + "  archivo file -> ejecuta comandos en file                    " + Valor.BGBLACK + " "
//                + Valor.RESET);
//        consola.imprimirln(" " +
//                Valor.BGMENU + "  fortuna <valor>                                             " + Valor.BGBLACK + " "
//                + Valor.RESET);
        consola.imprimirln(" " +
                " " + Valor.BGBLACK + "                                                                                           " + Valor.RESET);

    }

    private void analizarComandosDefault() {

        analizarComando("crear jugador Jugador1 Coche");
        analizarComando("crear jugador Jugador2 Pelota");

    }

    private void analizarComandosListar(String[] com) {

        try {

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

                    case "tratos":
                        listarTratos();
                        break;

                    default:
                        throw new ComandoIncorrectoException();
                }
            }

            if (com.length == 3) {

                if (com[1].equals("edificios"))
                    listarEdificios(this.tablero.getGruposMap().get(com[2]));

                else
                    throw new ComandoIncorrectoException();
            }

            if (com.length == 1)
                throw new ComandoIncorrectoException();

        } catch (ComandoIncorrectoException cie) {
            consola.imprimirError(cie.getMessage());
        }
    }

    /*
     * Método que interpreta el comando introducido y toma la accion
     * correspondiente.
     * Parámetro: cadena de caracteres (el comando).
     */
    private void analizarComando(String comando) {
        String[] com = comando.split(" ");
        try {

            switch (com[0]) {
                case "opciones":
                case "?":
                    analizarComandosOpciones();
                    break;

                case "default":
                    analizarComandosDefault();
                    break;

                case "archivo":
                    if (com.length == 2) {
                        cargarArchivo(com[1]);
                    }
                    break;

                case "crear":
                    if (partida_empezada)
                        throw new EstadoPartidaException("La partida ya está iniciada.");
                    else if (com.length == 4 && com[1].equals("jugador")) {
                        if (jugadores.size() <= 6) {
                            crear_jugador(com[2], com[3]);
                            consola.imprimirln(this.tablero.toString());
                        } else
                            throw new NumeroJugadoresException("Ya se ha alcanzado el número máximo de jugadores.");
                    } else
                        throw new ComandoIncorrectoException();
                    break;

                case "jugador":
                    descJugador();
                    break;

                case "listar":
                    analizarComandosListar(com);
                    break;

                case "empezar":
                    if (jugadores.size() <= 2)
                        throw new NumeroJugadoresException("No hay suficientes jugadores creados.");
                    partida_empezada = true;
                    break;

                case "lanzar":
                    if (jugadores.size() <= 2)
                        throw new NumeroJugadoresException("No hay suficientes jugadores creados. Hay "
                                + jugadores.size() + " jugadores creados.");
                    partida_empezada = true;
                    lanzarDados();
                    consola.imprimirln(this.tablero.toString());
                    break;

                case "cambiar":
                    if (com.length == 2 && com[1].equals("modo"))
                        cambairModo();
                    else
                        throw new ComandoIncorrectoException();
                    break;

                case "l":
                    if (jugadores.size() <= 2)
                        throw new NumeroJugadoresException("No hay suficientes jugadores creados. Hay "
                                + jugadores.size() + " jugadores creados.");
                    if (com.length != 3)
                        throw new ComandoIncorrectoException();
                    partida_empezada = true;
                    lanzarDados(Integer.parseInt(com[1]), Integer.parseInt(com[2]));
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
                                else
                                    throw new ComandoIncorrectoException();
                                break;
                            default: // describir casilla
                                descCasilla(com[1]);
                                break;
                        }
                    } else
                        throw new ComandoIncorrectoException();
                    break;

                case "comprar":
                    if (com.length == 2) {
                        comprar(com[1]);
                    } else
                        throw new ComandoIncorrectoException();
                    break;

                case "estadisticas":
                    if (com.length == 2) {
                        mostrarEstadisticasJugador(com[1]);
                    } else if (com.length == 1) {
                        mostrarEstadisticasPartida();
                    } else
                        throw new ComandoIncorrectoException();
                    break;

                case "hipotecar":
                    if (com.length == 2) {
                        accionhipotecar(com[1]);
                    } else
                        throw new ComandoIncorrectoException();
                    break;

                case "deshipotecar":
                    if (com.length == 2) {
                        acciondeshipotecar(com[1]);
                    } else
                        throw new ComandoIncorrectoException();
                    break;

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
                    else if (com.length == 3)
                        edificar(com[1], com[2]);
                    else
                        throw new ComandoIncorrectoException();
                    break;

                case "vender":
                    if (com.length == 4) {
                        desedificar(com[2], com[1], com[3]);
                    } else
                        throw new ComandoIncorrectoException();
                    break;

                case "fortuna":
                    if (!partida_empezada)
                        throw new EstadoPartidaException("La partida no está iniciada.");
                    this.jugadores.get(turno).setFortuna(Float.parseFloat(com[1]));
                    break;

                case "trato":
                    if (!partida_empezada)
                        throw new EstadoPartidaException("La partida no está iniciada.");
                    else if (!comprobaciones_trato(com))
                        throw new ComandoIncorrectoException();
                    else
                        trato(com);
                    break;

                case "eliminar":
                    if (com.length != 2)
                        throw new ComandoIncorrectoException();
                    else
                        eliminar_trato(com[1]);
                    break;

                case "aceptar":
                    if (com.length != 2)
                        throw new ComandoIncorrectoException();
                    else
                        aceptar_trato(com[1]);
                    break;

                default:
                    throw new ComandoIncorrectoException();
            }
        } catch (NumberFormatException nfe) {
            consola.imprimirError("Número/s mal introducido/s.");
        } catch (MonopolyException e) {
            consola.imprimirError(e.getMessage());
        }

    }

    /*
     * Método que realiza las acciones asociadas al comando 'describir jugador'.
     * Parámetro: comando introducido
     */
    private void descJugador(String[] partes) {
        try {
            for (Jugador J : this.jugadores) {
                if (J.getNombre().equals(partes[2])) {
                    consola.imprimirln(J.toString());
                    return;
                }
            }
            throw new NoExisteElementoException("El jugador " + partes[2] + " no existe.");
        } catch (NoExisteElementoException neee) {
            consola.imprimirError(neee.getMessage());
        }
    }

    // Sobrecarga: si no se pasa argumentos describe el jugador que tiene el turno
    // actual
    private void descJugador() {
        try {
            if (!partida_empezada)
                throw new EstadoPartidaException("No puedes describir un avatar antes de que empiece la partida.");
            consola.imprimirln("""
                    | Nombre: %s
                    | Avatar: %s
                    """.formatted(jugadores.get(turno).getNombre(), jugadores.get(turno).getAvatar().getId()));
        } catch (EstadoPartidaException epe) {
            consola.imprimirError(epe.getMessage());
        }
    }

    /*
     * Método que realiza las acciones asociadas al comando 'describir avatar'.
     * Parámetro: id del avatar a describir.
     */
    private void descAvatar(String ID) {
        try {
            for (int i = 1; i < avatares.size(); i++) {
                if (avatares.get(i).getId().equals(ID)) {
                    consola.imprimirln(avatares.get(i).getInfo());
                    return;
                }
            }
            throw new NoExisteElementoException("El avatar " + ID + " no existe.");
        } catch (NoExisteElementoException neee) {
            consola.imprimirError(neee.getMessage());
        }
    }

    /*
     * Método que realiza las acciones asociadas al comando 'describir
     * nombre_casilla'.
     * Parámetros: nombre de la casilla a describir.
     */
    private void descCasilla(String nombre) {
        try {
            Casilla c = this.tablero.encontrar_casilla(nombre);
            if (c == null)
                throw new NoExisteElementoException("La casilla " + nombre + " no existe.");
            consola.imprimir(c.infoCasilla(this.banca));
        } catch (NoExisteElementoException neee) {
            consola.imprimirError(neee.getMessage());
        }
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
        try {
            if (this.jugadores.get(turno).getEnCarcel())
                throw new CarcelException("No puedes lanzar los dados porque estás en la cárcel.");
            if (this.tirado)
                throw new AccionIncompatibleException("Ya has tirado en este turno.");

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
                    /*
                     * Si esta usando el movimiento avanzado del coche no cuenta,
                     * a no ser que sea la ultima tirada
                     */
                    && (!(av_coche != null && movimientoAvanzado.get(turno - 1))
                            || av_coche.getContadorTiradasCoche() == 4)) {

                if (av_coche != null)
                    /* TODO no se si se incrementa dos veces y esto sobra */
                    av_coche.incContadorTiradasCoche(); // solo puede tirar una vez si saca dobles al final
                jugador_puede_comprar = true;
                this.tirado = false;
                this.lanzamientos_dobles++;
                consola.imprimirln(("¡Has sacado dobles! Puedes tirar otra vez."));

                if (this.lanzamientos_dobles == 3) {
                    this.jugadores.get(turno).encarcelar(this.tablero.getPosiciones());
                    consola.imprimirln("¡Has sacado tres dobles seguidos! Vas a la cárcel sin pasar por salida.");
                    this.tirado = true;
                }
            }
        } catch (MonopolyException e) {
            consola.imprimirError(e.getMessage());
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

            // es solvente, hacer lo que sea
            if (!solvente)
                evaluarSolvente();

            if (!(this.avatares.get(turno) instanceof Pelota)) {

                if (this.avatares.get(turno) instanceof Coche)
                    tirado = ((Coche) this.avatares.get(turno)).getTirado();

                avatares.get(turno).evaluarAccion(valor1 + valor2, jugadores, tablero);
            }

            tirado = (tirado == true) ? true : jugadores.get(turno).getEnCarcel();
        }
    }

    private void cambairModo() {
        try {
            if (!partida_empezada)
                throw new EstadoPartidaException(
                        "No puedes cambiar el modo de movimiento antes de empezar la partida.");
            if (!movimientoAvanzadoSePuedeCambiar)
                throw new AccionIncompatibleException("¡Ya has cambiado de modo en este turno!");
            if (lanzamientos > 0)
                throw new AccionIncompatibleException("No puedes cambiar de modo después de lanzar los dados.");

            movimientoAvanzado.set(turno - 1, !movimientoAvanzado.get(turno - 1));
            movimientoAvanzadoSePuedeCambiar = false;

            if (movimientoAvanzado.get(turno - 1))
                consola.imprimirln("Se ha activado el modo avanzado");
            else
                consola.imprimirln("Se ha desactivado el modo avanzado");

        } catch (MonopolyException e) {
            consola.imprimirError(e.getMessage());
        }
    }

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
            }
        } while (opcion != 'S' && opcion != 's' && opcion != 'n' && opcion != 'N');
    }

    private void bancarrota(Jugador banca) {
        try {
            if (!partida_empezada)
                throw new EstadoPartidaException("No puedes decalrarte en bancarrota antes de que empiece la partida.");

            Jugador actual = this.jugadores.get(turno); // Jugador actual
            Casilla casillaactual = actual.getAvatar().getCasilla();

            // Quitar propiedades
            if ((!(casillaactual instanceof Propiedad)) || !actual.estaBancarrota()) { // Está en bancarrota por banca o
                                                                                       // se
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
            if ((casillaactual instanceof Propiedad && !((Propiedad) casillaactual).getDuenho().esBanca())) {// Es por
                                                                                                             // otro
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

            // Quitar tratos
            consola.imprimirln("Se eliminarán los tratos pendientes que implican al jugador " + actual.getNombre());
            Iterator<Trato> it = this.tratos.iterator();
            while (it.hasNext()) {
                Trato t = it.next();
                if (t.getProponedor().equals(actual) || t.getReceptor().equals(actual))
                    it.remove();
            }
            this.jugadores.remove(turno);
            this.avatares.remove(turno);
            this.movimientoAvanzado.remove(turno);
            movimientoAvanzado.set(turno - 1, false);
            this.tirado = true;

            if (this.turno > 0)
                --this.turno;

            if (this.jugadores.size() == 2) {
                consola.imprimirln("Sólo queda un jugador. La partida ha finalizado.");
                /* Esta linea creo que sobra */
                this.turno = 1; // Es para q el mensaje del q gane salga bien (creo)
                partida_finalizada = true;
                return;
            }

            /* Si esto esta a true se puede acabar turno evitando todas las restricciones */
            es_coche_y_no_puede_tirar = true;
            acabarTurno();
        } catch (EstadoPartidaException epe) {
            consola.imprimirError(epe.getMessage());
        }

    }

    private void accionhipotecar(String nombre) {
        Casilla c = this.tablero.encontrar_casilla(nombre);
        try {
            if (c == null)
                throw new NoExisteElementoException("La casilla " + nombre + " no existe.");

            else if (!(c instanceof Propiedad))
                throw new TipoPropiedadException("No puedes hipotecar " + nombre + " porque no es una propiedad.");

            else if (!partida_empezada)
                throw new EstadoPartidaException("No puedes hipotecar antes de que empiece la partida.");

            else {
                Propiedad p = (Propiedad) c;
                p.hipotecar(jugadores.get(turno));
                if (!jugadores.get(turno).estaBancarrota()) {
                    solvente = true;
                } else
                    consola.imprimirln("Aún no has saldado tus deudas.");
            }
        } catch (NumeroEdificiosException nee) {
            consola.imprimirError(nee.getMessage());
            consola.imprimirError("Edificios a demoler: " + ((Solar) c).listar_nombre_edificios());
            ;
        } catch (MonopolyException e) {
            consola.imprimirError(e.getMessage());
        }

    }

    private void acciondeshipotecar(String nombre) {
        try {
            Casilla c = this.tablero.encontrar_casilla(nombre);
            if (c == null)
                throw new NoExisteElementoException("La casilla " + nombre + " no existe.");

            else if (!(c instanceof Propiedad))
                throw new TipoPropiedadException("No puedes deshipotecar " + nombre + " porque no es una propiedad.");

            else if (!partida_empezada)
                throw new EstadoPartidaException("No puedes deshipotecar antes de que empiece la partida.");

            else {
                Propiedad p = (Propiedad) c;
                p.deshipotecar(jugadores.get(turno));
            }
        } catch (MonopolyException e) {
            consola.imprimirError(e.getMessage());
        }
    }

    private void lanzarDadosCarcel() {
        try {
            if (this.tirado)
                throw new AccionIncompatibleException("Ya has tirado en este turno.");

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
            } else if (!dadosDobles(dado1.getValor(), dado2.getValor())) {
                consola.imprimirln("No has sacado dobles! Dado1: " + dado1.getValor() + " Dado2: " + dado2.getValor());
                this.lanzamientos += 1;
            }

            this.jugadores.get(turno).setTurnosCarcel(this.jugadores.get(turno).getTurnosCarcel() + 1);
            this.tirado = true;
        } catch (AccionIncompatibleException aie) {
            consola.imprimirError(aie.getMessage());
        }

    }

    private void pagarCarcel() {
        try {
            if (this.tirado)
                throw new AccionIncompatibleException("Ya has tirado en este turno.");
            if (this.jugadores.get(turno).getFortuna() < Valor.PAGO_SALIR_CARCEL)
                throw new FortunaInsuficienteException(this.jugadores.get(turno).getFortuna(), Valor.PAGO_SALIR_CARCEL);

            this.tirado = false;
            this.jugadores.get(turno).setEnCarcel(false);
            this.jugadores.get(turno).sumarFortuna(-Valor.PAGO_SALIR_CARCEL);
            consola.imprimirln(
                    "Has pagado " + Valor.PAGO_SALIR_CARCEL + " para salir de la carcel. Puedes lanzar los dados.");
            this.jugadores.get(turno).setPagoTasasEImpuestos(
                    this.jugadores.get(turno).getPagoTasasEImpuestos() + Valor.PAGO_SALIR_CARCEL);
            this.banca.sumarGastos(Valor.PAGO_SALIR_CARCEL);
            consola.imprimirln("El bote de la banca ahora es " + this.banca.getGastos());
        } catch (MonopolyException e) {
            consola.imprimirError(e.getMessage());
        }
    }

    /*
     * Método que ejecuta todas las acciones realizadas con el comando 'comprar
     * nombre_casilla'.
     * Parámetro: cadena de caracteres con el nombre de la casilla.
     */
    private void comprar(String nombre) {
        try {
            Casilla casilla = tablero.encontrar_casilla(nombre);
            if (casilla == null)
                throw new NoExisteElementoException("La casilla " + nombre + " no existe.");

            /* Alguien se cargo esto en un commit :( */
            if (!jugador_puede_comprar && avatares.get(turno) instanceof Pelota)
                jugador_puede_comprar = true;

            /* Para la mierda del coche */
            if (!jugador_puede_comprar && movimientoAvanzado.get(turno - 1))
                throw new AccionIncompatibleException("Ya has comprado en este turno.");

            else if (!(casilla instanceof Propiedad))
                throw new TipoPropiedadException("No puedes comprar " + nombre + " porque no es una propiedad.");

            else if (!(lanzamientos > 0))
                throw new AccionIncompatibleException("No puedes comprar sin tirar los dados antes.");

            else {
                Propiedad p = (Propiedad) casilla;
                p.comprarCasilla(this.jugadores.get(turno), banca, movimientoAvanzado.get(turno - 1),
                        avatares.get(turno).getCasillasVisitadas());
                /*
                 * Esta variable se pone a true si los dados son dobles, asi para los que
                 * se mueven mas de una vez o pueden comprar mas de una no les dejan.
                 * En la pelota se pone a true si esta a false porque al parecer si puedes
                 * comprar todas las casillas que quieras
                 */
                jugador_puede_comprar = false;
            }

        } catch (MonopolyException e) {
            consola.imprimirError(e.getMessage());
        }
    }

    // Método que ejecuta todas las acciones relacionadas con el comando 'salir
    // carcel'.
    private void salirCarcel() {
        try {
            if (!partida_empezada)
                throw new EstadoPartidaException("No puedes salir de la cárcel antes de empezar la partida.");
            if (!this.jugadores.get(turno).getEnCarcel())
                throw new CarcelException("El jugador " + jugadores.get(turno).getNombre() + " no está en la cárcel.");
            if (this.tirado)
                throw new AccionIncompatibleException("Ya has tirado en este turno.");

            char opcion;
            do {
                consola.imprimirln("Como quieres salir de la cárcel?");
                consola.imprimirln("1) Lanzar dados (sacando dobles)");
                consola.imprimirln("2) Pagando el impuesto " + Valor.PAGO_SALIR_CARCEL);
                opcion = consola.leerChar("[" + jugadores.get(turno).getNombre() + "] ");
                switch (opcion) {
                    case '1':
                        lanzarDadosCarcel();
                        break;
                    case '2':
                        pagarCarcel();
                        break;
                }
            } while (opcion != '1' && opcion != '2');
        } catch (MonopolyException e) {
            consola.imprimirError(e.getMessage());
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
                    consola.imprimir(((Propiedad) c).toString());
                    consola.imprimir(", ");
                }
            }
        consola.imprimir("\n");
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
        try {
            if (grupo == null)
                throw new NoExisteElementoException("El grupo no existe.");

            consola.imprimirln(grupo.toString());
        } catch (NoExisteElementoException neee) {
            consola.imprimirError(neee.getMessage());
        }

    }

    private void mostrarEstadisticasJugador(String nombre) {
        try {
            for (Jugador J : this.jugadores) {
                if (J.getNombre().equals(nombre)) {
                    consola.imprimirln(J.estadisticasJugador());
                    return;
                }
            }
            throw new NoExisteElementoException("El jugador " + nombre + " no existe.");
        } catch (NoExisteElementoException neee) {
            consola.imprimirError(neee.getMessage());
        }
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
        try {
            if (!partida_empezada)
                throw new EstadoPartidaException("No puedes acabar turno antes de que empiece la partida.");
            if (!this.tirado)
                throw new AccionIncompatibleException("No has tirado los dados en este turno.");
            if (!this.solvente)
                throw new NoSolventeException(
                        "No has saldado tus deudas, hipoteca tus propiedades o vende tus edificios.");

            // es_coche_y_no_puede_tirar es lo mismo que this.tirado (creo). No lo cambio
            // para no joder nada (mas)
            if (partida_empezada && ((lanzamientos > 0 && this.tirado) || es_coche_y_no_puede_tirar) && this.solvente) {

                /* Esto no se donde meterlo, en cada turno se tiene que poner a true */
                movimientoAvanzadoSePuedeCambiar = true;
                jugador_puede_comprar = true;
                lanzamientos_dobles = 0;

                this.lanzamientos = 0;

                int numero_jugadores = this.jugadores.size() - 1; // La banca no cuenta
                if (this.turno < numero_jugadores) {
                    this.turno += 1;
                } else {
                    this.turno = 1; // Por la banca
                }
                consola.imprimirln("El jugador actual es: " + this.jugadores.get(turno).getNombre());
                consola.imprimirln("Tratos pendientes de " + this.jugadores.get(turno).getNombre() + ":");
                this.listarTratos();

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
                } else {
                    this.tirado = false;
                }
                es_coche_y_no_puede_tirar = this.tirado;
            }
        } catch (NoSolventeException nse) {
            consola.imprimirError(nse.getMessage());
            jugadores.get(turno).toString();
        } catch (MonopolyException e) {
            consola.imprimirError(e.getMessage());
        }
    }

    // Método que finaliza la partida (a la fuerza)
    public static void acabarPartida() {
        consola.imprimirln("Finalizando partida");
        /* Esto es un poco criminal */
        System.exit(0);
    }

    private void edificar(String tipo, String solar) {
        try {
            /*
             * Si no es la pelota o no esta en movimiento avanzado llamar a edificar normal
             */
            Casilla c = tablero.encontrar_casilla(solar);
            if (c == null)
                throw new NoExisteElementoException("La casilla " + solar + "no existe");

            if (!(avatares.get(turno) instanceof Pelota) || !movimientoAvanzado.get(turno - 1)) {

                if (c != avatares.get(turno).getCasilla())
                    throw new NoExisteElementoException("No puedes edificar en una casilla en la que no caiste");

                edificar(tipo);
                return;
            }

            /*
             * La casilla C pertenece a las casillas visitadas por la pelota en los rebotes
             * del modo avanzado
             */
            if (((Pelota) avatares.get(turno)).getCasillasVisitadas().contains(c)) {
                edificar(tipo, c);

            } else
                throw new NoExisteElementoException("No puedes edificar en una casilla en la que no caiste");

        } catch (MonopolyException e) {
            consola.imprimirError(e.getMessage());
        }
    }

    private void edificar(String tipo) {
        edificar(tipo, this.jugadores.get(turno).getAvatar().getCasilla());
    }

    private void edificar(String tipo, Casilla c) {
        /*
         * Este metodo asume que la casilla es una casilla donde el jugador
         * actual esta o rebotó siendo la pelota
         */
        try {
            if (!partida_empezada)
                throw new EstadoPartidaException("No puedes edificar antes de que empiece la partida.");

            /* Comprobacion que sobra hasta que no sobre */
            if (c == null)
                throw new TipoPropiedadException("La casilla no existe");

            if (!(c instanceof Solar))
                throw new TipoPropiedadException("No puedes edificar en " + c.getNombre() + " porque no es un solar.");

            else
                ((Solar) c).edificar(tipo, this.jugadores.get(this.turno));

        } catch (CasaEdificableException cee) {
            consola.imprimirError(cee.getMessage());
            consola.imprimirError("Hay " + ((Solar) c).obtenerNumeroCasas() + " casas en " + c.getNombre());

        } catch (HotelEdificableException hee) {
            consola.imprimirError(hee.getMessage());
            consola.imprimirError("Hay " + ((Solar) c).obtenerNumeroCasas() + " casas en " + c.getNombre());
            consola.imprimirError("Hay " + ((Solar) c).obtenerNumeroHoteles() + " hoteles en " + c.getNombre());

        } catch (PiscinaEdificableException psee) {
            consola.imprimirError(psee.getMessage());
            consola.imprimirError("Hay " + ((Solar) c).obtenerNumeroCasas() + " casas en " + c.getNombre());
            consola.imprimirError("Hay " + ((Solar) c).obtenerNumeroHoteles() + " hoteles en " + c.getNombre());
            consola.imprimirError("Hay " + ((Solar) c).obtenerNumeroPiscinas() + " piscinas en " + c.getNombre());

        } catch (PistaEdificableException ptee) {
            consola.imprimirError(ptee.getMessage());
            consola.imprimirError("Hay " + ((Solar) c).obtenerNumeroHoteles() + " hoteles en " + c.getNombre());
            consola.imprimirError(
                    "Hay " + ((Solar) c).obtenerNumeroPistasDeporte() + " pistas de deporte en " + c.getNombre());

        } catch (MonopolyException e) {
            consola.imprimirError(e.getMessage());
        }
    }

    private void desedificar(String casilla, String tipoedificio, String n) {
        try {
            if (!partida_empezada)
                throw new EstadoPartidaException("No puedes vender edificios antes de que empiece la partida.");

            Casilla c = this.tablero.encontrar_casilla(casilla);

            if (c == null)
                throw new NoExisteElementoException("La casilla " + casilla + " no existe.");

            if (!(c instanceof Solar))
                throw new TipoPropiedadException(
                        "No puedes vender edificios de " + c.getNombre() + " porque no es un solar.");

            ((Solar) c).desedificar(tipoedificio, this.jugadores.get(turno), n);
            if (!jugadores.get(turno).estaBancarrota()) {
                solvente = true;
            } else
                consola.imprimirln("Aún no has saldado tus deudas.");

        } catch (NumberFormatException nfe) {
            consola.imprimirError(n + " no es un entero.");
        } catch (Exception e) {
            consola.imprimirError(e.getMessage());
        }
    }

    Jugador obtenerJugadorDadoNombre(String nombreJugador) {
        for (Jugador j : this.jugadores) {
            if (j.getNombre().equals(nombreJugador))
                return j;
        }
        return null;

    }

    // TRATOS
    // ------------------------------------------------------------------------------------------------

    private boolean comprobaciones_trato(String[] com) {
        if (!(com.length == 5 || com.length == 6) || !com[2].equals("cambiar"))
            return false;
        return true;
    }

    private void trato(String[] com) {
        try {
            Jugador proponedor = this.jugadores.get(turno);
            Jugador receptor = obtenerJugadorDadoNombre(com[1]);

            if (receptor == null)
                throw new NoExisteElementoException("El jugador " + com[1] + " no existe.");

            if (receptor.equals(proponedor))
                throw new AccionIncompatibleException("No puedes proponerte un trato a ti mismo.");

            switch (com.length) {
                case 5: // tratos P-P, P-D, D-P
                    Casilla c1 = this.tablero.encontrar_casilla(com[3]);
                    Casilla c2 = this.tablero.encontrar_casilla(com[4]);

                    if (c1 != null && c2 != null) { // P-P
                        if (!(c1 instanceof Propiedad))
                            throw new TipoPropiedadException("No puedes proponer " + c1.getNombre()
                                    + " en un trato porque no es una propiedad.");
                        if (!(c2 instanceof Propiedad))
                            throw new TipoPropiedadException("No puedes proponer " + c2.getNombre()
                                    + " en un trato porque no es una propiedad.");

                        Propiedad p1 = (Propiedad) c1;
                        Propiedad p2 = (Propiedad) c2;

                        Trato t = new Trato(proponedor, receptor, p1, p2);
                        this.tratos.add(t);
                        consola.imprimir(t.toString());
                    }

                    else if (c1 != null && c2 == null) { // P-D
                        if (!(c1 instanceof Propiedad))
                            throw new TipoPropiedadException("No puedes proponer " + c1.getNombre()
                                    + " en un trato porque no es una propiedad.");

                        Propiedad p1 = (Propiedad) c1;
                        float d2 = Float.parseFloat(com[4]);
                        if (d2 < 0)
                            throw new NumberFormatException();

                        Trato t = new Trato(proponedor, receptor, p1, d2);
                        this.tratos.add(t);
                        consola.imprimir(t.toString());
                    }

                    else if (c1 == null && c2 != null) { // D-P
                        if (!(c2 instanceof Propiedad))
                            throw new TipoPropiedadException("No puedes proponer " + c2.getNombre()
                                    + " en un trato porque no es una propiedad.");

                        float d1 = Float.parseFloat(com[3]);
                        if (d1 < 0)
                            throw new NumberFormatException();
                        Propiedad p2 = (Propiedad) c2;

                        Trato t = new Trato(proponedor, receptor, d1, p2);
                        this.tratos.add(t);
                        consola.imprimir(t.toString());
                    }

                    else
                        throw new ComandoIncorrectoException();
                    break;

                case 6: // tratos P-P+D, P+D-P
                    Casilla ca1 = this.tablero.encontrar_casilla(com[3]);
                    Casilla ca2 = this.tablero.encontrar_casilla(com[4]);

                    if (ca1 != null && ca2 != null) { // P-P+D
                        if (!(ca1 instanceof Propiedad))
                            throw new TipoPropiedadException("No puedes proponer " + ca1.getNombre()
                                    + " en un trato porque no es una propiedad.");
                        if (!(ca2 instanceof Propiedad))
                            throw new TipoPropiedadException("No puedes proponer " + ca2.getNombre()
                                    + " en un trato porque no es una propiedad.");

                        Propiedad p1 = (Propiedad) ca1;
                        Propiedad p2 = (Propiedad) ca2;
                        float d2 = Float.parseFloat(com[5]);
                        if (d2 < 0)
                            throw new NumberFormatException();

                        Trato t = new Trato(proponedor, receptor, p1, p2, d2);
                        this.tratos.add(t);
                        consola.imprimir(t.toString());
                    }

                    else if (ca1 != null && ca2 == null) { // P+D-P
                        ca2 = this.tablero.encontrar_casilla(com[5]);
                        if (ca2 == null)
                            throw new NoExisteElementoException("La casilla " + com[5] + " no existe.");

                        if (!(ca1 instanceof Propiedad))
                            throw new TipoPropiedadException("No puedes proponer " + ca1.getNombre()
                                    + " en un trato porque no es una propiedad.");
                        if (!(ca2 instanceof Propiedad))
                            throw new TipoPropiedadException("No puedes proponer " + ca2.getNombre()
                                    + " en un trato porque no es una propiedad.");

                        Propiedad p1 = (Propiedad) ca1;
                        Propiedad p2 = (Propiedad) ca2;
                        float d1 = Float.parseFloat(com[4]);
                        if (d1 < 0)
                            throw new NumberFormatException();

                        Trato t = new Trato(proponedor, receptor, p1, d1, p2);
                        this.tratos.add(t);
                        consola.imprimir(t.toString());
                    } else
                        throw new ComandoIncorrectoException();
                    break;

                default:
                    throw new ComandoIncorrectoException();
            }
        } catch (NumberFormatException nfe) {
            consola.imprimirError("Número/s mal introducido/s.");
        } catch (MonopolyException e) {
            consola.imprimirError(e.getMessage());
        }
    }

    private void aceptar_trato(String ID) {
        try {
            Iterator<Trato> it = this.tratos.iterator();
            while (it.hasNext()) {
                Trato t = it.next();
                if (t.getID().equals(ID)) {
                    if (!this.jugadores.get(turno).equals(t.getReceptor()))
                        throw new AccionIncompatibleException("No puedes aceptar un trato que no está dirigido a ti.");

                    if (t.getPropiedad_p() != null) {
                        if (t.getPropiedad_p().getHipotecada()) {
                            char opcion = consola.leerChar("La propiedad " + t.getPropiedad_p().getNombre()
                                    + " está hipotecada, ¿seguro que desea proceder? (S/N): ");
                            do {
                                switch (opcion) {
                                    case 's':
                                    case 'S':
                                        break;

                                    case 'n':
                                    case 'N':
                                        return;
                                }
                            } while (opcion != 'S' && opcion != 's' && opcion != 'n' && opcion != 'N');
                        }
                    }
                    consola.imprimirln("Se intenta aceptar el trato: ");
                    consola.imprimir(t.toString());
                    t.aceptar();
                    it.remove();
                    return;
                }
            }
            throw new NoExisteElementoException("El trato " + ID + " no existe.");

        } catch (MonopolyException e) {
            consola.imprimirError(e.getMessage());
        }
    }

    private void eliminar_trato(String ID) {
        try {
            Iterator<Trato> it = this.tratos.iterator();
            while (it.hasNext()) {
                Trato t = it.next();
                if (t.getID().equals(ID)) {
                    if (!this.jugadores.get(turno).equals(t.getProponedor()))
                        throw new AccionIncompatibleException("No puedes eliminar un trato que no has propuesto.");
                    consola.imprimirln("Se elimina el trato: ");
                    consola.imprimir(t.toString());
                    it.remove();
                    return;
                }
            }
            throw new NoExisteElementoException("El trato " + ID + " no existe.");

        } catch (MonopolyException e) {
            consola.imprimirError(e.getMessage());
        }
    }

    private void listarTratos() {

        boolean comprobacion = false;
        if (this.tratos.size() == 0) {
            consola.imprimirln("No hay tratos pendientes en la partida.");
            return;
        }

        for (Trato t : this.tratos) {
            if (t.getReceptor().equals(this.jugadores.get(turno))) {
                comprobacion = true;
                consola.imprimir(t.toString());
            }
        }

        if (comprobacion == false) {
            consola.imprimirln("No hay tratos pendientes para " + this.jugadores.get(turno).getNombre());
        }
    }

}
