package jabberpoint.accessor;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import jabberpoint.constants.Constants;
import jabberpoint.constants.Constants.XML;
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
    public void write(Presentation presentation, String filename) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(filename))) {
            // XML Header + DOCTYPE
            out.println("<?xml version=\"1.0\"?>");
            // In the original JabberPoint, there's often a DOCTYPE line referencing a DTD:
            out.println("<!DOCTYPE presentation SYSTEM \"jabberpoint.dtd\">");
            
            // Root element
            out.println("<presentation>");

            // Show title
            out.print("  <" + Constants.XML.SHOWTITLE + ">");
            out.print(presentation.getTitle());
            out.println("</" + Constants.XML.SHOWTITLE + ">");

            // For each slide
            int slideCount = presentation.getSize();
            for (int slideNumber = 0; slideNumber < slideCount; slideNumber++) {
                Slide slide = presentation.getSlide(slideNumber);
                convertSlide(slide, out);
            }

            // Close root element
            out.println("</presentation>");
        }
    }

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
        
        if (slideItem instanceof TextItem) {
            TextItem textItem = (TextItem) slideItem;
            out.print(Constants.XML.TEXT + "\" " + Constants.XML.LEVEL + "=\"" + textItem.getLevel() + "\">");
            out.print(((TextItem) slideItem).getText());
        } 
        else if (slideItem instanceof BitmapItem) {
            BitmapItem bitmapItem = (BitmapItem) slideItem;
            out.print(Constants.XML.IMAGE + "\" " + Constants.XML.LEVEL + "=\"" + bitmapItem.getLevel() + "\">");
            out.print(((BitmapItem) slideItem).getName());
        }
        
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
