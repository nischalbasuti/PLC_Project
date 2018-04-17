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

    private int type;
    
    public MyArray(int type) {
        this.type = type;
    }
    
    public MyArray(ExpList expList, int type) {
        this.type = type;
        for(Aexp exp : expList.getExpressionList()){
            this.arrayList.add(exp.getSymbol());
        }
    }
    public MyArray(ExpList expList) {
        this.type = type;
        for(Aexp exp : expList.getExpressionList()){
            this.arrayList.add(exp.getSymbol());
        }
    }
    
    public MyArray() {
        this.type = -1;
    }
    
    public void append(Object value) {
        int type = (this.type == -1)? sym.CHAR : this.type;
        this.arrayList.add(new MySymbol(value, type)); // TODO: change from char to something else.
    }
    
    public void setType(int type) {
        this.type = type;
    }
    
    public int getType() {
        return this.type;
    }
    
    public MySymbol getSymbol(int index) {
        return this.arrayList.get(index);
    }
    
    
    public void setSymbol(int index, MySymbol symbol) {
        this.arrayList.set(index, symbol);
    }

    public Object getValue(int index) {
        return this.arrayList.get(index).getValue();
    }
    
    public void setValue(int index, Object value) {
        this.arrayList.set(index, new MySymbol(value, this.type));
    }
    
    @Override
    public String toString() {
        return this.arrayList.toString();
    }
}
