package mastermind;

import java.util.ArrayList;

/**
 *
 * @author franco-marino
 */
public class Code{
    private ArrayList<Peg> pegs;

    /**
     *
     */
    public Code() {
        this.pegs = new ArrayList();
    }
    
    /**
     *
     * @param code
     */
    public Code(Code code){
        this.pegs = new ArrayList();
        code.pegs.forEach((tmp) -> {
            this.pegs.add(new Peg(tmp));
        });
    }
    
    /**
     *
     * @param code
     */
    public Code(int[] code){
        this.pegs = new ArrayList();
        for(int i : code){
            this.pegs.add(new Peg(Colors.getColor(i)));
        }
    }
    
    /**
     *
     * @param p
     */
    public void addPeg(Peg p){
        this.pegs.add(p);
    }
    
    /**
     *
     * @return
     */
    public ArrayList<Peg> getCode(){
        return this.pegs;
    }
    
    /**
     *
     * @param index
     * @return
     */
    public Peg getPeg(int index){
        return this.pegs.get(index);
    }
    
    /**
     *
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
    public int contains(Peg p){
        int index = -1;
        for(Peg tmp:this.pegs){
            if(tmp.equals(p)) index = this.getPegIndex(tmp);
        }
        return index;
    }
      
    /**
     *
     * @param p
     * @return
     */
    public int getPegIndex(Peg p){
        return this.pegs.indexOf(p);
    }
    
    /**
     *
     * @return
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
