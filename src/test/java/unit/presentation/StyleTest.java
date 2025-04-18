package unit.presentation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jabberpoint.presentation.Style;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;
import java.util.List;

class StyleTest {
    private static final List<String> VALID_FONTS = Arrays.asList("Helvetica", "Arial", "SansSerif");
    
    @BeforeEach
    void setUp() {
        // Reset styles before each test
        Style.createStyles();
    }
    
    @Test
    void testStyleCreation() {
        Style style = new Style(10, Color.RED, 12, 5);
        
        assertEquals(10, style.getIndent());
        assertEquals(Color.RED, style.getColor());
        assertEquals(12, style.getFontSize());
        assertEquals(5, style.getLeading());
        assertTrue(VALID_FONTS.contains(style.getFont().getFamily()), 
            "Font should be one of: " + VALID_FONTS);
        assertEquals(Font.BOLD, style.getFont().getStyle());
    }
    
    @Test
    void testCreateStyles() {
        // Test all default styles
        Style[] styles = new Style[5];
        for (int i = 0; i < 5; i++) {
            styles[i] = Style.getStyle(i);
            assertNotNull(styles[i], "Style " + i + " should not be null");
        }
        
        // Test specific style properties
        assertAll("Default styles properties",
            // Style 0 (title)
            () -> assertEquals(0, styles[0].getIndent()),
            () -> assertEquals(Color.red, styles[0].getColor()),
            () -> assertEquals(48, styles[0].getFontSize()),
            () -> assertEquals(20, styles[0].getLeading()),
            
            // Style 1
            () -> assertEquals(20, styles[1].getIndent()),
            () -> assertEquals(Color.blue, styles[1].getColor()),
            () -> assertEquals(40, styles[1].getFontSize()),
            () -> assertEquals(10, styles[1].getLeading()),
            
            // Style 2
            () -> assertEquals(50, styles[2].getIndent()),
            () -> assertEquals(Color.black, styles[2].getColor()),
            () -> assertEquals(36, styles[2].getFontSize()),
            () -> assertEquals(10, styles[2].getLeading()),
            
            // Remaining styles indents
            () -> assertEquals(70, styles[3].getIndent()),
            () -> assertEquals(90, styles[4].getIndent())
        );
    }
    
    @Test
    void testGetStyleWithTooHighLevel() {
        // When requesting a style beyond the available styles,
        // should return the last available style
        Style lastStyle = Style.getStyle(4);
        Style tooHighStyle = Style.getStyle(10);
        
        assertSame(lastStyle, tooHighStyle);
    }
    
    @Test
    void testGetFontWithDifferentScales() {
        Style style = Style.getStyle(0);
        
        // Test with scale > 1
        float scale = 2.0f;
        Font scaledFont = style.getFont(scale);
        assertEquals(Math.round(48 * scale), scaledFont.getSize());
        assertTrue(VALID_FONTS.contains(scaledFont.getFamily()),
            "Font should be one of: " + VALID_FONTS);
        assertEquals(Font.BOLD, scaledFont.getStyle());
        
        // Test with scale < 1
        scale = 0.5f;
        scaledFont = style.getFont(scale);
        assertEquals(Math.round(48 * scale), scaledFont.getSize());
    }
    
    @Test
    void testToString() {
        Style style = new Style(10, Color.RED, 12, 5);
        String expected = "[10," + Color.RED + "; 12 on 5]";
        assertEquals(expected, style.toString());
    }
} 