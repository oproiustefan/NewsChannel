package pcbe.core.topics;

import pcbe.core.events.IEvent;
import pcbe.core.events.NewsArticleRead;

public class ReadTopic implements ITopic {

    @Override
    public boolean matches(IEvent event) {
        return event instanceof NewsArticleRead;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ReadTopic;
    }
}