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

public class ArgumentsList {
    private LinkedList<Argument> argumentsList = new LinkedList<>();
    
    public ArgumentsList() {
        this.argumentsList = new LinkedList<>();
    }
    
    public ArgumentsList(Argument argument) {
        this.argumentsList = new LinkedList<>();
        this.argumentsList.add(argument);
    }
    
    public ArgumentsList(ArgumentsList argumentsList, Argument argument) {
        this.argumentsList = argumentsList.getArgumentsList();
        this.argumentsList.add(argument);
    }
    
    public LinkedList<Argument> getArgumentsList() {
        return this.argumentsList;
    }
}