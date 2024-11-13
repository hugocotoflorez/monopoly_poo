package monopoly;

public interface Consola {
    public void imprimir(String mensaje);

    public void imprimirln(String string);

    public void imprimir_error(String mensaje);

    public String leer(String mensaje);
}