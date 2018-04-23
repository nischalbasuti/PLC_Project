/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

/**
 *
 * @author nischal
 */
public class KeyValue {
    private String key;
    private MySymbol symbol;
    
    public KeyValue(String key, Aexp exp) {
        this.key = key;
        this.symbol = exp.getSymbol();
    };
    
    public String getKey() {
        return this.key;
    }
    
    public MySymbol getSymbol() {
        return this.symbol;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    public void setSymbol(MySymbol symbol) {
        this.symbol = symbol;
    }
    
    @Override
    public String toString() {
        return "("+this.key+": "+this.symbol+")";
    }
}
