package src;

public class Astat {



    int statementType;
    public static int assignment = 0;
    public static int varDeclaration = 1;
    public static int print = 2;
    public static int ifthen = 3;
    public static int block = 4;
    public static int whileloop = 5;
    public static int funDeclaration = 6;
    public static int returnStatement = 7;
    /*
     * decleration statement: type variable = expr
     *
     */
    String decVariable;
    Aexp decExpr;
    int decType;
    public static Astat declare(int type, String Variable, Aexp expr) {
        Astat statement = new Astat();
        statement.statementType = varDeclaration;

        statement.decVariable = Variable;
        statement.decExpr = expr;
        statement.decType = type;

        return statement;
    }

    /*
     * assignment statement: variable = expr
     *
     */    
    String assVariable;
    Aexp assExpr;
    public static Astat assignment(String Variable, Aexp expr) {
        Astat statement = new Astat();
        statement.statementType = assignment;

        statement.assVariable = Variable;
        statement.assExpr = expr;

        return statement;

    }

    /*
     * while loop: while whileCondition begin whileBody end
     *
     */
    Aexp whileCondition;
    Astat whileBody;

    public static Astat whileloop(Aexp condition, Astat WhileBody) {
        Astat statement = new Astat();
        statement.statementType = whileloop;
        statement.whileCondition = condition;
        statement.whileBody = WhileBody;
        return statement;

    }
    /*
     * if then statement: if ifcondition then ifbody
     *
     */
    Aexp ifcondition;
    Astat ifbody;

    public static Astat ifthen(Aexp Condition, Astat Ifbody) {
        Astat statement = new Astat();
        statement.statementType = ifthen;
        statement.ifcondition = Condition;
        statement.ifbody = Ifbody;

        return statement;
    }

    /*
     * print statement: print e
     */
    Aexp printE;

    public static Astat print(Aexp expr) {

        Astat statement = new Astat();
        statement.statementType = print;
        statement.printE = expr;
        return statement;

    }

    /*
     * block statement: begin statement_list end
     */
    Lstat blockBody;

    public static Astat block(Lstat l) {
        Astat statement = new Astat();
        statement.statementType = block;
        statement.blockBody = l;
        return statement;
    }

    String typeToString(int type) {
        switch(type){
            case sym.INTDEF: return "int";
            case sym.FLOATDEF: return "float";
            case sym.BOOLEANDEF: return "boolean";
        }
        
        return "wut?";
    }
    public String getstat() {
        if (statementType == assignment) {
            return assVariable + "=" + assExpr.getexp();
        } else if (statementType == varDeclaration) {
            return typeToString(this.decType) + " " + decVariable + " = "+decExpr.getexp();
        }else if (statementType == ifthen) {
            return "if " + ifcondition.getexp() + " " + ifbody.getstat();
        } else if (statementType == print) {
            return "print " + printE.getexp();
        } else if (statementType == whileloop) {
            return "while " + whileCondition.getexp()+ " do " + whileBody.getstat();
        } else if (statementType == block) {
            return "block";
        } else {
            return "unknown";
        }
    }

    public void execute() {

        if (statementType == assignment) {
            SymbolTable.setValue(assVariable, assExpr.getValue());
            System.out.println(this.getstat());
        } else if (statementType == varDeclaration) {
            // if it's a variable, check the data type.
            if(decExpr.getType() == this.decType){
                //continue;
            } else {
                System.out.println("TYPE MISS MATCH:");
//                    System.out.println(this.getstat());
            }
            System.out.println(this.getstat()+" | "+decType+ " " +decVariable+" "+decExpr.getType());
            
            SymbolTable.declare(decType, decVariable, decExpr.getValue());
        }else if (statementType == ifthen) {

            if ((int)ifcondition.getValue() == 1) {
                ifbody.execute();
            }
            
        } else if (statementType == whileloop) {
            for (;;) {

                if ((int)whileCondition.getValue() == 1) {
                    whileBody.execute();
                } else {
                    break;
                }

            }

        } else if (statementType == print) {

            System.out.println(printE.getValue());

        } else if (statementType == block) {
            for (Astat s : blockBody.statementList) {
                s.execute();
            }
        }
    }
}
