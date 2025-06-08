package unit.constants;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import jabberpoint.constants.Constants;

import java.awt.Color;
import java.awt.Font;

class ConstantsTest {

    @Test
    void testUIConstants() {
        assertEquals(1200, Constants.UI.WIDTH);
        assertEquals(800, Constants.UI.HEIGHT);
        
        assertEquals(Color.white, Constants.UI.BGCOLOR);
        assertEquals(Color.black, Constants.UI.COLOR);
        assertEquals("Dialog", Constants.UI.FONTNAME);
        assertEquals(Font.BOLD, Constants.UI.FONTSTYLE);
        assertEquals(10, Constants.UI.FONTHEIGHT);
        assertEquals(1100, Constants.UI.XPOS);
        assertEquals(20, Constants.UI.YPOS);
        
        assertEquals("Helvetica", Constants.UI.STYLE_FONTNAME);
        assertEquals("Jabberpoint 1.6 - OU", Constants.UI.JABTITLE);
    }

    @Test
    void testCommandConstants() {
        assertEquals("About", Constants.Commands.ABOUT);
        assertEquals("File", Constants.Commands.FILE);
        assertEquals("Exit", Constants.Commands.EXIT);
        assertEquals("Go to", Constants.Commands.GOTO);
        assertEquals("Help", Constants.Commands.HELP);
        assertEquals("New", Constants.Commands.NEW);
        assertEquals("Next", Constants.Commands.NEXT);
        assertEquals("Open", Constants.Commands.OPEN);
        assertEquals("Page number?", Constants.Commands.PAGENR);
        assertEquals("Prev", Constants.Commands.PREV);
        assertEquals("Save", Constants.Commands.SAVE);
        assertEquals("View", Constants.Commands.VIEW);
        assertEquals("test.xml", Constants.Commands.TESTFILE);
        assertEquals("dump.xml", Constants.Commands.SAVEFILE);
    }

    @Test
    void testXMLConstants() {
        assertEquals("showtitle", Constants.XML.SHOWTITLE);
        assertEquals("title", Constants.XML.SLIDETITLE);
        assertEquals("slide", Constants.XML.SLIDE);
        assertEquals("item", Constants.XML.ITEM);
        assertEquals("level", Constants.XML.LEVEL);
        assertEquals("kind", Constants.XML.KIND);
        assertEquals("text", Constants.XML.TEXT);
        assertEquals("image", Constants.XML.IMAGE);
        assertEquals("dom", Constants.XML.DEFAULT_API_TO_USE);
    }

    @Test
    void testErrorMessageConstants() {
        assertEquals("Parser Configuration Exception", Constants.ErrorMessages.PCE);
        assertEquals("Unknown Element type", Constants.ErrorMessages.UNKNOWNTYPE);
        assertEquals("Number Format Exception", Constants.ErrorMessages.NFE);
        assertEquals("IO Error: ", Constants.ErrorMessages.IOERR);
        assertEquals("Jabberpoint Error ", Constants.ErrorMessages.JABERR);
        assertEquals("File ", Constants.ErrorMessages.FILE);
        assertEquals(" not found", Constants.ErrorMessages.NOTFOUND);
    }

    @Test
    void testFileConstants() {
        assertEquals("Demonstration presentation", Constants.Files.DEMO_NAME);
        assertEquals(".xml", Constants.Files.DEFAULT_EXTENSION);
    }

    @Test
    void testTextItemConstants() {
        assertEquals("No Text Given", Constants.TextItems.EMPTYTEXT);
    }

    @Test
    void testInfoConstants() {
        assertEquals("Jabberpoint 1.7 - Updated Architecture", Constants.Info.JABVERSION);
    }

    @Test
    void testConstantsAreNotNull() {
        assertNotNull(Constants.UI.BGCOLOR);
        assertNotNull(Constants.UI.COLOR);
        assertNotNull(Constants.UI.FONTNAME);
        assertNotNull(Constants.UI.STYLE_FONTNAME);
        assertNotNull(Constants.UI.JABTITLE);
        assertNotNull(Constants.Commands.PAGENR);
        assertNotNull(Constants.XML.DEFAULT_API_TO_USE);
        assertNotNull(Constants.Files.DEMO_NAME);
        assertNotNull(Constants.TextItems.EMPTYTEXT);
        assertNotNull(Constants.Info.JABVERSION);
    }
} 