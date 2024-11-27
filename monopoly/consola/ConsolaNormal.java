package monopoly.consola;

import java.util.Scanner;

import monopoly.Valor;

public class ConsolaNormal implements Consola {

    private Scanner scanner = new Scanner(System.in);

    @Override
    public void imprimir(String string) {
        System.out.print(string);
    }

    @Override
    public void imprimirln(String string) {
        System.out.println(string);
    }

    @Override
    public void imprimirError(String mensaje) {
        System.out.println(Valor.RED + mensaje + Valor.RESET);
    }

    @Override
    public String leer(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    @Override
    public int leerInt(String mensaje) {
        return Integer.parseInt(leer(mensaje));

    }

    @Override
    public char leerChar(String mensaje) {
        return leer(mensaje).charAt(0);
    }
}
