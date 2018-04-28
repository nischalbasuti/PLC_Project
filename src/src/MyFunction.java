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
    Astat statement;
    ArgumentsList argumentsList;
    String functionName;
    
    MySymbol returnSymbol;

    public MyFunction(String functionName, ArgumentsList argumentsList, Astat statement) {
        this.statement = statement;
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
            SymbolTable.declare(argumentsList.getArgumentsList().get(i).type, 
                argumentsList.getArgumentsList().get(i).name,
                expList.expressionList.get(i).getSymbol());
        }
        
        // Run the body of the function.
//        for(Astat statement: this.statementList.statementList) {
//            statement.execute();
//        }
        this.statement.execute();
        
        // TODO: get return value, maybe use a dedicated "return" variable?
        
        // restore the symbol table to global.
        SymbolTable.setCurrentSymbolTableToGlobal();
        
        return returnSymbol;
    }
}
