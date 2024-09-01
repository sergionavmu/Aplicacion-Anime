package abp.animeg7.anime.model;

public class Video {
    private int idanime;
    private int episode;
    private String url;
    private String image;

    public Video(int idanime, int episode, String url, String image) {
        this.idanime = idanime;
        this.episode = episode;
        this.url = url;
        this.image = image;
    }

    public Video() {
    }

    // Setters
    public void setIdanime(int idanime) {
        this.idanime = idanime;
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setImage(String image) {
        this.image = image;
    }

    // Getters
    public int getIdanime() {
        return idanime;
    }

    public int getEpisode() {
        return episode;
    }

    public String getUrl() {
        return url;
    }

    public String getImage() {
        return image;
    }

    // toString method
    @Override
    public String toString() {
        return "Video{" +
                "idanime=" + idanime +
                ", episode=" + episode +
                ", url='" + url + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
