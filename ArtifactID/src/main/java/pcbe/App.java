package pcbe;

import java.util.List;

import pcbe.core.actors.*;
import pcbe.core.domain.*;
import pcbe.core.topics.*;

interface ITask {
    void doTask() throws InterruptedException;
}

public class App {
    public static void main(final String[] args) throws InterruptedException {

        EventChannel channel = new EventChannel();
        NewsArticlesPersistence persistence = new NewsArticlesPersistence(channel);

        App.doTask(new ITask() {
            @Override
            public void doTask() throws InterruptedException {
                Reader reader = new Reader("Ionel", channel);
                reader.register(new CompositeAllMatchTopic(List.of(new AuthorTopic("CTP"), new PublishedTopic())));
                Reader reader2 = new Reader("Dorel", channel);
                reader2.register(new CompositeAllMatchTopic(List.of(new DomainTopic(NewsDomain.POLITICS),
                        new CompositeAnyMatchTopic(List.of(new PublishedTopic(), new ModifiedTopic())))));
            }
        });

        App.doTask(new ITask() {
            @Override
            public void doTask() throws InterruptedException {
                NewsEditor editor = new NewsEditor("CTP", channel);
                Thread.sleep(200);
                editor.publishArticle(new NewsArticle(new NewsSubdomain("PNL", NewsDomain.POLITICS), editor,
                        "N-o sa-ti vina sa crezi ce a facut Iohannis"));
                Thread.sleep(200);
                NewsEditor editor2 = new NewsEditor("Moise Guran", channel);
                NewsArticle gafa = new NewsArticle(new NewsSubdomain("PSD", NewsDomain.POLITICS), editor2,
                        "Ultima gafa a Vioricai");
                editor2.publishArticle(gafa);
                Thread.sleep(200);
                gafa.setTitle("Veorika a gafat iar");
            }
        });

        Thread.sleep(1000);
    }

    public static void doTask(ITask task) {
        new Thread(() -> {
            try {
                task.doTask();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }).start();
    }
}
