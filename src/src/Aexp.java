package src;

public class Aexp {

    private enum AexpType {
        INTEGER,
        FLOAT,
        BOOLEAN,
        CHAR,
        ARRAY,
        STRUCT,
        STRUCTELE,
        ARRAYELE,
        ID,
        EXP,
        FUNCTION
    }
    private int type;
    private AexpType eType;
    private MySymbol inum;
    private String id;
    private Args operands;
    private int operator;
    
    private Aexp indexExpr;
    
    
    // All expressions should be evaluated in Aexp.getSymbol(), NOT IN THE CONSTRUCTORS,
    // if done in the constructors, it might cause a NullPointerException.
    
    // example: 
    // public void MySymbol getSymbol(){
    //  ...
    //    int value = (int)this.expressionObject.getSymbol().getValue();
    //  ...
    //  }
    
    ExpList functionArgExpList;
    public Aexp(String functionName, ExpList expList){
        this.type = sym.FUNCDEF;
        this.functionArgExpList = expList;
        this.eType = AexpType.FUNCTION;
        this.id = functionName;
    }
    
    public Aexp(KeyValueList keyValueList) {
        this.type = sym.STRUCT;
        this.eType = AexpType.STRUCT;
        inum = new MySymbol(new MyStruct(keyValueList), this.type);
    }
    
    String key;
    public Aexp(String structName, String key) {
        this.type = sym.STRUCTELE;
        this.eType = AexpType.STRUCTELE;
        this.id = structName;
        this.key = key;
    }
    public Aexp(String arrayName, Aexp index) {
        this.type = sym.ARRAYELE;
        this.eType = AexpType.ARRAYELE; //TODO: change
        this.id = arrayName;
        this.indexExpr = index;
    }

    public Aexp(MyArray a) {
        this.eType = AexpType.ARRAY;
        this.type = sym.ARRAY;
        inum = new MySymbol(a, this.type);
    }
    
    public Aexp(ExpList expList, boolean isArray) {
        this.eType = AexpType.ARRAY;
        this.type = sym.ARRAY;
        inum = new MySymbol(new MyArray(expList), this.type);
    }

    Aexp(Integer x) {
        eType = AexpType.INTEGER;
        this.type = sym.INT;
        inum = new MySymbol(x, this.type);
    }
    Aexp(Float x) {
        eType = AexpType.FLOAT;
        this.type = sym.FLOAT;
        inum = new MySymbol(x, this.type);        
    }
    Aexp(Boolean x) {
        eType = AexpType.BOOLEAN;
        this.type = sym.BOOLEAN;
        operands = null;
        inum = new MySymbol(x, this.type);        
    }
    Aexp(String x, boolean isid) {
        if (isid) {
            eType = AexpType.ID;
            id = x;
        } else {
            eType = AexpType.CHAR;
            this.type = sym.CHAR;
            operands = null;
            inum = new MySymbol(x, this.type);
        }
    }

    Aexp(Args x, int op) {
        operands = x;
        operator = op;
        Aexp left = operands.getfi();
        Aexp right = operands.getse();
        if( left.getType() == sym.INT && right.getType() == sym.INT ) {
            this.type = sym.INT;
        } else if(( left.getType() == sym.FLOAT && right.getType() == sym.INT )
                ||( left.getType() == sym.INT && right.getType() == sym.FLOAT )
                ||( left.getType() == sym.FLOAT && right.getType() == sym.FLOAT )) {
            this.type = sym.FLOAT;
        } else {
		// TODO: Handle cases with id....need to assign type in getVariable();
		this.type = right.getType();
	}
        switch(operator) {
            case sym.EQ:
                this.type = sym.BOOLEAN;
                break;
            case sym.NOTEQ:
                this.type = sym.BOOLEAN;
                break;
            case sym.GREATEREQ:
                this.type = sym.BOOLEAN;
                break;
            case sym.GREATER:
                this.type = sym.BOOLEAN;
                break;
            case sym.LESSEREQ:
                this.type = sym.BOOLEAN;
                break;
            case sym.LESSER:
                this.type = sym.BOOLEAN;
                break;
            case sym.NOT:
                this.type = sym.BOOLEAN;
                break;
            case sym.AND:
                this.type = sym.BOOLEAN;
                break;
            case sym.OR:
                this.type = sym.BOOLEAN;
                break;
        }
        if(this.type == sym.BOOLEAN){
            eType = AexpType.BOOLEAN;
        } else{
            eType = AexpType.EXP;
        }
    }

    public String getexp() {

        String s = "";
        switch (this.eType) {
            case INTEGER: s = "" + inum; break;
            case FLOAT: s = "" + inum; break;
            case BOOLEAN:
                s = "" + inum;
                switch(operator) {
                    case sym.EQ:
                        s = operands.getfi().getexp() + " == " + operands.getse().getexp();
                        break;
                    case sym.NOTEQ:                        
                        break;
                    case sym.GREATEREQ:
                        break;
                    case sym.GREATER:
                        break;
                    case sym.LESSEREQ:
                        break;
                    case sym.LESSER:
                        break;
                    case sym.NOT:
                        break;
                    case sym.AND:
                        break;
                    case sym.OR:
                        break;
                    default:
                        s = "" + inum.toString();
                        break;
                } 
                break;
            case CHAR: s = "" + inum; break;
            case ARRAY: s = "" + inum; break;
            case STRUCT: s = "" + inum; break;
            case ID: s = id; break;
            case EXP:
                switch (operator) {
                    case sym.PLUS:
                        s = "PLUS(" + operands.getfi().getexp() + "," + operands.getse().getexp() + ")";
                        break;
                    case sym.MINUS:
                        s = "MINUS(" + operands.getfi().getexp() + "," + operands.getse().getexp() + ")";
                        break;
                    case sym.TIMES:
                        s = "TIMES(" + operands.getfi().getexp() + "," + operands.getse().getexp() + ")";
                        break;
                    case sym.DIVIDE:
                        s = "DIVIDE(" + operands.getfi().getexp() + "," + operands.getse().getexp() + ")";
                        break;
                    default: break;
                } break;
            default: break;
        }
        return s;
    }

