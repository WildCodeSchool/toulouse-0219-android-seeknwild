package fr.wildcodeschool.seeknwild.model;


import java.util.List;

public class User {

    private Long idUser;

    private String pseudo;

    private String password;

    private String email;

    private List<Adventure> adventures;

    private List<Picture> pictures;

    private UserAdventure userAdventure;

    public User() {
    }

    public User(Long idUser) {
      this.idUser = idUser;
    }
    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Adventure> getAdventures() {
        return adventures;
    }

    public void setAdventures(List<Adventure> adventures) {
        this.adventures = adventures;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    public UserAdventure getUserAdventure() {
        return userAdventure;
    }

    public void setUserAdventure(UserAdventure userAdventure) {
        this.userAdventure = userAdventure;
    }
}
