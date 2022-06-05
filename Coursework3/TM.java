import java.util.*;

public class TM extends FSA
    {
        public String alphabetTP[]; 
        public TransitionTP deltaTP[];
        private Tape tape;
        private int state;
        
        public TM(int n, String[] a, String[] aTP, Transition[] d, TransitionTP[] dTP, int[] f)
        {
            super(n,a,d,f);
            alphabetTP = aTP;
            deltaTP = dTP;
        }

        public void start()
        {
            tape = new Tape();
            state = 0;
            System.out.println("TM started in configuration: (q"+state+", "+tape+")");
        }

        public void fire(Transition t)
        {
            System.out.print("Fire "+t+"... ");
            if(t.from != state) {
                System.out.println("transition cannot be fired from state q"+state);
                return;
            }
            if(t instanceof TransitionTP) {
                TransitionTP tTP = (TransitionTP)t;
                if(tTP.isRead) {
                    if(!read(tTP.label,tTP.direction)) {
                        System.out.println("transition cannot be fired from tape "+tape);
                        return;
                    }
                }
                else write(tTP.label,tTP.direction);
            }
            else {
                System.out.print("read input "+t.label+"... ");
            }
            state = t.to;
            System.out.println("new configuration: (q"+state+", "+tape+")");
        }
        
        public int getState()
        {
            return state;
        }

        public String getTape()
        {
            return tape.toString();
        }

        private boolean read(String l,int d) 
        {
            if(!tape.read().equals(l)) return false;
            if(d==1) tape.moveR();
            else
                if (d==-1) tape.moveL();
            return true;
        }

        private void write(String l,int d) 
        {
            tape.write(l);
            if(d==1) tape.moveR();
            else
                if (d==-1) tape.moveL();
        }

        // Convert the TM into a 6-tuple string representation
        public String toString()
        {
            String s = "("+toStringAsSet(alphabet)+", "+toStringAsSet(alphabetTP)+", {";
            for(int i=0; i<numStates; i++)
            {
                if(i!=0) s += ", ";
                s += "q"+i;
            }
            s += "}, "+toStringAsSet(merge(delta,deltaTP))+", q0, {";
            for(int i=0; i<finalStates.length; i++)
            {
                if(i!=0) s += (", ");
                s += "q"+finalStates[i];
            }
            return s+"})";
        }
            
        // Perform all kinds of checks on a TM
        public String check()
        {
            // Check the alphabets are valid
            if(alphabet==null) return "Bad alphabet (null)";
            // Check the number of states is valid
            if(alphabetTP==null) return "Bad tape alphabet (null)";
            if(!Arrays.asList(alphabetTP).contains("$")) return "Bad tape alphabet (no $)";
            // Check the number of states is valid
            if(numStates<=0) return "Bad number of states ("+numStates+")";
            // Check that the transition relation is valid 
            checkTRelation(delta,"transition relation");
            // Check that final states are valid
            if(finalStates==null) return "Bad (null) final states";
            for(int s : finalStates) {
                String checkS = checkState(s);
                if(checkS != "") return ("Bad final states element: "+checkS);
            }
            return "";
        }
    
        private String checkState(int s)
        {
            if (s<0 || s>=this.numStates) return ("incorrect state number ("+s+")");
            return "";
        }
    
        private String checkTRelation(Transition[] a,String name)
        {
            if(a==null) return ("Bad (null) "+name);
            for(Transition t : a) {
                String checkT = checkTransition(t); 
                if (checkT != "") return ("Bad "+name+" element "+t+": "+checkT); 
            }
            return "";
        }
    
        private String checkLabel(String l, String[] a)
        {
            for(String s : a) if(s == l) return ""; 
            if(l != "") return ("label not in corresponding alphabet ("+l+")"); return "";
        }
    
        private String checkTransition(Transition t) 
        {
            String checkC = checkState(t.from); 
            if(checkC != "") return checkC;
            checkC = checkState(t.to);
            if(checkC != "") return checkC;
            return checkLabel(t.label,alphabet);
        }
    
        private String checkTransitionTP(TransitionTP t) 
        {
            String checkC = checkState(t.from); 
            if(checkC != "") return checkC;
            checkC = checkState(t.to);
            if(checkC != "") return checkC;
            checkC = checkLabel(t.label,alphabetTP);
            if(checkC != "") return checkC;
            if(t.direction<-1 || t.direction>1) return ("wrong direction ("+t.direction+")");
            return "";
        }

        // Convert an array that represents a set into a string 
        private String toStringAsSet(Object[] x)
        {    
            String t = Arrays.toString(x);
            return "{"+t.substring(1,t.length()-1)+"}";
        }

        // Merge 2 arrays
        private Object[] merge(Object[] x1,Object[] x2)
        {    
            Object[] res = new Object[x1.length+x2.length];
            int i=0;
            for(Object x : x1) res[i++] = x;
            for(Object x : x2) res[i++] = x;
            return res;
        }

        public static Object[] getSamples()
        {
            int num = 10;
            Object[][] tests = new Object[][]{
                new Object[]{3,3,"0","1","2",6,0,"1",0,0,"0",1,0,"2",2,1,"1",2,2,"0",2,2,"1",2,1,2},
                new Object[]{3,3,"0","1","2",6,0,"0",0,0,"0",1,0,"2",2,1,"1",2,2,"0",2,2,"1",2,1,2},
                new Object[]{3,3,"0","1","2",6,0,"0",0,0,"0",1,0,"0",2,1,"1",2,2,"0",2,2,"1",2,1,2},
                new Object[]{3,3,"0","1","2",6,0,"1",0,0,"0",1,0,"2",2,1,"1",2,2,"0",2,2,"0",1,1,2},
                new Object[]{3,3,"0","1","2",7,0,"1",0,0,"0",1,0,"2",2,1,"0",2,2,"0",2,2,"0",0,2,"0",1,1,2},
                new Object[]{3,5,"0","1","2","a","b",7,0,"1",0,0,"0",1,0,"2",2,1,"0",2,2,"a",2,2,"b",0,2,"c",1,1,2},
                new Object[]{3,5,"0","1","2","a","b",7,0,"1",0,0,"0",1,0,"2",2,1,"0",2,2,"a",2,2,"b",2,2,"c",2,1,2},
                new Object[]{3,5,"0","1","2","a","b",7,0,"1",0,0,"1",1,0,"2",2,1,"0",2,2,"a",2,2,"b",2,2,"a",1,1,2},
                new Object[]{4,4,"a","b","c","d",16,0,"a",0,0,"b",1,0,"c",2,0,"d",3,1,"a",0,1,"b",1,1,"c",2,1,"d",3,2,"a",0,2,"b",1,2,"c",2,2,"d",3,3,"a",0,3,"b",1,3,"c",2,3,"d",3,4,0,1,2,3},
                new Object[]{1,5,"0","1","2","a","b",0,0}
            };
            FSA[] samples = new FSA[num];
            for(int i=0; i<num; i++)
                samples[i] = fromArray(tests[i]);
            Boolean[] res = new Boolean[]{ true, false, false, false, false, true, true, false, true, true };    
            return new Object[]{ samples, res};
        }

        private static FSA fromArray(Object[] X)
        {
            int i,n,ns = (int)X[0];
            n = (int)X[1];
            String[] a = new String[n];
            for(i=2; i<n+2; i++)
                a[i-2] = (String)X[i];
            n = (int)X[i]; i++;
            Transition[] ts = new Transition[n];
            for(int j=0; j<n; i+=3, j++) 
                ts[j] = new Transition((int)X[i],(String)X[i+1],(int)X[i+2]);
            n = (int)X[i]; i++;
            int[] fs = new int[n];
            for(int j=0; j<n; i++, j++)
                fs[j] = (int)X[i];
            return new FSA(ns,a,ts,fs);
        }
    }
    
