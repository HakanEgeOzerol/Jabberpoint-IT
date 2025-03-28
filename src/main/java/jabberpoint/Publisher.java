package jabberpoint;

/**
 * Publisher interface for the Observer pattern.
 * Classes that want to notify subscribers of events should implement this interface.
 */
public interface Publisher {
    /**
     * Add a subscriber to receive notifications
     * @param subscriber The subscriber to add
     */
    void addSubscriber(Subscriber subscriber);
    
    /**
     * Remove a subscriber from receiving notifications
     * @param subscriber The subscriber to remove
     * @return The removed subscriber or null if not found
     */
    Subscriber removeSubscriber(Subscriber subscriber);
    
    /**
     * Notify all subscribers of an event
     * @param event The event type
     * @param data The data associated with the event
     */
    void notifySubscribers(Event event, Object data);
} 