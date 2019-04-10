package mastermind;

/**
 *
 * @author franco-marino
 */
public class Peg {
    private Colors color;
    
    /**
     * Instatiate new Peg with a color
     * @param color
     */
    public Peg(Colors color){
        this.color = color;
    }
    
    /**
     * Instatiante new Peg from another peg
     * @param peg
     */
    public Peg(Peg peg){
        this.color = peg.color;
    }

    /**
     * return Peg color
     * @return Peg color
     */
    public Colors getColor() {
        return color;
    }
    
    /**
     * set Peg color
     * @param color
     */
    public void setColor(Colors color){
        this.color = color;
    }
    
    /**
     * return a colored circle
     * @return a colored unicode string
     */
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
    
    /**
     * check if this peg is equals to the peg passed as parameter
     * @param peg
     * @return
     */
    public boolean equals(Peg peg){
        return (this.color.equals(peg.getColor()));
    }
    


  
}
