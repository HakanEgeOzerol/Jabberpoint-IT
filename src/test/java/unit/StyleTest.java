package unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jabberpoint.presentation.Style;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
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
        // Test that all default styles are created
        Style style0 = Style.getStyle(0);
        Style style1 = Style.getStyle(1);
        Style style2 = Style.getStyle(2);
        Style style3 = Style.getStyle(3);
        Style style4 = Style.getStyle(4);
        
        // Test style 0 (title style)
        assertEquals(0, style0.getIndent());
        assertEquals(Color.red, style0.getColor());
        assertEquals(48, style0.getFontSize());
        assertEquals(20, style0.getLeading());
        
        // Test style 1
        assertEquals(20, style1.getIndent());
        assertEquals(Color.blue, style1.getColor());
        assertEquals(40, style1.getFontSize());
        assertEquals(10, style1.getLeading());
        
        // Test style 2
        assertEquals(50, style2.getIndent());
        assertEquals(Color.black, style2.getColor());
        assertEquals(36, style2.getFontSize());
        assertEquals(10, style2.getLeading());
        
        // Test remaining styles have expected values
        assertEquals(70, style3.getIndent());
        assertEquals(90, style4.getIndent());
    }
    
    @Test
    void testGetStyleWithValidLevels() {
        for (int i = 0; i < 5; i++) {
            Style style = Style.getStyle(i);
            assertNotNull(style);
        }
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