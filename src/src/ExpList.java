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
public class ExpList {
    LinkedList<Aexp> expressionList;
    
    public ExpList(Aexp aexp) {
        expressionList = new LinkedList<>();
        this.expressionList.add(aexp);
    }
    
    public ExpList(ExpList expList, Aexp exp) {
        this.expressionList = expList.getExpressionList();
        this.expressionList.add(exp);
    }
    
    public void append(Aexp exp) {
        expressionList.add(exp);
    }
    
    public LinkedList<Aexp> getExpressionList() {
        return this.expressionList;
    }
}
