package jabberpoint;

import java.util.HashSet;

/**
 * Publisher abstract class for the Observer pattern.
 * Classes that want to notify subscribers of events should implement this interface.
 */
public abstract class Publisher {
    protected HashSet<Subscriber> subscribers;

    public Publisher() {
        this.subscribers = new HashSet<>();
    }

	public void addSubscriber(Subscriber subscriber) {
		subscribers.add(subscriber);
	}
	
	public Subscriber removeSubscriber(Subscriber subscriber) {
		if (subscribers.remove(subscriber)) {
			return subscriber;
		}
		return null;
	}
	
	public void notifySubscribers(Event event, Object data) {
		for (Subscriber subscriber : subscribers) {
			subscriber.update(event, data, this);
		}
	}
} 