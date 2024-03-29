package fr.wildcodeschool.seeknwild.activity;

import fr.wildcodeschool.seeknwild.model.UserAdventure;

public class UserAdventureSingleton {

    private static UserAdventureSingleton instance;
    private UserAdventure userAdventure;

    private UserAdventureSingleton() {
    }

    public static UserAdventureSingleton getInstance() {
        if (instance == null) {
            instance = new UserAdventureSingleton();
        }
        return instance;
    }

    public UserAdventure getUserAdventure() {
        return userAdventure;
    }

    public void setUserAdventure(UserAdventure userAdventure) {
        this.userAdventure = userAdventure;
    }

    public Long getUserAdventureId() {
        return userAdventure.getIdUserAdventure();
    }

}