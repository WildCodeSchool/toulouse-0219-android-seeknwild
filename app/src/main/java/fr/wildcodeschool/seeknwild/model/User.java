package fr.wildcodeschool.seeknwild.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {

    @SerializedName("idUser")
    private Long idUser;

    @SerializedName("pseudo")
    private String pseudo;

    @SerializedName("password")
    private String password;

    @SerializedName("email")
    private String email;

    @SerializedName("adventures")
    private List<Adventure> adventures;

    @SerializedName("pictures")
    private List<Picture> pictures;

    @SerializedName("userAdventure")
    private UserAdventure userAdventure;

    public User() {
    }

}
