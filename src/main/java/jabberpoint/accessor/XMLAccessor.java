package jabberpoint.accessor;

import java.io.IOException;
import org.w3c.dom.Element;

import jabberpoint.presentation.Presentation;


/** XMLAccessor, reads and writes XML files
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class XMLAccessor extends Accessor {
	
    /** Default API to use. */
    // Using constant from Constants.XML
    // protected static final String DEFAULT_API_TO_USE = "dom";
    
    /** namen van xml tags of attributen */
    // Using constants from Constants.XML
    // protected static final String SHOWTITLE = "showtitle";
    // protected static final String SLIDETITLE = "title";
    // protected static final String SLIDE = "slide";
    // protected static final String ITEM = "item";
    // protected static final String LEVEL = "level";
    // protected static final String KIND = "kind";
    // protected static final String TEXT = "text";
    // protected static final String IMAGE = "image";
    
    /** tekst van messages */
    // Using constants from Constants.ErrorMessages
    // protected static final String PCE = "Parser Configuration Exception";
    // protected static final String UNKNOWNTYPE = "Unknown Element type";
    // protected static final String NFE = "Number Format Exception";
    
    private XMLWriter writer;
    private XMLReader reader;
    
    public XMLAccessor() {
        this.writer = new XMLWriter();
        this.reader = new XMLReader();
    }
    
    protected String getTitle(Element element, String tagName) {
        return reader.getTitle(element, tagName);
    }

    @Override
    public void loadFile(Presentation presentation, String filename) throws IOException {
        reader.loadFile(presentation, filename);
    }

    @Override
    public void saveFile(Presentation presentation, String filename) throws IOException {
        writer.saveFile(presentation, filename);
    }
}
