
package src;

import java.util.ArrayList;

public class Lstat {

    ArrayList<Astat> statementList;
    static Astat current_statement;

    public Lstat(Astat s){
        statementList  = new ArrayList<Astat>();
        statementList.add(s);
    }

    public Lstat(Lstat l, Astat s){
        statementList = l.statementList;
        statementList.add(s);
    }

    public void execute(){
        for (Astat astat : statementList) {
            Lstat.current_statement = astat;
            astat.execute();
        }
    }

}
