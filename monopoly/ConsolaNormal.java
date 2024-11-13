package monopoly;

import java.util.Scanner;

public class ConsolaNormal implements Consola {

    private Scanner scanner = new Scanner(System.in);

    @Override
    public void imprimir(String string) {
        System.out.print(string);
    }

    @Override
    public void imprimirln(String string){
        System.out.println(string);
    }

    @Override
    public void imprimir_error(String mensaje){
        System.out.println(Valor.RED + mensaje + Valor.RESET);
    }

    @Override
    public String leer(String mensaje) {
        System.out.println(mensaje);
        return scanner.next();
    }
}
