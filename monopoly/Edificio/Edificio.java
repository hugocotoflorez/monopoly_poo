package monopoly.Edificio;

import monopoly.Casilla;

public abstract class Edificio {
    private String ID;
    private float precio; // Precio de los edificios
    private Casilla casilla; // Los edificios se sitúan en casillas

    // Constructor
    public Edificio(String ID, Casilla casilla, float precio) {

        this.ID = ID;
        this.casilla = casilla;
        this.precio = precio;

    }

    // Getters y Setters
    public String getID() {

        return ID;

    }

    public float getPrecio() {

        return precio;

    }

    public Casilla getCasilla() {

        return casilla;

    }

    // Método abstracto para personalizar la lógica de inicialización
    public abstract String getTipo();

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
