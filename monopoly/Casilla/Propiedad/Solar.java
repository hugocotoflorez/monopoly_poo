package monopoly.Casilla.Propiedad;

import monopoly.Valor;
import monopoly.Edificio.*;
import partida.Jugador;

import java.util.ArrayList;
import java.util.Iterator;

import monopoly.Grupo;
import monopoly.Juego;


public class Solar extends Propiedad{
    private Grupo grupo;
    private ArrayList<Edificio> edificios;

    public Solar(String nombre, int posicion, float valor, float hipoteca, Jugador duenho){
        super(nombre, posicion, valor, hipoteca, duenho);
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
    public void hipotecar(Jugador solicitante){
        if (this.edificios.size() != 0){ //TODO excepcion
            Juego.consola.imprimir("Debes vender los edificios de esta propiedad antes de hipotecarla.");
            Juego.consola.imprimir("Edificios de esta propiedad: ");
            this.listar_edificios_casilla();
        }
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

        if (this.grupo.esDuenhoGrupo(this.getDuenho()))
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
    public String infoCasilla(){
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
                this.listar_nombres_edificios(), alquilerbase * 0.60f, alquilerbase * 0.60f,
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

    private boolean estaEnMaximoEdificios(){

        return false;
    }

    private boolean puedeEdificar(Jugador solicitante){
        int numero_veces_caidas = this.getCaidasEnCasilla()[solicitante.getAvatar().getTurno()];
        return ((this.getGrupo().esDuenhoGrupo(solicitante) || numero_veces_caidas > 2) && this.getDuenho().equals(solicitante));
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
            Juego.consola.imprimir(e.toString());
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

    //TODO excepciones
    private void pagar_edificio(Jugador solicitante, Edificio edificio){
        if (solicitante.getFortuna() >= edificio.getPrecio()){
            this.edificios.add(edificio);
            solicitante.sumarFortuna(-edificio.getPrecio());
            solicitante.setDineroInvertido(solicitante.getDineroInvertido() + edificio.getPrecio());

            Juego.consola.imprimir("Se ha edificado " + edificio.getID() + " en " + this.getNombre() + " por " + edificio.getPrecio());
            Juego.consola.imprimir("La fortuna de " + solicitante.getNombre() + "pasa de " + (solicitante.getFortuna() + edificio.getPrecio()) + " a " + solicitante.getFortuna());
        }
        else Juego.consola.imprimir("No tienes suficiente fortuna, tienes " + solicitante.getFortuna() + " y necesitas " + edificio.getPrecio());
    }

    //TODO excepciones
    public void edificar(String tipo, Jugador solicitante){
        if (!this.puedeEdificar(solicitante)) Juego.consola.imprimir("No puedes edificar en esta casilla.");
        
        else{
            switch (tipo){
                case "casa":
                    if (this.esCasaEdificable()){
                        Casa casa = new Casa(this);
                        pagar_edificio(solicitante, casa);
                        Valor.NumeroCasasConstruidas++;
                    }
                    else {
                        Juego.consola.imprimir("No puedes edificar una casa ahora mismo.");
                        Juego.consola.imprimir("Asegúrate de que el número de casas no supera el máximo.");
                    }
                    break;

                case "hotel":
                    if (this.esHotelEdificable()){
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
                    else {
                        Juego.consola.imprimir("No puedes edificar un hotel ahora mismo.");
                        Juego.consola.imprimir("Asegúrate de que el número de hoteles no supera el número de solares en el grupo y de que hay 4 casas en este solar.");
                    }
                    break;

                case "piscina":
                    if (this.esPiscinaEdificable()){
                        Piscina piscina = new Piscina(this);
                        pagar_edificio(solicitante, piscina);
                        Valor.NumeroPiscinasConstruidas++;
                    }
                    else {
                        Juego.consola.imprimir("No puedes edificar una piscina ahora mismo.");
                        Juego.consola.imprimir("Asegúrate de que el número de piscinas no supera el número de solares en el grupo y de que hay al menos 1 hotel y 2 casas en este solar.");
                    }
                    break;

                case "pista":
                    if(this.esPistaDeporteEdificable()){
                        PistaDeporte pista = new PistaDeporte(this);
                        pagar_edificio(solicitante, pista);
                        Valor.NumeroPistasConstruidos++;
                    }
                    else {
                        Juego.consola.imprimir("No puedes edificar una pista de deportes ahora mismo.");
                        Juego.consola.imprimir("Asegúrate de que el número de pistas de deporte no supera el número de solares en el grupo y de hay al menos 2 hoteles en este solar.");
                    }
                    break;

                default:
                    Juego.consola.imprimir("Tipo de edificio incorrecto. Tipos correctos: <casa>, <hotel>, <piscina>, <pista>.");
                    break;
            }
        }
    }

    //TODO excepciones
    public void desedificar(String tipo, Jugador solicitante, String num){
        if (!this.getDuenho().equals(solicitante)){
            Juego.consola.imprimir("No puedes vender edificios de " + this.getNombre() + " porque no te pertenece.");
            return;
        }

        int n = Integer.parseInt(num); //TODO poner una excepción NumberFormatException

        switch(tipo){
            case "casa":
                if (n > this.obtenerNumeroCasas()){
                    Juego.consola.imprimir("No hay suficientes casas en el solar. Sólo hay " + this.obtenerNumeroCasas());
                    return;
                }
                break;

            case "hotel":
                if (n > this.obtenerNumeroHoteles()){
                    Juego.consola.imprimir("No hay suficientes hoteles en el solar. Sólo hay " + this.obtenerNumeroHoteles());
                    return;
                }
                break;

            case "piscina":
                if (n > this.obtenerNumeroPiscinas()){
                    Juego.consola.imprimir("No hay suficientes piscinas en el solar. Sólo hay " + this.obtenerNumeroPiscinas());
                    return;
                }
                break;

            case "pista":
                if (n > this.obtenerNumeroPiscinas()){
                    Juego.consola.imprimir("No hay suficientes pistas de deporte en el solar. Sólo hay " + this.obtenerNumeroPistasDeporte());
                    return;
                }
                break;

            default:
                Juego.consola.imprimir("Tipo de edificio incorrecto. Tipos correctos: <casa>, <hotel>, <piscina>, <pista>.");
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

        Juego.consola.imprimir(solicitante.getNombre() + " vende " + n + " " + tipo + "s en" + this.getNombre() + " recibiendo " + fortuna_anhadida);    
        Juego.consola.imprimir("La fortuna de " + solicitante.getNombre() + "pasa de " + (solicitante.getFortuna() - fortuna_anhadida + " a " + solicitante.getFortuna()));
        
    }

    //Sobrecarga para demoler todos los edificios de una casilla a la vez cuando se cae en bancarrota
    public void desedificar(){
        this.edificios.clear();
    }
}
