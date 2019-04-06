/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mastermind;

import java.io.IOException;
import static mastermind.Utility.*;
/**
 *
 * @author franc
 */
public class Mastermind {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        boolean restart = false;
        printBanner();
        do{
            Gameboard gameboard = initGame();
            if(gameboard !=null){
                 gameboard.play();
                restart = restartGame();
                clearConsole();
            } else restart = false;
        }while(restart);
        
        System.out.println("Thank you for playing!");
    }
    
}
