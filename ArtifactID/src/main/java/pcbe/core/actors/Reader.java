package pcbe.core.actors;

import pcbe.core.domain.NewsArticle;
import pcbe.core.events.IEvent;
import pcbe.core.events.NewsArticleRead;
import pcbe.core.topics.ITopic;

public class Reader {
    private EventChannel channel;
    private final String name;

    public Reader(String name, EventChannel channel) {
        this.name = name;
        this.channel = channel;
    }

    public void register(ITopic topic) {
        this.channel.register(topic, new ISubscriber() {
            @Override
            public void notify(IEvent event) {
                NewsArticle article = event.getArticle();
                read(event.getArticle());
                channel.dispatch(new NewsArticleRead(article));
            }
        });
    }

    public void read(NewsArticle article) {
        System.out.println("Reader [" + name + "] has read article [" + article.getTitle() + "] by ["
                + article.getAuthorName() + "].");
    }

    public String getName() {
        return name;
    }
}