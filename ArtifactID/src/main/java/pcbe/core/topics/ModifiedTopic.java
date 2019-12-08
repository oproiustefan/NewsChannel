package pcbe.core.topics;

import pcbe.core.events.IEvent;
import pcbe.core.events.NewsArticleModified;

public class ModifiedTopic implements ITopic {

    @Override
    public boolean matches(IEvent event) {
        return event instanceof NewsArticleModified;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ModifiedTopic;
    }
}