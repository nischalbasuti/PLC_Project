package src;

import java.util.HashMap;
import java.util.Hashtable;

public class SymbolTable extends Hashtable<String, MySymbol>{

    static SymbolTable globalTable;
    static SymbolTable currentTable;
    
    static HashMap<String, SymbolTable> localSymbolTables;
    
    static {
        globalTable = new SymbolTable();
        currentTable = new SymbolTable();
        currentTable = globalTable;
        
        localSymbolTables = new HashMap<>();
    }
    
    // Create a new symbol table. should be called when a function is declared.
    static public void createLocalSymbolTable(String functionName) {
        SymbolTable.localSymbolTables.put(functionName, new SymbolTable());
    }
   
    // Change the current symbol table to a local one. should be called when a 
    // function is declared and at the start of a function call.
    static public void setCurrentSymbolTableToLocal(String functionName) {
        currentTable = localSymbolTables.get(functionName);
    }
    
    // Set the current symbol table to the global symbol table. Should be called
    // after a function decleration and after a function call.
    static public void setCurrentSymbolTableToGlobal() {
        currentTable = globalTable;
    }
    
    static public void dump() {
        System.out.println("***SymbolTable Dump***");
	    System.out.println(globalTable.toString());
    }
    
    // Used to set the value of an existing variable.
    static void setValue(String id, MySymbol value){
        if(currentTable.get(id).getType() != value.getType()) {
            System.out.println("Type Error in SymbolTable.setValue()");
            SymbolTable.dump();
        }
        currentTable.put(id,value);
    }
    
    // Used to create a new variable.
    static void declare(int type, String id, MySymbol value) {
        if(type != value.getType()) {
            // If type being declared and the type of symbol are not the same,
            // throw an error or something.
            System.out.println("Type Error in SymbolTable.declare() | "+type+" "+value.getType());
            SymbolTable.dump();
        }
        // TODO: handle variables that are aleady defined.
        currentTable.put(id,value);
    }
    
    static void declareArray(int arraySize, String id, MySymbol value) {
        SymbolTable.declare(sym.ARRAY, id, value);
    }
    
    // Get the type of the symbol
    // i.e sym.INT/FLOAT/BOOLEAN
    static int getType(String id) {
        return SymbolTable.getSymbol(id).getType();
    }

    // get the value of the Symbol.
    // i.e. the actual integer/float/boolean values.
    static Object getValue(String id){
        return  SymbolTable.getSymbol(id).getValue();
    }

    // Get the Symbol object for currentTable.
    // if variabled doesn't exist in the current table, get value form globalTable
    // if it exists in neither, exit the program.
    static MySymbol getSymbol(String id){
        if(SymbolTable.currentTable.containsKey(id)){
            return  SymbolTable.currentTable.get(id);    
        } else if (SymbolTable.globalTable.containsKey(id)) {
            return globalTable.get(id);
        }
        System.out.println("Variable "+id+" was not declared.");
       // System.exit(-1);
        return new MySymbol();
    }
}
