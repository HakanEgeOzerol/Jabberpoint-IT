package unit.slideitem.factories;

import org.junit.jupiter.api.Test;

import jabberpoint.slideitem.BitmapItemFactory;
import jabberpoint.slideitem.SlideItemFactory;
import jabberpoint.slideitem.ISlideItemFactory;
import jabberpoint.slideitem.TextItemFactory;

import static org.junit.jupiter.api.Assertions.*;

public class SlideItemFactoryTest {
    @Test
    void testFactoryInterface() {
        // Test that both concrete factories implement the SpecificSlideItemFactory interface correctly
        ISlideItemFactory textFactory = new TextItemFactory();
        ISlideItemFactory bitmapFactory = new BitmapItemFactory();
        
        assertNotNull(textFactory);
        assertNotNull(bitmapFactory);
    }
    
    @Test
    void testMainSlideItemFactory() {
        // Test that the main SlideItemFactory works correctly
        SlideItemFactory factory = new SlideItemFactory();
        
        assertNotNull(factory);
        
        // Test creating text items
        assertNotNull(factory.createSlideItem("text", 1, "Test text"));
        
        // Test creating image items
        assertNotNull(factory.createSlideItem("image", 1, "test.jpg"));
        
        // Test invalid type
        assertThrows(IllegalArgumentException.class, () -> {
            factory.createSlideItem("invalid", 1, "content");
        });
    }
}