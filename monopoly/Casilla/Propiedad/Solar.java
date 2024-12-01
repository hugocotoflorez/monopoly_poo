package monopoly.Casilla.Propiedad;

import monopoly.Valor;
import monopoly.Edificio.*;
import monopoly.MonopolyException.AccionException.CarcelException;
import monopoly.MonopolyException.AccionException.FortunaInsuficienteException;
import monopoly.MonopolyException.ComandoException.TipoIncorrectoException;
import monopoly.MonopolyException.PropiedadException.DuenhoException;
import monopoly.MonopolyException.PropiedadException.HipotecadaException;
import monopoly.MonopolyException.PropiedadException.EdificioException.*;
import partida.*;

import java.util.ArrayList;
import java.util.Iterator;

import monopoly.Grupo;
import monopoly.Juego;


public class Solar extends Propiedad{
    private Grupo grupo;
    private ArrayList<Edificio> edificios;

    public Solar(String nombre, int posicion, float valor, Jugador duenho){
        super(nombre, posicion, valor, duenho);
        this.edificios = new ArrayList<Edificio>();
    }

    //GETTERS Y SETTERS -------------
    public Grupo getGrupo(){
        return this.grupo;
    }

    public ArrayList<Edificio> getEdificios(){
        return this.edificios;
    }

    public void setGrupo(Grupo grupo){
        this.grupo = grupo;
    }

    //No hay setter de edificios por que se manipula (añaden y quitan edificios) con las funciones de arraylist

    //---------------------------------

    @Override
    public void hipotecar(Jugador solicitante) throws DuenhoException, HipotecadaException, CarcelException, NumeroEdificiosException{
        if (this.edificios.size() != 0)
            throw new NumeroEdificiosException("Debes vender los edificios de " + this.getNombre() + " antes de hipotecarla.");
        
        else super.hipotecar(solicitante);
    }

    @Override
    public float calcularAlquiler(int tirada){
        int numeroCasas = this.obtenerNumeroCasas();
        int numeroHoteles = this.obtenerNumeroHoteles();
        int numeroPiscinas = this.obtenerNumeroPiscinas();
        int numeroPistas = this.obtenerNumeroPistasDeporte();

        float nuevoImpuestoCasas = 0f;
        float nuevoImpuestoHoteles = 0f;
        float nuevoImpuestoPiscinas = 0f;
        float nuevoImpuestoPistas = 0f;

        float alquilerinicial = this.grupo.getValor()*0.10f;

        if (!this.getDuenho().esBanca() && this.grupo.esDuenhoGrupo(this.getDuenho()))
            alquilerinicial *= 2;

        if (numeroCasas == 1) {
            nuevoImpuestoCasas = 5 * alquilerinicial;
        }
        if (numeroCasas == 2) {
            nuevoImpuestoCasas = 15 * alquilerinicial;
        }
        if (numeroCasas == 3) {
            nuevoImpuestoCasas = 35 * alquilerinicial;
        }
        if (numeroCasas == 4) {
            nuevoImpuestoCasas = 50 * alquilerinicial;
        }

        if (numeroHoteles >= 1) {
            nuevoImpuestoHoteles = 70 * alquilerinicial * numeroHoteles;
        }

        if (numeroPiscinas >= 1) {
            nuevoImpuestoPiscinas = 25 * alquilerinicial * numeroPiscinas;
        }

        if (numeroPistas >= 1) {
            nuevoImpuestoPistas = 25 * alquilerinicial * numeroPistas;
        }

        return alquilerinicial + nuevoImpuestoCasas + nuevoImpuestoHoteles + nuevoImpuestoPiscinas + nuevoImpuestoPistas;
    }

    @Override
    public String infoCasilla(Jugador banca){
        String info = new String();

        float alquilerbase = this.getGrupo().getValor();
        if(this.grupo.esDuenhoGrupo(this.getDuenho())) alquilerbase *= 2;

        info = """
                {
                Nombre: %s
                Tipo: solar
                Grupo: %s
                Propietario: %s
                Valor: %f
                Alquiler: %f
                Edificios: %s
                Valor hotel: %f
                Valor piscina: %f
                Valor pista de deporte: %f
                Alquiler 1 casa: %f
                Alquiler 2 casas: %f
                Alquiler 3 casas: %f
                Alquiler 4 casas: %f
                Alquiler hotel: %f
                Alquiler piscina: %f
                Alquiler pista de deporte: %f
                }
                """.formatted(this.getNombre(), this.grupo.getID(), this.getDuenho().getNombre(), this.getValor(), this.calcularAlquiler(0),
                this.listar_nombre_edificios(), alquilerbase * 0.60f, alquilerbase * 0.60f,
                alquilerbase * 0.40f, alquilerbase * 1.25f, alquilerbase * 5f, alquilerbase * 15f, alquilerbase * 35f, alquilerbase * 50f,
                alquilerbase * 70f, alquilerbase * 25f, alquilerbase * 25f);
        return info;
    }


