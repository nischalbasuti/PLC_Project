/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.util.HashMap;

/**
 *
 * @author nischal
 */
public class MyStruct {
    private final HashMap<String, MySymbol> hashmap = new HashMap<>();
    
    public MyStruct(KeyValueList keyValueList) {
        for(KeyValue keyValue : keyValueList.getKeyValues()) {
            this.hashmap.put(keyValue.getKey(), keyValue.getSymbol());
        }
    }
    
    public void addSymbol(String key, MySymbol valueSymbol) {
        this.hashmap.put(key, valueSymbol);
    }
    
    public MySymbol getSymbol(String key) {
        if(!this.hashmap.containsKey(key)) {
            System.out.println("Key out of range");
            System.out.println(">"+Lstat.current_statement.getstat());
            System.exit(-1);
        }
        return this.hashmap.get(key);
    }
    
    public HashMap<String, MySymbol> getHashmap() {
        return this.hashmap;
    }
    
    @Override
    public String toString() {
        return this.hashmap.toString();
    }
}
