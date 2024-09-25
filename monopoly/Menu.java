package monopoly;

import java.util.ArrayList;
import java.util.Scanner;
import partida.*;

public class Menu {

    //Atributos
    private ArrayList<Jugador> jugadores = new ArrayList<Jugador>(); //Jugadores de la partida.
    private ArrayList<Avatar> avatares = new ArrayList<Avatar>(); //Avatares en la partida.
    private int turno = 0; //Índice correspondiente a la posición en el arrayList del jugador (y el avatar) que tienen el turno
    private int lanzamientos; //Variable para contar el número de lanzamientos de un jugador en un turno.
    private Tablero tablero; //Tablero en el que se juega.
    private Dado dado1; //Dos dados para lanzar y avanzar casillas.
    private Dado dado2;
    private Jugador banca; // El jugador banca.
    private boolean tirado; // Booleano para comprobar si el jugador que tiene el turno ha tirado o no.
    private boolean solvente; // Booleano para comprobar si el jugador que tiene el turno es solvente, es
                              // decir, si ha pagado sus deudas.

    public Menu() {
        iniciarPartida();
        tablero = new Tablero(banca);
        System.out.println(tablero);
    }

    // Método para inciar una partida: crea los jugadores y avatares.
    private void iniciarPartida() {
        Jugador banca = new Jugador();
        Tablero tablero = new Tablero(banca);
        Casilla casilla = tablero.obtenerCasilla(1);
        Scanner scanner = new Scanner(System.in);
        String tipoAvatar = new String("Avatar No Valido");
        System.out.println("Introduce nombre de jugador: ");
        String nombreJugador = scanner.next();
        
        while (!tipoAvatar.equals("Sombrero") && !tipoAvatar.equals("Esfinge") && !tipoAvatar.equals("Pelota") && !tipoAvatar.equals("Coche")){

        System.out.println("Introduce tu avatar: [Esfinge / Sombrero / Pelota / Coche] ");
        tipoAvatar = scanner.next();

        }

        Avatar avatar = new Avatar(tipoAvatar,casilla,this.avatares);
        this.avatares.add(avatar);
        Jugador jugador = new Jugador(nombreJugador,tipoAvatar,casilla,avatares);
        avatar.setJugador(jugador);
        this.jugadores.add(jugador);
        scanner.close();
    }

    /*
     * Método que interpreta el comando introducido y toma la accion
     * correspondiente.
     * Parámetro: cadena de caracteres (el comando).
     */
    private void analizarComando(String comando) {
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
    }

    /*
     * Método que realiza las acciones asociadas al comando 'describir
     * nombre_casilla'.
     * Parámetros: nombre de la casilla a describir.
     */
    private void descCasilla(String nombre) {
    }

    // Método que ejecuta todas las acciones relacionadas con el comando 'lanzar
    // dados'.
    private void lanzarDados() {
    }

    /*
     * Método que ejecuta todas las acciones realizadas con el comando 'comprar
     * nombre_casilla'.
     * Parámetro: cadena de caracteres con el nombre de la casilla.
     */
    private void comprar(String nombre) {
    }

    // Método que ejecuta todas las acciones relacionadas con el comando 'salir
    // carcel'.
    private void salirCarcel() {
    }

    // Método que realiza las acciones asociadas al comando 'listar enventa'.
    private void listarVenta() {
    }

    // Método que realiza las acciones asociadas al comando 'listar jugadores'.
    private void listarJugadores() {
    }

    // Método que realiza las acciones asociadas al comando 'listar avatares'.
    private void listarAvatares() {
    }

    // Método que realiza las acciones asociadas al comando 'acabar turno'.
    private void acabarTurno() {
    }

}
