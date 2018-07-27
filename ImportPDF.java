import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper; 
import org.apache.fontbox.FontBoxFont;
import java.io.File;
import java.io.IOException;

/**
 * Write a description of class ImportPDF here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ImportPDF
{
    public static void main (String [] args) throws IOException
    {
        System.setProperty("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");
        File file = new File(("C:\\Users\\Mitsos\\Documents\\Diss\\Diss books\\Java\\Algorithhms 4th Edition by Robert Sedgewick, Kevin Wayne.pdf")); 
        PDDocument document = null;
        try
        {
            document = PDDocument.load(file);
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);
            System.out.println(text);
        }
        finally
        {
            
                document.close();
           }
        }

        
    
    
}
