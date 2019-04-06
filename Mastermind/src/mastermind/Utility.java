/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mastermind;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author franc
 */
public class Utility {
    private final static BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
    
    
    private static void printBanner(){
        System.out.println("Mastermind by franco-marino");
    }
    
    public static Gameboard initGame() throws IOException{
        Gameboard game = null;
        int codeLength;
        boolean AI;
        printBanner();
        System.out.println("[*] Choose settings:");
        
        System.out.println("[*] Game mode: ");
        System.out.println("\t1) You vs CPU");
        System.out.println("\t2) CPU vs You (You decide the code and the CPU try to guess it)");
        AI = (askForInteger("Choose: ",1,2)==1) ? false : true;
        
        if(AI){
            codeLength = 4;
            printColorsMenu();
            Code code = askForCode(codeLength);
            game = new Gameboard(code);
        }else{
            System.out.println("[*] Code length: ");
            codeLength = askForInteger("Choose (between 3 and 6): ", 3, 6);
            game = new Gameboard(codeLength);
        }
        
        return game;
    }
    
    public static Code askForCode(int length) throws IOException{
        Code code;
        boolean valid;
        do{
            valid = true;
            code = new Code();
            String stringCode = askForString("Choose[R,G,B,Y,W,C]: ",length);
            for(int i=0;i<length && valid;i++){
                if(getColor(0,stringCode.charAt(i)) == Colors.None) {
                    valid = false;
                }
                else {
                    code.addPeg(new Peg(getColor(0,stringCode.charAt(i))));
                }
            }
            if(!valid) System.err.println("Your code is not valid, try again");
        }while(!valid);
        
        return code;
    }
    
    private static String askForString(String message,int length) throws IOException{
        boolean valid = false;
        String s = "";
        do{
            System.out.print(message);
            s = buffer.readLine();
            if(s.length()==length) valid = true;
            else System.err.println("String length must be "+length);
        }while(!valid);
        
        return s;
    }
    
    private static int askForInteger(String message,int min,int max) throws IOException{
        int choose=-1;
        boolean validate = false;
        do{
            try {
                System.out.print(message);
                choose = Integer.parseInt(buffer.readLine());
                if(choose>=min && choose<=max)validate = true;
                else System.err.println("[*] Your input must be between " + min +" and " + max + ",try again");
                
            } catch (NumberFormatException e) {
                System.err.println("[*] Your input is not a number, try again");
            }
        }while(!validate);
        
        
        return choose;
    }
    
    private static char askForChar(String message,char values[]){
        char choose = 0;
        boolean validate = false;
        do{
            try{
                System.out.print(message);
                choose = buffer.readLine().toLowerCase().charAt(0);
                for(char temp : values){
                    if(choose == temp) validate = true;
                }
                if(!validate) System.err.println("[*] Your input is not valid, try again");
            }catch(IOException e){
                System.err.println("[*] You input is not a char");
            }
        }while(!validate);
  
        return choose;
    }
    
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
    
    private static void printColorsMenu(){
        System.out.println("[*] Choose yout code values:");
        for(Colors temp :  Colors.values()){
            System.out.printf("%d) %s%n",temp.ordinal()+1,temp.name());
        }
    }

    public static void printInteger(String message,int integerToDisplay){
        System.out.println(message + integerToDisplay);
    }
    
    public static void clearConsole(){
        System.out.print("\033[H\033[2J");
    }
    
    public static boolean restartGame(){
        boolean restart = false;
        char choose = askForChar("[*] Do you want to play again?[y,n]: ", new char[]{'y','n'});
        if(choose == 'y') restart = true;
        return restart;
    }

    static void displayGuessResult(String message,GuessResult result) {
        System.out.print(message);
        //Display red pegs
        for(Peg redPeg:result.getTotallyCorrect()){
            System.out.print(redPeg.getColoredPeg());
        }
        
        //Display white pegs
        for(Peg whitePeg:result.getOnlyValuesCorrect()){
            System.out.print(whitePeg.getColoredPeg());
        }
        
        System.out.println();
    }
    
    static void displayCode(String message,Code code){
        System.out.print(message);
        for(Peg peg:code.getCode()){
            System.out.print(peg.getColoredPeg());
        }
        System.out.println();
    }

    static void displayWinMessage() {
        System.out.println("Congratulations win!");
    }

    static void displayFailMessage() {
        System.out.println("Oh no, you lose :(. Try again");
    }
}
