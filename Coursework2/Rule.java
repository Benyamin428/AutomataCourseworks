/* A class implementing CFG rules. 
 * This is just a glorified way to define of store together: 
 * the LHS of a rule (a String) and the RHS (a String array). */
public class Rule
{
    public String var;
    public String rhs[];

    public Rule(String v, String[] r)
    {
        var=v; rhs=r;
    }
}
    