    //EDIFICIOS -------------------------------------
    
    
    public int obtenerNumeroCasas(){
        int ret = 0;
        for (Edificio e : this.edificios){
            if( e instanceof Casa) ret ++;
        }
        return ret;
    }

    public int obtenerNumeroHoteles(){
        int ret = 0;
        for (Edificio e : this.edificios){
            if( e instanceof Hotel) ret ++;
        }
        return ret;
    }

    public int obtenerNumeroPiscinas(){
        int ret = 0;
        for (Edificio e : this.edificios){
            if( e instanceof Piscina) ret ++;
        }
        return ret;
    }

    public int obtenerNumeroPistasDeporte(){
        int ret = 0;
        for (Edificio e : this.edificios){
            if( e instanceof PistaDeporte) ret ++;
        }
        return ret;
    }

    private boolean puedeEdificar(Jugador solicitante){
        int numero_veces_caidas = this.getCaidasEnCasilla()[solicitante.getAvatar().getTurno()];
        return ((this.getGrupo().esDuenhoGrupo(solicitante) || numero_veces_caidas > 2));
    }

    private boolean esCasaEdificable(){
        int casas_grupo = this.getGrupo().obtenerNumCasasGrupo();
        int casas_solar = this.obtenerNumeroCasas();
        int hoteles_grupo = this.getGrupo().obtenerNumHotelesGrupo();
        int max = this.getGrupo().getNumCasillas();

        if (hoteles_grupo < max) return (casas_solar < 4); //Si aún no hay el máximo de hoteles (2 o 3), puedo edificar hasta 4 casas
        else return (casas_grupo < max); //Si ya tengo el máximo de hoteles, sólo puedo edificar hasta el máximo de casas (2 o 3)
    }

    private boolean esHotelEdificable(){
        int casas_grupo = this.getGrupo().obtenerNumCasasGrupo();
        int casas_solar = this.obtenerNumeroCasas();
        int hoteles_grupo = this.getGrupo().obtenerNumHotelesGrupo();
        int max = this.getGrupo().getNumCasillas();

        if (hoteles_grupo < max - 1) return (casas_solar == 4); //Si aún puedo construir más hoteles sin llegar al máximo, adelante
        if (hoteles_grupo < max) return (casas_grupo - 4 <= max && casas_solar == 4); //Si al siguiente hotel que construya llego al máximo, sólo puedo si no hay el máximo de casas
        else return false;
    }

    private boolean esPiscinaEdificable(){
        int max = this.getGrupo().getNumCasillas();
        return (this.obtenerNumeroCasas() >= 2 && this.obtenerNumeroHoteles() >= 1 && this.obtenerNumeroPiscinas() < max);
    }

    private boolean esPistaDeporteEdificable(){
        int max = this.getGrupo().getNumCasillas();
        return (this.obtenerNumeroHoteles() >= 2 && this.obtenerNumeroPistasDeporte() < max);
    }

    public void listar_info_edificios(){
        for (Edificio e : this.edificios){
            Juego.consola.imprimirln(e.toString());
        }
    }

    public String listar_nombre_edificios(){
        String ret = new String();
        for (Edificio e : this.edificios){
            ret += e.getID();
            ret += ", ";
        }
        return ret;
    }

    public String listar_nombre_edificios_tipo(String tipo) {

        String ret = new String();

        ret += "[ ";

        for (Edificio e : this.edificios)
            if (e.getID().contains(tipo))
                ret += e.getID() + ", ";

        ret += " ]";

        return ret;

    }

    private void pagar_edificio(Jugador solicitante, Edificio edificio)throws FortunaInsuficienteException{
        if (solicitante.getFortuna() < edificio.getPrecio())
            throw new FortunaInsuficienteException(solicitante.getFortuna(), edificio.getPrecio());

        else{
            this.edificios.add(edificio);
            solicitante.sumarFortuna(-edificio.getPrecio());
            solicitante.setDineroInvertido(solicitante.getDineroInvertido() + edificio.getPrecio());

            Juego.consola.imprimirln("Se ha edificado " + edificio.getID() + " en " + this.getNombre() + " por " + edificio.getPrecio());
            Juego.consola.imprimirln("La fortuna de " + solicitante.getNombre() + "pasa de " + (solicitante.getFortuna() + edificio.getPrecio()) + " a " + solicitante.getFortuna());
        }
    }

