public class Question2
{
    public static void main(String[] args) 
    {
        // DEMO code, using automaton of Figure 1

        FSA A0=generateFSA0();


        // Check and print the automaton A0
        checkPrintFSA(A0,"A0");

        // Check if A0 accepts some words
        System.out.println("accepts 001: "+isAccepted(A0,new String[]{"0","0","1"}));
        System.out.println("accepts 112: "+isAccepted(A0,new String[]{"1","1","2"}));
        System.out.println("accepts 201: "+isAccepted(A0,new String[]{"2","0","1"}));
        System.out.println("accepts epsilon: "+isAccepted(A0,new String[]{ }));

        // Check if A0 is deterministic -- the method needs to be implemented
        System.out.println("deterministic: "+isDeterministic(A0));

        // Part (b)
        System.out.println("Printout(b)");
        printThem();

        // Part (c)
        System.out.println("Printout(c)");
        runThem();

        // Part (d)
        System.out.println("Printout(d)");
        testIsDeterministic();

    }

    public static FSA generateFSA0() {
        String[] alphabet = new String[]{ "0", "1", "2" };
        Transition[] delta = new Transition[] { 
            new Transition(0,"0",0), 
            new Transition(0,"0",1),
            new Transition(0,"2",2),
            new Transition(1,"1",2),
            new Transition(2,"0",2),
            new Transition(2,"1",2)
        };
        int[] finals = new int[] { 2 };
        FSA A = new FSA(3,alphabet,delta,finals);
        return A;
    }

    public static FSA generateFSA1() {
        String[] alphabet = {"0", "1"};
        Transition[] delta = new Transition[] {
            new Transition(0, "1", 0),
            new Transition(0, "1", 1),
            new Transition(1, "0", 2),
            new Transition(1, "1", 3),
            new Transition(3, "0", 5),
            new Transition(5, "1", 4),
            new Transition(4, "1", 6),
            new Transition(6, "1", 5)
        };

        int[] finals = new int[] { 6 };
        FSA A = new FSA(7,alphabet,delta,finals);
        return A;        
    }

    public static FSA generateFSA2() {
        String[] alphabet = {"0", "1", "2"};
        Transition[] delta = new Transition[] {
            new Transition(0, "2", 0),
            new Transition(0, "1", 1),
            new Transition(1, "1", 2),
            new Transition(2, "0", 3),
            new Transition(3, "1", 0),
            new Transition(0, "0", 4),
            new Transition(4, "1", 5),
            new Transition(5, "2", 0)
        };

        int[] finals = new int[] {0, 4};
        FSA A = new FSA(6,alphabet,delta,finals);
        return A;     
    }

    public static FSA generateFSA3() {
        String[] alphabet = {"0", "1", "2", "3"};
        Transition[] delta = new Transition[] {
            new Transition(0, "3", 1),
            new Transition(0, "1", 1),
            new Transition(1, "1", 2),
            new Transition(1, "3", 0),
            new Transition(2, "0", 1),
            new Transition(2, "3", 1) 
        };

        int[] finals = new int[] {1, 2};
        FSA A = new FSA(3,alphabet,delta,finals);
        return A;    
    }
    
    public static void printThem() {
        checkPrintFSA(generateFSA1(), "A1");
        checkPrintFSA(generateFSA2(), "A2");
        checkPrintFSA(generateFSA3(), "A3");
    }

    public static void runThem() {
        String[] w1 = {"1", "1", "0", "1", "1"};
        String[] w2 = {"1", "1", "0", "1", "0"};
        String[] w3 = {"1", "1", "0", "0", "1"};
        FSA A1 = generateFSA1();
        FSA A2 = generateFSA2();
        FSA A3 = generateFSA3();
        FSA[] A = {A1, A2, A3};
        String[] ALabel = {"A1", "A2", "A3"};

        for (int i = 0; i<A.length; i++) {
            System.out.print("\n" + ALabel[i] + " accepts: ");
            System.out.print("11011 ");
            if((isAccepted(A[i], w1)) == true) {
                System.out.print("yes, ");
            }
            else {
                System.out.print("no, ");
            }      
            System.out.print("11010 ");
            if((isAccepted(A[i], w2)) == true) {
                System.out.print("yes, ");
            }
            else {
                System.out.print("no, ");
            }     
            System.out.print("11001 ");
            if((isAccepted(A[i], w3)) == true) {
                System.out.print("yes\n");
            }
            else {
                System.out.print("no\n");
            }
        }     
    }

    public static Boolean isDeterministic(FSA A) {
        int count = 0;
        for (int i=1; i<(A.delta).length; i++) {
            if ((A.delta[i-1]).from == (A.delta[i]).from) {
                if ((A.delta[i-1]).label == (A.delta[i]).label) {
                    count += 1;
                    break;
                }
            }
        }

        if (count > 1) {
            return false;
        }
        else {
            return true;
        }
    }

    // DO NOT CHANGE THE REMAINING CODE
    
    // print FSA A, after checking it is valid
    public static void checkPrintFSA(FSA A, String name) {
        if(A==null) return;
        String s = A.check();
        if(s!="") System.out.println("Error found in "+name+":\n"+s);
        else System.out.println(name+" = "+A.toString());
    }

    // check if FSA A accepts word w -- naive implementation
    public static Boolean isAccepted(FSA A, String[] w) {
        return isAcceptedRec(A,w,0,0);
    }
    
    // test the isDeterministic method on 10 FSAs
    public static void testIsDeterministic() {
        int j, marks = 0;
        Object[] X = FSA.getSamples();
        FSA[] tests = (FSA[])X[0];
        Boolean[] res = (Boolean[])X[1];
        for(int i=0; i<tests.length; i++) {
            System.out.println("Test "+(i+1)+": "+tests[i].toString());
            j = (res[i]==isDeterministic(tests[i])) ? 1 : 0;
            System.out.println("Result: "+j);
            marks += j;
        }
        System.out.println("Total: "+marks);
    }

    private static Boolean isAcceptedRec(FSA A, String[] w, int q, int i) {
        if(i==w.length) {
            for(int qF : A.finalStates)
                if(q==qF) return true;
            return false;
        }
        for(Transition t : A.delta) 
            if(t.from==q && t.label==w[i])
                if(isAcceptedRec(A,w,t.to,i+1)) return true;
        return false;
    }

    
}
