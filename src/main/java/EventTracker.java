import java.util.HashMap;
import java.util.Map;

public class EventTracker implements Tracker {

    private static EventTracker INSTANCE = new EventTracker();

    private Map<String, Integer> tracker;

    private EventTracker() {
        this.tracker = new HashMap<>();
    }

    synchronized public static EventTracker getInstance() {
        return INSTANCE;
    }

    synchronized public void push(String message) {
        tracker.put(message, tracker.getOrDefault(message, 0) + 1);
    }

    synchronized public Boolean has(String message) {
        return tracker.containsKey(message);
    }

    synchronized public void handle(String message, EventHandler e) {
        e.handle();
        if (tracker.containsKey(message)) {
            int newValue =  tracker.get(message) - 1;
            if (newValue < 1) {
                tracker.remove(message);
            } else {
                tracker.put(message, newValue);
            }
        }
    }



    // Do not use this. This constructor is for tests only
    // Using it breaks the singleton class
    EventTracker(Map<String, Integer> tracker) {
        this.tracker = tracker;
    }

    public Map<String, Integer> getTracker() {
        return tracker;
    }
}
