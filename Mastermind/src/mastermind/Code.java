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
public class Code{
    private ArrayList<Peg> pegs;
    public Code() {
        this.pegs = new ArrayList();
    }
    
    public Code(Code code){
        this.pegs = new ArrayList();
        for(Peg tmp:code.pegs){
            this.pegs.add(new Peg(tmp));
        }
    }
    
    public Code(int[] code){
        this.pegs = new ArrayList();
        for(int i : code){
            this.pegs.add(new Peg(Utility.getColor(1,i)));
        }
    }
    
    public void addPeg(Peg p){
        this.pegs.add(p);
    }
    
    public ArrayList<Peg> getCode(){
        return this.pegs;
    }
    
    public Peg getPeg(int index){
        return this.pegs.get(index);
    }
    
    public boolean isEquals(Code code){
        boolean equals = true;
        for(int i=0;i<this.pegs.size();i++){
            if(!this.pegs.get(i).isEquals(code.getCode().get(i))) equals = false;
        }
        return equals;
    }
    
    public int contains(Peg p){
        int index = -1;
        for(Peg tmp:this.pegs){
            if(tmp.isEquals(p)) index = this.getPegIndex(tmp);
        }
        return index;
    }
      
    public int getPegIndex(Peg p){
        return this.pegs.indexOf(p);
    }
    
    public int getCodeLength(){
        return this.pegs.size();
    }
        
    public String toString(){
        StringBuilder code = new StringBuilder();
        for(Peg tmp : this.pegs){
            code.append(tmp.getColoredPeg());
        }
        code.append("\n");
        
        return code.toString();
    }
    
}
