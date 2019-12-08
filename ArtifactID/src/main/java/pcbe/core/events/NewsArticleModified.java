package pcbe.core.events;

import pcbe.core.domain.NewsArticle;

public class NewsArticleModified implements IEvent {
    private NewsArticle article;

    public NewsArticleModified(NewsArticle article) {
        this.article = article;
    }

    @Override
    public NewsArticle getArticle() {
        return article;
    }
}