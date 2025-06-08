package unit.accessor;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jabberpoint.accessor.DemoPresentation;
import jabberpoint.presentation.Presentation;
import jabberpoint.presentation.Slide;
import jabberpoint.slideitem.BitmapItem;
import jabberpoint.slideitem.SlideItem;
import jabberpoint.slideitem.TextItem;

import java.util.Vector;

class DemoPresentationTest {

    private DemoPresentation demoPresentation;
    private Presentation presentation;

    @BeforeEach
    void setUp() {
        demoPresentation = new DemoPresentation();
        presentation = new Presentation();
    }

    @Test
    void testLoadFile() {
        demoPresentation.loadFile(presentation, "unused");

        assertEquals("Demo Presentation", presentation.getTitle());
        assertEquals(3, presentation.getSize(), "Should have 3 slides");
    }

    @Test
    void testFirstSlide() {
        demoPresentation.loadFile(presentation, "unused");

        Slide firstSlide = presentation.getSlide(0);

        assertEquals("JabberPoint", firstSlide.getTitle());
        Vector<SlideItem> items = firstSlide.getSlideItems();
        assertTrue(items.size() > 0, "First slide should have items");
        
        boolean foundJavaToolText = false;
        for (SlideItem item : items) {
            if (item instanceof TextItem) {
                TextItem textItem = (TextItem) item;
                if (textItem.getText().contains("Java Presentation Tool")) {
                    foundJavaToolText = true;
                    assertEquals(1, textItem.getLevel());
                    break;
                }
            }
        }
        assertTrue(foundJavaToolText, "Should contain 'Java Presentation Tool' text");
    }

    @Test
    void testSecondSlide() {
        demoPresentation.loadFile(presentation, "unused");

        Slide secondSlide = presentation.getSlide(1);

        assertEquals("Demonstration of levels and stijlen", secondSlide.getTitle());
        Vector<SlideItem> items = secondSlide.getSlideItems();
        assertTrue(items.size() > 0, "Second slide should have items");
        
        boolean foundLevel1 = false, foundLevel2 = false, foundLevel3 = false, foundLevel4 = false;
        for (SlideItem item : items) {
            if (item instanceof TextItem) {
                TextItem textItem = (TextItem) item;
                switch (textItem.getLevel()) {
                    case 1: foundLevel1 = true; break;
                    case 2: foundLevel2 = true; break;
                    case 3: foundLevel3 = true; break;
                    case 4: foundLevel4 = true; break;
                }
            }
        }
        assertTrue(foundLevel1, "Should have level 1 items");
        assertTrue(foundLevel2, "Should have level 2 items");
        assertTrue(foundLevel3, "Should have level 3 items");
        assertTrue(foundLevel4, "Should have level 4 items");
    }

    @Test
    void testThirdSlide() {
        demoPresentation.loadFile(presentation, "unused");

        Slide thirdSlide = presentation.getSlide(2);

        assertEquals("The third slide", thirdSlide.getTitle());
        Vector<SlideItem> items = thirdSlide.getSlideItems();
        assertTrue(items.size() > 0, "Third slide should have items");
        
        boolean foundBitmapItem = false;
        for (SlideItem item : items) {
            if (item instanceof BitmapItem) {
                BitmapItem bitmapItem = (BitmapItem) item;
                assertEquals(1, bitmapItem.getLevel());
                assertTrue(bitmapItem.getName().contains("JabberPoint.jpg"));
                foundBitmapItem = true;
                break;
            }
        }
        assertTrue(foundBitmapItem, "Third slide should contain a bitmap item");
    }

    @Test
    void testLoadFileWithNullFilename() {
        assertDoesNotThrow(() -> demoPresentation.loadFile(presentation, null));
        assertEquals("Demo Presentation", presentation.getTitle());
    }

    @Test
    void testLoadFileWithEmptyFilename() {
        assertDoesNotThrow(() -> demoPresentation.loadFile(presentation, ""));
        assertEquals("Demo Presentation", presentation.getTitle());
    }

    @Test
    void testSaveFileThrowsException() {
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            demoPresentation.saveFile(presentation, "test.xml");
        });
        
        assertEquals("Save As->Demo! called", exception.getMessage());
    }

    @Test
    void testSaveFileWithNullFilename() {
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            demoPresentation.saveFile(presentation, null);
        });
        
        assertEquals("Save As->Demo! called", exception.getMessage());
    }

    @Test
    void testLoadFileAppendsToExistingContent() {
        Slide existingSlide = new Slide();
        existingSlide.setTitle("Existing Slide");
        presentation.append(existingSlide);
        presentation.setTitle("Existing Title");

        demoPresentation.loadFile(presentation, "unused");

        assertEquals("Demo Presentation", presentation.getTitle());
        assertEquals(4, presentation.getSize());
        assertEquals("Existing Slide", presentation.getSlide(0).getTitle());
        assertEquals("JabberPoint", presentation.getSlide(1).getTitle());
    }
} 