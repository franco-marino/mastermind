package mastermind;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
/**
 *
 * @author franco-marino
 */
public class AI {
 
    private ArrayList<Code> combinations;
    private ArrayList<Code> candidatedSolutions;
    private final int CODE_LENGTH = 4;
    private final int NUM_COLORS = 6;
    private Code currentGuess;
    private final Gameboard gameboard;
    
    /**
     * Instantiate AI object and create the set of 1296 combinations
     * @param gameboard
     */
    public AI(Gameboard gameboard){
        this.currentGuess = new Code(new int[]{1,1,2,2});
        this.gameboard = new Gameboard(gameboard);
        createSet();
    }
    
    /**
     * Create the set of 1296 code combinations
     */
    private void createSet(){
        this.combinations = new ArrayList<>();
        int[] current = new int[]{0,0,0,0};
        ArrayList<Integer> elements = new ArrayList<>(CODE_LENGTH);
        for(int i=1;i<=NUM_COLORS;i++){
            elements.add(i);
        }

        combinationRecursive(CODE_LENGTH,0,current,elements);
        candidatedSolutions = new ArrayList<>(combinations);
    }
    
    /**
     * Recursive function used to create set of combinations
     * @param codeLength
     * @param position
     * @param current
     * @param elements 
     */
    private void combinationRecursive(int codeLength,int position,int[] current, ArrayList<Integer>elements){
        if(position >= codeLength){
            this.combinations.add(new Code(current));
            return;
        }
        
        for(int j=0;j<elements.size();j++){
            current[position] = elements.get(j);
            combinationRecursive(codeLength, position+1, current, elements);
        }
    }
    
    /**
     * return combinations arrayList
     * @return an arrayList of combinations
     */
    public ArrayList<Code> getCombinations(){
        return this.combinations;
    }

    /**
     * return candidate solutions
     * @return an arrayList of combinations
     */
    public ArrayList<Code> getCandidatedSolutions() {
        return this.candidatedSolutions;
    }

    /**
     * return currentGuess
     * @return AI current Guess
     */
    public Code getCurrentGuess() {
        return currentGuess;
    }
    
    /**
     * remove a code from combinations arrayList
     * @param codeToRemove
     * @param whereRemove
     * @return index of removed combination
     */
    public int removeCombination(Code codeToRemove, ArrayList<Code> whereRemove){
        int index=-1;
        for (Code c : whereRemove) {
            if(c.equals(codeToRemove)) index = whereRemove.indexOf(c);
        }
        if(index!=-1) whereRemove.remove(index);
        
        return index;
    }
    
    /**
     * increment score if guessResult already exists, otherwise add a new entry and set score=1
     * @param scoresCount
     * @param pegScore
     */
    public void registerScoreCount(HashMap<GuessResult,Integer> scoresCount,GuessResult pegScore){
        if(scoresCount.containsKey(pegScore)) scoresCount.replace(pegScore,scoresCount.get(pegScore)+1);
        else scoresCount.put(pegScore,1);
    }
    
    /**
     * remove all code that not give the same guessResult of currentGuess
     * @param resultToCheck
     */
    public void cleanSolutions(GuessResult resultToCheck) {
        Iterator<Code> it = this.candidatedSolutions.iterator();
        while (it.hasNext()){
            GuessResult result = gameboard.checkCode(new Code(this.currentGuess),new Code(it.next()));
            if(!result.equals(resultToCheck)) it.remove();
        }
    }
    
    /**
     * implementation of minmax algorithm. It's used to get the best guess for the next turn
     */
    public void minmax() {
        HashMap<GuessResult, Integer> scoresCount = new HashMap();
        HashMap<Code,Integer> scores = new HashMap();
        ArrayList<Code> nextGuesses = new ArrayList();

        for(Code combination:this.combinations){
            for(Code candidate:this.candidatedSolutions){
                GuessResult pegScore = gameboard.checkCode(new Code(combination), new Code(candidate));
                registerScoreCount(scoresCount, pegScore);
            }
            int maxScore = (Collections.max(scoresCount.values()));
            scores.put(combination, maxScore);
            scoresCount.clear();
        }
      
        int min =(Collections.min(scores.values()));
        
        scores.entrySet().stream().filter((entry) -> (entry.getValue() == min)).forEachOrdered((entry) -> {
            nextGuesses.add(new Code(entry.getKey()));
        });
        
        //get best guess
        boolean found = false;
        for(Code code : nextGuesses){
            for(Code candidate: this.candidatedSolutions){
                if(candidate.equals(code)){
                    this.currentGuess = new Code(code);
                    found = true;
                }
            }
        }
        if(!found){
            for(Code code: nextGuesses){
                for(Code combination: this.combinations){
                    if(combination.equals(code)) {
                        this.currentGuess = new Code(code);
                        found = true;
                    }
                }
            }
        }
        
        
    }
   
}
