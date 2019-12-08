package pcbe.core.topics;

import pcbe.core.events.IEvent;
import pcbe.core.events.NewsArticleDeleted;

public class DeletedTopic implements ITopic {

    @Override
    public boolean matches(IEvent event) {
        return event instanceof NewsArticleDeleted;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof DeletedTopic;
    }
}