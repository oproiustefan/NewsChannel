package pcbe.core.events;

import pcbe.core.domain.NewsArticle;

public class NewsArticlePublished implements IEvent {
    private NewsArticle article;

    public NewsArticlePublished(NewsArticle article) {
        this.article = article;
    }

    @Override
    public NewsArticle getArticle() {
        return article;
    }
}