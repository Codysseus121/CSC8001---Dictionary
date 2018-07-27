import org.apache.commons.text.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import java.io.File;
import java.util.*;
import java.io.*;

/**
 * Write a description of class ParseHTML here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ParseHTML
{
    // instance variables - replace the example below with your own

    /**
     * Constructor for objects of class ParseHTML
     */
    public static void main (String [] args) throws IOException
    {
        File input = new File("C:\\Users\\Mitsos\\Desktop\\New Regular Expression Functions in MySQL 8.0 _ MySQL Server Blog.html");
        PrintWriter outFile = new PrintWriter("C://users//Mitsos//Desktop//htmltotext.txt");
        Document doc = Jsoup.parse(input, "UTF-8");
        String plaintxt = doc.toString();
        String finaltext = Jsoup.parse(plaintxt).body().text();
        //System.out.println(finaltext);
        // String finaltxt = HTMLtoTEXT.html2PlainText(plaintxt);
        // String [] text = finaltxt.trim().split(" ");
        // for (String word : text)
         outFile.print(finaltext);
         outFile.close();
    }
}
