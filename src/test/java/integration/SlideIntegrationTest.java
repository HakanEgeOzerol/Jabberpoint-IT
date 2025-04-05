package integration;

import jabberpoint.presentation.Slide;
import jabberpoint.slideitem.BitmapItem;
import jabberpoint.slideitem.TextItem;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SlideIntegrationTest {
    
    @Test
    public void testSlideItemManagement() {
        Slide slide = new Slide();
        slide.setTitle("Test Slide");
        
        // Test text items
        slide.append(1, "Text Item 1");
        slide.append(2, "Text Item 2");
        
        assertEquals(2, slide.getSize());
        assertTrue(slide.getSlideItem(0) instanceof TextItem);
        assertEquals(1, slide.getSlideItem(0).getLevel());
        
        // Test bitmap items
        BitmapItem bitmapItem = new BitmapItem(3, "test.jpg");
        slide.append(bitmapItem);
        
        assertEquals(3, slide.getSize());
        assertTrue(slide.getSlideItem(2) instanceof BitmapItem);
        assertEquals(3, slide.getSlideItem(2).getLevel());
    }
} 