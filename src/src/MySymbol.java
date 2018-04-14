/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

/**
 * @author nischal
 */
public class MySymbol {
    private Object value;
    private int type;

    public MySymbol() {}

    public MySymbol(Object value, int type) {
        this.value = value;
        this.type = type;
    }
    
    public MySymbol(MySymbol symbol){
	this.value = symbol.getValue();
	this.type = symbol.getType();
    }
    
    public void setValue(Object value) {
	this.value = value;
    }
    
    public void setType(int type) {
	this.type = type;
    }
    
    public Object getValue() {
        return this.value;
    }
    
    public int getType() {
        return this.type;
    }

    // Check if equal to another Symbol object.
    // They are equal if they have the same value and type attributes.
    public boolean isEqual(MySymbol otherSymbol) {
        if( this.type ==  otherSymbol.getType() ) {
            switch(otherSymbol.getType()) {
                case sym.INT:
                    return (int)this.value == (int)otherSymbol.getValue();
                case sym.FLOAT:
                    return (float)this.value == (float)otherSymbol.getValue();
                case sym.BOOLEAN:
                    return (boolean)this.value == (boolean)otherSymbol.getValue();
            // case sym.CHAR:
            //     if((int)this.value == (int)otherSymbol.getValue()) {
            //         return true;
            //     } else {
            //         return false;
            //     }
            }
        } else {
            //TODO: handle properly
            System.out.println("type error: can't compare.");
        }
        return false;
    }
    
    @Override
    public String toString() {
        switch(this.type) {
            case sym.INT:
                return "[value: " + ((Integer)this.value).toString() + ", type: " + this.type + "]\n";
            case sym.FLOAT:
                return "[value: " + ((Float)this.value).toString() + ", type: " + this.type + "]\n";
            case sym.BOOLEAN:
                return "[value: " + ((Boolean)this.value).toString() + ", type: " + this.type +"]\n";
            default:
                return "[value: " + this.value.toString() + ", (fucked up)type: " + this.type +"]\n";
        }
    }
}
