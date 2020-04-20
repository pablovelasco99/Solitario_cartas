/*
 * Representa una carta, formada por un número y su palo correspondiente
 */
package solitario.Core;

/**
 *
 * @author AEDI
 */
public class Carta {
    
    private int num;
    private Palos palo;
    
    //Creamos una carta con su respectivo palo
    public Carta(int num, Palos palo){
        
        this.num = num;
        this.palo = palo;
    }

    //Devuelve numero de la carta
    public int getNum() {
        
        return num;
    }

    //Devuelve el palo de la carta
    public Palos getPalo() {
        return palo;
    }
    
    //Muestra la carta
    public String toString(){
        StringBuilder str = new StringBuilder();
        int num = 0;
        str.append("{");
        if((getNum() > 7)){
            str.append(getNum()+2);
        }else{
            str.append(getNum());
        }
        str.append(" / ").append(getPalo()).append("}");
        return str.toString();
    }
		


}