    public MySymbol getSymbol() {
        MySymbol symbol = new MySymbol();
	    symbol.setType(this.type);
	
        Aexp left;
        Aexp right;
        switch (this.eType) {
            case INTEGER:
                symbol.setValue(inum.getValue());
                break;
            case FLOAT:
                symbol.setValue(inum.getValue());
                break;
            case CHAR:
                symbol.setValue(inum.getValue());
                break;
            case ARRAY:
                symbol.setValue(inum.getValue());
                break;
            case STRUCT:
                symbol.setValue(inum.getValue());
                break;
            case ARRAYELE:
                MyArray array = (MyArray)(SymbolTable.getSymbol(id).getValue());
                this.type = array.getSize();
//                this.type = array.getSymbol(index).getType();
                int index = (int)this.indexExpr.getSymbol().getValue();
                symbol = new MySymbol(array.getSymbol(index));
                break;
            case STRUCTELE:
                MyStruct struct = (MyStruct)(SymbolTable.getSymbol(id).getValue());
                String key = (String)this.key;
                symbol = new MySymbol(struct.getSymbol(key));
                break;
            case ID:
                //expression is a variable
                try {
                    symbol = new MySymbol(SymbolTable.getSymbol(id));
                    this.type = SymbolTable.getType(id);
                } catch (NullPointerException e) {
                    System.err.println(id + " was not declared.");
                    e.printStackTrace();
                    System.exit(0);
                }
                break;
            case FUNCTION:
                MyFunction function = (MyFunction) SymbolTable.getSymbol(id).getValue();
                function.call(functionArgExpList);
                symbol = new MySymbol(function.returnSymbol);
                
                break;
            case EXP:
                left = operands.getfi();
                right = operands.getse();
                // Expression is a math expression.
                // TODO: do some type checking, maybe encapsulate operations inside the Symbol class.
                // TODO: fix setting this.type for expressions.
                switch (operator) {
                    case sym.PLUS:
                        symbol = left.getSymbol().add(right.getSymbol());
                        break;
                    case sym.MINUS:
                        symbol = left.getSymbol().subtract(right.getSymbol());
                        break;                                                           
                    case sym.TIMES:                                                      
                        symbol = left.getSymbol().multiply(right.getSymbol());
                        break;                                                           
                    case sym.DIVIDE:                                                     
                        symbol = left.getSymbol().divide(right.getSymbol());
                        break;
                    default:
                        break;
                }
                break;
            case BOOLEAN:
                boolean tempVal = false;
                try{
                    left = operands.getfi();
                    right = operands.getse();
                    //TODO: do typechecking for comarison operators.
                    switch(operator) {
                        // TODO: perform type check if following are integer/float/char.
                        case sym.EQ:
                            tempVal = left.getSymbol().isEqual(right.getSymbol());
                            break;
                        case sym.NOTEQ:
                            tempVal = !left.getSymbol().isEqual(right.getSymbol());
                            break;
                        case sym.GREATEREQ:
                            tempVal = left.getSymbol().isGreaterAndEqual(right.getSymbol());
                            break;
                        case sym.GREATER:
                            tempVal = left.getSymbol().isGreater(right.getSymbol());
                            break;
                        case sym.LESSEREQ:
                            tempVal = left.getSymbol().isLessAndEqual(right.getSymbol());
                            break;
                        case sym.LESSER:
                            tempVal = left.getSymbol().isLess(right.getSymbol());
                            break;
                        // TODO: prform type check if the following are boolean or not.
                        case sym.NOT:
                            tempVal = left.getSymbol().not(); // check if true or not;
                            break;
                        case sym.OR:
                            tempVal = left.getSymbol().or(right.getSymbol());
                            break;
                        case sym.AND:
                            tempVal = left.getSymbol().and(right.getSymbol());
                            break;
                        default:
                            // TODO: This shouldn't happen I think, not sure.
                            // Check when not sleep deprived.
                            tempVal = (boolean)inum.getValue();
                            break;
                    }
                } catch(NullPointerException e) {
                    tempVal = (boolean)inum.getValue();
                }

                if(tempVal == true) {
                    symbol.setValue(true);
                } else {
                    symbol.setValue(false);
                }
            default: 
                break;
        }
        
        return symbol;
    }
    
    public boolean isId() {
        switch (this.eType) {
            case ID:
                return true;
            default:
                return false;
        }
    }
    
    // TODO: Refactor this if we have enough time.
    // --ONLY USE THIS FUNCTION INSIDE THIS CLASS--
    // everywhere else, use Aexp.getSymbol().getType(). My code is fucked up.
    public int getType() {
       return this.type;
    }
}
