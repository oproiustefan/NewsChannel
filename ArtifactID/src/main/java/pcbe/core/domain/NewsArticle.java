package pcbe.core.domain;

import java.util.Date;

import pcbe.core.actors.NewsEditor;

public class NewsArticle {
    private NewsSubdomain subdomain;
    private final NewsEditor author;
    private String title;
    private final Date publishDate;
    private Date lastModifiedDate;

    public NewsArticle(NewsSubdomain subdomain, NewsEditor author, String title) {
        this.subdomain = subdomain;
        this.author = author;
        this.title = title;
        this.publishDate = new Date(System.currentTimeMillis());
        this.lastModifiedDate = new Date(System.currentTimeMillis());
    }

    public void setSubdomain(final NewsSubdomain subdomain) {
        this.subdomain = subdomain;
        this.onArticleModified();
    }

    public void setTitle(final String title) {
        this.title = title;
        this.onArticleModified();
    }

    private void onArticleModified() {
        this.lastModifiedDate = new Date(System.currentTimeMillis());
        this.author.onArticleModified(this);
    }

    public NewsSubdomain getSubdomain() {
        return subdomain;
    }

    public String getAuthorName() {
        return author.getName();
    }

    public String getTitle() {
        return title;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }
}
