import java.util.*;

public class Question2
{
    public static void main(String[] args) 
    {
        // DEMO code, using CFG of Question 1(c)
        CFG G0=generateCFG0();
        
        // Check if G0 accepts some words
        System.out.println("accepts 20: "+isAccepted(G0,new String[]{"2","0"}));
        System.out.println("accepts 1201: "+isAccepted(G0,new String[]{"1","2","0","1"}));
        System.out.println("accepts 1203: "+isAccepted(G0,new String[]{"1","2","0","3"}));
        System.out.println("accepts epsilon: "+isAccepted(G0,new String[]{ }));

        // Check if G0 is in CNF -- the method needs to be implemented
        System.out.println("in CNF: "+isInCNF(G0));

        // Part (b)
        System.out.println("Printout(b)");
        runThem();

        // Part (c)
        System.out.println("Printout(c)");
        testIsInCNF();

    }

    public static CFG generateCFG0()
    {
        String[] alphabet = new String[]{ "0", "1", "2", "3" };
        String[] vars = new String[]{ "S", "Y" };
        Rule[] R = new Rule[] { 
            new Rule("S",new String[]{"1","S","1"}), 
            new Rule("S",new String[]{"3","S","3"}), 
            new Rule("S",new String[]{"Y"}), 
            new Rule("Y",new String[]{"2","0"}),
            new Rule("Y",new String[]{"2","0","Y"})
        };
        return new CFG(alphabet,vars,R,"S");
    }

    public static CFG generateCFG1() {
        String[] alphabet = new String[]{ "0", "1", "2"};
        String[] vars = new String[]{ "S", "Y"};
        Rule[] R = new Rule[] {
            new Rule("S", new String[]{"1", "Y"}),
            new Rule("S", new String[]{"2", "S"}),
            new Rule("S", new String[]{}),
            new Rule("Y", new String[]{"S", "1"}),
            new Rule("Y", new String[]{"0", "Y", "1", "0"}),
            new Rule("Y", new String[]{"0"})
        };
        return new CFG(alphabet, vars, R, "S"); 
    }

    public static CFG generateCFG2() {
        String[] alphabet = new String[]{"(", ")"};
        String[] vars = new String[]{"S"};
        Rule[] R = new Rule[] {
            new Rule("S", new String[]{}),
            new Rule("S", new String[]{"(", "S", ")"}),
            new Rule("S", new String[]{"S", "S"})
        };
        
        return new CFG(alphabet, vars, R, "S"); 
    }

    public static CFG generateCFG3() {
        String[] alphabet = new String[]{"0", "1", "2", "3"};
        String[] vars = new String[]{"S"};
        Rule[] R = new Rule[] {
            new Rule("S", new String[]{"3", "X"}),
            new Rule("S", new String[]{"1", "X"}),
            new Rule("X", new String[]{"3", "S"}),
            new Rule("X", new String[]{"1", "Y"}),
            new Rule("X", new String[]{}),
            new Rule("Y", new String[]{"0", "X"}),
            new Rule("Y", new String[]{"3", "X"}),
            new Rule("Y", new String[]{})
        };
        return new CFG(alphabet, vars, R, "S"); 
    }
    
    public static void runThem() {
        HashMap<Boolean, String> check = new HashMap<Boolean, String>();
        check.put(true, "yes");
        check.put(false, "no");

        CFG G0=generateCFG0();
        CFG G1=generateCFG1();
        CFG G2=generateCFG2();
        CFG G3=generateCFG3();
        
        System.out.println("G0 accepts: 2020 [" + check.get(isAccepted(G0, new String[]{"2", "0", "2", "0"})) + "], 120201 [" + check.get(isAccepted(G0, new String[]{"1", "2", "0", "2", "0", "1"})) + "], 2021 [" + check.get(isAccepted(G0, new String[]{"2", "0", "2", "1"})) + "]");
        System.out.println("G1 accepts: epsilon [" + check.get(isAccepted(G1, new String[]{})) + "], 210 [" + check.get(isAccepted(G1, new String[]{"2", "1", "0"})) + "], 210010 [" + check.get(isAccepted(G1, new String[]{"2", "1", "0", "0", "1", "0"})) + "]");
        System.out.println("G2 accepts: (()) [" + check.get(isAccepted(G2, new String[]{"(", "(", ")", ")"})) + "], ()(()) [" + check.get(isAccepted(G2, new String[]{"(", ")", "(", "(", ")", ")"})) + "], ((()))) [" + check.get(isAccepted(G2, new String[]{"(", "(", "(", ")", ")", ")", ")"})) + "]");
        System.out.println("G3 accepts: 11011 [" + check.get(isAccepted(G3, new String[]{"1", "1", "0", "1", "1"})) + "], 11010 [" + check.get(isAccepted(G3, new String[]{"1", "1", "0", "1", "0"})) + "], 11001 [" + check.get(isAccepted(G3, new String[]{"1", "1", "0", "0", "1"})) + "]");
    }

