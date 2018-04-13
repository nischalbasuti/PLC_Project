package src;

public class Aexp {

    private enum AexpType {
        INTEGER,
        FLOAT,
        BOOLEAN,
        ID,
        EXP
    }
    int type;
    private final AexpType eType;
    private Float inum;
    private String id;
    private Args operands;
    private int operator;

    Aexp(Integer x) {
        eType = AexpType.INTEGER;
        this.type = sym.INTDEF;
        inum = (float)x;        
    }
    Aexp(Float x) {
        eType = AexpType.FLOAT;
        this.type = sym.FLOATDEF;
        inum = x;        
    }
    Aexp(Boolean x) {
        eType = AexpType.BOOLEAN;
        this.type = sym.BOOLEANDEF;
        operands = null;
        inum = (x) ? 1.0f : 0.0f;        
    }
    Aexp(String x) {
        eType = AexpType.ID;
        id = x;        
    }

    Aexp(Args x, int op) {
        operands = x;
        operator = op;
        Aexp left = operands.getfi();
        Aexp right = operands.getse();
        if( left.getType() == sym.INT && right.getType() == sym.INT ) {
            this.type = sym.INTDEF;
        } else if(( left.getType() == sym.FLOAT && right.getType() == sym.INT )
                ||( left.getType() == sym.INT && right.getType() == sym.FLOAT )
                ||( left.getType() == sym.FLOAT && right.getType() == sym.FLOAT )) {
            this.type = sym.FLOATDEF;
        }
        switch(operator) {
            case sym.EQ:
                this.type = sym.BOOLEANDEF;
                break;
            case sym.NOTEQ:
                this.type = sym.BOOLEANDEF;
                break;
            case sym.GREATEREQ:
                this.type = sym.BOOLEANDEF;
                break;
            case sym.GREATER:
                this.type = sym.BOOLEANDEF;
                break;
            case sym.LESSEREQ:
                this.type = sym.BOOLEANDEF;
                break;
            case sym.LESSER:
                this.type = sym.BOOLEANDEF;
                break;
            case sym.NOT:
                this.type = sym.BOOLEANDEF;
                break;
            case sym.AND:
                this.type = sym.BOOLEANDEF;
                break;
            case sym.OR:
                this.type = sym.BOOLEANDEF;
                break;
        }
        if(this.type == sym.BOOLEANDEF){
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
                        s = "" + inum;
                        break;
                } 
                break;
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

    public float getValue() {
        Object val = 0;
        Aexp left;
        Aexp right;
        switch (this.eType) {
            case INTEGER:
                // expression is a number
                val = inum;
                break;
            case FLOAT:
                val = inum;
                break;
            case ID:
                //expression is a variable
                try {
                    val = SymbolTable.getValue(id);
                    this.type = SymbolTable.getType(id);                    
                } catch (NullPointerException e) {
                    System.out.print(id + " was not declared");
                    e.printStackTrace();
                    System.exit(0);
                }
                break;
            case EXP:
                left = operands.getfi();
                right = operands.getse();
                //expression is a math expression
                switch (operator) {
                    case sym.PLUS:
                        val = left.getValue() + right.getValue();
                        break;
                    case sym.MINUS:
                        val = left.getValue() - right.getValue();
                        break;
                    case sym.TIMES:
                        val = left.getValue() * right.getValue();
                        break;
                    case sym.DIVIDE:
                        val = left.getValue() / right.getValue();
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
                    switch(operator) {
                        // TODO: perform type check if following are integer/float/char.
                        case sym.EQ:
                            tempVal = left.getValue() == right.getValue();
                            break;
                        case sym.NOTEQ:
                            tempVal = left.getValue() != right.getValue();
                            break;
                        case sym.GREATEREQ:
                            tempVal = left.getValue() >= right.getValue();
                            break;
                        case sym.GREATER:
                            tempVal = left.getValue() > right.getValue();
                            break;
                        case sym.LESSEREQ:
                            tempVal = left.getValue() <= right.getValue();
                            break;
                        case sym.LESSER:
                            tempVal = left.getValue() < right.getValue();
                            break;
                        // TODO: prform type check if the following are boolean or not.
                        case sym.NOT:
                            tempVal = left.getValue() == 1.0; // check if true or not;
                            break;
                        case sym.OR:
                            if(left.getValue() == 1.0 || right.getValue() == 1.0){
                                tempVal = true;
                            }
                            break;
                        case sym.AND:
                            if(left.getValue() == 1 && right.getValue() == 1){
                                tempVal = true;
                            } else if(left.getValue() == 0 && right.getValue() == 0){
                                tempVal = true;
                            }
                            break;
                        default:
                            tempVal = (inum == 1.0) ? true: false;
                            break;
                    }
                } catch(NullPointerException e) {
                    tempVal = (this.inum == 1.0f) ? true : false;
                }

                if(tempVal == true) {
                    val = 1.0f;
                } else {
                    val = 0.0f;
                }
            default: 
                break;
        }
        
        if(this.operator == sym.EQ)
            System.out.println("FUUCCCKKKKKK: "+(float)val);
        return (float) val;
    }
    
    public boolean isId() {
        switch (this.eType) {
            case ID:
                return true;
            default:
                return false;
        }
    }
    
    public int getType() {
//       if(isId()) {
//           return SymbolTable.getType(this.id);
//       }
       return this.type;
    }
}
