
/**
 * Write a description of class Concordance here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Concordance implements Comparable<Concordance>
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

    
    public Concordance(String lcontext, String kwic, String rcontext)
    {
        this.lcontext=lcontext;
        this.kwic=kwic;
        this.rcontext=rcontext;
        //this.paragraph=paragraph;

    }
    
    public Concordance()
    {}
    
    public String getLContext()
    { return lcontext;}

    public String getRContext()
    { return rcontext;}

    public String getKwic()
    { return kwic;}

    public String getPara()
    { return paragraph;}

    public int compareTo(Concordance con)
    {
        return kwic.compareTo(con.kwic);

    }

    public String toString()
    {
        return lcontext + " " + kwic + " " + rcontext;

    }
}
