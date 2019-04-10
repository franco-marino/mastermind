package mastermind;

import java.io.IOException;
import static mastermind.Utility.*;
/**
 *
 * @author franco-marino
 */
public class Mastermind {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws mastermind.ColorNotFoundException
     */
    public static void main(String[] args) throws IOException, ColorNotFoundException {
        boolean restart = false;
        printBanner();
        do{
            Gameboard gameboard = initGame();
            if(gameboard != null){
                 gameboard.play();
                restart = restartGame();
                clearConsole();
            } else restart = false;
        }while(restart);
        
       exitMessage();
    }
    
}
