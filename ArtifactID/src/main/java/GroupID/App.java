package GroupID;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

enum NewsDomain {
    POLITICS, SPORTS, ENTERTAINMENT, HEALTH, TECH, ECONOMY
}

class NewsSubdomain {
    private final String name;
    private final NewsDomain domain;

    public NewsSubdomain(final String name, final NewsDomain domain) {
        this.name = name;
        this.domain = domain;
    }

    public String getName() {
        return name;
    }

    public NewsDomain getDomain() {
        return domain;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof NewsSubdomain) && ((NewsSubdomain) o).name == this.name;
    }
}

class NewsArticle {
    private NewsSubdomain subdomain;
    private final NewsEditor author;
    private String title;
    private final Date publishDate;
    private Date lastModifiedDate;

    public NewsArticle(NewsSubdomain subdomain, NewsEditor author, String title) {
        this.subdomain = subdomain;
        this.author = author;
        this.title = title;
        this.publishDate = new Date(System.currentTimeMillis());
        this.lastModifiedDate = new Date(System.currentTimeMillis());
    }

    public void setSubdomain(final NewsSubdomain subdomain) {
        this.subdomain = subdomain;
        this.setLastModifiedDateToNow();
    }

    public void setTitle(final String title) {
        this.title = title;
        this.setLastModifiedDateToNow();
    }

    private void setLastModifiedDateToNow() {
        this.lastModifiedDate = new Date(System.currentTimeMillis());
    }

    public NewsSubdomain getSubdomain() {
        return subdomain;
    }

    public String getAuthorName() {
        return author.getName();
    }

    public String getTitle() {
        return title;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }
}

interface IEvent {
    NewsArticle getArticle();
}

class NewsArticlePublished implements IEvent {
    private NewsArticle article;

    public NewsArticlePublished(NewsArticle article) {
        this.article = article;
    }

    @Override
    public NewsArticle getArticle() {
        return article;
    }
}

class NewsArticleModified implements IEvent {
    private NewsArticle article;

    public NewsArticleModified(NewsArticle article) {
        this.article = article;
    }

    @Override
    public NewsArticle getArticle() {
        return article;
    }
}

class NewsArticleDeleted implements IEvent {
    private NewsArticle article;

    public NewsArticleDeleted(NewsArticle article) {
        this.article = article;
    }

    @Override
    public NewsArticle getArticle() {
        return article;
    }
}

class NewsArticleRead implements IEvent {
    private NewsArticle article;

    public NewsArticleRead(NewsArticle article) {
        this.article = article;
    }

    @Override
    public NewsArticle getArticle() {
        return article;
    }
}

interface ITopic {
    boolean matches(IEvent event);
}

class DomainTopic implements ITopic {
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

class SubdomainTopic implements ITopic {
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

class TitleTopic implements ITopic {
    private String title;

    public TitleTopic(String title) {
        this.title = title;
    }

    @Override
    public boolean matches(IEvent event) {
        return this.title.equals(event.getArticle().getTitle());
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof TitleTopic && ((TitleTopic) o).title.equals(this.title);
    }
}

class ReadTopic implements ITopic {

    @Override
    public boolean matches(IEvent event) {
        return event instanceof NewsArticleRead;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ReadTopic;
    }
}

class PublishedTopic implements ITopic {

    @Override
    public boolean matches(IEvent event) {
        return event instanceof NewsArticlePublished;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PublishedTopic;
    }
}

class DeletedTopic implements ITopic {

    @Override
    public boolean matches(IEvent event) {
        return event instanceof NewsArticleDeleted;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof DeletedTopic;
    }
}

class CompositeAllMatchTopic implements ITopic {
    private List<ITopic> topics;

    public CompositeAllMatchTopic(List<ITopic> topics) {
        this.topics = topics;
    }

    @Override
    public boolean matches(final IEvent event) {
        return topics.stream().allMatch(new Predicate<ITopic>() {
            @Override
            public boolean test(ITopic t) {
                return t.matches(event);
            }
        });
    }
}

class CompositeAnyMatchTopic implements ITopic {
    private List<ITopic> topics;

    public CompositeAnyMatchTopic(List<ITopic> topics) {
        this.topics = topics;
    }

    @Override
    public boolean matches(final IEvent event) {
        return topics.stream().anyMatch(new Predicate<ITopic>() {
            @Override
            public boolean test(ITopic t) {
                return t.matches(event);
            }
        });
    }
}

interface ISubscriber {
    void notify(IEvent event);
}

class NewsArticlesPersistence implements ISubscriber {
    private List<NewsArticle> articles;
    private EventChannel channel;

    public NewsArticlesPersistence(EventChannel channel) {
        this.channel = channel;
        this.articles = new ArrayList<NewsArticle>();
    }

    @Override
    public void notify(IEvent event) {

    }
}

class EventChannel {
    private HashMap<ITopic, List<ISubscriber>> subscribersMap;
    private final Object REGISTER_LOCK = new Object();

    public EventChannel() {
        subscribersMap = new HashMap<>();
    }

    public void register(ITopic topic, ISubscriber subscriber) {
        synchronized (REGISTER_LOCK) {
            List<ISubscriber> subscribers = subscribersMap.get(topic);
            if (subscribers == null) {
                subscribers = new ArrayList<ISubscriber>();
                subscribers.add(subscriber);
                subscribersMap.put(topic, subscribers);

            } else {
                subscribers.add(subscriber);
            }
        }
    }

    public void dispatch(IEvent event) {
        synchronized (REGISTER_LOCK) {
            Set<ISubscriber> subscribersToNotify = new HashSet<>();
            for (ITopic t : subscribersMap.keySet()) {
                if (t.matches(event)) {
                    subscribersToNotify.addAll(subscribersMap.get(t));
                }
            }
            for (ISubscriber subscriber : subscribersToNotify) {
                subscriber.notify(event);
            }
        }
    }
}

class NewsEditor implements ISubscriber {
    private final String name;
    private EventChannel channel;

    public NewsEditor(String name, EventChannel channel) {
        this.name = name;
        this.channel = channel;
    }

    public String getName() {
        return name;
    }

    @Override
    public void notify(IEvent event) {
        NewsArticle article = event.getArticle();
        System.out.println("Editor [" + name + "] was notified that [" + article.getTitle() + "] has just been read.");
    }

    public void publishArticle(NewsArticle article) {
        channel.dispatch(new NewsArticlePublished(article));
    }
}

public class App {
    public static void main(final String[] args) {
        System.out.println("Hello World!");
    }
}
