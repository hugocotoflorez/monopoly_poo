package monopoly;

import java.util.Scanner;

public class ConsolaNormal implements Consola {

    private Scanner scanner = new Scanner(System.in);

    @Override
    public void imprimir(String string) {
        System.out.println(string);
    }

    @Override
    public String leer(String mensaje) {
        System.out.println(mensaje);
        return scanner.next();
    }
}
