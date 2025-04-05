package acceptance;

import jabberpoint.accessor.DemoPresentation;
import jabberpoint.presentation.Presentation;
import jabberpoint.presentation.Slide;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JabberPointAcceptanceTest {
    
    @Test
    public void testDemoPresentationLoading() {
        Presentation presentation = new Presentation();
        DemoPresentation demo = new DemoPresentation();
        
        // Load demo presentation
        demo.loadFile(presentation, "");
        
        // Verify demo content
        assertEquals("Demo Presentation", presentation.getTitle());
        assertTrue(presentation.getSize() > 0);
        
        // Test first slide
        Slide firstSlide = presentation.getSlide(0);
        assertEquals("JabberPoint", firstSlide.getTitle());
    }
} 