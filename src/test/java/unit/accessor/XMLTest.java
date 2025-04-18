package unit.accessor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;

import jabberpoint.accessor.XMLAccessor;
import jabberpoint.accessor.XMLReader;
import jabberpoint.accessor.XMLWriter;
import jabberpoint.presentation.Presentation;
import jabberpoint.presentation.Slide;
import jabberpoint.slideitem.BitmapItem;
import jabberpoint.slideitem.BitmapItemFactory;
import jabberpoint.slideitem.SlideItem;
import jabberpoint.slideitem.TextItem;
import jabberpoint.slideitem.TextItemFactory;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Vector;

public class XMLTest {
    private Presentation presentation;
    private XMLReader reader;
    private XMLWriter writer;
    private String testImagePath;
    
    @BeforeEach
    void setUp() {
        presentation = new Presentation();
        reader = new XMLReader();
        writer = new XMLWriter();
        
        // Set up the test image path once
        URL resourceUrl = getClass().getClassLoader().getResource("test.jpg");
        if (resourceUrl != null) {
            testImagePath = new File(resourceUrl.getFile()).getAbsolutePath();
        } else {
            testImagePath = "test.jpg"; // Fallback to relative path
        }
    }

    @Test
    void testWriteAndReadPresentation(@TempDir Path tempDir) throws IOException {
        // Create a test presentation
        presentation.setTitle("Test Presentation");
        
        // Create a slide with different types of items
        Slide slide = new Slide();
        slide.setTitle("Test Slide");
        slide.append(new TextItem(1, "Test Text Item"));
        
        // Use the preconfigured test image path
        slide.append(new BitmapItem(2, testImagePath));
        presentation.append(slide);
        
        // Write to temporary file
        File tempFile = tempDir.resolve("test.xml").toFile();
        writer.saveFile(presentation, tempFile.getPath());
        
        // Read back the presentation
        Presentation readPresentation = new Presentation();
        reader.loadFile(readPresentation, tempFile.getPath());
        
        // Verify the contents
        assertEquals("Test Presentation", readPresentation.getTitle(), "Presentation title should match");
        assertEquals(1, readPresentation.getSize(), "Should have one slide");
        
        Slide readSlide = readPresentation.getSlide(0);
        assertEquals("Test Slide", readSlide.getTitle(), "Slide title should match");
        
        Vector<SlideItem> items = readSlide.getSlideItems();
        assertEquals(2, items.size(), "Should have two items");
        
        // Verify first item (TextItem)
        assertTrue(items.get(0) instanceof TextItem, "First item should be TextItem");
        TextItem textItem = (TextItem) items.get(0);
        assertEquals(1, textItem.getLevel(), "Text item level should be 1");
        assertEquals("Test Text Item", textItem.getText(), "Text content should match");
        
        // Verify second item (BitmapItem) - only check that it's a BitmapItem and the level is correct
        assertTrue(items.get(1) instanceof BitmapItem, "Second item should be BitmapItem");
        BitmapItem bitmapItem = (BitmapItem) items.get(1);
        assertEquals(2, bitmapItem.getLevel(), "Bitmap item level should be 2");
        // Check that the name contains test.jpg but don't check for full path equality
        assertTrue(bitmapItem.getName() != null && bitmapItem.getName().contains("test.jpg"), 
                "Image name should contain test.jpg");
    }

    @Test
    void testFactories() {
        TextItemFactory textFactory = new TextItemFactory();
        BitmapItemFactory bitmapFactory = new BitmapItemFactory();
        
        // Test text item creation
        SlideItem textItem = textFactory.createSlideItem("text", 1, "Test Content");
        assertTrue(textItem instanceof TextItem);
        assertEquals(1, textItem.getLevel());
        assertEquals("Test Content", ((TextItem)textItem).getText());
        
        // Use a special factory method that doesn't actually load the image
        SlideItem bitmapItem = bitmapFactory.createSlideItem("image", 2, testImagePath);
        assertTrue(bitmapItem instanceof BitmapItem);
        assertEquals(2, bitmapItem.getLevel());
        assertEquals(testImagePath, ((BitmapItem)bitmapItem).getName());
    }

    @Test
    void testInvalidXML(@TempDir Path tempDir) {
        File invalidFile = tempDir.resolve("invalid.xml").toFile();
        Presentation emptyPresentation = new Presentation();
        
        // Test reading non-existent file
        assertThrows(IOException.class, () -> {
            reader.loadFile(emptyPresentation, invalidFile.getPath());
        });
    }

    @Test
    void testXMLAccessor(@TempDir Path tempDir) throws IOException {
        XMLAccessor accessor = new XMLAccessor();
        
        // Create test presentation
        presentation.setTitle("Accessor Test");
        Slide slide = new Slide();
        slide.setTitle("Accessor Slide");
        slide.append(new TextItem(1, "Accessor Text"));
        presentation.append(slide);
        
        // Test save and load using accessor
        File tempFile = tempDir.resolve("accessor-test.xml").toFile();
        accessor.saveFile(presentation, tempFile.getPath());
        
        Presentation loadedPresentation = new Presentation();
        accessor.loadFile(loadedPresentation, tempFile.getPath());
        
        assertEquals("Accessor Test", loadedPresentation.getTitle());
        assertEquals(1, loadedPresentation.getSize());
        assertEquals("Accessor Slide", loadedPresentation.getSlide(0).getTitle());
    }
} 