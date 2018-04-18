package src;

public class Astat {

    int statementType;
    public static int assignment = 0;
    public static int varDeclaration = 1;
    public static int print = 2;
    public static int ifthen = 3;
    public static int ifthenelse = 8;
    public static int block = 4;
    public static int whileloop = 5;
    public static int funDeclaration = 6;
    public static int returnStatement = 7;
    public static int arrayAssignment = 9;
    /*
     * decleration statement: type variable = expr
     */
    String decVariable;
    Aexp decExpr;
    int decType;
    public static Astat declare(int type, String Variable, Aexp expr) {
        Astat statement = new Astat();
        statement.statementType = varDeclaration;

        statement.decVariable = Variable;
        statement.decExpr = expr;
        switch(type) {
            case sym.INTDEF:
                statement.decType = sym.INT;
                break;
            case sym.FLOATDEF:
                statement.decType = sym.FLOAT;
                break;
            case sym.BOOLEANDEF:
                statement.decType = sym.BOOLEAN;
                break;
            case sym.CHARDEF:
                statement.decType = sym.CHAR;
                break;
            case sym.ARRAYDEF:
                statement.decType = sym.ARRAY;
                break;
            default:
                // TODO: do something useful, dumbass.
                statement.decType = type;
                break;
        }

        return statement;
    }
    
    Aexp decArraySizeExp;
    public static Astat declare(int type, Aexp arraySizeExp, String Variable, Aexp expr) {
        Astat statement = new Astat();
        statement.statementType = varDeclaration;

        statement.decVariable = Variable;
        statement.decExpr = expr;
        statement.decArraySizeExp = arraySizeExp;
        //TODO: refactor to use SymConverter.
        switch(type) {
            case sym.INTDEF:
                statement.decType = sym.INT;
                break;
            case sym.FLOATDEF:
                statement.decType = sym.FLOAT;
                break;
            case sym.BOOLEANDEF:
                statement.decType = sym.BOOLEAN;
                break;
            case sym.CHARDEF:
                statement.decType = sym.CHAR;
                break;
            case sym.ARRAYDEF:
                statement.decType = sym.ARRAY;
                break;
            default:
                // TODO: do something useful, dumbass.
                statement.decType = type;
                break;
        }

        return statement;
    }

    /*
     * assignment statement: variable = expr
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
    
    Aexp assIndexExp;
    public static Astat assignment(String arrayName, Aexp arrayIndex, Aexp expr) {
        Astat statement = new Astat();
        statement.statementType = arrayAssignment;

        statement.assVariable = arrayName;
        statement.assExpr = expr;
        statement.assIndexExp = arrayIndex;

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
     * if then else statement: if ifcondition then ifbody else elsebody
     *
     */
    Astat elsebody;
    public static Astat ifthenelse(Aexp Condition, Astat ifbody, Astat elsebody) {
        Astat statement = new Astat();
        statement.statementType = ifthenelse;
        statement.ifcondition = Condition;
        statement.ifbody = ifbody;
        statement.elsebody = elsebody;

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

    public String getstat() {
        if (statementType == assignment) {
            return assVariable + "=" + assExpr.getexp();
        } else if (statementType == varDeclaration) {
            return SymConverter.getTypeString(this.decType) + " " + decVariable + " = "+decExpr.getexp();
        }else if (statementType == ifthen) {
            return "if " + ifcondition.getexp() + " " + ifbody.getstat();
        }else if(statementType == ifthenelse){
            return "if then else TODO"; //TODO
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
        System.out.println(">> "+this.getstat());
        if (statementType == assignment) {
            SymbolTable.setValue(assVariable, assExpr.getSymbol());
        } else if(statementType == arrayAssignment) {
            MySymbol symbol = SymbolTable.getSymbol(assVariable);
            MyArray array = (MyArray)symbol.getValue();
            //TODO make sure assIndexExp is an integer.
            array.setSymbol((int)assIndexExp.getSymbol().getValue(), assExpr.getSymbol());
//            SymbolTable.setValue(assVariable, new MySymbol(array,array.getType()));
        } else if (statementType == varDeclaration) {
	    
            if(this.decType == decExpr.getSymbol().getType()){
                //continue;
            } else {
                System.out.println("TYPE MISS MATCH:");
                System.out.println(this.getstat()+" | "+decType+ " " +decVariable+" "+decExpr.getSymbol().getType());
            }
            
            if(decType == sym.ARRAY) {
                //TODO: make sure decArraySizeExp is an int
                int arraySize = (int)decArraySizeExp.getSymbol().getValue();
                MyArray newArray = (MyArray)(decExpr.getSymbol().getValue());
                newArray.setSize(arraySize);
                SymbolTable.declare(decType, decVariable, new MySymbol(newArray,decType));
            } else {
                SymbolTable.declare(decType, decVariable, decExpr.getSymbol());
            }
        } else if (statementType == ifthen) {

            if (ifcondition.getSymbol().isEqual(new MySymbol(true, sym.BOOLEAN))) {
                ifbody.execute();
            }
            
        } else if (statementType == ifthenelse) {
           if(ifcondition.getSymbol().isEqual(new MySymbol(true, sym.BOOLEAN))) {
               ifbody.execute();
           } else {
               elsebody.execute();
           }
        } else if (statementType == whileloop) {
            for (;;) {
                if (whileCondition.getSymbol().isEqual(new MySymbol(true, sym.BOOLEAN))) {
                    whileBody.execute();
                } else {
                    break;
                }
            }

        } else if (statementType == print) {

            System.out.println(printE.getSymbol());

        } else if (statementType == block) {
            for (Astat s : blockBody.statementList) {
                s.execute();
            }
        }
    }
}
