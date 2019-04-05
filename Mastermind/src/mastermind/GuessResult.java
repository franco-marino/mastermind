/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mastermind;

import java.util.ArrayList;

/**
 *
 * @author franc
 */
public class GuessResult {
    private ArrayList<Peg> totallyCorrect;
    private ArrayList<Peg> onlyValuesCorrect;

    public GuessResult() {
        this.totallyCorrect = new ArrayList<>();
        this.onlyValuesCorrect =  new ArrayList<>();
    }

    public ArrayList<Peg> getTotallyCorrect() {
        return totallyCorrect;
    }

    public ArrayList<Peg> getOnlyValuesCorrect() {
        return onlyValuesCorrect;
    }

    public boolean isGuessed(int codeLength) {
        return (this.totallyCorrect.size() == codeLength);
    }
    

    public void setTotallyCorrect(int size) {
        for(int i=0;i<size;i++){
            this.totallyCorrect.add(new Peg(Colors.RED));
        }
    }

    public void setOnlyValuesCorrect(int size) {
        for(int i=0;i<size;i++){
            this.onlyValuesCorrect.add(new Peg(Colors.WHITE));
        }
    }
    
    public boolean equals(GuessResult result){
        return ((this.totallyCorrect.size() == result.getTotallyCorrect().size()) && (this.onlyValuesCorrect.size()==result.getOnlyValuesCorrect().size()));
    }
    
    

}
