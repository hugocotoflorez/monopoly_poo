package monopoly.Edificio;

import monopoly.Casilla.Propiedad.*;;

public abstract class Edificio {
    private String ID;
    private float precio; // Precio de los edificios
    private Solar casilla; // Los edificios se sitúan en propiedades

    // Constructor general
    public Edificio(String ID, Solar casilla, float precio) {
        this.ID = ID;
        this.casilla = casilla;
        this.precio = precio;
    }

    //GETTERS Y SETTERS -------------------------------
    public String getID() {
        return ID;
    }

    public float getPrecio() {
        return precio;
    }

    public Solar getCasilla() {
        return casilla;
    }

    public void setID(String ID){
        this.ID = ID;
    }

    public void setPrecio(float precio){
        this.precio = precio;
    }

    public void setCasilla (Solar casilla){
        this.casilla = casilla;
    }
    //--------------------------------------------------


    @Override
    public String toString() {

        return "{\n" +
                "  id: " + ID + ",\n" +
                "  propietario: " + casilla.getDuenho().getNombre() + ",\n" +
                "  casilla: " + casilla.getNombre() + ",\n" +
                "  grupo: " + casilla.getGrupo().getID() + ",\n" +
                "  coste: " + precio + "\n" +
                "}";
    }
}
