/*
* Representa la baraja española, 40 cartas, 4 palos, valores de las cartas de 1 a 12 (excepto 8 y 9). 
* Estructura: se utilizará un TAD adecuado
* Funcionalidad: estando la baraja desordenada, devolverá la carta situada encima del montón de cartas
 */
package solitario.Core;

import java.util.Collections;
import java.util.Stack;


public class Baraja {
	
    public static final int cartas = 40;
    
    public Stack<Carta> baraja;
    
    //Creamos la baraja, para cada numero del 1 al 10 le asignamos un palo
    public Baraja(){
        
        baraja = new Stack<>();
        
        for(int i= 1; i <= 10; i++){
            for(Palos palo : Palos.values()){
                baraja.add(new Carta(i, palo));
            }
        }
        
        //Para resolverlo de manera fácil comentar la siguiente linea
        Collections.shuffle(baraja);
    }
    
    //Elimina una carta de la baraja
    public Carta popCarta(){
        return baraja.pop();
    }

    //Comprueba si la baraj está vacia
    public boolean esVacio(){
        return baraja.isEmpty();
    }
}
