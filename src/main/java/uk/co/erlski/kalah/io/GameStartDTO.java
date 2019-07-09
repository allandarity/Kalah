package uk.co.erlski.kalah.io;

/**
 * A DTO object that is returned when a game has started
 * @author Elliott Lewandowski
 */
public class GameStartDTO {

    private long id;
    private String url;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
