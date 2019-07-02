package fr.wildcodeschool.seeknwild.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

import fr.wildcodeschool.seeknwild.R;
import fr.wildcodeschool.seeknwild.model.Treasure;
import fr.wildcodeschool.seeknwild.model.User;
import fr.wildcodeschool.seeknwild.model.UserAdventure;

public class CannotRateActivity extends AppCompatActivity {

    private Treasure treasure;
    private UserAdventure userAdventure;
    private Long idUserAdventure;
    private Long iduser;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cannot_rate);

        UserSingleton userSingleton = UserSingleton.getInstance();
        user = userSingleton.getUser();
        iduser = userSingleton.getUserId();

        UserAdventureSingleton userAdventureSingleton = UserAdventureSingleton.getInstance();
        userAdventure = userAdventureSingleton.getUserAdventure();
        idUserAdventure = userAdventureSingleton.getUserAdventureId();
        List<Treasure> treasures = userAdventure.getAdventure().getTreasures();
        treasure = treasures.get(userAdventure.getCurrentTreasure());

        Button btContinued = findViewById(R.id.btContinue);
        btContinued.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserAdventureSingleton.getInstance().setUserAdventure(userAdventure);
                UserSingleton.getInstance().setUser(user);
                startActivity(new Intent(CannotRateActivity.this, HomeActivity.class));
            }
        });
    }
}
