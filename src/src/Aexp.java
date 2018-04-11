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
                        val = operands.getfi().getValue() + operands.getse().getValue();
                        break;
                    case sym.MINUS:
                        val = operands.getfi().getValue() - operands.getse().getValue();
                        break;
                    case sym.TIMES:
                        val = operands.getfi().getValue() * operands.getse().getValue();
                        break;
                    case sym.DIVIDE:
                        val = operands.getfi().getValue() / operands.getse().getValue();
                        break;
                    default:
                        break;
                }
                break;
            case BOOLEAN:
                left = operands.getfi();
                right = operands.getse();
                boolean tempVal = false;
                switch(operator) {
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
                }
                if(tempVal == true) {
                    val = 1.0f;
                } else {
                    val = 0.0f;
                }
            default: 
                break;
        }
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
       return this.type;
    }
}
