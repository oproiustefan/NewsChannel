package pcbe.core.actors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pcbe.core.events.IEvent;
import pcbe.core.topics.ITopic;

public class EventChannel {
    private HashMap<ITopic, List<ISubscriber>> subscribersMap;
    private final Object REGISTER_LOCK = new Object();

    public EventChannel() {
        subscribersMap = new HashMap<>();
    }

    public void register(ITopic topic, ISubscriber subscriber) {
        synchronized (REGISTER_LOCK) {
            List<ISubscriber> subscribers = subscribersMap.get(topic);
            if (subscribers == null) {
                subscribers = new ArrayList<ISubscriber>();
                subscribers.add(subscriber);
                subscribersMap.put(topic, subscribers);
            } else {
                subscribers.add(subscriber);
            }
        }
    }

    public void dispatch(IEvent event) {
        synchronized (REGISTER_LOCK) {
            Set<ISubscriber> subscribersToNotify = new HashSet<>();
            for (ITopic t : subscribersMap.keySet()) {
                if (t.matches(event)) {
                    subscribersToNotify.addAll(subscribersMap.get(t));
                }
            }
            for (ISubscriber subscriber : subscribersToNotify) {
                new Thread(() -> subscriber.notify(event)).start();
            }
        }
    }
}