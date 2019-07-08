package fr.wildcodeschool.seeknwild.model;

import java.util.ArrayList;
import java.util.List;

public class Adventure {

    private Long idAdventure;

    private String title;

    private String description;

    private Double distance;

    private Float rate;

    private String adventurePicture;

    private User user;

    private List<Treasure> treasures = new ArrayList<>();

    private boolean published;

    public Adventure() {
    }

    public Long getIdAdventure() {
        return idAdventure;
    }

    public void setIdAdventure(Long idAdventure) {
        this.idAdventure = idAdventure;
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
        this.description = description;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getAdventurePicture() {
        return adventurePicture;
    }

    public void setAdventurePicture(String adventurePicture) {
        this.adventurePicture = adventurePicture;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Treasure> getTreasures() {
        return treasures;
    }

    public void setTreasures(List<Treasure> treasures) {
        this.treasures = treasures;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }
}