/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solitario.Core;

import java.util.Stack;

/**
 *
 * @author pablo
 */
public class Mesa {
    
    public final int numFilas = 4;
    public final int numColumnas = 4;
    public enum Estado {CORRIENDO, GANADO, PERDIDO};
    private Estado estado = Estado.CORRIENDO;
    
    private Stack<Carta> [][] montonInterior;
    private Stack<Carta> [] montonExterior;
    
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
    
    public void distribuirMesa(Baraja baraja){
        
        baraja = new Baraja();
        
        for(int i = 0; i < numFilas; i++){
            for(int j = 0; j < numColumnas; j++){
                fuerzaBruta(baraja.popCarta(), i, j);
            }
        }
        for(int i = 0; i < numFilas; i++){
                fuerzaBruta(baraja.popCarta(), i, i);
                fuerzaBruta(baraja.popCarta(), i, 3-i);
        }
        for(int i = 0; i < numFilas; i++){
            for(int j = 0; j < numColumnas; j++){
                fuerzaBruta(baraja.popCarta(), i, j);
            }
        }
    }
    
    public void fuerzaBruta(Carta carta, int x, int y){
        
        montonInterior[x][y].add(carta);
    }
    
    //Calcula el numero de cartas del monton exterior
    public int getNumCartasMontonExterior(){
        int num = 0;
        
        for(int i = 0; i <  montonExterior.length; i++){
            num += montonExterior[i].size();
        }
        
        return num;
    }
    
    //Comprueba que la carta se puede mover por dentro del tablero a la posicion indicada
    public boolean movimientoOportunoDentro(int x, int y){
        
        boolean movimiento = false;
        
        if((montonInterior[(y-5)/4][(y-5)%4].peek().getNum() == montonInterior[(x-5)/4][(x-5)%4]
                .peek().getNum() + 1) && montonInterior[(y-5)/4][(y-5)%4].peek()
                        .getPalo() == montonInterior[(x-5)/4][(x-5)%4].peek().getPalo()){
            
            movimiento = true;
        }
        
        return movimiento;
        
    }
    
    //Comprueba que el movimiento se puede realizar hacia el monton exterior
    public boolean movimientoOportunoFuera(int x, int y){
        
        boolean movimiento = false;
        
        if((montonInterior[(x-5)/4][(x-5)%4].peek().getNum() == montonExterior[y]
                .peek().getNum() + 1) && montonInterior[(x-5)/4][(x-5)%4].peek()
                .getPalo() == montonExterior[y].peek().getPalo()){
            
            movimiento = true;
        }
        
        return movimiento;
    }
   
    public Estado getEstado(){
        
        if(getNumCartasMontonExterior() == 40){
            estado = Estado.GANADO;
        }
        else{
            if(movimientoPosible() == false){
                estado = Estado.PERDIDO;
            }
        }
        return estado;
    }
    
    public boolean movimientoPosible(){
     
        boolean recorrida = false;
        
        int i = 0;
        while(recorrida != true && i != numFilas){
            int j = 0;
            while (recorrida != true && j != numColumnas){
                if(movimientoPosibleHaciaFuera(i, j)){
                    recorrida = true;
                }else{
                    if(movimientoPosibleDentro(i, j)){
                        recorrida = true;
                    }
                }
                j++;
            }
            i++;
        }
        
        return recorrida;
    }
    
    //Comprueba si hay movimientos posible hacia fuera del monton
    private boolean movimientoPosibleHaciaFuera(int x, int y){
     
        boolean disponible = false;
        
        int i = 0;
        while(i < numColumnas && disponible != true){
            if(!montonInterior[x][y].isEmpty()){
                if(montonExterior[i].isEmpty()){
                    if(montonInterior[x][y].peek().getNum() == 1){
                        disponible = true;
                    }
                }else{
                    if(montonExterior[i].peek().getNum() == montonInterior[x][y]
                            .peek().getNum() - 1 && montonExterior[i].peek()
                            .getPalo() == montonInterior[x][y].peek().getPalo()){
                        disponible = true;
                    }
                }
            }
            i++;
        }
        return disponible;
    }
    
    //Comprueba si hay movimientos disponibles por dentro del tablero
    private boolean movimientoPosibleDentro(int x, int y){
        
        boolean disponible = false;
        
        int i= 1;
        int j = 0;
        
        while(i < numFilas && disponible != true){
            while(j < numColumnas && disponible != true){
                if(!montonInterior[i][j].isEmpty() && ! montonInterior[x][y].isEmpty()){
                    if(montonInterior[x][y].peek().getNum() == 1){
                        disponible = true;
                    }else{
                        if(montonInterior[i][j].peek().getNum() == montonInterior[x][y]
                                .peek().getNum() + 1 && montonInterior[i][j].peek()
                                .getPalo() == montonInterior[x][y].peek().getPalo()){
                            disponible = true;
                        }
                    }
                }
                j++;
            }
            i++;
        }
        return disponible;
    }
    
    //Le da al jugador la carta del monton de la pos (x,y)
    public Carta getCartaMonton(int x, int y){
        return montonInterior[x][y].pop();
    }
  

    public void jugada(Carta c, int x, int y){
        
        if(y < 4){
            if(c.getNum() == 1 && montonExterior[y].isEmpty()){
                montonExterior[y].push(c);
            }else{
                if(!montonExterior[y].isEmpty() && c.getNum() == montonExterior[y].peek().getNum() + 1 &&
                        c.getPalo() == montonExterior[y].peek().getPalo()){
                    montonExterior[y].push(c);
                }
                else{
                    montonInterior[(x-5)/4][(x-5)%4].push(c);
                } 
            }
        }else{
            if(c.getNum() == montonInterior[(y-5)/4][(y-5)%4].peek().getNum() - 1 
                    && c.getPalo() == montonInterior[(y-5)/4][(y-5)%4].peek().getPalo()){
                montonInterior[(y-5)/4][(y-5)%4].push(c);
            }else{
                montonInterior[(x-5)/4][(x-5)%4].push(c);
            }
            
        }
    }
    
    
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
