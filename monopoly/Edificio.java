/*Edificaciones:

 En cada casilla de solar se pueden construir edificios.                                                           CHECK¿?

 Los edificios pueden ser de cuatro tipos: casas, hoteles, piscinas y pistas de deporte.                           CHECK¿?

 La construcción de cada edificio tiene un coste que dependerá (1) del tipo al que pertenece y (2) del
solar en el que se construye. Por tanto, cada solar tiene un coste de construcción para cada tipo de
edificio.                                                                                                           CHECK¿?

 En un solar se puede construir una casa si dicho solar pertenece al jugador cuyo avatar se encuentra
en la casilla y si (1) el avatar ha caído más de dos veces en esa misma casilla o (2) el jugador posee el
grupo de casillas a la que pertenece dicha casilla.                                                                 CHECK¿?

 En un solar se puede construir un hotel si en dicho solar ya se han construido cuatro casas y, además,
se deberán substituir las casas por el hotel.                                                                       CHECK¿?

 En un solar se puede construir una piscina si se han construido, al menos, un hotel y dos casas.                  CHECK¿?

 En un solar se puede construir una pista de deporte si se han construido, al menos, dos hoteles.                  CHECK¿?

 El número de edificios que se pueden construir en un solar está limitado al número total de edificios
que se pueden construir en el grupo al que dicho solar pertenece: si el grupo consta de dos casillas, se
pueden construir, como máximo, 2 hoteles, 2 casas, 2 piscinas y 2 pistas de deporte; mientras que, si el
grupo está compuesto por tres casillas, se puede construir, como máximo, 3 hoteles, 3 casas, 3 piscinas
y 3 pistas de deporte.                                                                                              CHECK¿?

 Cada edificio tiene un coste de alquiler que depende (1) del tipo al que pertenece y (2) de la propiedad
o casilla en la que se ha construido.                                                                               CHECK¿? - No tiene alquiler perdon pero creo que funciona

 El coste de alquiler que debe pagar un avatar que cae en una propiedad con edificios será la suma del
coste de alquiler de todos los edificios construidos en dicha propiedad                                             CHECK¿?

El valor inicial de una casa y de un hotel es el 60% del valor inicial del solar en el que se edifica.              CHECK¿?

 El valor inicial de una piscina es el 40% del valor inicial del solar en el que se edifica.                       CHECK¿?

 El valor inicial de una pista de deporte es el 125% del solar en el que se edifica.                               CHECK¿?

 Listar edificios construidos                                                                                      CHECK¿?

 IDs unicos por cada edificio                                                                                      CHECK¿?

 Listar edificios construidos en un grupo                                                                          CHECK¿?

 Añadir prints a edificar                                                                                          CHECK¿? - Los prints en caso de no poder edificar no son los del guión pero me da pereza

 Vender edificios                                                                                                  CHECK¿? - Buscar la casilla desde menu / Vender n casas

 Actualizar describir Casilla

 Actualizar describir Jugador                                                                                      CHECK¿?

 Agregar opciones al menú                                                                                          Falta agregar desedificar
*/
package monopoly;

public class Edificio {

    private String ID;
    private String tipo; // Piscina/Edificios/Hoteles
    private float precio; // Precio de los edificios
    private Casilla casilla; // Los edificios se situan en casillas

    public String getTipo() {

        return this.tipo;

    }

    public void setTipo(String tipo) {

        if (tipo.equals("Casa") || tipo.equals("Hotel") || tipo.equals("Piscina") || tipo.equals("Pista de deportes"))
            this.tipo = tipo;

    }

    public void setPrecio(float precio) {

        if (precio > 0) {

            this.precio = precio;

        }
    }

    public float getPrecio() {

        return this.precio;

    }

    public void setCasilla(Casilla casilla) {

        this.casilla = casilla;

    }

    private Casilla getCasilla() {

        return this.casilla;

    }

    public String getID() {

        return this.ID;

    }

    public void setID(String tipo, int num) {

        this.ID = tipo;

    }

    public Edificio(String tipo, Casilla casilla) {

            if (tipo.equals("Casa")) {

                this.tipo = "Casa";
                this.casilla = casilla;

                this.ID = "Casa-";
                this.ID += Valor.NumeroCasasConstruidas;

                this.precio = casilla.getGrupo().getValor() * 0.60f;

            }
            if (tipo.equals("Hotel")) {

                this.tipo = "Hotel";
                this.casilla = casilla;

                this.ID = "Hotel-";
                this.ID += Valor.NumeroHotelesConstruidos;

                this.precio = casilla.getGrupo().getValor() * 0.60f;


            }
            if (tipo.equals("Piscina")) {

                this.tipo = "Piscina";
                this.casilla = casilla;

                this.ID = "Piscina-";
                this.ID += Valor.NumeroPiscinasConstruidas;

                this.precio = casilla.getGrupo().getValor() * 0.40f;

            }
            if (tipo.equals("Pista de deportes")) {

                this.tipo = "Pista de deportes";
                this.casilla = casilla;

                this.ID = "Pista de deportes-";
                this.ID += Valor.NumeroPistasConstruidos;

                this.precio = casilla.getGrupo().getValor() * 1.25f;
            }

    }


    @Override
    public String toString(){

/* FORMATO DE EJEMPLO
    {
        id: casa-2,
        propietario: Pedro,
        casilla: Solar1,
        grupo: negro
        coste: 600000
        },

*/

        String ret = new String();

        ret +=   "{\n";
        ret += ("  id: " + this.ID + "\n");
        ret += ("  propietario: " +this.casilla.getDuenho().getNombre() + "\n");
        ret += ("  casilla: " + this.casilla.getNombre() + "\n");
        ret += ("  grupo: " + this.casilla.getGrupo().getID() + "\n");
        ret += ("  coste: " +this.precio + "\n");
        ret +=   "}\n";

        return ret;
    }

}
