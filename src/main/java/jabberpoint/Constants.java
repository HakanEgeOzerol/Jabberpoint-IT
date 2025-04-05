package jabberpoint;

import java.awt.Color;
import java.awt.Font;

public class Constants {
    public static class UI {
        public static final int WIDTH = 1200;
        public static final int HEIGHT = 800;
        
        // SlideViewerComponent constants
        public static final Color BGCOLOR = Color.white;
        public static final Color COLOR = Color.black;
        public static final String FONTNAME = "Dialog";
        public static final int FONTSTYLE = Font.BOLD;
        public static final int FONTHEIGHT = 10;
        public static final int XPOS = 1100;
        public static final int YPOS = 20;
        
        // Style constants
        public static final String STYLE_FONTNAME = "Helvetica";
        
        // SlideViewerFrame constants
        public static final String JABTITLE = "Jabberpoint 1.6 - OU";
    }
    
    public static class Commands {
        public static final String ABOUT = "About";
        public static final String FILE = "File";
        public static final String EXIT = "Exit";
        public static final String GOTO = "Go to";
        public static final String HELP = "Help";
        public static final String NEW = "New";
        public static final String NEXT = "Next";
        public static final String OPEN = "Open";
        public static final String PAGENR = "Page number?";
        public static final String PREV = "Prev";
        public static final String SAVE = "Save";
        public static final String VIEW = "View";
        
        public static final String TESTFILE = "test.xml";
        public static final String SAVEFILE = "dump.xml";
    }
    
    public static class XML {
        public static final String SHOWTITLE = "showtitle";
        public static final String SLIDETITLE = "title";
        public static final String SLIDE = "slide";
        public static final String ITEM = "item";
        public static final String LEVEL = "level";
        public static final String KIND = "kind";
        public static final String TEXT = "text";
        public static final String IMAGE = "image";
        public static final String DEFAULT_API_TO_USE = "dom";
    }
    
    public static class ErrorMessages {
        public static final String PCE = "Parser Configuration Exception";
        public static final String UNKNOWNTYPE = "Unknown Element type";
        public static final String NFE = "Number Format Exception";
        public static final String IOERR = "IO Error: ";
        public static final String JABERR = "Jabberpoint Error ";
        public static final String FILE = "File ";
        public static final String NOTFOUND = " not found";
    }
    
    public static class Files {
        public static final String DEMO_NAME = "Demonstration presentation";
        public static final String DEFAULT_EXTENSION = ".xml";
    }
    
    public static class TextItems {
        public static final String EMPTYTEXT = "No Text Given";
    }
    
    public static class Info {
        public static final String JABVERSION = "Jabberpoint 1.7 - Updated Architecture";
    }
}
