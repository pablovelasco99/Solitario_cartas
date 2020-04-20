/*
 * Representa al único jugador de la partida, identificado por el nombre 
 * Funcionalidad: le da la vuelta a una carta que está boca abajo, escoge una carta para moverla o al montón de descarte
 *                o la mueve encima de otra carta del interior
 */
package solitario.Core;

import java.util.Stack;
import solitario.IU.ES;

/**
 *
 * @author AEDI
 */
public class Jugador {
    
    private Mesa mesa;
    
    public Jugador(){
        mesa = new Mesa();
        mesa.distribuirMesa();
    }
    
    //El jugador indica origen y destino de la jugada, dentro de comprueba si el movmiento es 
    //Hacia fuera o dentro, si estamos moviento un uno o una carta cualquiera y si se puede realizar o no el movimiento
    public void jugada(Stack<Carta> origen, Stack<Carta> destino) throws Exception{
        
        if(movimientoHaciaFuera(destino, mesa)){
            if(moviendoUnos(origen, destino)){
                destino.push(origen.pop());
            }else{
                if(!destino.isEmpty() && movimientOportunoFuera(origen, destino)){
                    destino.push(origen.pop());
                }
                else{
                    System.err.println("Movimiento invalido, mueve la carta a "
                            + "un montón válido");
                }
            }
        }else{
            if(!movimientoHaciaFuera(destino, mesa)){
                if(!destino.isEmpty() && movimientOportunoDentro(origen, destino)){
                    destino.push(origen.pop());
                }else{
                    System.err.println("Movimiento invalido, mueve la carta a "
                            + "un montón válido");
                }
            }
        }
        
    }
    
    //devuelve la mesa
    public Mesa getMesa(){
        return mesa;
    }
    
    //Comprueba si la carta que vamos a mover es un 1 o no
    public boolean moviendoUnos(Stack<Carta> origen, Stack<Carta> destino){
        if(origen.peek().getNum() == 1 && destino.isEmpty()){
            return true;
        }else{
            return false;
        }
    }
    
    //Comprueba si el movimiento se puede realizar por dentro de la mesa
    public boolean movimientOportunoDentro(Stack<Carta> origen, Stack<Carta> destino){
        
        boolean movimiento;
        
        if ((destino.peek().getNum() == origen.peek().getNum() + 1) && 
                destino.peek().getPalo() == origen.peek().getPalo()) {
            movimiento = true;
        } else {
            movimiento = false;
        }
        return movimiento;
    }
    
    //Comprueba si el movimiento se puede realizar hacia los montones de fuera de la mesa
    public boolean movimientOportunoFuera(Stack<Carta> origen, Stack<Carta> destino){
        
        boolean movimiento;
        
        if(destino.isEmpty()){
            movimiento = true;
        }else{
            if((destino.peek().getNum() == origen.peek().getNum() - 1) && destino.peek().getPalo() == origen.peek().getPalo()){
                movimiento = true;
            }else{
                movimiento = false;
            }
        }
        return movimiento;
    }
    
    //Comprueba si el movimiento seleccionado es hacia fuera
    public boolean movimientoHaciaFuera(Stack<Carta> x, Mesa m) throws Exception{
        
        boolean fuera = true;
        
        for(int i = 1; i < mesa.numFilas; i++){
            for(int j = 0; j < mesa.numColumnas; j++){
                if(m.getMontonInterior(i, j).equals(x)){
                    fuera = false;
                }
            }
        }
        
        return fuera;
        
    }
    
    //El jugador selecciona el monton de origen
    public Stack<Carta> seleccionOrigen(Mesa m) throws Exception{
        int i = 0;
        while(i < 5 || i > 20){
            i = ES.pideNumero("Introduce el montón de la carta que deseas mover:");
            if(i < 5 ||i > 20){
                System.err.println("Montón no válido, introduce un montón correcto [4-20]: ");
            }
        }
        
        return m.getMontonInterior((i-5)/4, (i-5)%4);
    }
    
    //El jugador selecciona el monton destino
    public Stack<Carta> seleccionDestino(Mesa m) throws Exception{
        int i = 0;
        while(i < 1 || i > 20){
            i = ES.pideNumero("Introduce el montón a donde quieres mover la carta:");
            if(i < 1 ||i > 20){
                System.err.println("Montón no válido, introduce un montón correcto [1-20]: ");
            }
        }
        if(i < 5){
            return m.getMontonExterior(i-1);
        }else{
            return m.getMontonInterior((i-5)/4, (i-5)%4);
        }
        
    }
	
	

}

