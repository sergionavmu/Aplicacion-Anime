package abp.animeg7.anime.model;

public class Favorito {
    private int iduser;
    private int idanime;
    private boolean status;

    public Favorito(int iduser, int idanime) {
        this.iduser = iduser;
        this.idanime = idanime;
    }

    public Favorito() {
    }

    // Setters
    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public void setIdanime(int idanime) {
        this.idanime = idanime;
    }

    // Getters
    public int getIduser() {
        return iduser;
    }

    public int getIdanime() {
        return idanime;
    }

    // toString method
    @Override
    public String toString() {
        return "Favorito{" +
                "iduser=" + iduser +
                ", idanime=" + idanime +
                '}';
    }
}