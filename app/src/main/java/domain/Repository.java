package domain;

import java.util.Map;

/**
 * Domain class to represent a GitHub repository.
 */
public class Repository {

    private final String name;
    private final String description;
    private final String htmlUrl;
    private final Map<String, String> owner;
    private final boolean fork;

    public Repository(String name, String description,
                      String htmlUrl, Map<String, String> owner, boolean fork) {
        this.name = name;
        this.description = description;
        this.htmlUrl = htmlUrl;
        this.owner = owner;
        this.fork = fork;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getHtmlUrlRepo() {
        return htmlUrl;
    }

    public String getHtmlUrlOwner() {
        return owner.get("html_url");
    }

    public String getOwnerLogin() {
        return owner.get("login");
    }

    public boolean isFork() {
        return fork;
    }
}