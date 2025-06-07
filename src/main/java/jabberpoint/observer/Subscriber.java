package jabberpoint.observer;

public interface Subscriber {
    void update(Event event, Object data);
} 