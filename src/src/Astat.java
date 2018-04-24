package src;

public class Astat {

    int statementType;
    public static final int ASSIGNMENT = 0;
    public static final int VAR_DECLARATION = 1;
    public static final int PRINT = 2;
    public static final int IFTHEN = 3;
    public static final int IFTHENELSE = 8;
    public static final int BLOCK = 4;
    public static final int WHILELOOP = 5;
    public static final int FUN_DECLARATION = 6;
    public static final int RETURN_STATEMENT = 7;
    public static final int ARRAY_ASSIGNMENT = 9;
    public static final int STRUCT_ASSIGNMENT = 10;
    /*
     * decleration statement: type variable = expr
     */
    String decVariable;
    Aexp decExpr;
    int decType;

    public static Astat declare(int type, String Variable, Aexp expr) {
        Astat statement = new Astat();
        statement.statementType = VAR_DECLARATION;

        statement.decVariable = Variable;
        statement.decExpr = expr;
        statement.decType = SymConverter.defTypetoDataType(type);

        return statement;
    }

    Aexp decArraySizeExp;

    public static Astat declare(int type, Aexp arraySizeExp, String Variable, Aexp expr) {
        Astat statement = new Astat();
        statement.statementType = VAR_DECLARATION;

        statement.decVariable = Variable;
        statement.decExpr = expr;
        statement.decArraySizeExp = arraySizeExp;
        statement.decType = SymConverter.defTypetoDataType(type);

        return statement;
    }

    /*
     * assignment statement: variable = expr
     */
    String assVariable;
    Aexp assExpr;

    public static Astat assignment(String Variable, Aexp expr) {
        Astat statement = new Astat();
        statement.statementType = ASSIGNMENT;

        statement.assVariable = Variable;
        statement.assExpr = expr;

        return statement;

    }

    Aexp assIndexExp;

    public static Astat assignment(String arrayName, Aexp arrayIndex, Aexp expr) {
        Astat statement = new Astat();
        statement.statementType = ARRAY_ASSIGNMENT;

        statement.assVariable = arrayName;
        statement.assExpr = expr;
        statement.assIndexExp = arrayIndex;

        return statement;

    }

    String assKey;

    public static Astat assignment(String structName, String structKey, Aexp expr) {
        Astat statement = new Astat();
        statement.statementType = STRUCT_ASSIGNMENT;

        statement.assVariable = structName;
        statement.assExpr = expr;
        statement.assKey = structKey;

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
        statement.statementType = WHILELOOP;
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
        statement.statementType = IFTHEN;
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
        statement.statementType = IFTHENELSE;
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
        statement.statementType = PRINT;
        statement.printE = expr;
        return statement;
    }

    /*
     * block statement: begin statement_list end
     */
    Lstat blockBody;

    public static Astat block(Lstat l) {
        Astat statement = new Astat();
        statement.statementType = BLOCK;
        statement.blockBody = l;
        return statement;
    }

    public String getstat() {
        if (statementType == ASSIGNMENT) {
            return assVariable + "=" + assExpr.getexp();
        } else if (statementType == VAR_DECLARATION) {
            return SymConverter.getTypeString(this.decType) + " " + decVariable + " = " + decExpr.getexp();
        } else if (statementType == IFTHEN) {
            return "if " + ifcondition.getexp() + " " + ifbody.getstat();
        } else if (statementType == IFTHENELSE) {
            return "if then else TODO"; //TODO
        } else if (statementType == PRINT) {
            return "print " + printE.getexp();
        } else if (statementType == WHILELOOP) {
            return "while " + whileCondition.getexp() + " do " + whileBody.getstat();
        } else if (statementType == BLOCK) {
            return "block";
        } else {
            return "unknown";
        }
    }

    public void execute() {
        // System.out.println(">> "+this.getstat());
        switch (statementType) {
            case ASSIGNMENT:
                SymbolTable.setValue(assVariable, assExpr.getSymbol());
                break;
            case ARRAY_ASSIGNMENT:
                MySymbol symbolArray = SymbolTable.getSymbol(assVariable);
                MyArray array = (MyArray) symbolArray.getValue();
                //TODO make sure assIndexExp is an integer.
                array.setSymbol((int) assIndexExp.getSymbol().getValue(), assExpr.getSymbol());
                break;
            case STRUCT_ASSIGNMENT:
                MySymbol symbolStruct = SymbolTable.getSymbol(assVariable);
                MyStruct struct = (MyStruct) symbolStruct.getValue();
                struct.addSymbol(assKey, assExpr.getSymbol());
                break;
            case VAR_DECLARATION:
                if (this.decType != decExpr.getSymbol().getType()) {
                    System.out.println("TYPE MISS MATCH:");
                    System.out.println(this.getstat() + " | " + decType + " " + decVariable + " " + decExpr.getSymbol().getType());
                }

                if (decType == sym.ARRAY) {
                    //TODO: make sure decArraySizeExp is an int
                    int arraySize = (int) decArraySizeExp.getSymbol().getValue();
                    MyArray newArray = (MyArray) (decExpr.getSymbol().getValue());
                    newArray.setSize(arraySize);
                    SymbolTable.declare(decType, decVariable, new MySymbol(newArray, decType));
                } else {
                    SymbolTable.declare(decType, decVariable, decExpr.getSymbol());
                }
                break;
            case IFTHEN:
                if (ifcondition.getSymbol().isEqual(new MySymbol(true, sym.BOOLEAN))) {
                    ifbody.execute();
                }
                break;
            case IFTHENELSE:
                if (ifcondition.getSymbol().isEqual(new MySymbol(true, sym.BOOLEAN))) {
                    ifbody.execute();
                } else {
                    elsebody.execute();
                }
                break;
            case WHILELOOP:
                for (;;) {
                    if (whileCondition.getSymbol().isEqual(new MySymbol(true, sym.BOOLEAN))) {
                        whileBody.execute();
                    } else {
                        break;
                    }
                }
                break;
            case PRINT:
                System.out.println(printE.getSymbol());
                break;
            case BLOCK:
                for (Astat s : blockBody.statementList) {
                    s.execute();
                }
                break;
        }
    }
}
