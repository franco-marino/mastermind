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
    private final int CODE_LENGTH = 4;
    private final int NUM_COLORS = 6;
    private Code currentGuess;
    private Gameboard gameboard;
    
    public AI(Gameboard gameboard){
        this.currentGuess = new Code(new int[]{1,1,2,2});
        this.gameboard = new Gameboard(gameboard);
        createSet();
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
            if(c.equals(codeToRemove)) index = this.combinations.indexOf(c);
        }
        if(index!=-1) this.combinations.remove(index);
        
        return index;
    }
    
    public int removeCandidatedSolution(Code codeToRemove){
        int index =-1;
        for(Code c : this.candidatedSolutions){
            if(c.equals(codeToRemove)) index = this.candidatedSolutions.indexOf(c);
        }
        if(index != -1) this.combinations.remove(index);
        return index;
    }
    
    public void registerScoreCount(HashMap<GuessResult,Integer> scoresCount,GuessResult pegScore){
        if(scoresCount.containsKey(pegScore)) {
            scoresCount.replace(pegScore,scoresCount.get(pegScore));
        }
        else {
            scoresCount.put(pegScore,1);
        }
    }
    
    public void cleanSolutions(GuessResult resultToCheck) {
        ArrayList<Code> codesToRemove = new ArrayList();
        for(Code c:this.candidatedSolutions){
            GuessResult result = gameboard.checkCode(new Code(this.currentGuess),new Code(c));
            if(!result.equals(resultToCheck)) codesToRemove.add(c);
        } 
        this.candidatedSolutions.removeAll(codesToRemove);
    }
    
    public void minimax() {
        HashMap<GuessResult, Integer> scoresCount = new HashMap();
        HashMap<Code,Integer> scores = new HashMap();
        ArrayList<Code> nextGuesses = new ArrayList();
        
        for(Code combination : this.combinations){
            for(Code candidate : this.candidatedSolutions){
                GuessResult pegScore = gameboard.checkCode(new Code(combination), new Code(candidate));
                registerScoreCount(scoresCount,pegScore);
            }
            int maxScore = (Collections.max(scoresCount.values()));
            scores.put(combination, maxScore);
            scoresCount.clear();   
        }
        int min =(Collections.min(scores.values()));
        
        for(Map.Entry<Code,Integer> entry : scores.entrySet()){
            if(entry.getValue() == min) nextGuesses.add(new Code(entry.getKey()));
        }
        
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
