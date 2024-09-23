package monopoly;
// hola soy guillee
import partida.*;
import java.util.ArrayList;


class Grupo {

    //Atributos
    private ArrayList<Casilla> miembros; //Casillas miembros del grupo.
    private String colorGrupo; //Color del grupo
    private int numCasillas; //Número de casillas del grupo.

    //Constructor vacío.
    public Grupo() {
    }

    /*Constructor para cuando el grupo está formado por DOS CASILLAS:
    * Requiere como parámetros las dos casillas miembro y el color del grupo.
     */
    public Grupo(Casilla cas1, Casilla cas2, String colorGrupo) {
        this.miembros = new ArrayList<Casilla>();
        this.miembros.add(cas1);
        this.miembros.add(cas2);
        this.colorGrupo = colorGrupo;
        this.numCasillas = 2;
        cas1.setGrupo(this);
        cas2.setGrupo(this);
    }

    /*Constructor para cuando el grupo está formado por TRES CASILLAS:
    * Requiere como parámetros las tres casillas miembro y el color del grupo.
     */
    public Grupo(Casilla cas1, Casilla cas2, Casilla cas3, String colorGrupo) {
        this.miembros = new ArrayList<Casilla>();
        this.miembros.add(cas1);
        this.miembros.add(cas2);
        this.miembros.add(cas3);
        this.colorGrupo = colorGrupo;
        this.numCasillas = 3;
        cas1.setGrupo(this);
        cas2.setGrupo(this);
        cas3.setGrupo(this);
    }

    public ArrayList<Casilla> getMiembros(){
        return this.miembros;
    }

    public String getColor(){
        return this.colorGrupo;
    }

    public int getNumCasillas(){
        return this.numCasillas;
    }



    /* Método que añade una casilla al array de casillas miembro de un grupo.
    * Parámetro: casilla que se quiere añadir.
     */
    public void anhadirCasilla(Casilla miembro) {
        this.miembros.add(miembro);
    }

    /*Método que comprueba si el jugador pasado tiene en su haber todas las casillas del grupo:
    * Parámetro: jugador que se quiere evaluar.
    * Valor devuelto: true si es dueño de todas las casillas del grupo, false en otro caso.
     */
    public boolean esDuenhoGrupo(Jugador jugador) {
        for(Casilla c:this.miembros){
            if(c.getDuenho()!= jugador){
                return false;
            }
        }
        return true;
    }

}
