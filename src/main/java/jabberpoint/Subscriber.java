package jabberpoint;

/**
 * Subscriber interface for the Observer pattern.
 * Classes that want to receive updates from a Publisher should implement this interface.
 */
public interface Subscriber {
    /**
     * Called when the publisher has an update to share
     * @param event The type of event that occurred
     * @param data The data associated with the event
     * @param publisher The publisher that triggered the event
     */
    void update(Event event, Object data, Publisher publisher);
} 