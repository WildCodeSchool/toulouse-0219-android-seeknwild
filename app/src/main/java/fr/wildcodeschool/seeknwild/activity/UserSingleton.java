package fr.wildcodeschool.seeknwild.activity;

import fr.wildcodeschool.seeknwild.model.User;

public class UserSingleton {

    private static UserSingleton instance;
    private User user;

    private UserSingleton() {
    }

    public static UserSingleton getInstance() {
        if (instance == null) {
            // if no instance exists, create one
            instance = new UserSingleton();
        }
        return instance;
    }

    public User getUser() {
        return user;
    }

    public Long getUserId() {
        return user.getIdUser();
    }

    public void setUser(User user) {
        this.user = user;
    }
}
