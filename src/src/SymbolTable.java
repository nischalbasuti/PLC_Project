package src;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.ListIterator;
import java.util.Stack;

public class SymbolTable extends Hashtable<String, MySymbol>{

    static SymbolTable globalTable;
    static SymbolTable currentTable;
    static Stack<SymbolTable> symbolTableStack = new Stack<>();
    
    String name = "global";
    public SymbolTable(){
        
    }
    public SymbolTable(String name){
        this.name = name;
    }
    
    static {
        globalTable = new SymbolTable();
        currentTable = new SymbolTable();
        currentTable = globalTable;
        
        symbolTableStack.push(globalTable);
    }
    
    // Create a new symbol table. should be called when a function is declared.
    static public void pushSymbolTable() {
        currentTable = new SymbolTable();
        symbolTableStack.push(currentTable);
    }
    
    static public void popSymbolTable() {
        symbolTableStack.pop();
        currentTable = symbolTableStack.peek();
    }
    
    static public void dump() {
        System.out.println("\n***SymbolTable Dump***");
	    System.out.println("Current table: "+currentTable.name);
        System.out.println(currentTable.toString());
        System.out.println("******************************\n");
    }
    static public void dumpGlobal() {
        System.out.println("\n***SymbolTable Dump***");
	    System.out.println(globalTable.toString());
        System.out.println("*****************************\n");
    }
    
    // Used to set the value of an existing variable.
    static void setValue(String id, MySymbol value){
        
        ListIterator<SymbolTable> iterator = SymbolTable.symbolTableStack.listIterator(SymbolTable.symbolTableStack.size());
        
        while(iterator.hasPrevious()) {
            SymbolTable tempSymbolTable = iterator.previous();
            if(tempSymbolTable.containsKey(id)) {
                if(tempSymbolTable.get(id).getType() != value.getType()) {
                    System.out.println("Type Error assigning ");
                    System.out.println(">"+Lstat.current_statement.getstat());
                    SymbolTable.dump();
                    System.exit(-1);
                }
                tempSymbolTable.put(id, value);
                return;
            }
        }
        
        System.out.println(id + " was not declared.");
    }
    
    // Used to create a new variable.
    static void declare(int type, String id, MySymbol value) {
        if(currentTable.containsKey(id)) {
            System.out.println("Error: Identifier "+id+" already declared.");
            System.out.println(">"+Lstat.current_statement.getstat());
            System.exit(-1);
        }
        if(SymConverter.defTypetoDataType(type) != MySymbol.getCompatableType(new MySymbol(new Object(), type),value)) {
            // If type being declared and the type of symbol are not the same,
            // throw an error or something.
            System.out.println("Type Error declaring");
            System.out.println(">"+Lstat.current_statement.getstat());
            SymbolTable.dump();
            System.exit(-1);
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
        
        //TODO: traverse backwards in the stack instead of jumping to the globalTable if variable not found.
        
        ListIterator<SymbolTable> iterator = SymbolTable.symbolTableStack.listIterator(SymbolTable.symbolTableStack.size());
        
        while(iterator.hasPrevious()) {
            SymbolTable tempSymbolTable = iterator.previous();
            if(tempSymbolTable.containsKey(id)) {
                return tempSymbolTable.get(id);
            }
        }
            
//        return "[value: " + this.value.toString() + ", type: " + SymConverter.getTypeString(this.type) +"]";
        
        System.out.println("Variable "+id+" was not declared.");
        System.exit(-1);
        return new MySymbol();
    }
}
