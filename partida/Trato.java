package partida;

import monopoly.*;
import monopoly.Casilla.Propiedad.Propiedad;
import monopoly.MonopolyException.AccionException.FortunaInsuficienteException;
import monopoly.MonopolyException.PropiedadException.DuenhoException;

// Queda por cambiar Propiedad por Propiedad

public class Trato {

    Jugador proponedor;
    Jugador receptor;

    float dinero_p;
    float dinero_r;

    Propiedad propiedad_p;
    Propiedad propiedad_r;

    int tipo;
    String ID;

    //CONSTRUCTORES -------------------------------------------------------------------------------------------------

    public Trato(Jugador proponedor, Jugador receptor, Propiedad propiedad_p, Propiedad propiedad_r) throws DuenhoException{ // Propiedad - propiedad
        if(!(propiedad_p.getDuenho().equals(proponedor)))
            throw new DuenhoException("El jugador " + proponedor.getNombre() + " no tiene la propiedad " + propiedad_p.getNombre());
        if(!(propiedad_r.getDuenho().equals(receptor)))
            throw new DuenhoException("El jugador " + receptor.getNombre() + " no tiene la propiedad " + propiedad_r.getNombre());
            
        this.proponedor = proponedor;
        this.receptor = receptor;

        this.propiedad_p = propiedad_p;
        this.dinero_p = -1f;
        this.propiedad_r = propiedad_r;
        this.dinero_r = -1f;

        this.tipo = 0;
        this.ID = "trato-" + Valor.NumeroTratos++;
    }

    public Trato(Jugador proponedor, Jugador receptor, Propiedad propiedad_p, Float dinero_r) throws DuenhoException{ // Propiedad - dinero
        if(!(propiedad_p.getDuenho().equals(proponedor)))
            throw new DuenhoException("El jugador " + proponedor.getNombre() + " no tiene la propiedad " + propiedad_p.getNombre());
        
        this.proponedor = proponedor;
        this.receptor = receptor;

        this.propiedad_p = propiedad_p;
        this.dinero_p = -1f;
        this.propiedad_r = null;
        this.dinero_r = dinero_r;

        this.tipo = 1;
        this.ID = "trato-" + Valor.NumeroTratos++;
    }

    public Trato(Jugador proponedor, Jugador receptor, Float dinero_p, Propiedad propiedad_r) throws FortunaInsuficienteException, DuenhoException{ // Dinero - propiedad
        if(!(propiedad_r.getDuenho().equals(receptor)))
            throw new DuenhoException("El jugador " + receptor.getNombre() + " no tiene la propiedad " + propiedad_r.getNombre());
        if (proponedor.getFortuna() < dinero_p)
            throw new FortunaInsuficienteException(proponedor.getFortuna(), dinero_p);

        this.proponedor = proponedor;
        this.receptor = receptor;

        this.propiedad_p = null;
        this.dinero_p = dinero_p;
        this.propiedad_r = propiedad_r;
        this.dinero_r = -1f;

        this.tipo = 2;
        this.ID = "trato-" + Valor.NumeroTratos++;
    }

    public Trato(Jugador proponedor, Jugador receptor, Propiedad propiedad_p, Propiedad propiedad_r, Float dinero_r) throws DuenhoException{ // Propiedad - propiedad + dinero
        if(!(propiedad_p.getDuenho().equals(proponedor)))
            throw new DuenhoException("El jugador " + proponedor.getNombre() + " no tiene la propiedad " + propiedad_p.getNombre());
        if(!(propiedad_r.getDuenho().equals(receptor)))
            throw new DuenhoException("El jugador " + receptor.getNombre() + " no tiene la propiedad " + propiedad_r.getNombre());

        this.proponedor = proponedor;
        this.receptor = receptor;

        this.propiedad_p = propiedad_p;
        this.dinero_p = -1f;
        this.propiedad_r = propiedad_r;
        this.dinero_r = dinero_r;

        this.tipo = 3;
        this.ID = "trato-" + Valor.NumeroTratos++;
    }

