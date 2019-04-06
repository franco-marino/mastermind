/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mastermind;

/**
 *
 * @author franc
 */
public class Peg {
    private Colors color;
    
    public Peg(Colors c){
        this.color = c;
    }
    
    public Peg(Peg p){
        this.color = p.color;
    }

    public Colors getColor() {
        return color;
    }
    
    public void setColor(Colors c){
        this.color = c;
    }
    
    
    public String getColoredPeg(){
        String coloredPeg = "";
        switch(this.color){
            case RED:
                coloredPeg = "\33[31m\u25C9\33[0m";
            break;
            case GREEN:
                coloredPeg = "\33[32m\u25C9\33[0m";
            break;
            case BLUE:
                coloredPeg = "\33[34m\u25C9\33[0m";
            break;
            case YELLOW:
                coloredPeg = "\33[33m\u25C9\33[0m";
            break;
            case WHITE:
                coloredPeg = "\33[37m\u25C9\33[0m";
            break;
            case CYAN:
                coloredPeg = "\33[36m\u25C9\33[0m";
            break;
            
        }
        
        return coloredPeg;
    }
    
    public boolean equals(Peg p){
        return (this.color.equals(p.getColor()));
    }
    


  
}
