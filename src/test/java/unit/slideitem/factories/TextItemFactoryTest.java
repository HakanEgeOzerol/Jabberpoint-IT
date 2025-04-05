package unit.slideitem.factories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jabberpoint.slideitem.SlideItem;
import jabberpoint.slideitem.TextItem;
import jabberpoint.slideitem.TextItemFactory;

import static org.junit.jupiter.api.Assertions.*;

public class TextItemFactoryTest {
    private TextItemFactory factory;

    @BeforeEach
    void setUp() {
        factory = new TextItemFactory();
    }

    @Test
    void testCreateTextItem() {
        SlideItem item = factory.createSlideItem("text", 1, "Test Content");
        assertTrue(item instanceof TextItem);
        assertEquals(1, item.getLevel());
        assertEquals("Test Content", ((TextItem)item).getText());
    }

    @Test
    void testCreateWithNullContent() {
        SlideItem item = factory.createSlideItem("text", 1, null);
        assertEquals("", ((TextItem)item).getText());
    }

    @Test
    void testInvalidType() {
        assertThrows(IllegalArgumentException.class, () -> {
            factory.createSlideItem("image", 1, "Test Content");
        });
    }

    @Test
    void testVoidCreateMethod() {
        assertThrows(IllegalArgumentException.class, () -> {
            factory.createSlideItem(-1, "Test");
        });
        
        // Valid case shouldn't throw
        assertDoesNotThrow(() -> {
            factory.createSlideItem(1, "Test");
        });
    }
}