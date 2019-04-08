package mastermind;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
    private final int MAX_AI_ATTEMPTS = 10;
    
    //Player vs CPU

    /**
     *
     */
    public Gameboard() {
        this.AI = false;
        this.attempt = 1;
        this.gameCode = generateCode();
        this.win = false;
    }
    
    //CPU vs Player

    /**
     *
     * @param code
     */
    public Gameboard(Code code){
        this.AI = true;
        this.gameCode = code;
        this.attempt = 1;
        this.win = false;
    }
    
    /**
     *
     * @param gameboard
     */
    public Gameboard(Gameboard gameboard){
       this.AI = gameboard.AI;
       this.attempt = gameboard.attempt;
       this.gameCode = gameboard.gameCode;
       this.win = gameboard.win;
    }

    /**
     *
     * @return
     */
    public int getCodeLength() {
        return codeLength;
    }

    /**
     *
     * @return
     */
    public boolean isAI() {
        return AI;
    }

    /**
     *
     * @return
     */
    public int getAttempt() {
        return attempt;
    }

    /**
     *
     * @return
     */
    public Code getGameCode() {
        return gameCode;
    }
     
    /**
     *
     * @throws IOException
     */
    public void play() throws IOException{
        Utility.clearConsole();
        if(AI){
            playAI();
        } else{
            playUser();
        }
        
        if(!win) Utility.displayCode("[*] Game code: ", gameCode);
    }
    
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
    
    private Colors generateRandomColor(){
        Random random = new Random();
        return Colors.values()[random.nextInt(Colors.values().length)];
    }
    
    private void playAI() throws UnsupportedEncodingException{
        //set combinations
        AI ai = new AI(new Gameboard(this));
        do{
            Utility.printInteger("Attempt: ", attempt);
            ai.removeCombination(ai.getCurrentGuess());
            ai.removeCandidatedSolution(ai.getCurrentGuess());
           
            GuessResult result = checkCode(new Code(ai.getCurrentGuess()),new Code(this.gameCode));
            Utility.displayCode("Guess: ",ai.getCurrentGuess());
            Utility.displayGuessResult("Result: ",result);
            if(result.isGuessed(this.codeLength)) this.win = true;
            else{
                ai.cleanSolutions(result);
                ai.minimax();
            }
            this.attempt++;
        }while(!gameOver());
        if(win) Utility.displayWinMessage("AI");
        else Utility.displayFailMessage("AI");
       
    }
    
    private void playUser() throws IOException{
        do{
            Utility.printInteger("Attempt number: ", attempt);
            Code guessCode = Utility.askForCode(this.codeLength);
            GuessResult result = checkCode(new Code(guessCode),new Code(this.gameCode));
            Utility.displayCode("Your guess: ", guessCode);
            Utility.displayGuessResult("Result: ",result);            
            if(result.isGuessed(this.codeLength)) this.win = true;
            this.attempt++;
        }while(!gameOver());
        if(win) Utility.displayWinMessage("Human");
        else Utility.displayFailMessage("Human");
    }
    
    /**
     *
     * @param guess
     * @param code
     * @return
     */
    public GuessResult checkCode(Code guess,Code code){
        GuessResult result = new GuessResult();
        result.setTotallyCorrect(totallyCorrect(guess,code));
        result.setOnlyValuesCorrect(onlyValuesCorrect(guess,code));
        return result;
    }
    
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
     *
     * @return
     */
    public boolean gameOver(){
        return (this.attempt>(AI ? MAX_AI_ATTEMPTS : MAX_HUMAN_ATTEMPTS) || this.win);
    }
    
}
