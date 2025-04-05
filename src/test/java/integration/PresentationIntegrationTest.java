package integration;

import jabberpoint.presentation.Presentation;
import jabberpoint.presentation.Slide;
import jabberpoint.slideitem.TextItem;

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
        assertEquals(-1, presentation.getSlideNumber()); // Initial state
        presentation.setSlideNumber(0);
        assertEquals(0, presentation.getSlideNumber());
        presentation.nextSlide();
        assertEquals(1, presentation.getSlideNumber());
        presentation.prevSlide();
        assertEquals(0, presentation.getSlideNumber());
        
        // Test slide content
        assertEquals("Test Slide 1", presentation.getCurrentSlide().getTitle());
        TextItem item = (TextItem) presentation.getCurrentSlide().getSlideItems().get(0);
        assertEquals("Test Content 1", item.getText());
    }
    
    @Test
    public void testPresentationClear() {
        Presentation presentation = new Presentation();
        Slide slide = new Slide();
        presentation.append(slide);
        
        assertEquals(1, presentation.getSize());
        presentation.clear();
        assertEquals(0, presentation.getSize());
        assertEquals(-1, presentation.getSlideNumber());
    }
    
    @Test
    public void testSlideManagement() {
        Presentation presentation = new Presentation();
        
        // Test empty presentation
        assertNull(presentation.getSlide(0));
        
        // Test slide addition and retrieval
        Slide slide = new Slide();
        slide.setTitle("Test");
        presentation.append(slide);
        
        assertEquals(1, presentation.getSize());
        assertNotNull(presentation.getSlide(0));
        assertEquals("Test", presentation.getSlide(0).getTitle());
    }
} 