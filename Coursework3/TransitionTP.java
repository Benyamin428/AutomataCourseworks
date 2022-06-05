public class TransitionTP extends Transition
{
    public boolean isRead;
    public int direction;
    
    TransitionTP(int f,boolean b,String l,int d,int t)
    {
        super(f,l,t);
        isRead = b;
        direction = d;
    }

    public String toString()
    {
        String x = isRead ? ",read(" : ",write(";
        x += label+",";
        x += direction==-1 ? "L" : (direction==1 ? "R" : "N");
        return "(q"+from+x+"),q"+to+")";
    }
}
