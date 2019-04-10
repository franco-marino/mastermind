package mastermind;

/**
 *
 * @author franco-marino
 */
public enum Colors {

    None,
    RED,
    GREEN,
    BLUE,
    YELLOW,
    WHITE,
    CYAN;
    
    /**
     * return a Color by the index
     * @param index
     * @return
     */
    public static Colors getColor(int index){
        return Colors.values()[index]; 
    }
    
    /**
     * return a color by the char passed as parameter, if it is not equals to a color theow a "ColorNotFoundException"
     * @param c
     * @return
     * @throws ColorNotFoundException 
     */
    public static Colors getColor(char c) throws ColorNotFoundException{
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

            default: throw new ColorNotFoundException(c);
        }
        return color;
    }
    
        
}
