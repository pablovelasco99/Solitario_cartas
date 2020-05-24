/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solitario.IU;
import solitario.Core.Baraja;
import solitario.Core.Jugador;
import solitario.Core.Mesa;
import solitario.Core.Mesa.Estado;

/**
 *
 * @author pablo
 */
public class Solitario {
    
    public static void inicioPartida(){
        
        Jugador j1 = new Jugador("Pablo");
        Mesa mesa = new Mesa();
        Baraja baraja = new Baraja();
        mesa.distribuirMesa(baraja);
        int origen = 0;
        int destino = 0;
        
        do{
            try{
                System.out.println(mesa.toString());
                origen = jugadaSeleccionOrigen();
                destino = jugadaSeleccionDestino();
                mesa.jugada(j1.takeCarta(mesa, origen), origen, destino);
                
            }catch(Exception exc){
                System.err.println(exc.getMessage());
            }
            System.out.println(mesa.getEstado());
        }while(mesa.movimientoPosible() == Estado.CORRIENDO);
        
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
