/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solitario.Core;

import solitario.IU.ES;

/**
 *
 * @author pablo
 */
public class JugadorNuevo {
    
    private String nombre;
    
    public JugadorNuevo(String nombre){
        this.nombre = nombre;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    //Take la carta de (x,y) posicion de la mesa
    public Carta takeCarta(MesaNueva m, int x) throws Exception{
        return m.getCartaMonton((x-5)/4,(x-5)%4);
    }
    
    public Carta peekCartaMonton(MesaNueva m, int x){
        return m.peekCartaMonton((x-5)/4,(x-5)%4);
    }
    
    
    public int jugadaSeleccionOrigen(){
        
        int i = 0;
        
        while(i < 5 || i > 20){
            i = ES.pideNumero("Introduce el monton de la carta que deseas mover: ");
            if(i < 5 || i > 20){
                System.err.println("Montón no válido, introduce un montón correcto [4-20]: ");
            }
        }
        
        return i;
    }
    
    public int jugadaSeleccionDestino(){
        
        int i = 0;
        
        while(i < 1 || i > 20){
            i = ES.pideNumero("Introduce el montón a donde quieres mover la carta: ");
            if(i < 1 ||i > 20){
                System.err.println("Montón no válido, introduce un montón correcto [1-20]: ");
            }
        }
        return i;
    }
    
}
