package mastermind;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 *
 * @author franco-marino
 */
public class Utility {
    private final static BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
    
    /**
     * print a banner and the game 's rules
     */
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
   
    /**
     * asks game mode and set the gameboard object 
     * @return a gameboard
     * @throws IOException
     */
    public static Gameboard initGame() throws IOException, ColorNotFoundException{
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
    
    /**
     * asks to user a code as a sequence of char
     * @param length
     * @return code by the user input
     * @throws IOException
     * @throws mastermind.ColorNotFoundException
     */
    public static Code askForCode(int length) throws IOException, ColorNotFoundException{
        Code code;
        boolean valid;
        do{
            valid = true;
            code = new Code();
            String stringCode = askForString("Choose[R,G,B,Y,W,C]: ",length);
            for(int i=0;i<length && valid;i++){
                try {
                    code.addPeg(new Peg(Colors.getColor(stringCode.charAt(i))));  
                } catch (ColorNotFoundException e) {
                    System.out.println(e.getMessage());
                    valid = false;
                }
            }
            //if(!valid) System.err.println("Your code is not valid, try again");
        }while(!valid);
        return code;
    }
    
    /**
     * asks to user a string, and check the length
     * @param message
     * @param length
     * @return string from user input
     * @throws IOException 
     */
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
    
    
    /**
     * asks to user an integer value that must be between min and max value passed as parameter
     * @param message
     * @param min
     * @param max
     * @return an integer from user input
     * @throws IOException 
     */
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
    
    /**
     * asks to user a char and check if it's valid with the values parameter
     * @param message
     * @param values
     * @return char from input user
     */
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
    /**
     * print list of colors that user can use
     */
    private static void printColorsMenu(){
        System.out.print("[*] The colors that you can choose: ");
        for(Colors temp :  Colors.values()){
            if(temp!=Colors.None) System.out.print(temp.name() + " ");
        }
        System.out.println();
    }

    /**
     * print the message and the integer passed as parameter
     * @param message
     * @param integerToDisplay
     */
    public static void printInteger(String message,int integerToDisplay){
        System.out.println(message + integerToDisplay);
    }
    
    /**
     * clear the console(it works only on a terminal)
     */
    public static void clearConsole(){
        System.out.print("\033[H\033[2J");
    }
    
    /**
     * asks for a char(y/n=) to decide whether to start the game
     * @return true if input is equals to 'y' otherwise false 
     */
    public static boolean restartGame(){
        boolean restart = false;
        char choose = askForChar("[*] Do you want to play again?[y,n]: ", new char[]{'y','n'});
        if(choose == 'y') restart = true;
        return restart;
    }

    /**
     * Display the message and the guess Result passed as parameter
     * @param message
     * @param result 
     */
    static void displayGuessResult(String message,GuessResult result) {
        System.out.println(message + result.toString());
    }
    
    /**
     * Display the message and the code passed as parameter
     * @param message
     * @param code 
     */
    static void displayCode(String message,Code code){
        System.out.println(message + code.toString());
 
    }
    
    /**
     * Display the win message for the player passed as parameter
     * @param player 
     */
    static void displayWinMessage(String player) {
        String message = player +" WIN";
        String delimiter  = "";
        StringBuilder output = new StringBuilder();
        for(int i=0;i<message.length()+10;i++){
            delimiter+="-";
        }
        output.append(delimiter).append("\n");
        output.append(String.format("%s%"+(delimiter.length()-1)+"s%n","|","|"));
        output.append(String.format("%s %s%"+(delimiter.length()-message.length()-2)+"s%n","|",message,"|"));
        output.append(String.format("%s%"+(delimiter.length()-1)+"s%n","|","|"));
        output.append(delimiter).append("\n");
        
        System.out.println(output);
        
        
    }
    
    /**
     * Display the fail message for the player passed as parameter
     * @param player 
     */
    static void displayFailMessage(String player) {
        String message = "OH,NO " +player +" LOST";
        String delimiter  = "";
        StringBuilder output = new StringBuilder();
        for(int i=0;i<message.length()+10;i++){
            delimiter+="-";
        }
        output.append(delimiter).append("\n");
        output.append(String.format("%s%"+(delimiter.length()-1)+"s%n","|","|"));
        output.append(String.format("%s %s%"+(delimiter.length()-message.length()-2)+"s%n","|",message,"|"));
        output.append(String.format("%s%"+(delimiter.length()-1)+"s%n","|","|"));
        output.append(delimiter).append("\n");
        
        System.out.println(output);
    }
    
    /**
     * Output of message showed when game end
     */
    static void exitMessage(){
        String message = "THANK YOU FOR PLAYING";
        String delimiter  = "";
        StringBuilder output = new StringBuilder();
        for(int i=0;i<message.length()+10;i++){
            delimiter+="-";
        }
        output.append(delimiter).append("\n");
        output.append(String.format("%s%"+(delimiter.length()-1)+"s%n","|","|"));
        output.append(String.format("%s %s%"+(delimiter.length()-message.length()-2)+"s%n","|",message,"|"));
        output.append(String.format("%s%"+(delimiter.length()-1)+"s%n","|","|"));
        output.append(delimiter).append("\n");
        
        System.out.println(output);
    }

}
