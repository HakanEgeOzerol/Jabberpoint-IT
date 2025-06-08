package jabberpoint.observer;

import java.util.HashSet;

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
			subscriber.update(event, data);
		}
	}
} 