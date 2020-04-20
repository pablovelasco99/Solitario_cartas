/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solitario.IU;
import solitario.Core.JugadorNuevo;
import solitario.Core.MesaNueva;

/**
 *
 * @author pablo
 */
public class SolitarioNuevo {
    
    public static void inicioPartida(){
        
        JugadorNuevo j1 = new JugadorNuevo("Pablo");
        MesaNueva mesa = new MesaNueva();
        mesa.distribuirMesa();
        do{
            try{
                System.out.println(mesa.toString());
                if(mesa.permitirCambio(mesa.peekCartaMonton((j1.jugadaSeleccionOrigen()-5)/4, (j1.jugadaSeleccionOrigen()-5)%4), 
                        mesa.peekCartaMonton((j1.jugadaSeleccionDestino()-5)/4, (j1.jugadaSeleccionDestino()-5)%4))){
                    mesa.putCarta(j1.takeCarta(mesa, j1.jugadaSeleccionOrigen()), j1.jugadaSeleccionDestino());
                }               
            }catch(Exception exc){
                System.err.println("Error");
            }
        }while(mesa.estadoCorriendo());
        System.out.println(mesa.toString());
        if(mesa.estadoGanado() == 1){
            System.out.println("Has ganado " + j1.getNombre());
        }else{
            System.out.println("Has perdido " + j1.getNombre());
        }
        
    }
    
}
