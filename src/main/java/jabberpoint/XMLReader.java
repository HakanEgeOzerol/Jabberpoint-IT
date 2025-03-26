package jabberpoint;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * XMLReader: A class for reading a Presentation from an XML file.
 * 
 * This class mirrors the functionality of the loadFile method in XMLAccessor.
 * It uses DOM parsing to read slides, slide items, etc.
 *
 * @author ...
 */
public class XMLReader {

    // Common XML tag/attribute names (same as in XMLAccessor)
    protected static final String SHOWTITLE   = "showtitle";
    protected static final String SLIDETITLE  = "title";
    protected static final String SLIDE       = "slide";
    protected static final String ITEM        = "item";
    protected static final String LEVEL       = "level";
    protected static final String KIND        = "kind";
    protected static final String TEXT        = "text";
    protected static final String IMAGE       = "image";

    // Some message strings (for error reporting, same as XMLAccessor)
    protected static final String PCE         = "Parser Configuration Exception";
    protected static final String UNKNOWNTYPE = "Unknown Element type";
    protected static final String NFE         = "Number Format Exception";

    private final TextItemFactory textItemFactory;
    private final BitmapItemFactory bitmapItemFactory;

    public XMLReader() {
        this.textItemFactory = new TextItemFactory();
        this.bitmapItemFactory = new BitmapItemFactory();
    }

    /**
     * Reads an XML file and populates the given Presentation.
     * 
     * @param presentation The Presentation model to fill
     * @param filename     The path to the XML file
     * @throws IOException if any IO error occurs
     */
    public void loadFile(Presentation presentation, String filename) throws IOException {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(new File(filename));

            Element doc = document.getDocumentElement();
            // Set the presentation title
            presentation.setTitle(getTitle(doc, SHOWTITLE));

            // Process each <slide> element
            NodeList slides = doc.getElementsByTagName(SLIDE);
            for (int i = 0; i < slides.getLength(); i++) {
                Element xmlSlide = (Element) slides.item(i);
                Slide slide = new Slide();
                slide.setTitle(getTitle(xmlSlide, SLIDETITLE));
                presentation.append(slide);

                // Process <item> elements within each slide
                NodeList slideItems = xmlSlide.getElementsByTagName(ITEM);
                for (int j = 0; j < slideItems.getLength(); j++) {
                    Element item = (Element) slideItems.item(j);
                    loadSlideItem(slide, item);
                }
            }

        } catch (IOException iox) {
            System.err.println("IO Error while reading " + filename + ": " + iox);
            throw iox;
        } catch (SAXException sax) {
            System.err.println("SAXException while reading " + filename + ": " + sax.getMessage());
        } catch (ParserConfigurationException pcx) {
            System.err.println(PCE + ": " + pcx.getMessage());
        }
    }

    /**
     * Helper method to get text content of a tag (e.g. <showtitle>, <title>).
     */
    private String getTitle(Element element, String tagName) {
        NodeList titles = element.getElementsByTagName(tagName);
        if (titles != null && titles.getLength() > 0) {
            return titles.item(0).getTextContent();
        }
        return "";
    }

    /**
     * Loads a single <item> element into the given Slide.
     */
    private void loadSlideItem(Slide slide, Element item) {
        NamedNodeMap attributes = item.getAttributes();
        String levelText = attributes.getNamedItem(LEVEL).getTextContent();
        String type = attributes.getNamedItem(KIND).getTextContent();
        String content = item.getTextContent();

        try {
            int level = Integer.parseInt(levelText);
            SlideItem slideItem;
            if (TEXT.equals(type)) {
                slideItem = textItemFactory.createSlideItem(type, level, content);
            } else if (IMAGE.equals(type)) {
                slideItem = bitmapItemFactory.createSlideItem(type, level, content);
            } else {
                System.err.println(UNKNOWNTYPE + ": " + type);
                return;
            }
            slide.append(slideItem);
        } catch (NumberFormatException x) {
            System.err.println(NFE);
        }
    }
}
