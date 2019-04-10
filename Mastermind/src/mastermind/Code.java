package mastermind;

import java.util.ArrayList;

/**
 *
 * @author franco-marino
 */
public class Code{
    private ArrayList<Peg> pegs;

    /**
    * Instantiate an empty code object 
    */
    public Code() {
        this.pegs = new ArrayList();
    }
    
    /**
     * Instantiate a code from another with a copy
     * @param code
     */
    public Code(Code code){
        this.pegs = new ArrayList();
        code.pegs.forEach((tmp) -> {
            this.pegs.add(new Peg(tmp));
        });
    }
    
    /**
     * Instantiate a new code from an array of integers that indicates colors index
     * @param code
     */
    public Code(int[] code){
        this.pegs = new ArrayList();
        for(int i : code){
            this.pegs.add(new Peg(Colors.getColor(i)));
        }
    }
    
    /**
     * Add a new peg to the code
     * @param peg
     */
    public void addPeg(Peg peg){
        this.pegs.add(peg);
    }
    
    /**
     * return an arrayList of pegs
     * @return
     */
    public ArrayList<Peg> getCode(){
        return this.pegs;
    }
    
    /**
     * return the peg from the arrayList at the passed index
     * @param index
     * @return
     */
    public Peg getPeg(int index){
        return this.pegs.get(index);
    }
    
    /**
     * compare two code and return true if they are equals
     * @param code
     * @return
     */
    public boolean equals(Code code){
        boolean equals = true;
        for(int i=0;i<this.pegs.size();i++){
            if(!this.pegs.get(i).equals(code.getCode().get(i))) equals = false;
        }
        return equals;
    }
    
    /**
     *
     * @param p
     * @return
     */
    public int contains(Peg pegToCheck){
        int index = -1;
        for(Peg peg:this.pegs){
            if(peg.equals(pegToCheck)) index = this.getPegIndex(peg);
        }
        return index;
    }
      
    /**
     * Search the peg passed in the arrayList and return index
     * @param p
     * @return index of the peg if exists, otherwise return -1
     */
    public int getPegIndex(Peg peg){
        return this.pegs.indexOf(peg);
    }
    
    /**
     * 
     * @return code length
     */
    public int getCodeLength(){
        return this.pegs.size();
    }
    
    @Override
    public String toString(){
        StringBuilder code = new StringBuilder();
        this.pegs.forEach((tmp) -> {
            code.append(tmp.getColoredPeg());
        });        
        return code.toString();
    }
    
}
