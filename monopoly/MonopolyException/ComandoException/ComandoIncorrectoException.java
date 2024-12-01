package monopoly.MonopolyException.ComandoException;

public class ComandoIncorrectoException extends ComandoException{
    public ComandoIncorrectoException(){
        super("Opción incorrecta. ['?'/'opciones' para ver las opciones]");
    }
}
