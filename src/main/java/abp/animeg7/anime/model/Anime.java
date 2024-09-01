package abp.animeg7.anime.model;

public class Anime {
    private int id;
    private String name;
    private String description;
    private String type;
    private int year;
    private String image;
    private String originalName;
    private String rating;
    private String demography;
    private String genre;
    private String image2;

    private boolean active;



    public Anime(int id, String name, String description, String type, int year, String image, String originalName, String rating, String demography, String genre, String image2,  boolean active) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.year = year;
        this.image = image;
        this.originalName = originalName;
        this.rating = rating;
        this.demography = demography;
        this.genre = genre;
        this.image2 = image2;
        this.active = active;
    }

    public Anime() {
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setDemography(String demography) {
        this.demography = demography;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }




    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public int getYear() {
        return year;
    }

    public String getImage() {
        return image;
    }

    public String getOriginalName() {
        return originalName;
    }

    public String getRating() {
        return rating;
    }

    public String getDemography() {
        return demography;
    }

    public String getGenre() {
        return genre;
    }

    public String getImage2() {
        return image2;
    }


    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    // toString method
    @Override
    public String toString() {
        return "Anime{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", year=" + year +
                ", image='" + image + '\'' +
                ", originalName='" + originalName + '\'' +
                ", rating='" + rating + '\'' +
                ", demography='" + demography + '\'' +
                ", genre='" + genre + '\'' +
                ", image2='" + image2 + '\'' +
                ", active='" + active + '\'' +
                '}';
    }
}

