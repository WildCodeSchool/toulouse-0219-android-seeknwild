package fr.wildcodeschool.seeknwild.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Treasure {

    private Long idTreasure;

    private String description;

    private Double latTreasure;

    private Double longTreasure;

    private String pictureTreasure;

    private Adventure adventure;

    private List<UserAdventure> userAdventures = new ArrayList<>();

    public Treasure() {
    }


    public Long getIdTreasure() {
        return idTreasure;
    }

    public void setIdTreasure(Long idTreasure) {
        this.idTreasure = idTreasure;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getLatTreasure() {
        return latTreasure;
    }

    public void setLatTreasure(Double latTreasure) {
        this.latTreasure = latTreasure;
    }

    public Double getLongTreasure() {
        return longTreasure;
    }

    public void setLongTreasure(Double longTreasure) {
        this.longTreasure = longTreasure;
    }

    public String getPictureTreasure() {
        return pictureTreasure + "?t=" + System.currentTimeMillis();
    }

    public void setPictureTreasure(String pictureTreasure) {
        this.pictureTreasure = pictureTreasure;
    }

    public Adventure getAdventure() {
        return adventure;
    }

    public void setAdventure(Adventure adventure) {
        this.adventure = adventure;
    }

    public List<UserAdventure> getUserAdventures() {
        return userAdventures;
    }

    public void setUserAdventures(List<UserAdventure> userAdventures) {
        this.userAdventures = userAdventures;
    }
}
