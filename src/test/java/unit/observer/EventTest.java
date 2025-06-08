package unit.observer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

import jabberpoint.observer.Event;
import jabberpoint.presentation.Slide;

class EventTest {

    @Test
    void testEventEnumValues() {
        Event[] events = Event.values();
        
        assertTrue(events.length >= 4);
        assertNotNull(Event.SLIDE_CHANGED);
        assertNotNull(Event.PRESENTATION_LOADED);
        assertNotNull(Event.PRESENTATION_CLEARED);
        assertNotNull(Event.UNKNOWN);
    }

    @Test
    void testEventValueOf() {
        assertEquals(Event.SLIDE_CHANGED, Event.valueOf("SLIDE_CHANGED"));
        assertEquals(Event.PRESENTATION_LOADED, Event.valueOf("PRESENTATION_LOADED"));
        assertEquals(Event.PRESENTATION_CLEARED, Event.valueOf("PRESENTATION_CLEARED"));
        assertEquals(Event.UNKNOWN, Event.valueOf("UNKNOWN"));
    }

    @Test
    void testPresentationStateConstructorAndGetters() {
        Slide mockSlide = mock(Slide.class);
        int slideNumber = 5;
        int totalSlides = 10;
        String title = "Test Presentation";

        Event.PresentationState state = new Event.PresentationState(mockSlide, slideNumber, totalSlides, title);

        assertEquals(mockSlide, state.getSlide());
        assertEquals(slideNumber, state.getSlideNumber());
        assertEquals(totalSlides, state.getTotalSlides());
        assertEquals(title, state.getTitle());
    }

    @Test
    void testPresentationStateWithNullValues() {
        Event.PresentationState state = new Event.PresentationState(null, 0, 0, null);

        assertNull(state.getSlide());
        assertEquals(0, state.getSlideNumber());
        assertEquals(0, state.getTotalSlides());
        assertNull(state.getTitle());
    }

    @Test
    void testPresentationStateWithEdgeCaseValues() {
        Slide mockSlide = mock(Slide.class);
        int slideNumber = -1;
        int totalSlides = Integer.MAX_VALUE;
        String title = "";

        Event.PresentationState state = new Event.PresentationState(mockSlide, slideNumber, totalSlides, title);

        assertEquals(mockSlide, state.getSlide());
        assertEquals(slideNumber, state.getSlideNumber());
        assertEquals(totalSlides, state.getTotalSlides());
        assertEquals(title, state.getTitle());
    }
} 