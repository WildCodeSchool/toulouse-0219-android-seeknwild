package fr.wildcodeschool.seeknwild.model;

import java.util.List;

public class Adventure {

    private Long idAdventure;

    private String title;

    private String description;

    private Double distance;

    private Double rate;

    private String adventurePicture;

    private User user;

    private List<Treasure> treasures;

    private UserAdventure userAdventure;

    private boolean published;

    public Adventure() {
    }

    public boolean getAlreadyDone() {
        //TODO: vérifier si l'aventure  à déjà été faite
        return true;
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

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
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

    public UserAdventure getUserAdventure() {
        return userAdventure;
    }

    public void setUserAdventure(UserAdventure userAdventure) {
        this.userAdventure = userAdventure;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }
}