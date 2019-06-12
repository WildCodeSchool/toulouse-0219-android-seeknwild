package fr.wildcodeschool.seeknwild.model;

public class AdventureModel {

    private String title;
    private String description;
    private String distance;
    private String imageAdventureUrl;
    private String imageStarRate;
    private Boolean alreadyDone;


    public AdventureModel(String title, String description, String distance, String imageAdventureUrl, String imageStarRate, boolean alreadyDone) {
        this.title = title;
        this.description = description;
        this.distance = distance;
        this.imageAdventureUrl = imageAdventureUrl;
        this.imageStarRate = imageStarRate;
        this.alreadyDone = alreadyDone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        description = description;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        distance = distance;
    }

    public String getImageAdventureUrl() {
        return imageAdventureUrl;
    }

    public void setImageAdventureUrl(String imageUrl) {
        this.imageAdventureUrl = imageUrl;
    }

    public String getImageStarRate() {
        return imageStarRate;
    }

    public void setImageStarRate(String imageStarRate) {
        this.imageStarRate = imageStarRate;
    }

    public Boolean getAlreadyDone() {
        return alreadyDone;
    }

    public void setAlreadyDone(Boolean alreadyDone) {
        this.alreadyDone = alreadyDone;
    }
}
