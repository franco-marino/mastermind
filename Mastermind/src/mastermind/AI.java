/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mastermind;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author marsh
 */
public class AI {
 
    private ArrayList<Code> combinations;
    private ArrayList<Code> candidatedSolutions;
    private final int MAX_COMBINATION = 1296;
    private final int CODE_LENGTH = 4;
    private final int NUM_COLORS = 6;
    private HashMap<GuessResult, Integer> scoresCount;
    private HashMap<Code,Integer> scores;
    private Code currentGuess;
    private ArrayList<Code> nextGuesses;
    
    public AI(){
        createSet();
        this.currentGuess = new Code(new int[]{1,1,2,2});
        this.scoresCount = new HashMap<>();
        this.scores = new HashMap<>();
        this.nextGuesses = new ArrayList();
    }
    
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
    
    private void combinationRecursive(int codeLength,int position,int[] current, ArrayList<Integer>elements){
        if(position >= codeLength){
            this.combinations.add(new Code(current));
            return;
        }
        
        for(int j=0;j<elements.size();j++){
            current[position] = elements.get(j);
            combinationRecursive(codeLength, position+1, current, elements);
        }
        return;
    }
    
    public ArrayList<Code> getCombinations(){
        return this.combinations;
    }

    public ArrayList<Code> getCandidatedSolutions() {
        return this.candidatedSolutions;
    }

    public Code getCurrentGuess() {
        return currentGuess;
    }
    
    public int removeCombination(Code codeToRemove){
        int index=-1;
        for(Code c:this.combinations){
            if(c.isEquals(codeToRemove)) index = this.combinations.indexOf(c);
        }
        if(index!=-1) this.combinations.remove(index);
        
        return index;
    }
    
    public int removeCandidatedSolution(Code codeToRemove){
        int index=-1;
        for(Code c:this.candidatedSolutions){
            if(c.isEquals(codeToRemove)) index = this.candidatedSolutions.indexOf(c);
        }
        if(index!=-1) this.candidatedSolutions.remove(index);
        
        return index;
    }
    
    public void registerScoreCount(GuessResult pegScore){
        if(this.scoresCount.containsKey(pegScore)) {
            this.scoresCount.replace(pegScore,this.scoresCount.get(pegScore));
        }
        else {
            this.scoresCount.put(pegScore,1);
        }
    }
    
    public void registerScore(Code c, int score){
        this.scores.put(c, score);
    }
    
    
    public int getMaxScore(){
        return (Collections.max(this.scoresCount.values()));
    }
    
    public int getMinScore(){
        return (Collections.min(this.scores.values()));
    }
    
    public void setNextGuesses(){
        int min = getMinScore();
        
        for(Map.Entry<Code,Integer> entry:scores.entrySet()){
            if(entry.getValue() == min) this.nextGuesses.add(entry.getKey());
        }
    }
    
    public Code getNextGuess(){
        
        for(Code c:this.nextGuesses){
            if(this.candidatedSolutions.contains(c)) return c;
        }
        
        for(Code c:this.nextGuesses){
            if(this.combinations.contains(c)) return c;
        }
        
        return new Code();
    }
    
    void clearScoresCount() {
        this.scoresCount.clear();
    }
   
}
