package pcbe.core.events;

import pcbe.core.domain.NewsArticle;

public interface IEvent {
    NewsArticle getArticle();
}
