package fr.wildcodeschool.seeknwild;

public class AdventureModel {

    private String title;
    private String Description;
    private String Distance;
    private String imageAdventureUrl;
    private String imageStarRate;
    private String alreadyDone;


    public AdventureModel(String title, String description, String distance, String imageAdventureUrl, String imageStarRate, String alreadyDone) {
        this.title = title;
        this.Description = description;
        this.Distance = distance;
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
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDistance() {
        return Distance;
    }

    public void setDistance(String distance) {
        Distance = distance;
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

    public String getAlreadyDone() {
        return alreadyDone;
    }

    public void setAlreadyDone(String alreadyDone) {
        this.alreadyDone = alreadyDone;
    }
}
