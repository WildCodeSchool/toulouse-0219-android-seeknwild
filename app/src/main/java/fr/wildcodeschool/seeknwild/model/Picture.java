package fr.wildcodeschool.seeknwild.model;

import com.google.gson.annotations.SerializedName;

public class Picture {

    private Long idPicture;

    private String urlPicture;

    private User user;

    public Picture() {}

    public Long getIdPicture() {
        return idPicture;
    }

    public void setIdPicture(Long idPicture) {
        this.idPicture = idPicture;
    }

    public String getUrlPicture() {
        return urlPicture;
    }

    public void setUrlPicture(String urlPicture) {
        this.urlPicture = urlPicture;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
