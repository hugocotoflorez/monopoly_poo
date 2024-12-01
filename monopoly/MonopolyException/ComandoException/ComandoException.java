package monopoly.MonopolyException.ComandoException;

import monopoly.MonopolyException.MonopolyException;

public abstract class ComandoException extends MonopolyException {
    public ComandoException(String message){
        super(message);
    }
}
