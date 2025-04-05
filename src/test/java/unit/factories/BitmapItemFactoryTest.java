package unit.factories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jabberpoint.slideitem.BitmapItem;
import jabberpoint.slideitem.BitmapItemFactory;
import jabberpoint.slideitem.SlideItem;
import unit.*;

import static org.junit.jupiter.api.Assertions.*;

public class BitmapItemFactoryTest {
    private BitmapItemFactory factory;

    @BeforeEach
    void setUp() {
        factory = new BitmapItemFactory();
    }

    @Test
    void testCreateBitmapItem() {
        SlideItem item = factory.createSlideItem("image", 2, "test.jpg");
        assertTrue(item instanceof BitmapItem);
        assertEquals(2, item.getLevel());
        assertEquals("test.jpg", ((BitmapItem)item).getName());
    }

    @Test
    void testInvalidType() {
        assertThrows(IllegalArgumentException.class, () -> {
            factory.createSlideItem("text", 1, "test.jpg");
        });
    }

    @Test
    void testNullOrEmptyPath() {
        assertThrows(IllegalArgumentException.class, () -> {
            factory.createSlideItem("image", 1, null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            factory.createSlideItem("image", 1, "");
        });
    }

    @Test
    void testVoidCreateMethod() {
        assertThrows(IllegalArgumentException.class, () -> {
            factory.createSlideItem(1, "");
        });

        // Valid case shouldn't throw
        assertDoesNotThrow(() -> {
            factory.createSlideItem(1, "test.jpg");
        });
    }
} 