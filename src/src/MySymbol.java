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
    
    //ARTHEMETIC OPERATIONS ////////////////////////////////////////////////////
    public MySymbol add(MySymbol b) {
        MySymbol retSymbol = new MySymbol();
        retSymbol.setType( getCompatableType(this, b) );
        
        if(retSymbol.getType() == sym.INT) {
            retSymbol.setValue( (int)this.getValue() + (int)b.getValue() );
        } else if(retSymbol.getType() == sym.FLOAT) {
            retSymbol.setValue( Float.valueOf(this.getValue().toString()) + Float.valueOf(b.getValue().toString()) );
        } else if(retSymbol.getType() == sym.CHAR) {
            retSymbol.setValue(this.getValue().toString() + b.getValue().toString() );
        }
        
        return retSymbol;
    }
    public MySymbol subtract(MySymbol b) {
        MySymbol retSymbol = new MySymbol();
        retSymbol.setType( getCompatableType(this, b) );
        
        if(retSymbol.getType() == sym.INT) {
            retSymbol.setValue( (int)this.getValue() - (int)b.getValue() );
        } else if(retSymbol.getType() == sym.FLOAT) {
            retSymbol.setValue( Float.valueOf(this.getValue().toString()) - Float.valueOf(b.getValue().toString()) );
        }
        
        return retSymbol;
    }
    public MySymbol divide(MySymbol b) {
        MySymbol retSymbol = new MySymbol();
        retSymbol.setType( getCompatableType(this, b) );
        
        if(retSymbol.getType() == sym.INT) {
            retSymbol.setValue( (int)this.getValue() / (int)b.getValue() );
        } else if(retSymbol.getType() == sym.FLOAT) {
            retSymbol.setValue( Float.valueOf(this.getValue().toString()) / Float.valueOf(b.getValue().toString()) );
        }
        
        return retSymbol;
    }
    public MySymbol multiply(MySymbol b) {
        MySymbol retSymbol = new MySymbol();
        retSymbol.setType( getCompatableType(this, b) );
        
        if(retSymbol.getType() == sym.INT) {
            retSymbol.setValue( (int)this.getValue() * (int)b.getValue() );
        } else if(retSymbol.getType() == sym.FLOAT) {
            retSymbol.setValue( Float.valueOf(this.getValue().toString()) * Float.valueOf(b.getValue().toString()) );
        }
        
        return retSymbol;
    }
    ////////////////////////////////////////////////////////////////////////////
    
    //Comparison Operations ////////////////////////////////////////////////////
    
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
            }
        } else {
            //TODO: handle properly
            System.out.println("type error: can't compare.");
        }
        return false;
    }
    
    public boolean isGreater(MySymbol otherMySymbol) {
        boolean ret = false;
        if(getCompatableType(this, otherMySymbol) != -1) {
            ret =  Float.valueOf(this.getValue().toString()) > Float.valueOf((otherMySymbol.getValue().toString()));
        }
        return ret;
    }
    public boolean isGreaterAndEqual(MySymbol otherMySymbol) {
        boolean ret = false;
        if(getCompatableType(this, otherMySymbol) != -1) {
            ret =  Float.valueOf(this.getValue().toString()) >= Float.valueOf((otherMySymbol.getValue().toString()));
        }
        return ret;
    }
    public boolean isLess(MySymbol otherMySymbol) {
        boolean ret = false;
        if(getCompatableType(this, otherMySymbol) != -1) {
            ret =  Float.valueOf(this.getValue().toString()) < Float.valueOf((otherMySymbol.getValue().toString()));
        }
        return ret;
    }
    public boolean isLessAndEqual(MySymbol otherMySymbol) {
        boolean ret = false;
        if(getCompatableType(this, otherMySymbol) != -1) {
            ret =  Float.valueOf(this.getValue().toString()) <= Float.valueOf((otherMySymbol.getValue().toString()));
        }
        return ret;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    // This method checks it two Symbol objects are of a compatable datatype.
    // if they are compatable, then it returns the data type of which a resulting
    // Symbol object should be if an operation is performed on the two objects.
    private int getCompatableType(MySymbol a, MySymbol b) {
        int retType = -1;
        switch(a.getType()) {
            case sym.INT:
                if(b.getType() == sym.INT){
                    retType = sym.INT;
                } else if (b.getType() == sym.FLOAT) {
                    retType = sym.FLOAT;
                }
                break;
            case sym.FLOAT:
                if(b.getType() == sym.INT){
                    retType = sym.FLOAT;
                } else if (b.getType() == sym.FLOAT) {
                    retType = sym.FLOAT;
                }
                break;
            case sym.BOOLEAN:
                if(b.getType() == sym.BOOLEAN){
                    retType = sym.BOOLEAN;
                }
                break;
            case sym.CHAR:
//                if(b.getType() == sym.CHAR){
                    retType = sym.CHAR;
//                }
                break;
        }
        if(retType == -1) {
            System.out.println("Type error getCompatableType(). Symbols: "+a+ ", "+b);
            System.exit(1);
        }
        return retType;
    }
    
    @Override
    public String toString() {
        switch(this.type) {
            case sym.INT:
                return "[value: " + ((Integer)this.value).toString() + ", type: " + this.type+ "]";
            case sym.FLOAT:
                return "[value: " + ((Float)this.value).toString() + ", type: " + this.type +  "]";
            case sym.BOOLEAN:
                return "[value: " + ((Boolean)this.value).toString() + ", type: " + this.type +"]";
            case sym.CHAR:
                return "[value: " + (this.value).toString() + ", type: " + this.type +"]";
            default:
                return "[value: " + this.value.toString() + ", (fucked up)type: " + this.type +"]";
        }
    }
}
