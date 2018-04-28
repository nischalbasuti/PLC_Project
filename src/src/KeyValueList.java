/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.util.ArrayList;

/**
 * @author nischal
 */
public class KeyValueList {
    private ArrayList<KeyValue> keyValues = new ArrayList<>();
    
    public KeyValueList(KeyValue keyValue) {
        this.keyValues = new ArrayList<>();
        this.keyValues.add(keyValue);
    }
    
    public KeyValueList(KeyValueList keyValueList, KeyValue keyValue) {
        this.keyValues = keyValueList.getKeyValues();
        this.keyValues.add(keyValue);
    }
    
    public void append(KeyValue keyValue) {
        this.keyValues.add(keyValue);
    }
    
    public ArrayList<KeyValue> getKeyValues() {
        return this.keyValues;
    }
    
    @Override
    public String toString() {
        return this.keyValues.toString();
    }
}
