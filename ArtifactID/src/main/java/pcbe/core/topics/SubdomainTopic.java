package pcbe.core.topics;

import pcbe.core.domain.NewsSubdomain;
import pcbe.core.events.IEvent;

public class SubdomainTopic implements ITopic {
    private NewsSubdomain subdomain;

    public SubdomainTopic(NewsSubdomain subdomain) {
        this.subdomain = subdomain;
    }

    @Override
    public boolean matches(IEvent event) {
        return this.subdomain.equals(event.getArticle().getSubdomain());
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof SubdomainTopic && ((SubdomainTopic) o).subdomain.equals(this.subdomain);
    }
}