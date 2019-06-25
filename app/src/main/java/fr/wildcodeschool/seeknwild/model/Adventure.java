package fr.wildcodeschool.seeknwild.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Adventure {

    @SerializedName("idAdventure")
    private Long idAdventure;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("distance")
    private Double distance;
    @SerializedName("rate")
    private Double rate;
    @SerializedName("adventurePicture")
    private String adventurePicture;
    @SerializedName("user")
    private User user;
    @SerializedName("treasures")
    private List<Treasure> treasures;
    @SerializedName("userAdventure")
    private UserAdventure userAdventure;

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
}