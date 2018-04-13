package src;

public class Args {

    private Aexp Fi, Se;

    Args(Aexp x, Aexp y) {
        Fi = x;
        Se = y;
    }
    
    Args(Aexp x) {
        Fi = x;
        Se = x;
    }

    public Aexp getfi() {
        return Fi;
    }

    public Aexp getse() {
        return Se;
    }

}
