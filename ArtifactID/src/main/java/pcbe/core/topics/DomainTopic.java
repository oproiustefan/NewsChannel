package pcbe.core.topics;

import pcbe.core.domain.NewsDomain;
import pcbe.core.events.IEvent;

public class DomainTopic implements ITopic {
    private NewsDomain domain;

    public DomainTopic(NewsDomain domain) {
        this.domain = domain;
    }

    @Override
    public boolean matches(IEvent event) {
        return this.domain == event.getArticle().getSubdomain().getDomain();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof DomainTopic && ((DomainTopic) o).domain.equals(this.domain);
    }
}