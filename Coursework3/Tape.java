import java.util.ArrayList;

public class Tape
{
    private int head;
    private ArrayList<String> tape;
    
    public Tape()
    {
        head = 0;
        tape = new ArrayList<String>();
        tape.add("$");
    }

    public String read()
    {
        return tape.get(head);
    }
            
    public void write(String l)
    {
        tape.set(head,l);
    }

    public void moveL()
    {
        if(head==0) tape.add(0,"$");
        else head--;
    }

    public void moveR()
    {
        if(head==tape.size()-1) tape.add("$");
        head++;
    }

    public String toString()
    {
        String s = "";
        for(int i=0; i<head; i++) s += tape.get(i);
        s += "["+tape.get(head)+"]";
        for(int i=head+1; i<tape.size(); i++) s += tape.get(i);
        return s;
    }
}
