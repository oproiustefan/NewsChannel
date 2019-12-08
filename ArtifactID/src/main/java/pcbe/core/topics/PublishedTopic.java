package pcbe.core.topics;

import pcbe.core.events.IEvent;
import pcbe.core.events.NewsArticlePublished;

public class PublishedTopic implements ITopic {

    @Override
    public boolean matches(IEvent event) {
        return event instanceof NewsArticlePublished;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PublishedTopic;
    }
}