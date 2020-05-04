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
public class Jugador {
    
    private String nombre;
    
    public Jugador(String nombre){
        this.nombre = nombre;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    //Coge la carta de una posicion de la mesa
    //Take la carta de (x,y) posicion de la mesa
    public Carta takeCarta(Mesa m, int x) throws Exception{
        return m.getCartaMonton((x-5)/4,(x-5)%4);
    }
}
