package fr.wildcodeschool.seeknwild.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Treasure {

    @SerializedName("idTreasure")
    private Long idTreasure;

    @SerializedName("description")
    private String description;

    @SerializedName("latTreasure")
    private Double latTreasure;

    @SerializedName("longTreasure")
    private Double longTreasure;

    @SerializedName("pictureTreasure")
    private String pictureTreasure;

    @SerializedName("adventure")
    private Adventure adventure;

    @SerializedName("userAdventures")
    private List<UserAdventure> userAdventures = new ArrayList<>();

    public Treasure() {
    }

}
