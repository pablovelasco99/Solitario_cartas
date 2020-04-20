/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solitario.IU;
import solitario.Core.Baraja;
import solitario.Core.JugadorNuevo;
import solitario.Core.MesaNueva;
import solitario.Core.MesaNueva.Estado;

/**
 *
 * @author pablo
 */
public class SolitarioNuevo {
    
    public static void inicioPartida(){
        
        JugadorNuevo j1 = new JugadorNuevo("Pablo");
        MesaNueva mesa = new MesaNueva();
        Baraja baraja = new Baraja();
        mesa.distribuirMesa(baraja);
        int origen = 0;
        int destino = 0;
        
        do{
            try{
                System.out.println(mesa.toString());
                origen = jugadaSeleccionOrigen();
                System.out.println(origen);
                destino = jugadaSeleccionDestino();
                System.out.println(destino);
                mesa.jugada(j1.takeCarta(mesa, origen), origen, destino);
                
            }catch(Exception exc){
                System.err.println("hola");
            }
            
        }while(mesa.movimientoPosible());
        
        System.out.println(mesa.toString());
        
        if(mesa.getEstado() == Estado.GANADO){
            System.out.println("Has ganado " + j1.getNombre() + " :)");
        }else{
            System.out.println("Has perdido " + j1.getNombre() + " :)");
        }
        
    }
    
    
    public static int jugadaSeleccionOrigen(){
        
        int i = 0;
        
        while(i < 5 || i > 20){
            i = ES.pideNumero("Introduce el monton de la carta que deseas mover: ");
            if(i < 5 || i > 20){
                System.err.println("Montón no válido, introduce un montón correcto [4-20]: ");
            }
        }
        
        return i;
    }
    
    public static int jugadaSeleccionDestino(){
        
        int i = 0;
        
        while(i < 1 || i > 20){
            i = ES.pideNumero("Introduce el montón a donde quieres mover la carta: ");
            if(i < 1 ||i > 20){
                System.err.println("Montón no válido, introduce un montón correcto [1-20]: ");
            }
        }
        if(i < 5){
            return i-1;
        }else{
            return i;
        }
    }
    
    
}
