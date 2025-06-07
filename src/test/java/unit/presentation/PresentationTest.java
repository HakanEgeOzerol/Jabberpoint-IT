package unit.presentation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import jabberpoint.observer.Event;
import jabberpoint.observer.Subscriber;
import jabberpoint.presentation.Presentation;
import jabberpoint.presentation.Slide;

public class PresentationTest {
    private Presentation presentation;
    private Slide slide1;
    private Slide slide2;
    private Subscriber mockSubscriber;

    @BeforeEach
    public void setUp() {
        presentation = new Presentation();
        slide1 = new Slide();
        slide2 = new Slide();
        mockSubscriber = mock(Subscriber.class);
        
        slide1.setTitle("Slide 1");
        slide2.setTitle("Slide 2");
    }

    @Test
    public void testConstructor() {
        assertEquals(0, presentation.getSize());
        assertEquals(-1, presentation.getSlideNumber());
        assertNull(presentation.getTitle());
    }

    @Test
    public void testGetAndSetTitle() {
        assertNull(presentation.getTitle());
        
        presentation.setTitle("Test Presentation");
        assertEquals("Test Presentation", presentation.getTitle());
    }

    @Test
    public void testAppendSlide() {
        presentation.append(slide1);
        assertEquals(1, presentation.getSize());
        assertSame(slide1, presentation.getSlide(0));
        
        presentation.append(slide2);
        assertEquals(2, presentation.getSize());
        assertSame(slide2, presentation.getSlide(1));
    }

    @Test
    public void testGetSlide() {
        presentation.append(slide1);
        presentation.append(slide2);
        
        assertSame(slide1, presentation.getSlide(0));
        assertSame(slide2, presentation.getSlide(1));
        assertNull(presentation.getSlide(-1));
        assertNull(presentation.getSlide(2));
    }

    @Test
    public void testGetCurrentSlide() {
        assertNull(presentation.getCurrentSlide());
        
        presentation.append(slide1);
        presentation.append(slide2);
        
        assertNull(presentation.getCurrentSlide());
        
        presentation.setSlideNumber(0);
        assertSame(slide1, presentation.getCurrentSlide());
        
        presentation.setSlideNumber(1);
        assertSame(slide2, presentation.getCurrentSlide());
    }

    @Test
    public void testSetSlideNumber() {
        presentation.append(slide1);
        presentation.append(slide2);
        
        presentation.setSlideNumber(0);
        assertEquals(0, presentation.getSlideNumber());
        
        presentation.setSlideNumber(1);
        assertEquals(1, presentation.getSlideNumber());
        
        presentation.setSlideNumber(-1);
        assertEquals(1, presentation.getSlideNumber());
        
        presentation.setSlideNumber(5);
        assertEquals(1, presentation.getSlideNumber());
    }

    @Test
    public void testNextSlide() {
        presentation.append(slide1);
        presentation.append(slide2);
        presentation.setSlideNumber(0);
        
        presentation.nextSlide();
        assertEquals(1, presentation.getSlideNumber());
        
        presentation.nextSlide();
        assertEquals(1, presentation.getSlideNumber());
    }

    @Test
    public void testPrevSlide() {
        presentation.append(slide1);
        presentation.append(slide2);
        presentation.setSlideNumber(1);
        
        presentation.prevSlide();
        assertEquals(0, presentation.getSlideNumber());
        
        presentation.prevSlide();
        assertEquals(0, presentation.getSlideNumber());
    }

    @Test
    public void testClear() {
        presentation.append(slide1);
        presentation.append(slide2);
        presentation.setSlideNumber(1);
        
        presentation.clear();
        assertEquals(0, presentation.getSize());
        assertEquals(-1, presentation.getSlideNumber());
        assertNull(presentation.getCurrentSlide());
        
        presentation.append(slide1);
        assertEquals(1, presentation.getSize());
        assertEquals(-1, presentation.getSlideNumber());
    }

    @Test
    public void testSubscriberManagement() {
        presentation.addSubscriber(mockSubscriber);
        presentation.append(slide1);
        presentation.setSlideNumber(0);
        
        verify(mockSubscriber).update(eq(Event.SLIDE_CHANGED), any(Event.PresentationState.class));
        
        assertSame(mockSubscriber, presentation.removeSubscriber(mockSubscriber));
        
        presentation.clear();
        verifyNoMoreInteractions(mockSubscriber);
    }

    @Test
    public void testNotifySubscribers() {
        Subscriber mockSubscriber1 = mock(Subscriber.class);
        Subscriber mockSubscriber2 = mock(Subscriber.class);
        
        presentation.addSubscriber(mockSubscriber1);
        presentation.addSubscriber(mockSubscriber2);
        
        presentation.append(slide1);
        presentation.setSlideNumber(0);
        
        verify(mockSubscriber1).update(eq(Event.SLIDE_CHANGED), any(Event.PresentationState.class));
        verify(mockSubscriber2).update(eq(Event.SLIDE_CHANGED), any(Event.PresentationState.class));
    }
} 