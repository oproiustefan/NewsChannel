package pcbe.core.actors;

import pcbe.core.events.IEvent;

public interface ISubscriber {
    void notify(IEvent event);
}