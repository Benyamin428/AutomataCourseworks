public class Question2
{
    public static void main(String[] args) 
    {
        // DEMO code, using automaton of Figure 1

        TM M0=generateTM0();

        // Check and print the TM M0
        checkPrintTM(M0,"M0");
        // Run it on input epsilon
        M0.start();
        M0.fire(M0.deltaTP[1]);
        M0.fire(M0.deltaTP[3]);
        M0.fire(M0.deltaTP[5]);

        // Part (a)
        System.out.println("Printout(a)");
        checkPrintTM(generateTM1(),"M1");
        checkPrintTM(generateTM2(),"M2");

        // Part (c)
        System.out.println("Printout(b)");
        runABC();
        runAABBCC();

    }

    public static TM generateTM0() {
        String[] alphabet = new String[]{ "a", "b", "c" };
        String[] alphabetTP = new String[]{ "$", "0" };
        Transition[] delta = new Transition[] { 
            new Transition(0,"a",1), 
            new Transition(3,"b",2),
            new Transition(5,"c",4)
        };
        TransitionTP[] deltaTP = new TransitionTP[] { 
            new TransitionTP(1,false,"0",1,0), 
            new TransitionTP(0,true,"$",-1,2), 
            new TransitionTP(2,true,"0",-1,3), 
            new TransitionTP(2,true,"$",1,4), 
            new TransitionTP(4,true,"0",1,5), 
            new TransitionTP(4,true,"$",0,6)
        };
        int[] finals = new int[] { 6 };
        TM M = new TM(7,alphabet,alphabetTP,delta,deltaTP,finals);
        return M;
    }

    public static TM generateTM1() {
        String[] alphabet = new String[]{ "a", "b", "c" };
        String[] alphabetTP = new String[]{ "$", "0" };
        Transition[] delta = new Transition[] { 
            new Transition(0,"a",1), 
            new Transition(3,"b",2),
            new Transition(5,"c",6),
            new Transition(6,"c",7),
            new Transition(7,"c",4)
        };
        TransitionTP[] deltaTP = new TransitionTP[] { 
            new TransitionTP(1,false,"0",1,0), 
            new TransitionTP(0,true,"$",-1,2), 
            new TransitionTP(2,true,"0",-1,3), 
            new TransitionTP(2,true,"$",1,4), 
            new TransitionTP(4,true,"0",1,5), 
            new TransitionTP(4,true,"$",0,6)
        };
        int[] finals = new int[] { 8 };

        TM M = new TM(9,alphabet,alphabetTP,delta,deltaTP,finals);
        return M;
    }

    public static TM generateTM2() {
        String[] alphabet = new String[]{ "0", "1", "2" };
        String[] alphabetTP = new String[]{ "$", "0", "1" };
        Transition[] delta = new Transition[] { 
            new Transition(0,"0",1), 
            new Transition(0,"1",2),
            new Transition(4,"2",5),
            new Transition(6,"0",5),
            new Transition(7,"1",5)
        };
        TransitionTP[] deltaTP = new TransitionTP[] { 
            new TransitionTP(1,false,"0",1,0), 
            new TransitionTP(2,false,"1",1,0), 
            new TransitionTP(0,true,"$",-1,3), 
            new TransitionTP(3,true,"1",-1,3), 
            new TransitionTP(3,true,"0",-1,3), 
            new TransitionTP(3,true,"$",1,4),
            new TransitionTP(5,true,"1",1,7),
            new TransitionTP(5,true,"0",1,6),
            new TransitionTP(5,true,"$",0,8)
        };
        int[] finals = new int[] { 8 };

        TM M = new TM(9,alphabet,alphabetTP,delta,deltaTP,finals);
        return M;
    }

    public static void runABC() {
        TM M0=generateTM0();
        M0.start();
        M0.fire(M0.delta[0]);
        M0.fire(M0.deltaTP[0]);
        M0.fire(M0.deltaTP[1]);
        M0.fire(M0.deltaTP[2]);
        M0.fire(M0.delta[1]);
        M0.fire(M0.deltaTP[3]);
        M0.fire(M0.deltaTP[4]);
        M0.fire(M0.delta[2]);
        M0.fire(M0.deltaTP[5]);
    }

    public static void runAABBCC() {
        TM M0=generateTM0();
        M0.start();
        M0.fire(M0.delta[0]);
        M0.fire(M0.deltaTP[0]);
        M0.fire(M0.delta[0]);
        M0.fire(M0.deltaTP[0]);
        M0.fire(M0.deltaTP[1]);
        M0.fire(M0.deltaTP[2]);
        M0.fire(M0.delta[1]);
        M0.fire(M0.deltaTP[2]);
        M0.fire(M0.delta[1]);
        M0.fire(M0.deltaTP[3]);
        M0.fire(M0.deltaTP[4]);
        M0.fire(M0.delta[2]);
        M0.fire(M0.deltaTP[4]);
        M0.fire(M0.delta[2]);
        M0.fire(M0.deltaTP[5]);
    }

    // DO NOT CHANGE THE REMAINING CODE
    
    // print TM M, after checking it is valid
    public static void checkPrintTM(TM M, String name) {
        if(M==null) return;
        String s = M.check();
        if(s!="") System.out.println("Error found in "+name+":\n"+s);
        else System.out.println(name+" = "+M.toString());
    }

}
