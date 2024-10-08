package monopoly;

import java.util.ArrayList;
import java.util.Scanner;
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

    public Menu() {
        iniciarPartida();
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
        Scanner scanner = new Scanner(System.in);
        Jugador banca = new Jugador();
        this.avatares.add(null); // avatar banca
        this.jugadores.add(banca);
        this.tablero = new Tablero(banca);
        while (!partida_finalizada) {
            System.out.print("\n[>]: ");
            analizarComando(scanner.nextLine());
        }
        scanner.close();
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
                System.out.println("crear jugador <nombre> <tipo_avatar>");
                System.out.println("jugador");
                System.out.println("listar enventa");
                System.out.println("listar jugadores");
                System.out.println("listar avatares");
                System.out.println("lanzar dados");
                System.out.println("acabar");
                System.out.println("salir (carcel)");
                System.out.println("describir jugador  <nombre>");
                System.out.println("describir avatar <letra");
                System.out.println("comprar <casilla>");
                System.out.println("ver");
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

                }
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
                    break;
                }

            case "lanzar":

                if (com.length == 2 && com[1].equals("dados")) {
                    if (jugadores.size() >= 3) { // iniciar la partida
                        partida_empezada = true;
                        lanzarDados();
                        System.out.println(this.tablero);
                    } else
                        System.out.println("No tienes suficientes jugadores creados! (Mínimo 2).");
                    break;
                } else if (com.length == 2 && com[1].equals("dadoss")) {
                    lanzarDados(3, 3);
                    break;
                }

            case "acabar":
                acabarTurno();
                break;

            case "salir":
                salirCarcel();
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
                    break;
                }

            case "ver":
                System.out.println(this.tablero);
                break;

            case "SALIR":
                this.partida_finalizada = true;
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
                System.out.println(J.toString());
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
    private boolean dadosDobles(Dado d1, Dado d2) {
        return (d1.getValor() == d2.getValor());
    }

    private void lanzarDados() {
        if (this.lanzamientos < 2) {
            if (!this.tirado) {

                int casillaantes = avatares.get(turno).getCasilla().getPosicion(), casillanueva;
                this.dado1.hacerTirada();
                this.dado2.hacerTirada();
                this.tirado = true;
                this.lanzamientos += 1;
                int desplazamiento = this.dado1.getValor() + this.dado2.getValor();
                System.out
                        .print("El avatar " + this.avatares.get(turno).getId() + " avanza " + desplazamiento + " desde "
                                + this.avatares.get(turno).getCasilla().getNombre() + " hasta ");

                this.avatares.get(turno).moverAvatar(this.tablero.getPosiciones(), desplazamiento);
                System.out.println(avatares.get(turno).getCasilla().getNombre());

                casillanueva = avatares.get(turno).getCasilla().getPosicion();
                if ((casillaantes > casillanueva) && (casillanueva > 0)) {
                    System.out.println("¡Has pasado por la casilla! Ganaste "+ Valor.SUMA_VUELTA_ACTUAL);
                    jugadores.get(turno).sumarFortuna(Valor.SUMA_VUELTA_ACTUAL);
                    jugadores.get(turno).setVueltas(jugadores.get(turno).getVueltas()+1);
                }

                if (dadosDobles(dado1, dado2)) {
                    this.tirado = false;
                    System.out.println("Has sacado dobles! Puedes volver a lanzar los dados. ");
                }

                int vueltasmin = this.jugadores.get(turno).getVueltas();
                for(Jugador j: this.jugadores){
                    if (j.getVueltas() <= vueltasmin) vueltasmin = j.getVueltas();
                }
                if (vueltasmin < this.jugadores.get(turno).getVueltas()){
                    vueltasmin += 1;
                    if (this.jugadores.get(turno).getVueltas() % 4 == 0) //TODO
                }

            }
        } else {
            this.jugadores.get(turno).encarcelar(this.tablero.getPosiciones());
            System.out.println("Has sacado tres dobles seguidos! Vas a la carcel sin pasar por salida.");
        }
    }

    // sobrecarga de lanzar dados en la cual elegimos qué valor sacan los dados
    private void lanzarDados(int valor1, int valor2) {
        if (this.lanzamientos < 2) {
            if (!this.tirado) {

                int casillaantes = avatares.get(turno).getCasilla().getPosicion(), casillanueva;
                this.dado1.setValor(valor1);
                this.dado2.setValor(valor2);
                this.tirado = true;
                this.lanzamientos += 1;
                int desplazamiento = this.dado1.getValor() + this.dado2.getValor();
                System.out
                        .print("El avatar " + this.avatares.get(turno).getId() + " avanza " + desplazamiento + " desde "
                                + this.avatares.get(turno).getCasilla().getNombre() + " hasta ");

                this.avatares.get(turno).moverAvatar(this.tablero.getPosiciones(), desplazamiento);
                System.out.println(avatares.get(turno).getCasilla().getNombre());

                casillanueva = avatares.get(turno).getCasilla().getPosicion();
                if ((casillaantes > casillanueva) && (casillanueva > 0)) {
                    jugadores.get(turno).sumarFortuna(Valor.SUMA_VUELTA);
                }

                if (dadosDobles(dado1, dado2)) {
                    this.tirado = false;
                    System.out.println("Has sacado dobles! Puedes volver a lanzar los dados. ");
                }
            }
        } else {
            this.jugadores.get(turno).encarcelar(this.tablero.getPosiciones());
            System.out.println("Has sacado tres dobles seguidos! Vas a la carcel sin pasar por salida.");
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
        casilla.comprarCasilla(this.jugadores.get(turno), this.jugadores.get(0));
    }

    // Método que ejecuta todas las acciones relacionadas con el comando 'salir
    // carcel'.
    private void salirCarcel() {
        if (this.jugadores.get(turno).getEnCarcel() == true) {

            this.jugadores.get(turno).setEnCarcel(false);
            this.jugadores.get(turno).sumarFortuna(-Valor.PAGO_SALIR_CARCEL);
            this.jugadores.get(turno).setEnCarcel(false);
            System.out.println(this.jugadores.get(turno) + "paga " + Valor.PAGO_SALIR_CARCEL
                    + " y sale de la cárcel. Puede lanzar los dados.");
        } else {
            System.out.println("El jugador " + this.jugadores.get(turno).getNombre() + " no está en la cárcel.");
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
            if (this.tablero.obtenerCasilla(i).getDuenho() == banca
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
        } else {
            System.out.println("La partida todavia no ha empezado. ");
        }
    }

    // Método que finaliza la partida
    public static void acabarPartida() {
        System.out.println("FINALIZANDO PARTIDA");
        System.exit(0);
    }
}
