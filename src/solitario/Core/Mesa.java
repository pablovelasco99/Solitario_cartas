/*
* Representa la mesa de juego, donde estarán todas las cartas.
* Tendrá dos partes diferenciadas:
* - la parte interior, donde inicialmente estarán colocadas las cartas correctamente para jugar al solitario
* - los montones exteriores, donde estarán colocadas las cartas por palo ordenadas de menor a mayor
* Estructura: Se utilizarán TADs adecuados para su respresentación. En concreto:
* - Una matriz de Pilas para representar la parte o montón interior 
* - Un array de Pilas para representar los montones exteriores.
* Funcionalidad: colocar las cartas para iniciar el juego, quitar una carta de la parte interior, colocar una carta en el interior,
* colocar una carta en el montón exterior correspondiente, visualizar cartas en la mesa, etc

La Pila es una estructura de datos que existe en Java y que se corresponde con la clase Stack. Por lo tanto debereis hacer uso de dicha
clase para representar la mesa de juego, y en particular de los métodos que se indican a continuación (de ser necesarios):

        public boolean empty();
        // Produce: Si la pila está vacía devuelve true, sino false.
        public Carta peek();
        // Produce: Devuelve la Carta del tope de la pila, sin eliminarla de ella.
        public Carta pop();
        // Produce: Elimina la Carta del tope de la pila y la devuelve.
        public void push(Carta item);
        // Produce: Introduce la Carta en el tope de la pila.
 */

package solitario.Core;

import java.util.Stack;



/**
 *
 * @author AEDI
 */
public class Mesa {
    
    public final int numFilas = 4;
    public final int numColumnas = 4;
    
    private Stack<Carta> [][] montonInterior;
    private Stack<Carta> [] montonExterior;
    
    //Creamos la mesa, con un stack en cada posicion de la matriz y del monton exterior
    public Mesa(){
        
        montonInterior = new Stack[numFilas][numColumnas];
        
        for(int i = 0; i < montonInterior.length; i++){
            for(int j = 0; j < montonInterior[i].length; j++){
                montonInterior[i][j] = new Stack<>();
            }
        }
        
        montonExterior = new Stack[Palos.values().length];
        
        for(int i = 0; i < Palos.values().length; i++){
            montonExterior[i] = new Stack<>();
        }
        
    }
    
    //Devuelve el stack interior que está en la posicion que le pasamos
    public Stack<Carta> getMontonInterior(int i, int x) throws Exception{
        
        if(i < 0 || i >= montonInterior.length){
            throw new Exception("Posicion fuera de rango");
        }
        if(x < 0 || x >= montonInterior[0].length){
            throw new Exception("Posiciónn fuera de rango");
        }
        else{
            return montonInterior[i][x];
        }
    }
    
    //Devuelve el stack que está en la posicion que le pasamos
    public Stack<Carta> getMontonExterior(int i) throws Exception{
        
        if(i < 0 || i >= montonExterior.length){
            throw new Exception("Posicion fuera de rango");
        }
        else{
            return montonExterior[i];
        }
    }
    
    //Devuelve el numero de cartas que hay en el montón exterior (para saber si ganamos o no)
    public int getNumCartasMontonExterior(){
        int num = 0;
        
        for(int i = 0; i <  montonExterior.length; i++){
            num += montonExterior[i].size();
        }
        
        return num;
    }
    
    //Distribuimos las cartas por la mesa según la distribución dada
    public void distribuirMesa(){
        Baraja baraja = new Baraja();
        
        for(int i = 0; i < numFilas; i++){
            for(int j = 0; j < numColumnas; j++){
                try{
                    fuerzaBrutaCarta(baraja.popCarta(), getMontonInterior(i, j));
                }catch(Exception exc){
                    System.err.println("ERROR -> " + exc.getMessage());
                }
            }
        }
        for(int i = 0; i < numFilas; i++){
            try{
                fuerzaBrutaCarta(baraja.popCarta(), getMontonInterior(i,i));
                fuerzaBrutaCarta(baraja.popCarta(), getMontonInterior(i, 3-i));
            }catch(Exception exc){
                System.err.println("ERROR -> " + exc.getMessage());
            }
            
        }
        for(int i = 0; i < numFilas; i++){
            for(int j = 0; j < numColumnas; j++){
                try{
                    fuerzaBrutaCarta(baraja.popCarta(), getMontonInterior(i, j));
                }catch(Exception exc){
                    System.err.println("ERROR -> " + exc.getMessage());
                }
            }
        }
    }
    
    //Inserta una carta en la mesa (solo se usa al crear la mesa)
    public void fuerzaBrutaCarta(Carta carta, Stack<Carta> destino){
        destino.push(carta);
    }
    
    //Mostrar la mesa
    public String toString(){
        
        System.out.println("\n\n");
        StringBuilder str = new StringBuilder();
        int aux = 1;
        
        str.append("\t\t\t\t \033[35m   MONTON EXTERIOR\n");
        str.append("\u001B[36m______________________________________________________"
                + "_____________________________\n\n");
        for(int i = 0; i < montonExterior.length; i++){
            if(i == 3){
                str.append("\u001B[36mMonton " + aux).append("\t\t");
            }
            else{
                str.append("\u001B[36mMonton " + aux).append("\t|\t");
            }
            aux++;
        }
        
        str.append("\n");
        
        for(int j = 0; j < montonExterior.length; j++){
                if(montonExterior[j].isEmpty()){
                    if(j == 3){
                        str.append("  FIN").append("\t\t\t");
                    }
                    else{
                        str.append("  FIN").append("\t\t|\t");
                    }
                }else{
                    str.append(" " + montonExterior[j].peek().toString()).append("\t\t");
                }
            }
        
        str.append("\n\n");
        
        str.append("\t\t\t\t \033[35m   MONTON INTERIOR\n");
        str.append("\u001B[36m______________________________________________________"
                + "_____________________________\n\n");
        for(int i = 0; i < montonInterior.length; i++){
            for(int j = 0; j < montonInterior[i].length; j++){
                
                if(j == 3){
                    str.append("\u001B[36mMonton " + aux).append("\t\t");
                }else{
                    str.append("\u001B[36mMonton " + aux).append("\t|\t");
                }
                aux ++;
            }
            str.append("\n");
            for(int k = 0; k < montonInterior.length; k++){
                if(montonInterior[i][k].isEmpty()){
                    if(k == 3){
                        str.append("  FIN").append("\t\t\t");
                    }else{
                        str.append("  FIN").append("\t\t|\t");
                    }
                    
                }else{
                    str.append(" " + montonInterior[i][k].peek().toString()).append("\t\t");
                }
            }
            str.append("\n");
        }
        str.append("\u001B[36m______________________________________________________"
                + "_____________________________\n");
        
        return str.toString();
    }
    
}
