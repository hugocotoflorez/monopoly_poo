package monopoly;

// hola soy guillee
import partida.*;
import java.util.ArrayList;

import monopoly.Casilla.*;
import monopoly.Casilla.Propiedad.*;

public class Grupo {

    // Atributos
    String ID;
    private ArrayList<Solar> miembros; // Casillas miembros del grupo.
    private String colorGrupo; // Color del grupo
    private int numCasillas; // Número de casillas del grupo.

    // Constructor vacío.
    public Grupo() {
    }

    /*
     * Constructor para cuando el grupo está formado por DOS CASILLAS:
     * Requiere como parámetros las dos casillas miembro y el color del grupo.
     */
    public Grupo(Casilla cas1, Casilla cas2, String colorGrupo, String ID) {
        this.miembros = new ArrayList<Solar>();
        this.miembros.add((Solar) cas1);
        this.miembros.add((Solar) cas2);
        this.colorGrupo = colorGrupo;
        this.ID = ID;
        this.numCasillas = 2;
        ((Solar) cas1).setGrupo(this);
        ((Solar) cas2).setGrupo(this);
    }

    /*
     * Constructor para cuando el grupo está formado por TRES CASILLAS:
     * Requiere como parámetros las tres casillas miembro y el color del grupo.
     */
    public Grupo(Casilla cas1, Casilla cas2, Casilla cas3, String colorGrupo, String ID) {
        this.miembros = new ArrayList<Solar>();
        this.miembros.add((Solar) cas1);
        this.miembros.add((Solar) cas2);
        this.miembros.add((Solar) cas3);
        this.colorGrupo = colorGrupo;
        this.ID = ID;
        this.numCasillas = 3;
        ((Solar) cas1).setGrupo(this);
        ((Solar) cas2).setGrupo(this);
        ((Solar) cas3).setGrupo(this);
    }

    // GETTERS -------------------
    public ArrayList<Solar> getMiembros() {
        return this.miembros;
    }

    public String getColor() {
        return this.colorGrupo;
    }

    public int getNumCasillas() {
        return this.numCasillas;
    }
    public String getID(){
        return this.ID;
    }

    //No hay setters porque los grupos en principio no se modifican fuera del constructor de tablero

    public int obtenerNumCasasGrupo(){

        int ret = 0;
        for(Solar s : this.miembros){
            ret += s.obtenerNumeroCasas();
        }
        return ret;

    }

    public int obtenerNumHotelesGrupo(){

        int ret = 0;
        for(Solar s : this.miembros){
            ret += s.obtenerNumeroHoteles();
        }
        return ret;

    }

    public int obtenerNumPistasGrupo(){

        int ret = 0;
        for(Solar s : this.miembros){
            ret += s.obtenerNumeroPistasDeporte();
        }
        return ret;

    }

    public int obtenerNumPiscinasGrupo(){

        int ret = 0;
        for(Solar s : this.miembros){
            ret += s.obtenerNumeroPiscinas();
        }
        return ret;

    }

    // SETTERS

    /*
     * Método que añade una casilla al array de casillas miembro de un grupo.
     * Parámetro: casilla que se quiere añadir.
     */
    public void anhadirCasilla(Solar miembro) {
        this.miembros.add(miembro);
        this.numCasillas += 1;
    }

    public int getNumeroCasillas() {
        return this.miembros.size();
    }

    /*
     * Método que comprueba si el jugador pasado tiene en su haber todas las
     * casillas del grupo:
     * Parámetro: jugador que se quiere evaluar.
     * Valor devuelto: true si es dueño de todas las casillas del grupo, false en
     * otro caso.
     */
    public boolean esDuenhoGrupo(Jugador jugador) {
        if (!jugador.esBanca()){
            for (Solar c : this.miembros) {
                if (c.getDuenho() != jugador) {
                    return false;
                }
            }
        }
        return true;
    }

    // Función devuelve el total de recaudados de un grupo de un color en concreto
    public float totalRecaudado() {
        float ret = 0;
        for (Solar c : this.miembros) {
            ret += c.getRecaudado();
        }
        return ret;
    }

    @Override
    public String toString() {
        String ret = new String();
        for(Solar c : this.miembros){
            if (c.getEdificios().size()!=0){
                ret += """
                    {
                    propiedad: %s
                    casas: %s
                    hoteles: %s
                    piscinas: %s
                    pistas de deporte: %s
                    alquiler: %f
                    }
                    """.formatted(c.getNombre(), c.listar_nombre_edificios_tipo("casa"), c.listar_nombre_edificios_tipo("hotel"),c.listar_nombre_edificios_tipo("piscina"), c.listar_nombre_edificios_tipo("pista"), c.calcularAlquiler(0));
        
            }
        }
        return ret;
    }

    public float getValor() {
        switch (this.ID) {
            case "Negro": // G1
                return Valor.GRUPO_1;
            case "Cyan": // G2
                return Valor.GRUPO_2;
            case "Rosa": // G3
                return Valor.GRUPO_3;
            case "Amarillo": // G4
                return Valor.GRUPO_4;
            case "Rojo": // G5
                return Valor.GRUPO_5;
            case "Marron": // G6
                return Valor.GRUPO_6;
            case "Verde": // G7
                return Valor.GRUPO_7;
            case "Azul": // G8
                return Valor.GRUPO_8;
        }
        return 0;
    }

    /*
     * Función para llamar cuando un jugador tenga todos los solares de un grupo.
     * Duplica su alquiler inicial
     */
    public void actualizarAlquilerGrupo() {
        float valorinicial = this.getValor();
        for (Propiedad m : this.miembros) {
            m.setAlquiler(valorinicial* 2);
        }
    }
}
