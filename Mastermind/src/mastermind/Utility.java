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
    
    
    public static void printBanner(){
        String banner = "  __  __           _                      _           _  \n" +
        " |  \\/  |         | |                    (_)         | | \n" +
        " | \\  / | __ _ ___| |_ ___ _ __ _ __ ___  _ _ __   __| | \n" +
        " | |\\/| |/ _` / __| __/ _ \\ '__| '_ ` _ \\| | '_ \\ / _` | \n" +
        " | |  | | (_| \\__ \\ ||  __/ |  | | | | | | | | | | (_| | \n" +
        " |_|  |_|\\__,_|___/\\__\\___|_|  |_| |_| |_|_|_| |_|\\__,_| \n" +
        "                                                         \n" +
        "                                                         ";
        String rules = "Welcome, Mastermind is a code-breaking game. The purpose is to guess the secret code decided by the player or AI\n"
                        + "according to the chosen game mode. There are 6 colors and the code consists of 6 pegs, duplicates are allowed.\n"
                        + "You win if the code is matched within 10 attempts.Every time you try to guess the gameboard returns a code formed by\n"
                        + "red and white pegs: the red ones indicate the number of pegs of the right color and in the right position, \n"
                        + "the white ones indicate the pegs of the right color but in the wrong position. Have fun with the mastermind! ";
        System.out.println(banner);
        System.out.println(rules);
    }
    
    public static Gameboard initGame() throws IOException{
        Gameboard game = null;
        int codeLength;
        boolean AI;
        StringBuilder menu = new StringBuilder();
        menu.append("------------------------\n");
        menu.append("|        MENU          |\n");
        menu.append("|----------------------|\n");
        menu.append("|   1) You vs AI       |\n");
        menu.append("|----------------------|\n");
        menu.append("|   2) AI vs You       |\n");
        menu.append("|----------------------|\n");
        menu.append("|   3) Exit            |\n");
        menu.append("------------------------\n");
        System.out.println(menu.toString());
        
        int choose = askForInteger("Choose: ",1,3);
        if(choose != 3){
            if(choose == 2){
                printColorsMenu();
                Code code = askForCode(4);
                game = new Gameboard(code);
            }else{
                game = new Gameboard();
            }
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
        System.out.print("[*] The colors that you can choose: ");
        for(Colors temp :  Colors.values()){
            if(temp!=Colors.None) System.out.print(temp.name() + " ");
        }
        System.out.println();
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

    static void displayWinMessage(String player) {
        System.out.println(player + " win!");
    }

    static void displayFailMessage(String player) {
        System.out.println("Oh no, " +player+ " lost :(\nTry again");
    }
}
