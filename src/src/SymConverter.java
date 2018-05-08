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
            case sym.ARRAY: return "array";
            case sym.STRUCT: return "struct";
            default: return "undefined type: ["+type+"]";
        }
    }
    
    public static int defTypetoDataType(int defType) {
        switch(defType) {
            case sym.INTDEF: return sym.INT;
            case sym.FLOATDEF: return sym.FLOAT;
            case sym.BOOLEANDEF: return sym.BOOLEAN;
            case sym.CHARDEF: return sym.CHAR;
            case sym.ARRAYDEF: return sym.ARRAY;
            case sym.STRUCTDEF: return sym.STRUCT;
            default: 
//                System.err.println("SymConverter.defTypeToDataType() Undefined defType: "+defType);
                return defType;
        }
    }
}
