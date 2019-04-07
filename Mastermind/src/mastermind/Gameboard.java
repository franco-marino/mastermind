/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mastermind;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author franc
 */
public class Gameboard {
    private boolean AI;
    private final int codeLength = 4;
    private int attempt;
    private Code gameCode;
    private boolean win;
    private final int MAX_HUMAN_ATTEMPTS = 10;
    private final int MAX_AI_ATTEMPTS = 5;
    
    //Player vs CPU
    public Gameboard() {
        this.AI = false;
        this.attempt = 1;
        this.gameCode = generateCode();
        this.win = false;
    }
    
    //CPU vs Player
    public Gameboard(Code code){
        this.AI = true;
        this.gameCode = code;
        this.attempt = 1;
        this.win = false;
    }
    
    public Gameboard(Gameboard gameboard){
       this.AI = gameboard.AI;
       this.attempt = gameboard.attempt;
       this.gameCode = gameboard.gameCode;
       this.win = gameboard.win;
    }

    public int getCodeLength() {
        return codeLength;
    }

    public boolean isAI() {
        return AI;
    }

    public int getAttempt() {
        return attempt;
    }

    public Code getGameCode() {
        return gameCode;
    }
     
    public void play() throws IOException{
        Utility.clearConsole();
        //Utility.displayCode("[ONLY FOR DEBUG] Game code: ", gameCode);
        if(AI){
            System.out.println("AI is playing");
            playAI();
        } else{
            System.out.println("Human is playing");
            playUser();
        }
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
    
    private void playUser() throws IOException, UnsupportedEncodingException{
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
    
    public boolean gameOver(){
        return (this.attempt>(AI ? MAX_AI_ATTEMPTS : MAX_HUMAN_ATTEMPTS) || this.win);
    }
    
    @Override
    public String toString(){
        return "[*] Gameboard info:\n\tThe AI is " + ((this.AI) ? "playing" : "not playing") + "\n\tCode length: " + this.codeLength;
    }

}
