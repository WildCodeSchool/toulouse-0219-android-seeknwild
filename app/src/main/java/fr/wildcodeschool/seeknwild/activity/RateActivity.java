package fr.wildcodeschool.seeknwild.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.List;

import fr.wildcodeschool.seeknwild.R;
import fr.wildcodeschool.seeknwild.model.Adventure;
import fr.wildcodeschool.seeknwild.model.Treasure;
import fr.wildcodeschool.seeknwild.model.User;
import fr.wildcodeschool.seeknwild.model.UserAdventure;

public class RateActivity extends AppCompatActivity {

    private Treasure treasure;
    private UserAdventure userAdventure;
    private Long idUserAdventure;
    private Long idUser;
    private User user;
    private RatingBar ratingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        ratingBar = findViewById(R.id.rbStar);
        ratingBar.setRating(5);

        UserSingleton userSingleton = UserSingleton.getInstance();
        user = userSingleton.getUser();
        idUser = user.getIdUser();

        UserAdventureSingleton userAdventureSingleton = UserAdventureSingleton.getInstance();
        userAdventure = userAdventureSingleton.getUserAdventure();
        idUserAdventure = userAdventureSingleton.getUserAdventureId();
        List<Treasure> treasures = userAdventure.getAdventure().getTreasures();
        treasure = treasures.get(userAdventure.getCurrentTreasure());
        Button btContinued = findViewById(R.id.btContinue);
        final Adventure adventure = userAdventure.getAdventure();
        final Long idAdventure = userAdventure.getAdventure().getIdAdventure();
        btContinued.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: update photo & user
                VolleySingleton.getInstance(getApplicationContext()).updateAdventure(idAdventure, adventure, new VolleySingleton.ResponseListener<Adventure>() {
                    @Override
                    public void finished(Adventure response) {
                        adventure.setRate(ratingBar.getRating());
                        UserAdventureSingleton.getInstance().setUserAdventure(userAdventure);
                        UserSingleton.getInstance().setUser(user);
                        startActivity(new Intent(RateActivity.this, HomeActivity.class));
                    }
                });

            }
        });
    }
}