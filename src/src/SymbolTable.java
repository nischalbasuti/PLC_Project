package src;

import java.util.Hashtable;

public class SymbolTable extends Hashtable<String, MySymbol>{

    static SymbolTable globalTable;
    
    static {
        globalTable = new SymbolTable();
    }

    
    static public void dump() {
        System.out.println("***SymbolTable Dump***");
	    System.out.println(globalTable.toString());
    }
    
    // Used to set the value of an existing variable.
    static void setValue(String id, MySymbol value){
        if(globalTable.get(id).getType() != value.getType()) {
            System.out.println("Type Error in SymbolTable.setValue()");
            SymbolTable.dump();
        }
        // if a character type, remove the quotes
//        if(value.getType() == sym.CHAR) {
//            String str = value.getValue().toString();
//            value.setValue(str.substring(1, str.length()-1));
//        }
        globalTable.put(id,value);
    }
    
    // Used to create a new variable
    static void declare(int type, String id, MySymbol value) {
        if(type != value.getType()) {
            System.out.println("Type Error in SymbolTable.declare()");
            SymbolTable.dump();
        }
        // TODO: handle variables that are aleady defined.
        
        // if a character type, remove the quotes
//        if(value.getType() == sym.CHAR) {
//            String str = value.getValue().toString();
//            value.setValue(str.substring(1, str.length()-1));
//        }
        globalTable.put(id,value);
    }
    
    // Get the type of the symbol
    // i.e sym.INT/FLOAT/BOOLEAN
    static int getType(String id) {
        return globalTable.get(id).getType();
    }

    // get the value of the Symbol.
    // i.e. the actual integer/float/boolean values;
    static Object getValue(String id){
        return  globalTable.get(id).getValue();
    }

    // get the Symbol object.
    static MySymbol getSymbol(String id){
        return  globalTable.get(id);
    }
}
