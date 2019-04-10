package mastermind;

import java.util.ArrayList;

/**
 *
 * @author franco-marino
 */
public class GuessResult {
    private ArrayList<Peg> totallyCorrect;
    private ArrayList<Peg> onlyValuesCorrect;

    /**
     * Instantiate a guessResult object
     */
    public GuessResult() {
        this.totallyCorrect = new ArrayList<>();
        this.onlyValuesCorrect =  new ArrayList<>();
    }

    /**
     * get totally correct pegs (red pegs)
     * @return
     */
    public ArrayList<Peg> getTotallyCorrect() {
        return totallyCorrect;
    }

    /**
     * get only value correct pegs (white pegs) 
     * @return
     */
    public ArrayList<Peg> getOnlyValuesCorrect() {
        return onlyValuesCorrect;
    }

    /**
     * check if there are 4 totally correct pegs (red pegs)
     * @param codeLength
     * @return true if there is a guess
     */
    public boolean isGuessed(int codeLength) {
        return (this.totallyCorrect.size() == codeLength);
    }
    
    /**
     * add the number of pegs passed as parameter to the totally correct arrayList
     * @param size
     */
    public void setTotallyCorrect(int size) {
        for(int i=0;i<size;i++){
            this.totallyCorrect.add(new Peg(Colors.RED));
        }
    }

    /**
     * add the number of pegs passed as parameter to the only value correct arrayList
     * @param size
     */
    public void setOnlyValuesCorrect(int size) {
        for(int i=0;i<size;i++){
            this.onlyValuesCorrect.add(new Peg(Colors.WHITE));
        }
    }
    
    /**
     * 
     * 
     * @param result
     * @return
     */
    public boolean equals(GuessResult result){
        return ((this.totallyCorrect.size() == result.getTotallyCorrect().size()) && (this.onlyValuesCorrect.size()==result.getOnlyValuesCorrect().size()));
    }
    
    public String toString(){
        StringBuilder guessResult = new StringBuilder();
        //Display red pegs
        this.getTotallyCorrect().forEach((redPeg) -> {
           guessResult.append(redPeg.getColoredPeg());
        });
        
        //Display white pegs
        this.getOnlyValuesCorrect().forEach((whitePeg) -> {
           guessResult.append(whitePeg.getColoredPeg());
        });
        
        return guessResult.toString();
    }
    
    

}
