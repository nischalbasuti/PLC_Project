package src;

public class Aexp {

    private enum AexpType {
        INTEGER,
        FLOAT,
        BOOLEAN,
        ID,
        EXP
    }
    private int type;
    private final AexpType eType;
    private MySymbol inum;
    private String id;
    private Args operands;
    private int operator;

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
            this.type = sym.INT;
        } else if(( left.getType() == sym.FLOAT && right.getType() == sym.INT )
                ||( left.getType() == sym.INT && right.getType() == sym.FLOAT )
                ||( left.getType() == sym.FLOAT && right.getType() == sym.FLOAT )) {
            this.type = sym.FLOAT;
        } else {
		//TODO: Handle cases with id;
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
                // expression is a number
                symbol.setValue(inum.getValue());
                break;
            case FLOAT:
                symbol.setValue(inum.getValue());
                break;
            case ID:
                //expression is a variable
                try {
                    symbol = new MySymbol(SymbolTable.getSymbol(id));
                    this.type = SymbolTable.getType(id);
		    System.out.println(this.type);
                } catch (NullPointerException e) {
                    System.out.print(id + " was not declared");
                    e.printStackTrace();
                    System.exit(0);
                }
                break;
            case EXP:
                left = operands.getfi();
                right = operands.getse();
                // Expression is a math expression.
                // TODO: do some type checking, maybe encapsulate operations inside the Symbol class.
                // TODO: fix setting this.type for expressions.
		switch (operator) {
                    case sym.PLUS:
                        symbol.setValue((Integer)left.getSymbol().getValue() + (Integer)right.getSymbol().getValue());
			symbol.setType(right.getType());
                        break;
                    case sym.MINUS:
                        symbol.setValue((Integer)left.getSymbol().getValue() - (Integer)right.getSymbol().getValue());
                        break;                                                           
                    case sym.TIMES:                                                      
                        symbol.setValue((Integer)left.getSymbol().getValue() * (Integer)right.getSymbol().getValue());
                        break;                                                           
                    case sym.DIVIDE:                                                     
                        symbol.setValue((Integer)left.getSymbol().getValue() / (Integer)right.getSymbol().getValue());
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
                            tempVal = (Integer)left.getSymbol().getValue() >= (Integer)right.getSymbol().getValue();
                            break;
                        case sym.GREATER:
                            tempVal = (Integer)left.getSymbol().getValue() > (Integer)right.getSymbol().getValue();
                            break;
                        case sym.LESSEREQ:
                            tempVal = (Integer)left.getSymbol().getValue() <= (Integer)right.getSymbol().getValue();
                            break;
                        case sym.LESSER:
                            tempVal = (Integer)left.getSymbol().getValue() < (Integer)right.getSymbol().getValue();
                            break;
                        // TODO: prform type check if the following are boolean or not.
                        case sym.NOT:
                            tempVal = left.getSymbol().isEqual(new MySymbol(true, sym.BOOLEAN)); // check if true or not;
                            break;
                        case sym.OR:
                            if(left.getSymbol().isEqual(new MySymbol(true, sym.BOOLEAN)) 
				|| right.getSymbol().isEqual(new MySymbol(true, sym.BOOLEAN))){
                                tempVal = true;
                            }
                            break;
                        case sym.AND:
                            if(left.getSymbol().isEqual(new MySymbol(true, sym.BOOLEAN)) 
				&& right.getSymbol().isEqual(new MySymbol(true, sym.BOOLEAN))){
                                tempVal = true;
                            } else if(left.getSymbol().isEqual(new MySymbol(false, sym.BOOLEAN)) 
				&& right.getSymbol().isEqual(new MySymbol(false, sym.BOOLEAN))){
                                tempVal = true;
                            }
                            break;
                        default:
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
