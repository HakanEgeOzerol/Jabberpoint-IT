package unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;

import jabberpoint.BitmapItem;
import jabberpoint.Style;
import jabberpoint.Constants;

public class BitmapItemTest {
    private BitmapItem bitmapItem;
    private Style style;
    private Graphics graphics;
    private ImageObserver observer;
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalErr = System.err;

    @BeforeEach
    public void setUp() {
        System.setErr(new PrintStream(errContent));
        bitmapItem = new BitmapItem(1, "test.jpg");
        style = mock(Style.class);
        graphics = mock(Graphics.class);
        observer = mock(ImageObserver.class);
        
        // Set up common mock behavior
        when(style.getIndent()).thenReturn(10);
        when(style.getLeading()).thenReturn(5);
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
        assertEquals((int)(style.getIndent() * scale), bounds.x);
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

    @Test
    public void testLoadImageWithInvalidPath() {
        errContent.reset();
        BitmapItem item = new BitmapItem(1, "images/nonexistent.jpg");
        
        assertTrue(errContent.toString().contains("File images/nonexistent.jpg not found"));
        assertNull(item.getName());
    }

    @Test
    public void testGetBoundingBoxWithNullImage() {
        BitmapItem item = new BitmapItem(1, "nonexistent.jpg"); // This will have null bufferedImage
        Rectangle bounds = item.getBoundingBox(graphics, observer, 1.0f, style);
        
        assertNotNull(bounds);
        assertEquals((int)(style.getIndent()), bounds.x);
        assertEquals(0, bounds.y);
        assertEquals(0, bounds.width);
        assertEquals(0, bounds.height);
    }

    @Test
    public void testGetBoundingBoxWithDifferentScales() {
        float scale = 2.0f;
        Rectangle bounds = bitmapItem.getBoundingBox(graphics, observer, scale, style);
        
        assertNotNull(bounds);
        assertEquals((int)(style.getIndent() * scale), bounds.x);
        assertEquals(0, bounds.y);
        assertTrue(bounds.width >= 0);
        assertTrue(bounds.height >= 0);
        
        // Test with very small scale
        scale = 0.1f;
        Rectangle smallBounds = bitmapItem.getBoundingBox(graphics, observer, scale, style);
        assertTrue(smallBounds.width < bounds.width);
        assertTrue(smallBounds.height < bounds.height);
    }

    @Test
    public void testDrawWithNullImage() {
        BitmapItem item = new BitmapItem(1, "nonexistent.jpg"); // This will have null bufferedImage
        
        // Should not throw exception when drawing null image
        assertDoesNotThrow(() -> {
            item.draw(10, 10, 1.0f, graphics, style, observer);
        });
    }

    @Test
    public void testDrawWithDifferentScales() {
        // Should not throw exception with different scales
        assertDoesNotThrow(() -> {
            bitmapItem.draw(10, 10, 2.0f, graphics, style, observer);
            bitmapItem.draw(10, 10, 0.5f, graphics, style, observer);
            bitmapItem.draw(10, 10, 0.0f, graphics, style, observer);
        });
    }

    @Test
    public void testDrawWithDifferentPositions() {
        // Should not throw exception with different positions
        assertDoesNotThrow(() -> {
            bitmapItem.draw(0, 0, 1.0f, graphics, style, observer);
            bitmapItem.draw(-10, -10, 1.0f, graphics, style, observer);
            bitmapItem.draw(1000, 1000, 1.0f, graphics, style, observer);
        });
    }

    @Test
    public void testDrawWithValidImage() {
        // Create a mock Graphics and ImageObserver
        Graphics mockGraphics = mock(Graphics.class);
        ImageObserver mockObserver = mock(ImageObserver.class);
        Style mockStyle = mock(Style.class);
        
        // Set up the mock style
        when(mockStyle.getIndent()).thenReturn(10);
        when(mockStyle.getLeading()).thenReturn(5);
        
        // Create a BitmapItem with a test image
        BitmapItem item = new BitmapItem(1, "test.jpg");
        BufferedImage testImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        setBufferedImage(item, testImage);
        
        // Test drawing with different scale factors
        item.draw(20, 30, 1.0f, mockGraphics, mockStyle, mockObserver);
        verify(mockGraphics).drawImage(eq(testImage), anyInt(), anyInt(), anyInt(), anyInt(), eq(mockObserver));
        
        // Test with larger scale
        item.draw(20, 30, 2.0f, mockGraphics, mockStyle, mockObserver);
        verify(mockGraphics, times(2)).drawImage(eq(testImage), anyInt(), anyInt(), anyInt(), anyInt(), eq(mockObserver));
    }

    @Test
    public void testGetBoundingBoxWithValidImage() {
        // Create a mock Graphics and ImageObserver
        Graphics mockGraphics = mock(Graphics.class);
        ImageObserver mockObserver = mock(ImageObserver.class);
        Style mockStyle = mock(Style.class);
        
        // Set up the mock style
        when(mockStyle.getIndent()).thenReturn(10);
        when(mockStyle.getLeading()).thenReturn(5);
        
        // Create a BitmapItem with a test image
        BitmapItem item = new BitmapItem(1, "test.jpg");
        BufferedImage testImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        setBufferedImage(item, testImage);
        
        // Test with different scale factors
        Rectangle bounds1 = item.getBoundingBox(mockGraphics, mockObserver, 1.0f, mockStyle);
        assertEquals(10, bounds1.x);
        assertEquals(0, bounds1.y);
        assertEquals(100, bounds1.width);
        assertEquals(100, bounds1.height);
        
        Rectangle bounds2 = item.getBoundingBox(mockGraphics, mockObserver, 2.0f, mockStyle);
        assertEquals(20, bounds2.x);
        assertEquals(0, bounds2.y);
        assertEquals(200, bounds2.width);
        assertEquals(200, bounds2.height);
    }

    // Helper method to set the buffered image directly for testing
    private void setBufferedImage(BitmapItem item, BufferedImage image) {
        try {
            Field field = BitmapItem.class.getDeclaredField("bufferedImage");
            field.setAccessible(true);
            field.set(item, image);
        } catch (Exception e) {
            fail("Failed to set buffered image: " + e.getMessage());
        }
    }
} 