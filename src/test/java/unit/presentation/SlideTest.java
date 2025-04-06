package unit.presentation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Vector;

import jabberpoint.constants.Constants;
import jabberpoint.presentation.Slide;
import jabberpoint.presentation.Style;
import jabberpoint.slideitem.SlideItem;
import jabberpoint.slideitem.TextItem;

public class SlideTest {
    private Slide slide;
    private Graphics graphics;
    private ImageObserver observer;
    private Rectangle area;
    
    @BeforeAll
    public static void setUpHeadlessMode() {
        // Ensure we're running in headless mode for GitHub Actions
        System.setProperty("java.awt.headless", "true");
    }

    @BeforeEach
    public void setUp() {
        slide = new Slide();
        
        try {
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
            
            // Create a test area
            area = new Rectangle(0, 0, Constants.UI.WIDTH, Constants.UI.HEIGHT);
            
            // Initialize styles
            Style.createStyles();
        } catch (Exception e) {
            // In case we're in a headless environment where graphics can't be created
            graphics = null;
            System.err.println("Warning: Running in headless mode, graphics operations will be skipped: " + e.getMessage());
        }
    }

    @Test
    public void testConstructor() {
        assertNotNull(slide.getSlideItems());
        assertEquals(0, slide.getSize());
        assertNull(slide.getTitle());
    }

    @Test
    public void testAppendSlideItem() {
        SlideItem item = new TextItem(1, "Test Item");
        slide.append(item);
        
        assertEquals(1, slide.getSize());
        assertSame(item, slide.getSlideItem(0));
    }

    @Test
    public void testAppendTextDirectly() {
        slide.append(1, "Test Message");
        
        assertEquals(1, slide.getSize());
        SlideItem item = slide.getSlideItem(0);
        assertTrue(item instanceof TextItem);
        assertEquals("Test Message", ((TextItem)item).getText());
        assertEquals(1, item.getLevel());
    }

    @Test
    public void testGetAndSetTitle() {
        assertNull(slide.getTitle());
        
        slide.setTitle("Test Title");
        assertEquals("Test Title", slide.getTitle());
    }

    @Test
    public void testGetSlideItem() {
        SlideItem item1 = new TextItem(1, "Item 1");
        SlideItem item2 = new TextItem(2, "Item 2");
        
        slide.append(item1);
        slide.append(item2);
        
        assertSame(item1, slide.getSlideItem(0));
        assertSame(item2, slide.getSlideItem(1));
    }

    @Test
    public void testGetSlideItems() {
        SlideItem item1 = new TextItem(1, "Item 1");
        SlideItem item2 = new TextItem(2, "Item 2");
        
        slide.append(item1);
        slide.append(item2);
        
        Vector<SlideItem> items = slide.getSlideItems();
        assertEquals(2, items.size());
        assertSame(item1, items.elementAt(0));
        assertSame(item2, items.elementAt(1));
    }

    @Test
    public void testGetSize() {
        assertEquals(0, slide.getSize());
        
        slide.append(new TextItem(1, "Item 1"));
        assertEquals(1, slide.getSize());
        
        slide.append(new TextItem(2, "Item 2"));
        assertEquals(2, slide.getSize());
    }

    @Test
    public void testDraw() {
        if (graphics == null) {
            return; // Skip in headless environment
        }
        
        slide.setTitle("Test Title");
        slide.append(new TextItem(1, "Test Item"));
        
        // This mainly tests that no exception is thrown
        assertDoesNotThrow(() -> {
            slide.draw(graphics, area, observer);
        });
    }

    @Test
    public void testDrawWithDifferentScales() {
        if (graphics == null) {
            return; // Skip in headless environment
        }
        
        slide.setTitle("Test Title");
        slide.append(new TextItem(1, "Test Item"));
        
        // Test with different area sizes
        Rectangle smallArea = new Rectangle(0, 0, Constants.UI.WIDTH / 2, Constants.UI.HEIGHT / 2);
        Rectangle wideArea = new Rectangle(0, 0, Constants.UI.WIDTH * 2, Constants.UI.HEIGHT);
        Rectangle tallArea = new Rectangle(0, 0, Constants.UI.WIDTH, Constants.UI.HEIGHT * 2);
        
        assertDoesNotThrow(() -> {
            slide.draw(graphics, smallArea, observer);
            slide.draw(graphics, wideArea, observer);
            slide.draw(graphics, tallArea, observer);
        });
    }
} 