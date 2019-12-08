package pcbe.core.domain;

public class NewsSubdomain {
    private final String name;
    private final NewsDomain domain;

    public NewsSubdomain(final String name, final NewsDomain domain) {
        this.name = name;
        this.domain = domain;
    }

    public String getName() {
        return name;
    }

    public NewsDomain getDomain() {
        return domain;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof NewsSubdomain) && ((NewsSubdomain) o).name == this.name;
    }
}
