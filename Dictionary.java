import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.TreeMap;

/**
 * A class to take text input from a file and produce a list of words and their number of repetitions.
 * Taken from 'John Bentley: Programming Pearls', 15.1 and implemented in Java with two different methods.
 * Main method parses a text file, creates an ordered ArrayList of the words in the text file, and returns
 * a HashMap with the number of repetitions for each unique entry.
 * Second method simply counts, removes and prints the repetitions.
 * @Dimitrios P. 
 * @21-11-2017
 */
public class Dictionary
{ 
    private ArrayList <String> dictionary;
    private Scanner inFile;
    private HashMap <String,Integer> repetitions;

    public static void main(String[] args) throws FileNotFoundException
    {
        Dictionary myDictionary=new Dictionary();
        myDictionary.parseText();
        myDictionary.countElements();
        myDictionary.removeReps();
    }

    public Dictionary() 
    {
        this.dictionary = new ArrayList<String>();
        this.repetitions= new HashMap<String, Integer>();

    } 

    /**
     * Method parseText
     * Processes the text file and adds the results in a sorted ArrayList.
     *
     */
    private void parseText () throws FileNotFoundException
    { 
        inFile = new Scanner (new FileReader("C:\\Users\\Mitsos\\Desktop\\Ovid.txt")).useDelimiter("[^a-zA-Z]*\\s+[^a-zA-Z]*");
        while (inFile.hasNext()) 
            dictionary.add(inFile.next().toString().toLowerCase().replaceAll("[^a-zA-Z]","").trim());
        Collections.sort(dictionary);
    }

    /**
     * Method countElements
     * Counts the number of word repetitions and outputs the result to a HashMap(word,repetitions)
     */
    private void countElements ()
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

    }

    /**
     * Method removeReps
     * Removes the repetitions and prints the final form of the dictionary to the screen.
     */
    private void removeReps()

    {
        int count=dictionary.size();
        for (int i=0;i<count;i++)
        { 
            String word=dictionary.get(i);

            for (int j=i+1;j<count;j++)
            {

                if (word.equals(dictionary.get(j)))
                {

                    dictionary.remove(j--);
                    count--;

                }
            }
        }

        Map <String, Integer> entries = new TreeMap<String, Integer>(repetitions);
        for(Map.Entry<String, Integer> entry : entries.entrySet()) 
            System.out.printf("%-25s : %d\n",entry.getKey(), entry.getValue());

    }

    /** A second method to print word count. Does not return an object.
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

            System.out.println(word +" : "+repetitions);
            repetitions=1;
        }

    }

}


