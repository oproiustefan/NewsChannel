package pcbe.core.actors;

import java.util.List;

import pcbe.core.domain.NewsArticle;
import pcbe.core.events.IEvent;
import pcbe.core.events.NewsArticleModified;
import pcbe.core.events.NewsArticlePublished;
import pcbe.core.topics.*;

public class NewsEditor {
    private final String name;
    private EventChannel channel;

    public NewsEditor(String name, EventChannel channel) {
        this.name = name;
        this.channel = channel;
        ITopic thisAuthorTopic = new AuthorTopic(name);
        ITopic readTopic = new ReadTopic();
        ITopic editorTopic = new CompositeAllMatchTopic(List.of(thisAuthorTopic, readTopic));
        this.channel.register(editorTopic, new ISubscriber() {
            @Override
            public void notify(IEvent event) {
                System.out.println("Editor [" + name + "] has been notified that article ["
                        + event.getArticle().getTitle() + "] was read.");
            }
        });
    }

    public String getName() {
        return name;
    }

    public void publishArticle(NewsArticle article) {
        System.out.println("Editor [" + name + "] has published article [" + article.getTitle() + "].");
        channel.dispatch(new NewsArticlePublished(article));
    }

    public void onArticleModified(NewsArticle article) {
        System.out.println("Article [" + article.getTitle() + "] has been modified.");
        channel.dispatch(new NewsArticleModified(article));
    }
}