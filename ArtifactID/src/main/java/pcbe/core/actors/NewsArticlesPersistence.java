package pcbe.core.actors;

import java.util.ArrayList;
import java.util.List;

import pcbe.core.domain.NewsArticle;
import pcbe.core.events.IEvent;
import pcbe.core.topics.*;

public class NewsArticlesPersistence {
    private List<NewsArticle> articles;
    private EventChannel channel;
    private final Object PERSISTENCE_LOCK = new Object();

    public NewsArticlesPersistence(EventChannel channel) {
        this.channel = channel;
        this.articles = new ArrayList<NewsArticle>();
        ISubscriber onPublishedHandler = new ISubscriber() {
            @Override
            public void notify(IEvent event) {
                synchronized (PERSISTENCE_LOCK) {
                    NewsArticle article = event.getArticle();
                    System.out.println("Persistence was notified that [" + article.getTitle() + "] was published.");
                    articles.add(article);
                }
            }
        };
        ISubscriber onDeletedHandler = new ISubscriber() {
            @Override
            public void notify(IEvent event) {
                synchronized (PERSISTENCE_LOCK) {
                    NewsArticle article = event.getArticle();
                    System.out.println("Persistence was notified that [" + article.getTitle() + "] was removed.");
                    if (articles.contains(article)) {
                        articles.remove(article);
                    }
                }
            }
        };

        this.channel.register(new PublishedTopic(), onPublishedHandler);
        this.channel.register(new DeletedTopic(), onDeletedHandler);
    }
}