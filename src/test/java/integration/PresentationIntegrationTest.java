package integration;

import jabberpoint.Presentation;
import jabberpoint.Slide;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PresentationIntegrationTest {
    
    @Test
    public void testPresentationNavigation() {
        Presentation presentation = new Presentation();
        
        // Add test slides
        Slide slide1 = new Slide();
        slide1.setTitle("Test Slide 1");
        slide1.append(1, "Test Content 1");
        
        Slide slide2 = new Slide();
        slide2.setTitle("Test Slide 2");
        slide2.append(1, "Test Content 2");
        
        presentation.append(slide1);
        presentation.append(slide2);
        
        // Test navigation
        assertEquals(-1, presentation.getSlideNumber()); // Initial state is -1
        presentation.setSlideNumber(0);
        assertEquals(0, presentation.getSlideNumber());
        presentation.nextSlide();
        assertEquals(1, presentation.getSlideNumber());
        presentation.prevSlide();
        assertEquals(0, presentation.getSlideNumber());
    }
} 