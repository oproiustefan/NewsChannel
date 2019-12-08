package pcbe.core.events;

import pcbe.core.domain.NewsArticle;

public class NewsArticleRead implements IEvent {
    private NewsArticle article;

    public NewsArticleRead(NewsArticle article) {
        this.article = article;
    }

    @Override
    public NewsArticle getArticle() {
        return article;
    }
}