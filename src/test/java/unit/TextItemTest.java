package unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jabberpoint.presentation.Style;
import jabberpoint.slideitem.TextItem;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.text.AttributedString;

public class TextItemTest {
    private TextItem textItem;
    private Style style;
    private Graphics graphics;
    private ImageObserver observer;
    
    @BeforeEach
    public void setUp() {
        Style.createStyles();
        style = Style.getStyle(1);
        textItem = new TextItem(1, "Test Text");
        
        // Create a graphics context for testing
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        graphics = image.getGraphics();
        
        // Create a dummy image observer
        observer = new ImageObserver() {
            @Override
            public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                return false;
            }
        };
    }
    
    @Test
    public void testConstructorWithLevelAndText() {
        TextItem item = new TextItem(2, "Sample Text");
        assertEquals(2, item.getLevel());
        assertEquals("Sample Text", item.getText());
    }
    
    @Test
    public void testDefaultConstructor() {
        TextItem item = new TextItem();
        assertEquals(0, item.getLevel());
        assertEquals("No Text Given", item.getText());
    }
    
    @Test
    public void testGetText() {
        assertEquals("Test Text", textItem.getText());
        
        // Test with null text
        TextItem nullItem = new TextItem(1, null);
        assertEquals("", nullItem.getText());
    }
    
    @Test
    public void testGetAttributedString() {
        float scale = 1.0f;
        AttributedString attrStr = textItem.getAttributedString(style, scale);
        
        // Verify that the font attribute is set
        assertNotNull(attrStr);
        assertEquals(style.getFont(scale), 
            attrStr.getIterator().getAttributes().get(TextAttribute.FONT));
    }
    
    @Test
    public void testGetBoundingBox() {
        float scale = 1.0f;
        Rectangle bounds = textItem.getBoundingBox(graphics, observer, scale, style);
        
        assertNotNull(bounds);
        assertTrue(bounds.width > 0);
        assertTrue(bounds.height > 0);
        assertEquals((int)(style.getIndent() * scale), bounds.x);
        assertEquals(0, bounds.y);
    }
    
    @Test
    public void testDrawWithValidText() {
        // This mainly tests that no exception is thrown
        assertDoesNotThrow(() -> {
            textItem.draw(10, 10, 1.0f, graphics, style, observer);
        });
    }
    
    @Test
    public void testDrawWithNullText() {
        TextItem nullItem = new TextItem(1, null);
        // Should not throw exception and should return without drawing
        assertDoesNotThrow(() -> {
            nullItem.draw(10, 10, 1.0f, graphics, style, observer);
        });
    }
    
    @Test
    public void testDrawWithEmptyText() {
        TextItem emptyItem = new TextItem(1, "");
        // Should not throw exception and should return without drawing
        assertDoesNotThrow(() -> {
            emptyItem.draw(10, 10, 1.0f, graphics, style, observer);
        });
    }
    
    @Test
    public void testToString() {
        assertEquals("TextItem[1,Test Text]", textItem.toString());
        
        TextItem nullItem = new TextItem(2, null);
        assertEquals("TextItem[2,]", nullItem.toString());
    }
} 