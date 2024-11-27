package monopoly.consola;

public interface Consola {
    public void imprimir(String mensaje);

    public void imprimirln(String string);

    public void imprimirError(String mensaje);

    public String leer(String mensaje);

    public int leerInt(String mensaje);

    public char leerChar(String mensaje);
}