    public static Boolean isInCNF(CFG G) {
        Boolean checkIfCNF = true;

        for (int i=0; i<G.rules.length; i++) {
            for (int j=0; j<G.rules[i].rhs.length; j++) {
                //Rule 1
                if (G.startVar == G.rules[i].rhs[j]) {
                    checkIfCNF = false;
                }

                //Rule 4
                if (G.rules[i].rhs.length == 1) {
                    if (G.checkVar(G.rules[i].rhs[j]) == true) {
                        checkIfCNF = false;
                    }
                }

            }

            //Rule 2
            if (G.rules[i].rhs.length > 2) {
                checkIfCNF = false;
            }

            //Rule 3
            if (!G.startVar.equals(G.rules[i].var) && G.rules[i].rhs.length == 0) {
                checkIfCNF = false;
            }

            //Rule 5
            if (G.rules[i].rhs.length == 2) {
                if (G.checkVar(G.rules[i].rhs[0]) == true && G.checkLetter(G.rules[i].rhs[1]) == true) {
                    checkIfCNF = false;
                }
                else if (G.checkLetter(G.rules[i].rhs[0]) == true && G.checkLetter(G.rules[i].rhs[1]) == true) {
                    checkIfCNF = false;
                }
                else if (G.checkLetter(G.rules[i].rhs[0]) == true && G.checkVar(G.rules[i].rhs[1]) == true) {
                    checkIfCNF = false;
                }
            }

        }

        return checkIfCNF; 
    }

    // DO NOT CHANGE THE REMAINING CODE
    
    /* Print CFG G, after checking it is valid. */
    public static void checkPrintCFG(CFG G, String name) {
        if(G==null) return;
        String s = G.check();
        if(s!="") System.out.println("Error found in "+name+":\n"+s);
        else System.out.println(name+" = "+G.toString());
    }
    
    /* Check if CFG G accepts word w -- naive implementation. */
    public static Boolean isAccepted(CFG G, String[] w) {
        Stack<String> s = new Stack<>();
        s.push(G.startVar);
        return isAcceptedRec(G,w,s,0,2*w.length+1);
    }
        
    /* Test the isInCNF method on 10 CFGs. */
    public static void testIsInCNF() {
        int j, marks = 0;
        Object[] X = CFG.getSamples();
        CFG[] tests = (CFG[])X[0];
        Boolean[] res = (Boolean[])X[1];
        for(int i=0; i<tests.length; i++) {
            System.out.println("Test "+(i+1)+": "+tests[i].toString());
            j = (res[i]==isInCNF(tests[i])) ? 1 : 0;
            System.out.println("Result: "+j);
            marks += j;
        }
        System.out.println("Total: "+marks);
    }

    private static Boolean isAcceptedRec(CFG G, String[] w, Stack<String> s, int i, int bound) {
        if(bound<0) return false;
        if(s.empty()) return (i==w.length);
        String next = s.pop();
        if(G.checkLetter(next)) {
            if(i==w.length || !next.equals(w[i])) return false;
            return isAcceptedRec(G,w,s,i+1,bound-1);
        }
        for(Rule r : G.rules)
            if(r.var==next) {
                Stack<String> s2=(Stack<String>)s.clone();
                for(int j=r.rhs.length-1; j>=0; j--) s2.push(r.rhs[j]);
                if(isAcceptedRec(G,w,s2,i,bound-1)) return true;
            }
        return false;
    }

}

