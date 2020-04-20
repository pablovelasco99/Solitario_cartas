/**
 * Representa el juego del solitario, con sus reglas. 
 * Se recomienda una implementación modular.
 */
package solitario.IU;

import solitario.Core.Jugador;
import solitario.Core.Mesa;


/**
 *
 * @author AEDI
 */
public class Solitario {
    
    private static enum Estado {CORRIENDO, GANADO, PERDIDO};
    private static Estado estado = Estado.CORRIENDO;
    

    //Inicio de partida, seleccion de movimiento...
    public static void inicioPartida2() {
        
        Jugador j1 = new Jugador();
        do{
            System.out.println(j1.getMesa());
            try{
                j1.jugada(j1.seleccionOrigen(j1.getMesa()), j1.seleccionDestino(j1.getMesa()));
                estadoPartida(j1.getMesa());
            }catch(Exception exc){
                System.err.println("ERROR -> " + exc.getMessage());
            }
        }while(estado == Estado.CORRIENDO);
        System.out.println(j1.getMesa());
        if(estado == Estado.GANADO){
            System.out.println("Has ganado");
        }else{
            if(estado == Estado.PERDIDO){
                System.out.println("Has perdido");
            }
        }
        System.out.println("Fin");
    }
    
    //Valora el estado de la partida, si está ganada o no
    public static void estadoPartida(Mesa m) throws Exception{
        
        if(m.getNumCartasMontonExterior() == 40){
            System.out.println("Ganado");
            estado = Estado.GANADO;
        }else{
            if(movimientoPosible(m) == false){
                System.out.println("Perdido");
                estado = Estado.PERDIDO;
            }
        }
    }
    
    //Busca si hay o no movimientos posibles con las cartas que quedan en la mesa
    private static boolean movimientoPosible(Mesa m) throws Exception{
        
        boolean recorrida = false;
        
        int i = 0;
        while(recorrida != true && i != m.numFilas){
            int j = 0;
            while (recorrida != true && j != m.numColumnas){
                if(movimientoPosibleHaciaFuera(i, j, m)){
                    recorrida = true;
                }else{
                    if(movimientoPosibleDentro(i, j, m)){
                        recorrida = true;
                    }
                }
                j++;
            }
            i++;
        }
        
        return recorrida;
    }
    
    //Valora si hay un movimiento posible hacia fuera de la mesa
    public static boolean movimientoPosibleHaciaFuera(int x, int y, Mesa m) throws Exception{
        
        boolean disponible = false;
        
        for(int i = 0; i < m.numColumnas; i++){
            if(!m.getMontonInterior(x, y).isEmpty()){
                if(m.getMontonExterior(i).isEmpty()){
                    if(m.getMontonInterior(x, y).peek().getNum() == 1){
                            disponible = true;
                    }
                }else{
                    if (m.getMontonExterior(i).peek().getNum() == m.getMontonInterior(x, y).peek().getNum() - 1
                            && m.getMontonExterior(i).peek().getPalo() == m.getMontonInterior(x, y).peek().getPalo()) {
                        disponible = true;
                    }
                }
            }
            /*if(!m.getMontonExterior(i).isEmpty() && !m.getMontonInterior(x, y).isEmpty()){
                if (m.getMontonExterior(i).peek().getNum() == m.getMontonInterior(x, y).peek().getNum() - 1
                        && m.getMontonExterior(i).peek().getPalo() == m.getMontonInterior(x, y).peek().getPalo()) {
                    disponible = true;
                }
                
            }*/
            if(disponible == true){
                break;
            }
            
        }
        return disponible;
    }
    
    //Valora si hay movimientos posibles por dentro del monton interior
    public static boolean movimientoPosibleDentro(int x, int y, Mesa m) throws Exception{
        
        boolean disponible = false;
        
        int i = 1;
        int j = 0;
        while(i < m.numFilas && disponible != true){
            while(j < m.numColumnas & disponible != true){
                if(!m.getMontonInterior(i, j).isEmpty() && !m.getMontonInterior(x, y).isEmpty()){
                    if(m.getMontonInterior(x, y).peek().getNum() == 1){
                        disponible = true;
                        break;
                    }
                    else{
                        if (m.getMontonInterior(i, j).peek().getNum() == m.getMontonInterior(x, y).peek().getNum() + 1
                                && m.getMontonInterior(i, j).peek().getPalo() == m.getMontonInterior(x, y).peek().getPalo()) {
                            disponible = true;
                            break;
                        }
                    }
                }
                j++;
            }
            i++;
        }

        /* for(int i = 1; i < m.numFilas; i++){
            for(int j = 0; j < m.numColumnas; j++){
                if(!m.getMontonInterior(i, j).isEmpty() && !m.getMontonInterior(x, y).isEmpty()){
                    if(m.getMontonInterior(x, y).peek().getNum() == 1){
                        disponible = true;
                        break;
                    }
                    else{
                        if (m.getMontonInterior(i, j).peek().getNum() == m.getMontonInterior(x, y).peek().getNum() + 1
                                && m.getMontonInterior(i, j).peek().getPalo() == m.getMontonInterior(x, y).peek().getPalo()) {
                            disponible = true;
                            break;
                        }
                    }
                }
            }
            if(disponible == true){
                break;
            }
        } */
        return disponible;
    }
    
}
