/*Edificaciones:

 En cada casilla de solar se pueden construir edificios.

 Los edificios pueden ser de cuatro tipos: casas, hoteles, piscinas y pistas de deporte.

 La construcción de cada edificio tiene un coste que dependerá (1) del tipo al que pertenece y (2) del
solar en el que se construye. Por tanto, cada solar tiene un coste de construcción para cada tipo de
edificio.

 En un solar se puede construir una casa si dicho solar pertenece al jugador cuyo avatar se encuentra
en la casilla y si (1) el avatar ha caído más de dos veces en esa misma casilla o (2) el jugador posee el
grupo de casillas a la que pertenece dicha casilla.                                                                 TODO 

 En un solar se puede construir un hotel si en dicho solar ya se han construido cuatro casas y, además,
se deberán substituir las casas por el hotel.                                                                       CHECK¿?

 En un solar se puede construir una piscina si se han construido, al menos, un hotel y dos casas.                  CHECK¿?

 En un solar se puede construir una pista de deporte si se han construido, al menos, dos hoteles.                  CHECK¿?

 El número de edificios que se pueden construir en un solar está limitado al número total de edificios
que se pueden construir en el grupo al que dicho solar pertenece: si el grupo consta de dos casillas, se
pueden construir, como máximo, 2 hoteles, 2 casas, 2 piscinas y 2 pistas de deporte; mientras que, si el
grupo está compuesto por tres casillas, se puede construir, como máximo, 3 hoteles, 3 casas, 3 piscinas
y 3 pistas de deporte.

 Cada edificio tiene un coste de alquiler que depende (1) del tipo al que pertenece y (2) de la propiedad
o casilla en la que se ha construido.

 El coste de alquiler que debe pagar un avatar que cae en una propiedad con edificios será la suma del
coste de alquiler de todos los edificios construidos en dicha propiedad

El valor inicial de una casa y de un hotel es el 60% del valor inicial del solar en el que se edifica.              CHECK¿?

 El valor inicial de una piscina es el 40% del valor inicial del solar en el que se edifica.                       CHECK¿?

 El valor inicial de una pista de deporte es el 125% del solar en el que se edifica.                               CHECK¿?

*/
package monopoly;

public class Edificio {
    private String tipo;        // Piscina/Edificios/Hoteles
    private float precio;       // Precio de los edificios
    private Casilla casilla;    // Los edificios se situan en casillas

    public String getTipo(){

        return this.tipo;

    }

    public void setTipo(String tipo){

        if(tipo.equals("Casa") || tipo.equals("Hotel") || tipo.equals("Piscina") || tipo.equals("Pista de deportes"))
            this.tipo = tipo;

    }

    public void setPrecio(float precio){

        if(precio > 0){

            this.precio = precio;

        }
    }
    public float getPrecio(){

        return this.precio;

    }
    public void setCasilla(Casilla casilla){

        this.casilla = casilla;

    }

    private Casilla getCasilla(){

        return this.casilla;

    }

    public Edificio(String tipo, Casilla casilla){

        this.tipo = tipo;
        this.casilla = casilla;


        if(this.tipo.equals("Casa") || this.tipo.equals("Hotel"))
            this.precio = casilla.getValor() * 0.60f;

        if(this.tipo.equals("Piscina"))
            this.precio = casilla.getValor() * 0.40f;
            
        if(this.tipo.equals("Pista de deportes"))
            this.precio = casilla.getValor() * 1.25f;


    }

}