    public Trato(Jugador proponedor, Jugador receptor, Propiedad propiedad_p, Float dinero_p, Propiedad propiedad_r) throws DuenhoException, FortunaInsuficienteException{ // Propiedad + dinero - propiedad
        if(!(propiedad_p.getDuenho().equals(proponedor)))
            throw new DuenhoException("El jugador " + proponedor.getNombre() + " no tiene la propiedad " + propiedad_p.getNombre());
        if(!(propiedad_r.getDuenho().equals(receptor)))
            throw new DuenhoException("El jugador " + receptor.getNombre() + " no tiene la propiedad " + propiedad_r.getNombre());
        if (proponedor.getFortuna() < dinero_p)
            throw new FortunaInsuficienteException(proponedor.getFortuna(), dinero_p);

        this.proponedor = proponedor;
        this.receptor = receptor;

        this.propiedad_p = propiedad_p;
        this.dinero_p = dinero_p;
        this.propiedad_r = propiedad_r;
        this.dinero_r = -1f;

        this.tipo = 4;
        this.ID = "trato-" + Valor.NumeroTratos++;   
    }


    //GETTERS -----------------------------------------------------------------------------------------------------------------

    public Jugador getReceptor() {
        return this.receptor;
    }

    public Jugador getProponedor() {
        return this.proponedor;
    }

    public float getDinero_p(){
        return this.dinero_p;
    }

    public float getDinero_r(){
        return this.dinero_r;
    }

    public Propiedad getPropiedad_p(){
        return this.propiedad_p;
    }

    public Propiedad getPropiedad_r(){
        return this.propiedad_r;
    }

    public int getTipo(){
        return this.tipo;
    }

    public String getID() {
        return this.ID;
    }

    // No hay setters porque los atributos no se modifican fuera de esta clase

    //-----------------------------------------------------------------------------------------------------------------------------------------------



    

    private void trato0() throws DuenhoException { // P-P
        if(!(this.propiedad_p.getDuenho().equals(proponedor)))
            throw new DuenhoException("El jugador " + this.proponedor.getNombre() + " no tiene la propiedad " + this.propiedad_p.getNombre());
        if(!(this.propiedad_r.getDuenho().equals(receptor)))
            throw new DuenhoException("El jugador " + this.receptor.getNombre() + " no tiene la propiedad " + this.propiedad_r.getNombre());
         
        this.proponedor.anhadirPropiedad(this.propiedad_r);
        this.proponedor.eliminarPropiedad(this.propiedad_p);

        this.receptor.anhadirPropiedad(this.propiedad_p);
        this.receptor.eliminarPropiedad(this.propiedad_r);

        this.propiedad_p.setDuenho(this.receptor);
        this.propiedad_r.setDuenho(this.proponedor);
    }

    private void trato1() throws DuenhoException, FortunaInsuficienteException { //P-D
        if(!(this.propiedad_p.getDuenho().equals(proponedor)))
            throw new DuenhoException("El jugador " + this.proponedor.getNombre() + " no tiene la propiedad " + this.propiedad_p.getNombre());
        if (this.receptor.getFortuna() < this.dinero_r)
            throw new FortunaInsuficienteException(this.receptor.getFortuna(), this.dinero_r);

        this.proponedor.sumarFortuna(this.dinero_r);
        this.proponedor.eliminarPropiedad(this.propiedad_p);

        this.receptor.anhadirPropiedad(this.propiedad_p);
        this.receptor.sumarFortuna(-this.dinero_r);

        this.propiedad_p.setDuenho(this.receptor);
    }

    private void trato2() throws DuenhoException, FortunaInsuficienteException{ //D-P
        if(!(this.propiedad_r.getDuenho().equals(receptor)))
            throw new DuenhoException("El jugador " + this.receptor.getNombre() + " no tiene la propiedad " + this.propiedad_r.getNombre());
        if (this.proponedor.getFortuna() < this.dinero_p)
            throw new FortunaInsuficienteException(this.proponedor.getFortuna(), this.dinero_p);

        this.proponedor.anhadirPropiedad(this.propiedad_r);
        this.proponedor.sumarFortuna(-this.dinero_p);

        this.receptor.eliminarPropiedad(this.propiedad_r);
        this.receptor.sumarFortuna(this.dinero_p);

        this.propiedad_r.setDuenho(this.proponedor);
    }

