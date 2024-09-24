package partida;
import java.util.ArrayList;
import java.util.Random;
import monopoly.*;


public class Avatar {

    //Atributos
    private String id; //Identificador: una letra generada aleatoriamente.
    private String tipo; //Sombrero, Esfinge, Pelota, Coche
    private Jugador jugador; //Un jugador al que pertenece ese avatar.
    private Casilla lugar; //Los avatares se sitúan en casillas del tablero.

    //Constructor vacío
    public Avatar() {
    }
    public Avatar(String tipo, Jugador jugador, Casilla lugar){

        this.tipo = tipo;
        this.jugador = jugador;
        this.lugar = lugar;
        //this.id = generarId(avCreados);
    }

    /*Constructor principal. Requiere éstos parámetros:
    * Tipo del avatar, jugador al que pertenece, lugar en el que estará ubicado, y un arraylist con los
    * avatares creados (usado para crear un ID distinto del de los demás avatares).
     */
    private boolean checkAvatar(String x){
        return (x == "Sombrero" || x == "Esfinge" || x == "Pelota" || x == "Coche");
    }
    public Avatar(String tipo, Jugador jugador, Casilla lugar, ArrayList<Avatar> avCreados) {
        if(checkAvatar(tipo)){
            this.tipo = tipo;
        }
        this.jugador= jugador;
        this.lugar= lugar;
        generarId(avCreados);

    }

    public void moverAvatar(ArrayList<ArrayList<Casilla>> casillas, int valorTirada) {
        /**
         * Experimental:
         * es posible que funcione, pero probable que no
         *
         * Como funciona: Pasa la posicion del jugador a un indice [0, 40)
         * Convierte este indice a un indice del primer array y otro del
         * segundo array de casillas.
         */
        int posi = 0;
        for (int i = 0; i < 4; i++)
        if (casillas.get(i).contains(this.lugar)){
            posi+= casillas.get(i).indexOf(lugar);
            posi += 10 * i;
        }
        posi += valorTirada;
        lugar = casillas.get(posi/10).get(posi%10);
    }

    /*Método que permite generar un ID para un avatar. Sólo lo usamos en esta clase (por ello es privado).
    * El ID generado será una letra mayúscula. Parámetros:
    * - Un arraylist de los avatares ya creados, con el objetivo de evitar que se generen dos ID iguales.
     */
    private void generarId(ArrayList<Avatar> avCreados) {
        Random rnd = new Random();
        String letra = String.valueOf((char) ('A' + rnd.nextInt(26)));
        int aseguradodistinto = 0; 
        while (aseguradodistinto == 0){
            for(Avatar A:avCreados){
                if(A.id == letra){
                    letra = String.valueOf((char) ('A' + rnd.nextInt(26)));
                    aseguradodistinto = 0;
                    break;
                }
                aseguradodistinto =1;
            }
        }
        this.id = letra;
        }

        
        public void setTipo(String tipo){
            this.tipo = tipo;
        }
        public void setLugar(Casilla lugar){
            this.lugar = lugar;
        }

    }
