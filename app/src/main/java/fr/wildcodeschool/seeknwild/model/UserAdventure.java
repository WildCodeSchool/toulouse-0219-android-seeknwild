package fr.wildcodeschool.seeknwild.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class UserAdventure {

    private Long idUserAdventure;

    private boolean alreadyDone;

    private User user;

    private Adventure adventure;

    private List<Treasure> treasures = new ArrayList<>();

    public UserAdventure() {
    }

    public Long getIdUserAdventure() {
        return idUserAdventure;
    }

    public void setIdUserAdventure(Long idUserAdventure) {
        this.idUserAdventure = idUserAdventure;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Adventure getAdventure() {
        return adventure;
    }

    public void setAdventure(Adventure adventure) {
        this.adventure = adventure;
    }

    public List<Treasure> getTreasures() {
        return treasures;
    }

    public void setTreasures(List<Treasure> treasures) {
        this.treasures = treasures;
    }

    public boolean isAlreadyDone() {
        return alreadyDone;
    }

    public void setAlreadyDone(boolean alreadyDone) {
        this.alreadyDone = alreadyDone;
    }
}
