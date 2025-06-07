package jabberpoint.accessor;

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

import jabberpoint.constants.Constants;
import jabberpoint.presentation.Presentation;
import jabberpoint.presentation.Slide;
import jabberpoint.slideitem.SlideItem;
import jabberpoint.slideitem.SlideItemFactory;

/**
 * XMLReader: A class for reading a Presentation from an XML file.
 * 
 * This class mirrors the functionality of the loadFile method in XMLAccessor.
 * It uses DOM parsing to read slides, slide items, etc.
 *
 * @author ...
 */
public class XMLReader {

    private final SlideItemFactory slideItemFactory;

    public XMLReader() {
        this.slideItemFactory = new SlideItemFactory();
    }
    
    /**
     * Constructor for dependency injection
     */
    public XMLReader(SlideItemFactory slideItemFactory) {
        this.slideItemFactory = slideItemFactory;
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
            presentation.setTitle(getTitle(doc, Constants.XML.SHOWTITLE));

            NodeList slides = doc.getElementsByTagName(Constants.XML.SLIDE);
            for (int i = 0; i < slides.getLength(); i++) {
                Element xmlSlide = (Element) slides.item(i);
                Slide slide = new Slide();
                slide.setTitle(getTitle(xmlSlide, Constants.XML.SLIDETITLE));
                presentation.append(slide);

                NodeList slideItems = xmlSlide.getElementsByTagName(Constants.XML.ITEM);
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
            System.err.println(Constants.ErrorMessages.PCE + ": " + pcx.getMessage());
        }
    }

    /**
     * Helper method to get text content of a tag (e.g. <showtitle>, <title>).
     */
    public String getTitle(Element element, String tagName) {
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
        String levelText = attributes.getNamedItem(Constants.XML.LEVEL).getTextContent();
        String type = attributes.getNamedItem(Constants.XML.KIND).getTextContent();
        String content = item.getTextContent();

        try {
            int level = Integer.parseInt(levelText);
            SlideItem slideItem = slideItemFactory.createSlideItem(type, level, content);
            slide.append(slideItem);
        } catch (NumberFormatException x) {
            System.err.println(Constants.ErrorMessages.NFE);
        } catch (IllegalArgumentException e) {
            System.err.println(Constants.ErrorMessages.UNKNOWNTYPE + ": " + type);
        }
    }
}
