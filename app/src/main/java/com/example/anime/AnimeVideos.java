package com.example.anime;

import java.io.Serializable;

public class AnimeVideos implements Serializable {
    private int idAnime;
    private String episode;
    private String url;
    private String image;

    public AnimeVideos(int idAnime, String episode, String url, String image) {
        this.idAnime = idAnime;
        this.episode = episode;
        this.url = url;
        this.image = image;
    }

    public AnimeVideos() {

    }

    public int getIdAnime() {
        return idAnime;
    }

    public void setIdAnime(int idAnime) {
        this.idAnime = idAnime;
    }

    public String getEpisode() {
        return episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "AnimeVideo{" +
                "idAnime='" + idAnime + '\'' +
                ", episode='" + episode + '\'' +
                ", url='" + url + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
