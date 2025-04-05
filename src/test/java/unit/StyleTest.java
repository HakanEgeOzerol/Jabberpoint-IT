package unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jabberpoint.Style;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.awt.Font;

class StyleTest {
    
    @BeforeEach
    void setUp() {
        // Reset styles before each test
        Style.createStyles();
    }
    
    @Test
    void testStyleCreation() {
        Style style = new Style(10, Color.RED, 12, 5);
        
        assertEquals(10, style.indent);
        assertEquals(Color.RED, style.color);
        assertEquals(12, style.fontSize);
        assertEquals(5, style.leading);
        assertEquals("Helvetica", style.font.getFamily());
        assertEquals(Font.BOLD, style.font.getStyle());
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
        assertEquals(0, style0.indent);
        assertEquals(Color.red, style0.color);
        assertEquals(48, style0.fontSize);
        assertEquals(20, style0.leading);
        
        // Test style 1
        assertEquals(20, style1.indent);
        assertEquals(Color.blue, style1.color);
        assertEquals(40, style1.fontSize);
        assertEquals(10, style1.leading);
        
        // Test style 2
        assertEquals(50, style2.indent);
        assertEquals(Color.black, style2.color);
        assertEquals(36, style2.fontSize);
        assertEquals(10, style2.leading);
        
        // Test remaining styles have expected values
        assertEquals(70, style3.indent);
        assertEquals(90, style4.indent);
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
        assertEquals("Helvetica", scaledFont.getFamily());
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