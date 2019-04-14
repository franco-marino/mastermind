package mastermind;

import java.io.IOException;
import java.util.Random;

/**
 *
 * @author franco-marino
 */
public class Gameboard {
    private boolean AI;
    private final int codeLength = 4;
    private int attempt;
    private Code gameCode;
    private boolean win;
    private final int MAX_HUMAN_ATTEMPTS = 10;
    private final int MAX_AI_ATTEMPTS = 5;
    
    /**
     * Instantiate a gameboard for an "Human vs AI" game
     */
    public Gameboard() {
        this.AI = false;
        this.attempt = 1;
        this.gameCode = generateCode();
        this.win = false;
    }
    
    /**
     * Instantiate a gameboard for a "AI vs Human" game
     * @param code
     */
    public Gameboard(Code code){
        this.AI = true;
        this.gameCode = code;
        this.attempt = 1;
        this.win = false;
    }
    
    /**
     * Make a copy from an other gameboard
     * @param gameboard
     */
    public Gameboard(Gameboard gameboard){
       this.AI = gameboard.AI;
       this.attempt = gameboard.attempt;
       this.gameCode = gameboard.gameCode;
       this.win = gameboard.win;
    }

    /**
     * return codeLength
     * @return codeLength
     */
    public int getCodeLength() {
        return codeLength;
    }

    /**
     * return true if AI is playing otherwise false
     * @return if AI is playing (true or false)
     */
    public boolean isAI() {
        return AI;
    }

    /**
     * return the attempt
     * @return the attempt number
     */
    public int getAttempt() {
        return attempt;
    }

    /**
     * return the game code
     * @return the game code
     */
    private Code getGameCode() {
        return gameCode;
    }
     
    /**
     * play Game, if AI is playing call playAI method otherwise call playUser method 
     * @throws IOException
     * @throws mastermind.ColorNotFoundException
     */
    public void play() throws IOException, ColorNotFoundException{
        Utility.clearConsole();

        if(AI){
            playAI();
        } else{
            playUser();
        }
        
        if(!win) Utility.displayCode("[*] Game code: ", gameCode);
        
    }
    
    /**
     * generate a random Code, duplicated are allowed
     * @return generated random code
     */
    private Code generateCode(){
        Code c = new Code();
        int i=0;
        do{
            Peg randomPeg = new Peg(generateRandomColor());
            if(randomPeg.getColor()!=Colors.None) {
                c.addPeg(randomPeg);
                i++;
            }
        }while(i<this.codeLength);
        return c;
    }
    
    /**
     * Choose a random color from the Colors enum
     * @return 
     */
    private Colors generateRandomColor(){
        Random random = new Random();
        return Colors.values()[random.nextInt(Colors.values().length)];
    }
    
    /**
     * start a game for the AI that use minmax algorithm
     */
    private void playAI(){
        AI ai = new AI();
        do{
            Utility.printInteger("Attempt: ", attempt);
            ai.removeCombination(ai.getCurrentGuess(),ai.getCombinations());
            ai.removeCombination(ai.getCurrentGuess(),ai.getCandidatedSolutions());     
           
            GuessResult result = checkCode(new Code(ai.getCurrentGuess()),new Code(this.gameCode));
            Utility.displayCode("Guess: ",ai.getCurrentGuess());
            Utility.displayGuessResult("Result: ",result);
            if(result.isGuessed(this.codeLength)) this.win = true;
            else{
                ai.cleanSolutions(result,this);
                ai.minmax(this);
            }
            this.attempt++;
        }while(!gameOver() && !isWin());
        if(win) Utility.displayWinMessage("AI");
        else Utility.displayFailMessage("AI");
       
    }
    
    /**
     * start a game for the user, asks a code until the number of available attempts ends or he guess the code
     * @throws IOException
     * @throws ColorNotFoundException 
     */
    private void playUser() throws IOException, ColorNotFoundException{
        do{
            Utility.printInteger("Attempt number: ", attempt);
            Code guessCode = Utility.askForCode(this.codeLength);
            GuessResult result = checkCode(new Code(guessCode),new Code(this.gameCode));
            Utility.displayCode("Your guess: ", guessCode);
            Utility.displayGuessResult("Result: ",result);            
            if(result.isGuessed(this.codeLength)) this.win = true;
            this.attempt++;
        }while(!gameOver() && !isWin());
        if(win) Utility.displayWinMessage("Human");
        else Utility.displayFailMessage("Human");
    }
    
    /**
     * check the guess with the code passed as second parameter
     * @param guess
     * @param code
     * @return a GuessResult object that contains the result of the check
     */
    public GuessResult checkCode(Code guess,Code code){
        GuessResult result = new GuessResult();
        result.setTotallyCorrect(totallyCorrect(guess,code));
        result.setOnlyValuesCorrect(onlyValuesCorrect(guess,code));
        return result;
    }
    
    /**
     * calculate the totally correct pegs in the guess code 
     * @param guess
     * @param code
     * @return number of totally correct pegs
     */
    private int totallyCorrect(Code guess,Code code){
        int count=0;
        for(int i=0;i<code.getCodeLength();i++){
            if(code.getPeg(i).equals(guess.getPeg(i))) {
                count++;
                guess.getPeg(i).setColor(Colors.None);
                code.getPeg(i).setColor(Colors.None);
            }
        }
        return count;
    }
    
    /**
     * calculate the only value correct pegs in the guess code 
     * @param guess
     * @param code
     * @return number of only value correct pegs
     */
    private int onlyValuesCorrect(Code guess,Code code){
        int count=0;
        for(Peg p : guess.getCode()){
            if(p.getColor() != Colors.None){
                int gameCodeIndex = code.contains(p);
                if(gameCodeIndex >= 0 && gameCodeIndex != guess.getPegIndex(p)) {
                    count ++;
                    p.setColor(Colors.None);
                    code.getPeg(gameCodeIndex).setColor(Colors.None);
                }
            }
            
        }
        return count;
    }
    
    /**
     * checks if the maximum number of attempts has been exceeded
     * @return if true if game is over
     */
    public boolean gameOver(){
        return (this.attempt>(AI ? MAX_AI_ATTEMPTS : MAX_HUMAN_ATTEMPTS));
    }
    
    /**
     * check if win attribute is true
     * @return true if player or AI has won the game
     */
    public boolean isWin(){
        return this.win;
    }
    
}
