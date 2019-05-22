package fr.wildcodeschool.seeknwild;

public class AdventureModel {

    private String title;
    private String Description;
    private String Distance;
    private String imageUrl;

    public AdventureModel(String title, String description, String distance, String imageUrl) {
        this.title = title;
        Description = description;
        Distance = distance;
        this.imageUrl = imageUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
