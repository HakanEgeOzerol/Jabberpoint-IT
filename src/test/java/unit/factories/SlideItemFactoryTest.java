package unit.factories;

import org.junit.jupiter.api.Test;

import jabberpoint.slideitem.BitmapItemFactory;
import jabberpoint.slideitem.SlideItemFactory;
import jabberpoint.slideitem.TextItemFactory;
import unit.*;

import static org.junit.jupiter.api.Assertions.*;

public class SlideItemFactoryTest {
    @Test
    void testFactoryInterface() {
        // Test that both concrete factories implement the interface correctly
        SlideItemFactory textFactory = new TextItemFactory();
        SlideItemFactory bitmapFactory = new BitmapItemFactory();
        
        assertNotNull(textFactory);
        assertNotNull(bitmapFactory);
    }
}