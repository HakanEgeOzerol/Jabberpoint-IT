package jabberpoint;

/**
 * Event enumeration for the Observer pattern.
 * Defines various types of events that can be published.
 */
public enum Event {
    /** Slide navigation events */
    SLIDE_CHANGED,
    
    /** Presentation-level events */
    PRESENTATION_LOADED,
    PRESENTATION_CLEARED,
    
    /** Other events */
    UNKNOWN
} 