    private void trato3() throws DuenhoException, FortunaInsuficienteException{ // P-P+D
        if(!(this.propiedad_p.getDuenho().equals(proponedor)))
            throw new DuenhoException("El jugador " + this.proponedor.getNombre() + " no tiene la propiedad " + this.propiedad_p.getNombre());
        if(!(this.propiedad_r.getDuenho().equals(receptor)))
            throw new DuenhoException("El jugador " + this.receptor.getNombre() + " no tiene la propiedad " + this.propiedad_r.getNombre());
        if (this.receptor.getFortuna() < this.dinero_r)
            throw new FortunaInsuficienteException(this.receptor.getFortuna(), this.dinero_r);


        this.proponedor.anhadirPropiedad(this.propiedad_r);
        this.proponedor.eliminarPropiedad(this.propiedad_p);
        this.proponedor.sumarFortuna(this.dinero_r);

        this.receptor.anhadirPropiedad(this.propiedad_p);
        this.receptor.eliminarPropiedad(this.propiedad_r);
        this.receptor.sumarFortuna(-this.dinero_r);

        this.propiedad_p.setDuenho(this.receptor);
        this.propiedad_r.setDuenho(this.proponedor);
    }

    private void trato4() throws DuenhoException, FortunaInsuficienteException { // P+D-P
        if(!(this.propiedad_p.getDuenho().equals(proponedor)))
            throw new DuenhoException("El jugador " + this.proponedor.getNombre() + " no tiene la propiedad " + this.propiedad_p.getNombre());
        if(!(this.propiedad_r.getDuenho().equals(receptor)))
            throw new DuenhoException("El jugador " + this.receptor.getNombre() + " no tiene la propiedad " + this.propiedad_r.getNombre());
        if (this.proponedor.getFortuna() < this.dinero_p)
            throw new FortunaInsuficienteException(this.proponedor.getFortuna(), this.dinero_p);

        this.proponedor.anhadirPropiedad(this.propiedad_r);
        this.proponedor.eliminarPropiedad(this.propiedad_p);
        this.proponedor.sumarFortuna(-this.dinero_p);

        this.receptor.anhadirPropiedad(this.propiedad_p);
        this.receptor.eliminarPropiedad(this.propiedad_r);
        this.receptor.sumarFortuna(this.dinero_p);

        this.propiedad_p.setDuenho(this.receptor);
        this.propiedad_r.setDuenho(this.proponedor);
    }

    public void aceptar() throws DuenhoException, FortunaInsuficienteException{
        switch (tipo) {
            case 0: // P-P
                this.trato0();
                break;

            case 1: // P-D
                this.trato1();
                break;

            case 2: // D-P
                this.trato2();
                break;

            case 3: // P-P+D
                this.trato3();
                break;

            case 4: // P+D-P
                this.trato4();
                break;
        }
    }


    @Override
    public String toString() {

        String ret = new String();
        ret += "{\n";
        ret += ("\tid; " + this.ID + "\n");
        ret += ("\tpropuesto por: " + this.proponedor.getNombre() + "\n");

        switch (tipo) {

            case 0: // P-P
                ret += ("\tcambiar (" + this.propiedad_p.getNombre() + ", " + this.propiedad_r.getNombre() + ")\n");
                break;

            case 1: // P-D
                ret += ("\tcambiar (" + this.propiedad_p.getNombre() + ", " + this.dinero_r + ")\n");
                break;

            case 2: // D-P
                ret += ("\tcambiar (" + this.dinero_p + ", " + this.propiedad_r.getNombre() + ")\n");
                break;

            case 3: // P-P+D
                ret += ("\tcambiar (" + this.propiedad_p.getNombre() + ", " + this.propiedad_r.getNombre() + " y "
                        + this.dinero_r + ")\n");
                break;

            case 4: // P+D-P
                ret += ("\tcambiar (" + this.propiedad_p.getNombre() + " y " + this.dinero_p + ", "
                        + this.propiedad_r.getNombre() + ")\n");
                break;
        }
        ret += "}\n";
        return ret;
    }

}