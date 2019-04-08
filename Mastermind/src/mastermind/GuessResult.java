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
     *
     */
    public GuessResult() {
        this.totallyCorrect = new ArrayList<>();
        this.onlyValuesCorrect =  new ArrayList<>();
    }

    /**
     *
     * @return
     */
    public ArrayList<Peg> getTotallyCorrect() {
        return totallyCorrect;
    }

    /**
     *
     * @return
     */
    public ArrayList<Peg> getOnlyValuesCorrect() {
        return onlyValuesCorrect;
    }

    /**
     *
     * @param codeLength
     * @return
     */
    public boolean isGuessed(int codeLength) {
        return (this.totallyCorrect.size() == codeLength);
    }
    
    /**
     *
     * @param size
     */
    public void setTotallyCorrect(int size) {
        for(int i=0;i<size;i++){
            this.totallyCorrect.add(new Peg(Colors.RED));
        }
    }

    /**
     *
     * @param size
     */
    public void setOnlyValuesCorrect(int size) {
        for(int i=0;i<size;i++){
            this.onlyValuesCorrect.add(new Peg(Colors.WHITE));
        }
    }
    
    /**
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
