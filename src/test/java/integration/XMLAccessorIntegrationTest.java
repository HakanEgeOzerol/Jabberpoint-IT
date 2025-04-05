package integration;

import jabberpoint.accessor.XMLAccessor;
import jabberpoint.presentation.Presentation;
import jabberpoint.presentation.Slide;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class XMLAccessorIntegrationTest {
    
    @Test
    public void testSaveAndLoadPresentation(@TempDir Path tempDir) throws IOException {
        Presentation presentation = new Presentation();
        presentation.setTitle("Test Presentation");
        
        Slide slide = new Slide();
        slide.setTitle("Test Slide");
        slide.append(1, "Test Content");
        presentation.append(slide);
        
        // Use the TempDir to create a file path that works in any environment
        String filename = tempDir.resolve("test.xml").toString();
        XMLAccessor accessor = new XMLAccessor();
        
        // Save presentation
        accessor.saveFile(presentation, filename);
        
        // Load presentation
        Presentation loadedPresentation = new Presentation();
        accessor.loadFile(loadedPresentation, filename);
        
        assertEquals("Test Presentation", loadedPresentation.getTitle());
        assertEquals(1, loadedPresentation.getSize());
        assertEquals("Test Slide", loadedPresentation.getSlide(0).getTitle());
    }
} 