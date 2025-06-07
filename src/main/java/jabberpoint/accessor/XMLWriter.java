package jabberpoint.accessor;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import jabberpoint.constants.Constants;
import jabberpoint.presentation.Presentation;
import jabberpoint.presentation.Slide;
import jabberpoint.slideitem.BitmapItem;
import jabberpoint.slideitem.SlideItem;
import jabberpoint.slideitem.TextItem;

/**
 * XMLWriter: A class for writing a Presentation to an XML file.
 * 
 * This class mirrors the functionality of the saveFile method in XMLAccessor.
 * It uses a PrintWriter to output XML tags for slides, items, etc.
 * 
 * @author ...
 */
public class XMLWriter {

    // Using constants from Constants.XML
    // protected static final String SHOWTITLE  = "showtitle";
    // protected static final String SLIDETITLE = "title";
    // protected static final String SLIDE      = "slide";
    // protected static final String ITEM       = "item";
    // protected static final String LEVEL      = "level";
    // protected static final String KIND       = "kind";
    // protected static final String TEXT       = "text";
    // protected static final String IMAGE      = "image";

    public XMLWriter() {}  // Add constructor to match diagram

    /**
     * Writes the given Presentation to an XML file.
     * 
     * @param presentation The Presentation model to serialize
     * @param filename     The path to the XML file
     * @throws IOException if writing fails
     */

    private void convertSlide(Slide slide, PrintWriter out) {
        out.println("  <" + Constants.XML.SLIDE + ">");
        out.println("    <" + Constants.XML.SLIDETITLE + ">" + slide.getTitle() + "</" + Constants.XML.SLIDETITLE + ">");
        
        Vector<SlideItem> items = slide.getSlideItems();
        for (SlideItem item : items) {
            convertSlideItem(item, out);
        }
        
        out.println("  </" + Constants.XML.SLIDE + ">");
    }

    private void convertSlideItem(SlideItem slideItem, PrintWriter out) {
        out.print("    <" + Constants.XML.ITEM + " " + Constants.XML.KIND + "=\"");
        out.print(slideItem.toXMLString());
        out.println("</" + Constants.XML.ITEM + ">");
    }

    public void saveFile(Presentation p, String fn) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(fn))) {
            out.println("<?xml version=\"1.0\"?>");
            out.println("<presentation>");
            out.println("  <" + Constants.XML.SHOWTITLE + ">" + p.getTitle() + "</" + Constants.XML.SHOWTITLE + ">");
            
            for (int slideNumber = 0; slideNumber < p.getSize(); slideNumber++) {
                Slide slide = p.getSlide(slideNumber);
                convertSlide(slide, out);
            }
            
            out.println("</presentation>");
        }
    }
}
