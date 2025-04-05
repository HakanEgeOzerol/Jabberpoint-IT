package integration;

import jabberpoint.presentation.Slide;
import jabberpoint.slideitem.BitmapItem;
import jabberpoint.slideitem.TextItem;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.net.URL;

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
        
        // Get the absolute path to the test.jpg file in the test resources directory
        String testImagePath;
        URL resource = getClass().getClassLoader().getResource("test.jpg");
        if (resource != null) {
            testImagePath = new File(resource.getFile()).getAbsolutePath();
        } else {
            testImagePath = "test.jpg"; // Fallback to relative path
        }
        
        // Test bitmap items
        BitmapItem bitmapItem = new BitmapItem(3, testImagePath);
        slide.append(bitmapItem);
        
        assertEquals(3, slide.getSize());
        assertTrue(slide.getSlideItem(2) instanceof BitmapItem);
        assertEquals(3, slide.getSlideItem(2).getLevel());
    }
} 