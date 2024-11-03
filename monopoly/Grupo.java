package monopoly;

// hola soy guillee
import partida.*;
import java.util.ArrayList;

class Grupo {

    // Atributos
    private ArrayList<Casilla> miembros; // Casillas miembros del grupo.
    private String colorGrupo; // Color del grupo
    private int numCasillas; // Número de casillas del grupo.

    // Constructor vacío.
    public Grupo() {
    }

    /*
     * Constructor para cuando el grupo está formado por DOS CASILLAS:
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

    /*
     * Constructor para cuando el grupo está formado por TRES CASILLAS:
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

    // GETTERS
    public ArrayList<Casilla> getMiembros() {
        return this.miembros;
    }

    public String getColor() {
        return this.colorGrupo;
    }

    public int getNumCasillas() {
        return this.numCasillas;
    }

    // SETTERS

    /*
     * Método que añade una casilla al array de casillas miembro de un grupo.
     * Parámetro: casilla que se quiere añadir.
     */
    public void anhadirCasilla(Casilla miembro) {
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
        for (Casilla c : this.miembros) {
            if (c.getDuenho() != jugador) {
                return false;
            }
        }
        return true;
    }

    /*
     * Función para llamar cuando un jugador tenga todos los solares de un grupo.
     * Duplica su alquiler
     */
    public void actualizarAlquilerGrupo() {
        for (Casilla m : this.miembros) {
            m.setImpuesto(m.getImpuesto() * 2);
        }
    }

    // Función devuelve el total de recaudados de un grupo de un color en concreto
    public float totalRecaudado() {
        float ret = 0;
        for (Casilla c : this.miembros) {
            ret += c.getRecaudado();
        }
        return ret;
    }

    @Override

    public String toString() {

        /*
         * EJEMPLO DE REPRESENTACIÓN
         * 
         * {
         * propiedad: Solar18,
         * hoteles: [hotel-1]
         * casas: [casa-1],
         * piscinas: [piscina-1],
         * pistasDeDeporte: -
         * alquiler: 8000000
         * },
         * {
         * propiedad: Solar20,
         * hoteles: [hotel-3]
         * casas: [casa-7],
         * piscinas: -,
         * pistasDeDeporte: -,
         * alquiler: 6500000
         * }
         */

        String ret = new String();
        ret += "{\n";

        for (Casilla c : this.miembros) {

            ret += ("propiedad: " + c.getNombre());

            ret += ("hoteles: [ " + c.listar_edificios_grupo("Hotel"));

            ret += ("casas: " + c.listar_edificios_grupo("Casa"));

            ret += ("piscinas: " + c.listar_edificios_grupo("Piscina"));

            ret += ("pistasDeDeporte: " + c.listar_edificios_grupo("Pista de deportes"));

            ret += ("alquiler: ");

        }

        ret += "}\n";

        return ret;
    }

    public float getValor() {
        float ret;
        switch (this.colorGrupo) {
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
    }
}

