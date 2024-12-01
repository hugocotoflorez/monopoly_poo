package monopoly.MonopolyException.AccionException;

public class FortunaInsuficienteException extends AccionException{
    public FortunaInsuficienteException(float fortuna_actual, float fortuna_necesaria){
        super("No tienes la fortuna suficiente. Necesitas " + fortuna_necesaria + " y tienes " + fortuna_actual);
    }
}
