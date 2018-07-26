import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper; 
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
    public static void main (String [] args)
    {
    
    File file = new File("path of the document") 
PDDocument document = PDDocument.load(file);
    
    }
}
