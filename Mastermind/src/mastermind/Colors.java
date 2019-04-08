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

    /**
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
    public static Colors getColor(int mode,int index){
        Colors c;
        if(mode==1) {
            c = Colors.values()[index];
        }
        else{
            switch(index){
                case 'R':
                case 'r':
                    c = Colors.RED;
                break;
                case 'G':
                case 'g':
                    c = Colors.GREEN;
                break;
                case 'B':
                case 'b':
                    c = Colors.BLUE;
                break;
                case 'Y':
                case 'y':
                    c = Colors.YELLOW;
                break;
                case 'W':
                case 'w':
                    c = Colors.WHITE;
                break;
                case 'C':
                case 'c':
                    c = Colors.CYAN;
                break;

                default: c = Colors.None;
            }
        }
        
        return c;
    }
    
        
}
