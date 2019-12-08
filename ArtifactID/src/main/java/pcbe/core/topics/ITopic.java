package pcbe.core.topics;

import pcbe.core.events.IEvent;

public interface ITopic {
    boolean matches(IEvent event);
}
