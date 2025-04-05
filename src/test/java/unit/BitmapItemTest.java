package unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import jabberpoint.BitmapItem;
import jabberpoint.Style;

public class BitmapItemTest {
    private BitmapItem bitmapItem;
    private Style style;
    private Graphics graphics;
    private ImageObserver observer;
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalErr = System.err;

    @BeforeEach
    public void setUp() {
        // Set up error stream capture
        System.setErr(new PrintStream(errContent));
        
        // Create test resources
        Style.createStyles();
        style = Style.getStyle(1);
        
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
        
        // Create a BitmapItem with a test image
        bitmapItem = new BitmapItem(1, "test.jpg");
    }

    @Test
    public void testConstructorWithValidImage() {
        BitmapItem item = new BitmapItem(2, "test.jpg");
        assertEquals(2, item.getLevel());
        assertEquals("test.jpg", item.getName());
    }

    @Test
    public void testConstructorWithInvalidImage() {
        errContent.reset();
        BitmapItem item = new BitmapItem(1, "nonexistent.jpg");
        
        assertTrue(errContent.toString().contains("File nonexistent.jpg not found"));
        assertEquals(1, item.getLevel());
        assertEquals("nonexistent.jpg", item.getName());
    }

    @Test
    public void testDefaultConstructor() {
        BitmapItem item = new BitmapItem();
        assertEquals(0, item.getLevel());
        assertNull(item.getName());
    }

    @Test
    public void testGetName() {
        assertEquals("test.jpg", bitmapItem.getName());
        
        BitmapItem nullItem = new BitmapItem(1, null);
        assertNull(nullItem.getName());
    }

    @Test
    public void testGetBoundingBox() {
        float scale = 1.0f;
        Rectangle bounds = bitmapItem.getBoundingBox(graphics, observer, scale, style);
        
        assertNotNull(bounds);
        assertEquals((int)(style.indent * scale), bounds.x);
        assertEquals(0, bounds.y);
        assertTrue(bounds.width >= 0);
        assertTrue(bounds.height >= 0);
    }

    @Test
    public void testDraw() {
        // This mainly tests that no exception is thrown
        assertDoesNotThrow(() -> {
            bitmapItem.draw(10, 10, 1.0f, graphics, style, observer);
        });
    }

    @Test
    public void testToString() {
        assertEquals("BitmapItem[1,test.jpg]", bitmapItem.toString());
        
        BitmapItem nullItem = new BitmapItem(2, null);
        assertEquals("BitmapItem[2,null]", nullItem.toString());
    }
} 