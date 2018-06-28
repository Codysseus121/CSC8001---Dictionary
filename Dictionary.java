import java.util.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.TreeMap;
import java.io.*;

/**
 * A class to take text input from a file and produce a list of words and their number of repetitions.
 * Taken from 'John Bentley: Programming Pearls', 15.1 and implemented in Java with two different methods.
 * Main method parses a text file and returns a file with the number of repetitions for each unique entry.
 * Second method simply counts, removes and prints the repetitions to file.
 * @Dimitrios P. 
 * @21-11-2017
 */
public class Dictionary
{ 
    private ArrayList <String> dictionary;
    private Scanner inFile;
    private Map <String,Integer> repetitions;
    private static PrintWriter outFile;
    private String [] textarray;
    private List <Concordance> concordances = new ArrayList<Concordance>();

    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        long startTime = System.nanoTime();
        outFile=new PrintWriter("C://users//Mitsos//Desktop//Bibleconc.txt");
        Dictionary myDictionary=new Dictionary();
        myDictionary.tokenizeFile();
        myDictionary.generateConcordance(3);
        //myDictionary.printConc();
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println(totalTime);

    }

    public Dictionary() 
    {
        dictionary = new ArrayList<String>();
        repetitions= new TreeMap<String, Integer>();

    } 

    /**
     * Method parseText
     * Processes the text file and adds the results in a sorted ArrayList.
     *
     */
    public void parseText () throws FileNotFoundException
    { 
        inFile = new Scanner (new FileReader("C://users//Mitsos//Desktop//King_James_Bible.txt")).useDelimiter("[^a-zA-Z]*\\s+[^a-zA-Z]*");
        while (inFile.hasNext()) 
            dictionary.add(inFile.next().toString().toLowerCase().replaceAll("[^a-zA-Z]","").trim());
        Collections.sort(dictionary);
        System.out.println(dictionary.size());

        // int count=0;
        // for (String word : dictionary)
        // { System.out.println(word + " " + count);
        // count++;
        // }

    }

    public void processText () throws FileNotFoundException
    {
        inFile = new Scanner (new FileReader("C://users//Mitsos//Desktop//King_James_Bible.txt"));      

        while (inFile.hasNext())
            dictionary.add(inFile.next().replaceAll("\\n\\r\\t", "").trim());
        System.out.println(dictionary.size());

        // int count=0;
        // for (String word : dictionary)
        // { System.out.println(word + " " + count);
        // count++;
        // }

        // int sentence = 1;
        // for (String s : dictionary)
        // {System.out.println(sentence + " "+ s);
        // sentence++;}

    }

    public void tokenizeFile() throws FileNotFoundException, IOException

    {
        FileReader freader = new FileReader("C://users//Mitsos//Desktop//Revelation.txt");
        StreamTokenizer t = new StreamTokenizer(freader);

        while (t.nextToken() != t.TT_EOF)
        {
            if (t.ttype == t.TT_WORD)
            {
                dictionary.add(t.sval);
            }

        }
        //System.out.println(dictionary.size());
        // int count=0;
        // for (String word : dictionary)
        // { System.out.println(word + " " + count);
        // count++;
        // }
    }

    public void generateConcordance(int minlength) //Concordance object (KWIC, left context, right context, source file, paragraph)
    {
        String lcontext="";
        String rcontext="";
        String kwic="";
        String paragraph="";
        String word="";
        Concordance c=null;
        ConcDao dao= new ConcDao();
        

        for (int index=0 ; index<dictionary.size(); index++)
        {
            word=dictionary.get(index);
            //Concordance c = null;
            if (word.length()>=minlength)
            {   kwic=word;
                lcontext=getLeftContext(index, 7);//create array of strings
                rcontext=getRightContext (index, 7);
                paragraph=getParagraph(word, index, 30);
                
                c= new Concordance(lcontext, kwic, rcontext, paragraph);//instead of adding them to AL add to DB
                dao.insertCon(c);
                
            }

        }
        
    }

    private String getLeftContext(int index, int noofwords)
    {
        StringBuilder reversedcontext= new StringBuilder();

        int count=0;
        int i=index-1;
        while (i>=0 && count<=noofwords)
        {
            reversedcontext.append(dictionary.get(i)).append(" ");
            i--;
            count++;
        }
        String [] arr = reversedcontext.toString().split(" ");

        StringBuilder sb = new StringBuilder();
        for (int j=arr.length-1 ; j>=0 ; j--)
        {
            sb.append(arr[j]).append(" ");

        }
        String lc = sb.toString().trim();
        reversedcontext.setLength(0);
        sb.setLength(0);
        return lc;
    }

    private String getRightContext(int index, int noofwords)
    {
        StringBuilder rightcontext= new StringBuilder();
        int count=0;
        int i=index+1;
        while (i<dictionary.size() && count<=noofwords)
        {
            rightcontext.append(dictionary.get(i)).append(" ");
            count++;
            i++;
        }
        String rc = rightcontext.toString().trim();
        rightcontext.setLength(0);
        return rc;

    }

    private String getParagraph (String word, int index, int noofwords)
    {
        String paragraphleft = getLeftContext(index, noofwords);
        String paragraphright = getRightContext (index, noofwords);
        String paragraph = paragraphleft + " " + word + " " + paragraphright;

        return paragraph;
    }

    public void printConc()
    {

        Collections.sort(concordances);
        for (Concordance c: concordances)
        {
            outFile.printf("%-35s %25s %35s\n", c.getLContext(), c.getKwic(), c.getRContext());
        }

        outFile.close();
    }

    /**
     * Method countElements
     * Counts the number of word repetitions and outputs the result to a file.
     */
    public void countElements () 
    {
        String word=dictionary.get(0);
        int count=dictionary.size();
        int i=0;
        while (i<dictionary.size())
        {
            word=dictionary.get(i);
            if (repetitions.containsKey(word))
            {
                repetitions.put(word, repetitions.get(word)+1);
                i++;
            }

            else
            {
                repetitions.put(word,1);
                i++;
            }

        }

        for(Map.Entry<String, Integer> entry : repetitions.entrySet()) 
            outFile.printf("%-20s : %d\n",entry.getKey(), entry.getValue());
        outFile.close();
    }

    /** A second method to print word count to file.
     * 
     */
    public void countPrintRepetitions ()
    {

        try
        {
            parseText();
        }
        catch (FileNotFoundException f)
        {
            System.out.println("File not found");
        }
        String word=dictionary.get(0);
        int count=dictionary.size();
        int repetitions=1;

        for (int i=0;i<dictionary.size();i++)
        {
            word=dictionary.get(i);

            for (int j=i+1;j<count;j++)
            {
                String word2=dictionary.get(j);

                if (word.equals(word2))
                {
                    repetitions=repetitions+1;
                    word=word2;
                    dictionary.remove(j--);                    
                    count--;

                }
                else
                    break;
            }

            outFile.printf("%-25s : %d\n", word, repetitions);            
            repetitions=1;
        }
        outFile.close();
    }

}

