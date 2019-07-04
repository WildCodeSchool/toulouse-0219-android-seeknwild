package fr.wildcodeschool.seeknwild.model;

public class Authentication {

    private User user;

    private String error;


    public Authentication() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
