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
public class MyFunction {
    Lstat statements;
    ArgumentsList argumentsList;
    String functionName;
    
    // TODO: check if return type is okay.
    MySymbol returnSymbol = new MySymbol();

    public MyFunction(String functionName, ArgumentsList argumentsList, Lstat statements) {
        this.statements = statements;
        this.argumentsList = argumentsList;
        this.functionName = functionName;
    }
    
    public MySymbol call(ExpList expList) {
        // Change state to use this functions local symbol table
        SymbolTable.setCurrentSymbolTableToLocal(this.functionName);
        
        // Check if number of arguments are correct.
        if(argumentsList.getArgumentsList().size() != expList.expressionList.size()) {
            System.out.println("Function "+this.functionName+"() expects "+
                argumentsList.getArgumentsList().size()+" arguments, "+
                expList.expressionList.size()+" given.");
            System.exit(-1);
        }
        
        // Declare the arguments in the symbol table.
        for(int i = 0; i < argumentsList.getArgumentsList().size(); i++) {
            SymbolTable.declare(SymConverter.defTypetoDataType(argumentsList.getArgumentsList().get(i).type), 
                argumentsList.getArgumentsList().get(i).name,
                expList.expressionList.get(i).getSymbol());
        }
        
        for (Astat statement : this.statements.statementList) {
            statement.execute();
            if (statement.statementType == Astat.RETURN_STATEMENT) {
                this.returnSymbol = statement.returnExp.getSymbol();
                break;
            }
        }
        
        // TODO: get return value, maybe use a dedicated "return" variable?
        
        // restore the symbol table to global.
        SymbolTable.setCurrentSymbolTableToGlobal();
        
        return returnSymbol;
    }
}
