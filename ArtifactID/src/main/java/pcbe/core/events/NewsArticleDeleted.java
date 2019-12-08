package pcbe.core.events;

import pcbe.core.domain.NewsArticle;

public class NewsArticleDeleted implements IEvent {
    private NewsArticle article;

    public NewsArticleDeleted(NewsArticle article) {
        this.article = article;
    }

    @Override
    public NewsArticle getArticle() {
        return article;
    }
}