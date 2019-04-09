package mastermind;

/**
 *
 * @author franco-marino
 */
public enum Colors {

    /**
     *
     */
    None,

    /**git reset --hard HEAD~1


     *
     */
    RED,

    /**
     *
     */
    GREEN,

    /**
     *
     */
    BLUE,

    /**
     *
     */
    YELLOW,

    /**
     *
     */
    WHITE,

    /**
     *
     */
    CYAN;
    
    /**
     *
     * @param mode
     * @param index
     * @return
     */
    public static Colors getColor(int index){
        return Colors.values()[index]; 
    }
    public static Colors getColor(char c){
        Colors color;
        switch(c){
            case 'R':
            case 'r':
                color = Colors.RED;
            break;
            case 'G':
            case 'g':
                color = Colors.GREEN;
            break;
            case 'B':
            case 'b':
                color = Colors.BLUE;
            break;
            case 'Y':
            case 'y':
                color = Colors.YELLOW;
            break;
            case 'W':
            case 'w':
                color = Colors.WHITE;
            break;
            case 'C':
            case 'c':
                color = Colors.CYAN;
            break;

            default: color = Colors.None;
        }
        return color;
    }
    
        
}
