package unit.slideitem.factories;

import org.junit.jupiter.api.Test;

import jabberpoint.slideitem.BitmapItemFactory;
import jabberpoint.slideitem.SlideItem;
import jabberpoint.slideitem.SlideItemFactory;
import jabberpoint.slideitem.ISlideItemFactory;
import jabberpoint.slideitem.TextItem;
import jabberpoint.slideitem.TextItemFactory;

import static org.junit.jupiter.api.Assertions.*;

public class SlideItemFactoryTest {
    
    // Simple mock factory for testing registration
    private static class MockSlideItemFactory implements ISlideItemFactory {
        @Override
        public SlideItem createSlideItem(String type, int level, String content) {
            return new TextItem(level, content != null ? content : "Mock item");
        }
        
        @Override
        public void createSlideItem(int level, String content) {
        }
    }
    
    @Test
    void testFactoryInterface() {
        ISlideItemFactory textFactory = new TextItemFactory();
        ISlideItemFactory bitmapFactory = new BitmapItemFactory();
        
        assertNotNull(textFactory);
        assertNotNull(bitmapFactory);
    }
    
    @Test
    void testMainSlideItemFactory() {
        SlideItemFactory factory = new SlideItemFactory();
        
        assertNotNull(factory);
        
        SlideItem textItem = factory.createSlideItem("text", 1, "Test text");
        assertNotNull(textItem);
        
        SlideItem imageItem = factory.createSlideItem("image", 1, "test.jpg");
        assertNotNull(imageItem);
        
        assertThrows(IllegalArgumentException.class, () -> {
            factory.createSlideItem("invalid", 1, "content");
        });
    }
    
    @Test
    void testFactoryRegistration() {
        SlideItemFactory factory = new SlideItemFactory();
        
        ISlideItemFactory customFactory = new MockSlideItemFactory();
        factory.registerFactory("custom", customFactory);
        
        assertDoesNotThrow(() -> {
            SlideItem item = factory.createSlideItem("custom", 1, "Custom content");
            assertNotNull(item);
        });
    }
    
    @Test
    void testDefaultTextCreation() {
        SlideItemFactory factory = new SlideItemFactory();
        
        SlideItem defaultItem = factory.createSlideItem(1, "Default text");
        assertNotNull(defaultItem);
    }
    
    @Test
    void testNullInputs() {
        SlideItemFactory factory = new SlideItemFactory();
        
        assertThrows(IllegalArgumentException.class, () -> {
            factory.createSlideItem(null, 1, "content");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            factory.registerFactory(null, new TextItemFactory());
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            factory.registerFactory("test", null);
        });
    }
}