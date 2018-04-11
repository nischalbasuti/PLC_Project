package src;

import java.util.Hashtable;

public class SymbolTable extends Hashtable<String,Object>{

    static SymbolTable globalTable;
    static Hashtable<String, Integer> typeTable;
    
    static {
        globalTable = new SymbolTable();
        typeTable = new Hashtable();
    }

    static void setValue(String id, int value){
        globalTable.put(id,value);
    }
    
    static void setValue(String id, float value){
        globalTable.put(id,value);
    }
    
    static void declare(int type, String id, int value) {
        globalTable.put(id,value);
        typeTable.put(id, type);
    }
    
    static void declare(int type, String id, float value) {
        globalTable.put(id,value);
        typeTable.put(id, type);
    }
    
    static Integer getType(String id) {
        return typeTable.get(id);
    }

    static Object getValue(String id){
        return  globalTable.get(id);
    }
}
