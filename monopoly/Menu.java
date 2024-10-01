package monopoly;

import java.util.ArrayList;
import java.util.Scanner;
import org.xml.sax.ext.DefaultHandler2;
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

    public Menu() {
        iniciarPartida();
    }

    // Método para inciar una partida: crea los jugadores y avatares.
    private void iniciarPartida() {
        this.turno = 1;
        Jugador banca = new Jugador();
        this.jugadores.add(banca);
        this.tablero = new Tablero(banca);
        Casilla casilla = this.tablero.obtenerCasilla(1);
        Scanner scanner = new Scanner(System.in);
        String tipoAvatar = new String("Avatar No Valido");
        String nombreJugador;
        do {
            System.out.println("Introduce nombre de jugador: ");
            nombreJugador = new String(scanner.next());
            if(nombreJugador.equals("Stop"))
            while (!tipoAvatar.equals("Esfinge") && !tipoAvatar.equals("Sombrero") && !tipoAvatar.equals("Pelota")
                    && !tipoAvatar.equals("Coche")) {
                System.out.println("Introduce tu avatar: [ Esfinge / Pelota / Coche / Sombrero ] ");
                tipoAvatar = scanner.next();
            }
            Avatar avatar = new Avatar(tipoAvatar, casilla, this.avatares);
            this.avatares.add(avatar);
            Jugador jugador = new Jugador(nombreJugador, tipoAvatar, casilla, avatares);
            avatar.setJugador(jugador);
            this.jugadores.add(jugador);
        } while (nombreJugador.equals("Stop"));
        scanner.close();
    }

    /*
     * Método que interpreta el comando introducido y toma la accion
     * correspondiente.
     * Parámetro: cadena de caracteres (el comando).
     */
    private void analizarComando(String comando) {
        System.out.println("$>");
        switch (comando) {
            case "lanzar dados":
                lanzarDados();
                break;
            case "salir carcel":
                salirCarcel();
                break;
            case "acabar turno":
                acabarTurno();
                break;
            case "describir CASILLA":
                // descCasilla(); TODO
                break;
            case "Comprar CASILLA": // TODO
                break;
            case "listar enventa":
                break;
            case "mostrar tablero":
                System.out.println(tablero);
                break;
            case "acabar partida":
                acabarPartida();
                break;

            default:
                System.out.println("Opcion incorrecta.\n");
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
        if( this.tirado == false){
        this.dado1.hacerTirada();
        this.dado2.hacerTirada();
        this.tirado = true;
        int desplazamiento = this.dado1.getValor() + this.dado2.getValor();
        System.out.println("El avatar" + this.avatares.get(turno).getId() + "avanza" + desplazamiento + "desde" + this.avatares.get(turno).getCasilla().getNombre() ); //TODO
        }else{
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
    }

    // Método que ejecuta todas las acciones relacionadas con el comando 'salir
    // carcel'.
    private void salirCarcel() {
        if (this.jugadores.get(turno).getEnCarcel() == true) {

            this.jugadores.get(turno).setEnCarcel(false);
            this.jugadores.get(turno).setFortuna(this.jugadores.get(turno).getFortuna() - Valor.PAGO_SALIR_CARCEL);
        } else {
            System.out.println("El jugador" + this.jugadores.get(turno).getNombre() + "no está en la cárcel.\n");
        }
    }

    // Método que realiza las acciones asociadas al comando 'listar enventa'.
    private void listarVenta() {
    }

    // Método que realiza las acciones asociadas al comando 'listar jugadores'.
    private void listarJugadores() {
        System.out.println("Jugadores:");
        for (Jugador j : jugadores) {
            if (!j.esBanca()){
                System.out.println(j);
            }
        }
    }

    // Método que realiza las acciones asociadas al comando 'listar avatares'.
    private void listarAvatares() {
    }

    // Método que realiza las acciones asociadas al comando 'acabar turno'.
    private void acabarTurno() {
        this.turno += this.turno;
    }

    public static void acabarPartida(){
        System.out.println("FINALIZANDO PARTIDA");
        System.exit(0);
    }

}
