package pcbe.core.topics;

import pcbe.core.events.IEvent;

public class AuthorTopic implements ITopic {
    private String authorName;

    public AuthorTopic(String authorName) {
        this.authorName = authorName;
    }

    @Override
    public boolean matches(IEvent event) {
        return this.authorName.equals(event.getArticle().getAuthorName());
    }
}