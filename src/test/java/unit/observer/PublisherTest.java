package unit.observer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jabberpoint.observer.Event;
import jabberpoint.observer.Publisher;
import jabberpoint.observer.Subscriber;

class PublisherTest {

    @Mock
    private Subscriber mockSubscriber1;
    
    @Mock
    private Subscriber mockSubscriber2;

    private TestPublisher publisher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        publisher = new TestPublisher();
    }

    @Test
    void testAddSubscriber() {
        publisher.addSubscriber(mockSubscriber1);

        assertTrue(publisher.getSubscribers().contains(mockSubscriber1));
        assertEquals(1, publisher.getSubscribers().size());
    }

    @Test
    void testAddMultipleSubscribers() {
        publisher.addSubscriber(mockSubscriber1);
        publisher.addSubscriber(mockSubscriber2);

        assertTrue(publisher.getSubscribers().contains(mockSubscriber1));
        assertTrue(publisher.getSubscribers().contains(mockSubscriber2));
        assertEquals(2, publisher.getSubscribers().size());
    }

    @Test
    void testAddSameSubscriberTwice() {
        publisher.addSubscriber(mockSubscriber1);
        publisher.addSubscriber(mockSubscriber1);

        assertTrue(publisher.getSubscribers().contains(mockSubscriber1));
        assertEquals(1, publisher.getSubscribers().size());
    }

    @Test
    void testRemoveSubscriberSuccess() {
        publisher.addSubscriber(mockSubscriber1);

        Subscriber removed = publisher.removeSubscriber(mockSubscriber1);

        assertEquals(mockSubscriber1, removed);
        assertFalse(publisher.getSubscribers().contains(mockSubscriber1));
        assertEquals(0, publisher.getSubscribers().size());
    }

    @Test
    void testRemoveSubscriberNotPresent() {
        Subscriber removed = publisher.removeSubscriber(mockSubscriber1);

        assertNull(removed);
        assertEquals(0, publisher.getSubscribers().size());
    }

    @Test
    void testNotifySubscribers() {
        publisher.addSubscriber(mockSubscriber1);
        publisher.addSubscriber(mockSubscriber2);
        Event testEvent = Event.SLIDE_CHANGED;
        Object testData = "test data";

        publisher.notifySubscribers(testEvent, testData);

        verify(mockSubscriber1).update(testEvent, testData);
        verify(mockSubscriber2).update(testEvent, testData);
    }

    @Test
    void testNotifySubscribersWithNoSubscribers() {
        Event testEvent = Event.PRESENTATION_LOADED;
        Object testData = null;

        assertDoesNotThrow(() -> publisher.notifySubscribers(testEvent, testData));
    }

    @Test
    void testNotifySubscribersWithNullEvent() {
        publisher.addSubscriber(mockSubscriber1);

        publisher.notifySubscribers(null, "data");

        verify(mockSubscriber1).update(null, "data");
    }

    // Test implementation of abstract class
    private static class TestPublisher extends Publisher {
        public java.util.HashSet<Subscriber> getSubscribers() {
            return subscribers;
        }
    }
} 