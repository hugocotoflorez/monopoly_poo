package monopoly.Casilla.Propiedad;

import monopoly.Valor;
import monopoly.Edificio.Edificio;
import partida.Jugador;

import java.util.ArrayList;

import monopoly.Grupo;
import monopoly.Juego;


public class Solar extends Propiedad{
    private Grupo grupo;
    private ArrayList<Edificio> edificios;

    public Solar(String nombre, int posicion, float valor, float hipoteca, Jugador duenho){
        super(nombre, posicion, valor, hipoteca, duenho);
        this.edificios = new ArrayList<Edificio>();
    }

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

        float alquilerinicial = this.grupo.getValor();

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


    //EDIFICIOS !
    
    
    public int obtenerNumeroCasas(){

    }

    public int obtenerNumeroHoteles(){

    }

    public int obtenerNumeroPiscinas(){

    }

    public int obtenerNumeroPistasDeporte(){

    }

    private boolean estaEnMaximoEdificios(){

    }

    private boolean esEdificable(){

    }

    private boolean esCasaEdificable(){

    }

    private boolean esHotelEdificable(){

    }

    private boolean esPiscinaEdificable(){

    }

    private boolean esPistaDeporteEdificable(){

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

    public void edificar(String tipo, Jugador solicitante){

    }

    public void desedificar(String tipo, Jugador solicitante, String num){

    }

    //Sobrecarga para demoler todos los edificios de una casilla a la vez cuando se cae en bancarrota
    public void desedificar(){
        this.edificios.clear();
    }
}
