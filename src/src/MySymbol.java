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
    
    public MySymbol(MySymbol symbol) {
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
        retSymbol.setType( MySymbol.getCompatableType(this, b) );
        
        switch (retSymbol.getType()) {
            case sym.INT:
                retSymbol.setValue( (int)this.getValue() + (int)b.getValue() );
                break;
            case sym.FLOAT:
                retSymbol.setValue( Float.valueOf(this.getValue().toString()) 
                    + Float.valueOf(b.getValue().toString()) );
                break;
            case sym.CHAR:
                retSymbol.setValue(this.getValue().toString() 
                    + b.getValue().toString() );
                break;
            default:
                break;
        }
        
        return retSymbol;
    }
    public MySymbol subtract(MySymbol b) {
        MySymbol retSymbol = new MySymbol();
        retSymbol.setType( MySymbol.getCompatableType(this, b) );
        
        if(retSymbol.getType() == sym.INT) {
            retSymbol.setValue( (int)this.getValue() - (int)b.getValue() );
        } else if(retSymbol.getType() == sym.FLOAT) {
            retSymbol.setValue( Float.valueOf(this.getValue().toString()) 
                - Float.valueOf(b.getValue().toString()) );
        }
        
        return retSymbol;
    }
    public MySymbol divide(MySymbol b) {
        MySymbol retSymbol = new MySymbol();
        retSymbol.setType( MySymbol.getCompatableType(this, b) );
        
        if(retSymbol.getType() == sym.INT) {
            retSymbol.setValue( (int)this.getValue() / (int)b.getValue() );
        } else if(retSymbol.getType() == sym.FLOAT) {
            retSymbol.setValue( Float.valueOf(this.getValue().toString()) 
                / Float.valueOf(b.getValue().toString()) );
        }
        
        return retSymbol;
    }
    public MySymbol multiply(MySymbol b) {
        MySymbol retSymbol = new MySymbol();
        retSymbol.setType( MySymbol.getCompatableType(this, b) );
        
        if(retSymbol.getType() == sym.INT) {
            retSymbol.setValue( (int)this.getValue() * (int)b.getValue() );
        } else if(retSymbol.getType() == sym.FLOAT) {
            retSymbol.setValue( Float.valueOf(this.getValue().toString()) 
                * Float.valueOf(b.getValue().toString()) );
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
        if(MySymbol.getCompatableType(this, otherMySymbol) != -1) {
            ret =  Float.valueOf(this.getValue().toString())
                > Float.valueOf((otherMySymbol.getValue().toString()));
        }
        return ret;
    }
    public boolean isGreaterAndEqual(MySymbol otherMySymbol) {
        boolean ret = false;
        if(MySymbol.getCompatableType(this, otherMySymbol) != -1) {
            ret =  Float.valueOf(this.getValue().toString())
                >= Float.valueOf((otherMySymbol.getValue().toString()));
        }
        return ret;
    }
    public boolean isLess(MySymbol otherMySymbol) {
        boolean ret = false;
        if(MySymbol.getCompatableType(this, otherMySymbol) != -1) {
            ret =  Float.valueOf(this.getValue().toString()) 
                < Float.valueOf((otherMySymbol.getValue().toString()));
        }
        return ret;
    }
    public boolean isLessAndEqual(MySymbol otherMySymbol) {
        boolean ret = false;
        if(MySymbol.getCompatableType(this, otherMySymbol) != -1) {
            ret =  Float.valueOf(this.getValue().toString()) 
                <= Float.valueOf((otherMySymbol.getValue().toString()));
        }
        return ret;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    // Boolean operations //////////////////////////////////////////////////////
    public boolean not() {
        return !((boolean) this.getValue());
    }
    public boolean and(MySymbol otherSymbol) {
        return ((boolean)this.getValue()) && ((boolean)otherSymbol.getValue());
    }
    public boolean or(MySymbol otherSymbol) {
        return ((boolean)this.getValue()) || ((boolean)otherSymbol.getValue());
    }
    ////////////////////////////////////////////////////////////////////////////
    
    // This method checks if two MySymbol objects are of a compatable datatype.
    // if they are compatable, then it returns the data type of which a resulting
    // MySymbol object should be if an operation is performed on the two objects.
    public static int getCompatableType(MySymbol a, MySymbol b) {
        int retType = -1;
        switch(a.getType()) {
            case sym.INT:
                switch (b.getType()) {
                    case sym.INT:
                        retType = sym.INT;
                        break;
                    case sym.FLOAT:
                        retType = sym.FLOAT;
                        break;
                }
                break;
            case sym.FLOAT:
                switch (b.getType()) {
                    case sym.INT:
                        retType = sym.FLOAT;
                        break;
                    case sym.FLOAT:
                        retType = sym.FLOAT;
                        break;
                }
                break;
        }
        
        if (a.getType() == b.getType()) {
            retType = a.getType();
        }
        if(retType == -1) {
//            System.out.println("Type error getCompatableType(). Symbols: " 
//                + a +  ", "+b);
            System.out.println("Error: "+ SymConverter.getTypeString(a.getType()) + " and "
                + SymConverter.getTypeString(b.getType()) + " are not compatable.");
            System.out.println(">"+Lstat.current_statement.getstat());
            System.exit(1);
        }
        return retType;
    }
    
    @Override
    public String toString() {
        try{
            return this.value.toString();
        } catch (Exception e) {
            return "Can't convert to string "+e.getMessage();
        }
    }
}
