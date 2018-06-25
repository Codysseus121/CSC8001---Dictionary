
/**
 * Write a description of class Concordance here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Concordance
{
    
    private String kwic;
    private String lcontext;
    private String rcontext;
    private String paragraph;

    /**
     * Constructor for objects of class Concordance
     */
    public Concordance(String lcontext, String kwic, String rcontext, String paragraph)
    {
        this.lcontext=lcontext;
        this.kwic=kwic;
        this.rcontext=rcontext;
        this.paragraph=paragraph;
        
    }

    public String toString()
    {
    return lcontext + " " + kwic + " " + rcontext;
    
    }
}
