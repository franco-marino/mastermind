package mastermind;

/**
 *
 * @author franco-marino
 */
public class Peg {
    private Colors color;
    
    /**
     *
     * @param c
     */
    public Peg(Colors c){
        this.color = c;
    }
    
    /**
     *
     * @param p
     */
    public Peg(Peg p){
        this.color = p.color;
    }

    /**
     *
     * @return
     */
    public Colors getColor() {
        return color;
    }
    
    /**
     *
     * @param c
     */
    public void setColor(Colors c){
        this.color = c;
    }
    
    /**
     *
     * @return
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
     *
     * @param p
     * @return
     */
    public boolean equals(Peg p){
        return (this.color.equals(p.getColor()));
    }
    


  
}
