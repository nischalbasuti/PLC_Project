/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.util.LinkedList;

/**
 *
 * @author nischal
 */
public class KeyValueList {
    private LinkedList<KeyValue> keyValues = new LinkedList<>();
    
    public KeyValueList(KeyValue keyValue) {
        this.keyValues = new LinkedList<>();
        this.keyValues.add(keyValue);
    }
    
    public KeyValueList(KeyValueList keyValueList, KeyValue keyValue) {
        this.keyValues = keyValueList.getKeyValues();
        this.keyValues.add(keyValue);
    }
    
    public void append(KeyValue keyValue) {
        this.keyValues.add(keyValue);
    }
    
    public LinkedList<KeyValue> getKeyValues() {
        return this.keyValues;
    }
    
    @Override
    public String toString() {
        return this.keyValues.toString();
    }
}
