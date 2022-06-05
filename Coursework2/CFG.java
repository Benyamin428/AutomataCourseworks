import java.util.*;

public class CFG
{
    public String alphabet[];
    public String vars[];
    public Rule rules[];
    public String startVar;

    public CFG(String[] a, String[] v, Rule[] r, String sv)
    {
        alphabet=a; vars=v; rules=r; startVar=sv;
    }

    /* Convert this CFG G into a string. */
    public String toString()
    {
        String s = "("+toStringAsSet(alphabet)+", "+toStringAsSet(vars)+", R, "+startVar+")\n where R contains the rules:";
        for(Rule R: rules){
            s+="\n  "+R.var+" -> ";
            if(R.rhs.length==0) s+="eps";
            else
                for(String t: R.rhs) s+=t;
        }
        return s;
    }
    
    /* Perform all kinds of checks on this CFG
     * return "" if checks passed, otherwise an error message. */
    public String check()
    {
        // Check the set of variables is non empty, or null, and contains no duplicates
        if(this.vars==null) return ("Bad set of variables (null)");
        if(this.vars.length<=0) return ("Bad number of variables: "+this.vars.length);
        for(int i=0; i<this.vars.length; i++)
            for(int j=i+1; j<this.vars.length; j++)
                if(this.vars[i].equals(this.vars[j])) 
                    return ("This variable appears more than once: "+this.vars[i]);

        // Check that the production rules are all valid
        if(this.rules==null) return ("Bad set of rules (null)");
        for(int i=0; i<this.rules.length; i++){
            if(this.rules[i]==null) return ("Bad rule "+i+" (null)");
            if(!checkVar(this.rules[i].var))
                return ("Bad LHS variable ("+this.rules[i].var+") in rule "+i);
            if(!checkRuleRHS(this.rules[i].rhs))
                return ("Bad RHS ("+this.rules[i].rhs+") in rule "+i);
        };

        // Check that start variable is valid
        if(!checkVar(this.startVar))
            return ("Bad start variable: "+this.startVar);
 
        return "";
    }

    /* Check if the String v is a valid variable for this CFG
     * and return true if so, otherwise return false. */
    public boolean checkVar(String v)
    {
        for(int i=0; i<this.vars.length; i++){
            if(v.equals(this.vars[i])) return true;
        }
        return false;
    }

    /* Check if the String s is a valid letter for this CFG
     * and return true if so, otherwise return false. */
    public boolean checkLetter(String s)
    {
        // all alphabet symbols are valid symbols
        for(int i=0; i<this.alphabet.length; i++){
            if(s.equals(this.alphabet[i])) return true;
        }
        return false;
    }
 
    private boolean checkRuleRHS(String[] r)
    {
        for(String s : r)
            if((!checkVar(s))&&(!checkLetter(s)))
                return false;
        return true;
    }

    public static Object[] getSamples()
    {
        int num = 12;
        Object[][] tests = new Object[][]{
            new Object[]{4,"0","1","2","3",3,"S","Y","Z",5,2,"S","Y","Y",2,"S","Y","Z",1,"Y","0",2,"Z","Y","Y",1,"Z","3","S"},
            new Object[]{4,"0","1","2","3",3,"S","Y","Z",6,2,"S","Y","Y",2,"S","Y","Z",0,"S",1,"Y","0",2,"Z","Y","Y",1,"Z","3","S"},
            new Object[]{4,"0","1","2","3",3,"S","Y","Z",6,1,"S","Y",2,"S","Y","Z",0,"S",1,"Y","0",2,"Z","Y","Y",1,"Z","3","S"},
            new Object[]{4,"0","1","2","3",3,"S","Y","Z",6,2,"S","Y","2",2,"S","Y","Z",0,"S",1,"Y","0",2,"Z","Y","Y",1,"Z","3","S"},
            new Object[]{4,"0","1","2","3",3,"S","Y","Z",6,2,"S","Y","Y",2,"S","Y","Z",0,"S",1,"Y","0",3,"Z","Y","Y","Y",1,"Z","3","S"},
            new Object[]{4,"0","1","2","3",3,"S","Y","Z",6,2,"S","Y","Y",4,"S","Y","Z","Y","Z",0,"S",1,"Y","0",2,"Z","Y","Y",1,"Z","3","S"},
            new Object[]{4,"0","1","2","3",3,"S","Y","Z",6,2,"S","Y","Y",2,"S","Y","Z",0,"S",1,"Y","0",3,"Z","Y","Y","3",1,"Z","3","S"},
            new Object[]{4,"0","1","2","3",3,"S","Y","Z",6,2,"S","Y","Y",2,"S","Y","Z",0,"S",0,"Y",2,"Z","Y","Y",1,"Z","3","S"},
            new Object[]{4,"0","1","2","3",3,"S","Y","Z",6,2,"S","Y","Y",2,"S","Y","Z",0,"S",1,"Y","0",2,"Z","3","Y",1,"Z","3","S"},
            new Object[]{4,"0","1","2","3",3,"S","Y","Z",6,2,"S","0","1",2,"S","Y","Z",0,"S",1,"Y","0",2,"Z","Y","Y",1,"Z","3","S"},
            new Object[]{4,"0","1","2","3",3,"S","Y","Z",6,2,"S","Y","Y",2,"S","Y","Z",0,"S",1,"Y","0",2,"Z","Y","3",1,"Z","3","S"},
            new Object[]{4,"0","1","2","3",3,"S","Y","Z",6,2,"S","Y","Y",2,"S","Y","Z",0,"S",1,"Y","0",2,"Z","S","Y",1,"Z","3","S"}
        };
        CFG[] samples = new CFG[num];
        for(int i=0; i<num; i++)
            samples[i] = fromArray(tests[i]);
        Boolean[] res = new Boolean[]{ true, true, false, false, false, false, false, false, false, false, false, false };    
        return new Object[]{ samples, res};
    }

    private static CFG fromArray(Object[] X)
    {
        int i,n = (int)X[0];
        String[] a = new String[n];
        for(i=1; i<n+1; i++)
            a[i-1] = (String)X[i];
        n = (int)X[i]; i++;
        String[] vs = new String[n];
        for(int j=0; j<n; j++,i++)
            vs[j] = (String)X[i];
        n = (int)X[i]; i++;
        Rule[] rs = new Rule[n];
        for(int j=0; j<n; j++) {
            int k = (int)X[i]; i++;
            String var = (String)X[i]; i++;
            String[] rhs = new String[k];
            for(int jj=0; jj<k; jj++, i++)
                rhs[jj] = (String)X[i];
            rs[j] = new Rule(var,rhs);
        }
        return new CFG(a,vs,rs,(String)X[i]);
    }

    /* Convert an array that represents a set into a string. */
    private String toStringAsSet(Object[] x)
    {    
        String t = Arrays.toString(x);
        return "{"+t.substring(1,t.length()-1)+"}";
    }
}

    
