/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mastermind;

/**
 *
 * @author marsh
 */
public class ColorNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>ColorNotFoundException</code> without
     * detail message.
     */
    public ColorNotFoundException() {
    }
    
    public ColorNotFoundException(char c){
        super(c+ " is not a valid color");
    }

    /**
     * Constructs an instance of <code>ColorNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ColorNotFoundException(String msg) {
        super(msg);
    }
}
