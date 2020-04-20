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
public class MesaNueva {
    
    public final int numFilas = 4;
    public final int numColumnas = 4;
    private enum Estado {CORRIENDO, GANADO, PERDIDO};
    private Estado estado = Estado.CORRIENDO;
    
    private Stack<Carta> [][] montonInterior;
    private Stack<Carta> [] montonExterior;
    
    public MesaNueva(){
        
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
    
    public void distribuirMesa(){
        
        Baraja baraja = new Baraja();
        
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
    
    //Comprueba que estamos moviendo un 1
    public boolean moviendoUnos(int x, int y){
        
        boolean correcto = false;
        
        if(montonInterior[(x-5)/4][(x-5)%4].peek().getNum() == 1 && montonExterior[y-1].isEmpty()){
            correcto = true;
        }
        
        return correcto;
    }
    
    //Comprueba si el movimiento es hacia fuera
    public boolean movimientoHacieFuera(int x, int y){
        
        boolean fuera = true;
        
        if(x > 0){
            fuera = false;
        }
        
        return fuera;
        
    }
    
    //Valora el estado actual de la partida
    public void estadoPartida(String nombre){
        
        if(getNumCartasMontonExterior() == 40){
            estado = Estado.GANADO;
        }else{
            if(movimientoPosible() == false){
                estado = Estado.PERDIDO;
            }
        }
        
    }
   
    public boolean estadoCorriendo(){
        if(estado == Estado.CORRIENDO){
            return true;
        }else{
            return false;
        }
    }
    
    public int estadoGanado(){
        if(estado == Estado.GANADO){
            return 1;
        }else{
            return 2;
        }
    }
    
    private boolean movimientoPosible(){
     
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
    
    //Le muestra al jugador la carta de la pos (x, y)
    public Carta peekCartaMonton(int x, int y){
        return montonInterior[x][y].peek();
    }
    
    public boolean permitirCambio(Carta c, Carta q){
        
        if(c.getNum() == q.getNum() + 1 && c.getPalo() == q.getPalo()){
            return true;
        }else{
            return false;
        }
    }
    
    //Creo que al tomar una carta el jugador del monton, al hacer pop la elimina entonces si se equivoca esa carta ya se borra
    //Hacer un metodo para que, si el movimiento no es bueno, meta la carta otra vez donde estaba?
    //Pone la carta en el monton adecuado comprobando si es posible
    //Este método mas el metodo de takeCarta de jugador hacen la jugada en si
    public void putCarta(Carta c, int x){
        if(x < 5){
            //Tiene que ser ocmprobando la carta no los montones
            if(movimientoPosibleHaciaFuera((x-5)/4,(x-5)%4)){
                if(movimientoOportunoFuera((x-5)/4,(x-5)%4)){
                    if(permitirCambio(c, montonExterior[(x-5)%4].peek())){
                        montonExterior[(x-5)%4].add(c);
                    }
                }
            }else{
                System.err.println("No es posible mover la carta");
            }
        }else{
            if(movimientoPosibleDentro((x-5)/4,(x-5)%4)){
                if(movimientoOportunoDentro((x-5)/4,(x-5)%4)){
                    if(permitirCambio(c, montonInterior[(x-5)/4][(x-5)%4].peek())){
                        montonInterior[(x-5)/4][(x-5)%4].add(c);
                    }
                }
            }else{
                System.err.println("No es posible mover la carta");
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
