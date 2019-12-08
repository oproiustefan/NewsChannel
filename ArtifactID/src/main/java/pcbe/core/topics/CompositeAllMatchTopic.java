package pcbe.core.topics;

import java.util.List;

import pcbe.core.events.IEvent;

public class CompositeAllMatchTopic implements ITopic {
    private List<ITopic> topics;

    public CompositeAllMatchTopic(List<ITopic> topics) {
        this.topics = topics;
    }

    @Override
    public boolean matches(IEvent event) {
        return topics.stream().allMatch(t -> t.matches(event));
    }
}