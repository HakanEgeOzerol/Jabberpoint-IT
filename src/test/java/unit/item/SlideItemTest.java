package unit.item;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

import jabberpoint.presentation.Style;
import jabberpoint.slideitem.SlideItem;

public class SlideItemTest {
    
    // Concrete implementation of SlideItem for testing
    private class TestSlideItem extends SlideItem {
        public TestSlideItem(int level) {
            super(level);
        }
        
        public TestSlideItem() {
            super();
        }
        
        @Override
        public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style style) {
            return new Rectangle(0, 0, 100, 100);
        }
        
        @Override
        public void draw(int x, int y, float scale, Graphics g, Style style, ImageObserver observer) {
            // Do nothing, just for testing
        }
    }
    
    @Test
    public void testConstructorWithLevel() {
        SlideItem item = new TestSlideItem(5);
        assertEquals(5, item.getLevel());
    }
    
    @Test
    public void testDefaultConstructor() {
        SlideItem item = new TestSlideItem();
        assertEquals(0, item.getLevel());
    }
    
    @Test
    public void testGetLevel() {
        SlideItem item = new TestSlideItem(3);
        assertEquals(3, item.getLevel());
        
        SlideItem defaultItem = new TestSlideItem();
        assertEquals(0, defaultItem.getLevel());
    }
} 