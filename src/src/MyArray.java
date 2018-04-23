/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;
import java.util.*;

/**
 *
 * @author nischal
 */
public class MyArray {
    private final ArrayList<MySymbol> arrayList = new ArrayList<>();

    private int size;
    
    public MyArray(int size) {
        this.size = size;
        arrayList.ensureCapacity(size);
    }
    
    public MyArray(ExpList expList, int size) {
        this.size = size;
        arrayList.ensureCapacity(size);
        for(Aexp exp : expList.getExpressionList()){
            this.arrayList.add(exp.getSymbol());
        }
    }
    public MyArray(ExpList expList) {
        for(Aexp exp : expList.getExpressionList()){
            this.arrayList.add(exp.getSymbol());
        }
    }
    
    public MyArray() {
        this.size = -1;
    }
    
    public void append(Object value) {
        int type = (this.size == -1)? sym.CHAR : this.size;
        this.arrayList.add(new MySymbol(value, type)); // TODO: change from char to something else.
    }
    
    public void setSize(int size) {
        this.size = size;
        this.arrayList.ensureCapacity(size);
        while(this.arrayList.size() < size) {
            this.arrayList.add(null);
        }
    }
    
    public int getSize() {
        return this.arrayList.size();
    }
    
    public MySymbol getSymbol(int index) {
        //TODO out of range error
        return this.arrayList.get(index);
    }
    
    
    public void setSymbol(int index, MySymbol symbol) {
        this.arrayList.set(index, symbol);
    }

    public Object getValue(int index) {
        //TODO out of range error
        return this.arrayList.get(index).getValue();
    }
    
    public void setValue(int index, Object value) {
        this.arrayList.set(index, new MySymbol(value, this.size));
    }
    
    @Override
    public String toString() {
        return this.arrayList.toString();
    }
}
