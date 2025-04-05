package unit.slideitem.factories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jabberpoint.slideitem.BitmapItem;
import jabberpoint.slideitem.BitmapItemFactory;
import jabberpoint.slideitem.SlideItem;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.net.URL;

public class BitmapItemFactoryTest {
    private BitmapItemFactory factory;
    private String testImagePath;

    @BeforeEach
    void setUp() {
        factory = new BitmapItemFactory();
        
        // Get the absolute path to the test.jpg file in the test resources directory
        URL resource = getClass().getClassLoader().getResource("test.jpg");
        if (resource != null) {
            testImagePath = new File(resource.getFile()).getAbsolutePath();
        } else {
            testImagePath = "test.jpg"; // Fallback to relative path
        }
    }

    @Test
    void testCreateBitmapItem() {
        SlideItem item = factory.createSlideItem("image", 2, testImagePath);
        assertTrue(item instanceof BitmapItem);
        assertEquals(2, item.getLevel());
        assertEquals(testImagePath, ((BitmapItem)item).getName());
    }

    @Test
    void testInvalidType() {
        assertThrows(IllegalArgumentException.class, () -> {
            factory.createSlideItem("text", 1, testImagePath);
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
            factory.createSlideItem(1, testImagePath);
        });
    }
} 