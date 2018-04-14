/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

/**
 *
 * @author nischal
 */
public class SymConverter {
    public static String getTypeString(int type) {
        switch(type){
            case sym.INT: return "int";
            case sym.FLOAT: return "float";
            case sym.BOOLEAN: return "boolean";
            case sym.CHAR: return "char";
            default: return "undefined type";
        }
    }
}
