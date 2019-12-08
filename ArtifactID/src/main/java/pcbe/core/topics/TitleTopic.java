package pcbe.core.topics;

import pcbe.core.events.IEvent;

public class TitleTopic implements ITopic {
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