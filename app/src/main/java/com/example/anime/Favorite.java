package com.example.anime;

public class Favorite {
    private Anime anime;
    private int idAnime;
    private int idUser;
    boolean status;


    public Favorite() {
    }

    public Favorite(Anime anime, int idAnime, int idUser, boolean status) {
        this.anime = anime;
        this.idAnime = idAnime;
        this.idUser = idUser;
        this.status = status;
    }

    public Anime getAnime() {
        return anime;
    }

    public void setAnime(Anime anime) {
        this.anime = anime;
    }

    public int getIdAnime() {
        return idAnime;
    }

    public void setIdAnime(int idAnime) {
        this.idAnime = idAnime;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "anime=" + anime +
                ", idAnime=" + idAnime +
                ", idUser=" + idUser +
                ", status=" + status +
                '}';
    }
}