    public void edificar(String tipo, Jugador solicitante) throws FortunaInsuficienteException, TipoIncorrectoException, CasaEdificableException, HotelEdificableException, 
        PiscinaEdificableException, PistaEdificableException, CasillaNoEdificableException, DuenhoException, HipotecadaException, CasaEdificableException{
        if(!this.puedeEdificar(solicitante))
            throw new CasillaNoEdificableException("No eres el dueño del grupo " + this.getGrupo().getID() + " y/o no has caído más de dos veces en " + this.getNombre());
        if(!this.getDuenho().equals(solicitante))
            throw new DuenhoException("No puedes edificar en un solar que no te pertenece.");
        if(this.getHipotecada())
            throw new HipotecadaException(this.getNombre() + " está hipotecada, no puedes edificar en ella.");
        if(!solicitante.getAvatar().getCasilla().equals(this))
            throw new CasillaNoEdificableException("No puedes edificar en un solar desde otra casilla.");

            switch(tipo){
                case "casa":
                    if(!this.esCasaEdificable())
                        throw new CasaEdificableException("No puedes edificar una casa en " + this.getNombre() + " ahora mismo.");
                    else{
                        Casa casa = new Casa(this);
                        pagar_edificio(solicitante, casa);
                        Valor.NumeroCasasConstruidas++;
                    }
                    break;

                case "hotel":
                    if(!this.esHotelEdificable())
                        throw new HotelEdificableException("No puedes edificar un hotel en " + this.getNombre() + " ahora mismo.");
                    else{
                        Hotel hotel = new Hotel(this);
                        pagar_edificio(solicitante, hotel);
                        Valor.NumeroHotelesConstruidos++;
                        
                        //Quitar 4 casas
                        int quitadas = 0;
                        do{
                            for (Edificio e : this.edificios){
                                if (e instanceof Casa){
                                    this.edificios.remove(e);
                                    quitadas++;
                                    break;
                                }
                            }
                        } while (quitadas < 4);
                    }
                    break;
                
                case "piscina":
                    if(!this.esPiscinaEdificable())
                        throw new PiscinaEdificableException("No puedes edificar una piscina en " + this.getNombre() + " ahora mismo.");
                    else{
                        Piscina piscina = new Piscina(this);
                        pagar_edificio(solicitante, piscina);
                        Valor.NumeroPiscinasConstruidas++;
                    }
                    break;
                
                case "pista":
                    if(!this.esPistaDeporteEdificable())
                        throw new PiscinaEdificableException("No puedes edificar una pista de deportes en " + this.getNombre() + " ahora mismo.");
                    else{
                        PistaDeporte pista = new PistaDeporte(this);
                        pagar_edificio(solicitante, pista);
                        Valor.NumeroPistasConstruidos++;
                    }
                    break;
                
                default:
                    throw new TipoIncorrectoException("Tipo de edificio incorrecto. Tipos correctos: <casa>, <hotel>, <piscina>, <pista>.");

            }
        }

    //TODO excepciones
    public void desedificar(String tipo, Jugador solicitante, String num){
        if (!this.getDuenho().equals(solicitante)){
            Juego.consola.imprimirln("No puedes vender edificios de " + this.getNombre() + " porque no te pertenece.");
            return;
        }

        int n = Integer.parseInt(num); //TODO poner una excepción NumberFormatException

        switch(tipo){
            case "casa":
                if (n > this.obtenerNumeroCasas()){
                    Juego.consola.imprimirln("No hay suficientes casas en el solar. Sólo hay " + this.obtenerNumeroCasas());
                    return;
                }
                break;

            case "hotel":
                if (n > this.obtenerNumeroHoteles()){
                    Juego.consola.imprimirln("No hay suficientes hoteles en el solar. Sólo hay " + this.obtenerNumeroHoteles());
                    return;
                }
                break;

            case "piscina":
                if (n > this.obtenerNumeroPiscinas()){
                    Juego.consola.imprimirln("No hay suficientes piscinas en el solar. Sólo hay " + this.obtenerNumeroPiscinas());
                    return;
                }
                break;

            case "pista":
                if (n > this.obtenerNumeroPiscinas()){
                    Juego.consola.imprimirln("No hay suficientes pistas de deporte en el solar. Sólo hay " + this.obtenerNumeroPistasDeporte());
                    return;
                }
                break;

            default:
                Juego.consola.imprimirln("Tipo de edificio incorrecto. Tipos correctos: <casa>, <hotel>, <piscina>, <pista>.");
                return;
        }

        float fortuna_anhadida = 0f;
        int eliminados = 0;
        Iterator<Edificio> it = this.edificios.iterator();

        while(it.hasNext() && eliminados < n){
            Edificio e = it.next();
            if (e.getID().contains(tipo)){
                fortuna_anhadida += (e.getPrecio() / 2f);
                it.remove();
                eliminados++;
            }
        }

        solicitante.sumarFortuna(fortuna_anhadida);

        Juego.consola.imprimirln(solicitante.getNombre() + " vende " + n + " " + tipo + "s en" + this.getNombre() + " recibiendo " + fortuna_anhadida);    
        Juego.consola.imprimirln("La fortuna de " + solicitante.getNombre() + "pasa de " + (solicitante.getFortuna() - fortuna_anhadida + " a " + solicitante.getFortuna()));
        
    }

    //Sobrecarga para demoler todos los edificios de una casilla a la vez cuando se cae en bancarrota
    public void desedificar(){
        this.edificios.clear();
    }
}
