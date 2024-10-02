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
    private int lanzamientos; // Variable para contar el número de lanzamientos de un jugador en un turno.
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
        Avatar avatar = new Avatar(tipoAvatar, this.tablero.posicion_salida(), this.avatares);
        this.avatares.add(avatar);
        Jugador jugador = new Jugador(nombreJugador, tipoAvatar, this.tablero.posicion_salida(), this.avatares);
        avatar.setJugador(jugador);
        this.jugadores.add(jugador);
    }

    // Método para inciar una partida: crea los jugadores y avatares.
    private void iniciarPartida() {
        this.turno = 1;
        Scanner scanner = new Scanner(System.in);
        Jugador banca = new Jugador();
        this.avatares.add(null); // avatar banca
        this.jugadores.add(banca);
        this.tablero = new Tablero(banca);
        while (!partida_finalizada)
            analizarComando(scanner.nextLine());
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
            case "crear":
                if (partida_empezada)
                    System.out.println("La partida ya esta iniciada!");
                else if (com[1] == "jugador")
                    crear_jugador(com[2], com[3]);
                break;

            case "jugador":
                descJugador(com);
                break;

            case "listar":
                if (com[1] == "enventa")
                    listarVenta();
                if (com[1] == "jugadores")
                    listarJugadores();
                if (com[1] == "avatares")
                    listarAvatares();
                break;

            case "lanzar":
                if (!partida_empezada) // iniciar la partida
                    partida_empezada = true;

                if (com[1] == "dados")
                    lanzarDados();
                break;

            case "acabar":
                acabarTurno();
                break;

            case "salir":
                salirCarcel();
                break;

            case "describir":
                switch (com[1]) {
                    case "jugador": // describir jugador
                        descJugador(com);
                        break;
                    case "avatar": // describir avatar
                        descAvatar(com[2]);
                        break;
                    default: // describir casilla
                        descCasilla(com[1]);
                        break;
                }
                break;

            case "comprar":
                comprar(com[1]);
                break;

            case "ver":
                System.out.println(this.tablero);
                break;

            case "SALIR":
                this.partida_finalizada = true;
                break;
            default:
                System.out.println("Opcion incorrecta.");
                break;
        }
    }

    /*
     * Método que realiza las acciones asociadas al comando 'describir jugador'.
     * Parámetro: comando introducido
     */
    private void descJugador(String[] partes) {
    }

    /*
     * Método que realiza las acciones asociadas al comando 'describir avatar'.
     * Parámetro: id del avatar a describir.
     */
    private void descAvatar(String ID) {
        for (Avatar A : this.avatares) {
            if (A != null && A.getId().equals(ID)) {
                A.toString();
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
        System.out.println(tablero.encontrar_casilla(nombre));
    }

    // Método que ejecuta todas las acciones relacionadas con el comando 'lanzar
    // dados'.
    private void lanzarDados() {
        if (this.tirado == false) {
            this.dado1.hacerTirada();
            this.dado2.hacerTirada();
            this.tirado = true;
            int desplazamiento = this.dado1.getValor() + this.dado2.getValor();
            System.out.print("El avatar" + this.avatares.get(turno).getId() + "avanza" + desplazamiento + "desde"
                    + this.avatares.get(turno).getCasilla().getNombre() + "hasta");
            this.avatares.get(turno).moverAvatar(this.tablero.getPosiciones(), desplazamiento);
            System.out.println(avatares.get(turno).getCasilla().getNombre());
        } else {
            System.out.println("Ya has tirado en este turno.");
        }
        // TODO
    }

    /*
     * Método que ejecuta todas las acciones realizadas con el comando 'comprar
     * nombre_casilla'.
     * Parámetro: cadena de caracteres con el nombre de la casilla.
     */
    private void comprar(String nombre) {
        
        Casilla casilla = tablero.encontrar_casilla(nombre);
        if (casilla.getDuenho().esBanca() == true){
        jugadores.get(turno).sumarFortuna(-casilla.getValor());
        jugadores.get(turno).anhadirPropiedad(casilla);
        casilla.setDuenho(jugadores.get(turno));
        System.out.println("El jugador " + jugadores.get(turno).getNombre() + " ha comprado " + casilla.getNombre()
                + " por " + casilla.getValor() + ".");
        System.out.println("Su fortuna restante es " + jugadores.get(turno).getFortuna());
        }else{
            System.out.println("La casilla" + casilla.getNombre() + "ya tiene duenho");
        }
    }

    // Método que ejecuta todas las acciones relacionadas con el comando 'salir
    // carcel'.
    private void salirCarcel() {
        if (this.jugadores.get(turno).getEnCarcel() == true) {

            this.jugadores.get(turno).setEnCarcel(false);
            this.jugadores.get(turno).sumarFortuna(-Valor.PAGO_SALIR_CARCEL);
            System.out.println(this.jugadores.get(turno) + "paga " + Valor.PAGO_SALIR_CARCEL + " y sale de la cárcel. Puede lanzar los dados.");
        } else {
            System.out.println("El jugador" + this.jugadores.get(turno).getNombre() + "no está en la cárcel.\n");
        }
    }

    // Método que realiza las acciones asociadas al comando 'listar enventa'.
    private void listarVenta() {
        for(int i = 0; i<40; i++){
        if(this.tablero.obtenerCasilla(i).getDuenho()==banca && (this.tablero.obtenerCasilla(i).getTipo().equals("solar") 
        || this.tablero.obtenerCasilla(i).getTipo().equals("transporte") || this.tablero.obtenerCasilla(i).getTipo().equals("servicios"))){
            this.tablero.obtenerCasilla(i).infoCasilla();
        }

        }
    }

    // Método que realiza las acciones asociadas al comando 'listar jugadores'.
    private void listarJugadores() {
        System.out.println("Jugadores:");
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
                A.toString();
                System.out.println("\n");
            }
        }
    }

    // Método que realiza las acciones asociadas al comando 'acabar turno'.
    private void acabarTurno() {
        int numero_jugadores = this.jugadores.size() - 1; // La banca no cuenta
        if (numero_jugadores > 1){
            if(this.turno < numero_jugadores){
            this.turno += 1; 
            }
            else{
                this.turno = 1; //Por la banca

            }
    }else{
        System.out.println("Todavía no hay jugadores creados!");
        }
    }

    // Método que finaliza la partida
    public static void acabarPartida() {
        System.out.println("FINALIZANDO PARTIDA");
        System.exit(0);
    }

}
