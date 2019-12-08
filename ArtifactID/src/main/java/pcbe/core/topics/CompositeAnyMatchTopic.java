package pcbe.core.topics;

import java.util.List;

import pcbe.core.events.IEvent;

public class CompositeAnyMatchTopic implements ITopic {
    private List<ITopic> topics;

    public CompositeAnyMatchTopic(List<ITopic> topics) {
        this.topics = topics;
    }

    @Override
    public boolean matches(IEvent event) {
        return topics.stream().anyMatch(t -> t.matches(event));
    }
}