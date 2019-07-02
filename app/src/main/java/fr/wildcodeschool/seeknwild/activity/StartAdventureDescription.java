package fr.wildcodeschool.seeknwild.activity;

import android.content.Intent;
import android.support.v4.util.Consumer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import fr.wildcodeschool.seeknwild.R;
import fr.wildcodeschool.seeknwild.model.Adventure;
import fr.wildcodeschool.seeknwild.model.User;
import fr.wildcodeschool.seeknwild.model.UserAdventure;

public class StartAdventureDescription extends AppCompatActivity {

    private Long idAdventure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_aventure_description);

        UserSingleton userSingleton = UserSingleton.getInstance();
        User user = userSingleton.getUser();
        final Long userId = userSingleton.getUserId();

        final Intent intent = getIntent();
        idAdventure = intent.getLongExtra("idAdventure", 0);

        final TextView title = findViewById(R.id.tvTitleAdv);
        final TextView description = findViewById(R.id.tvDescriptionAdv);
        //ImageView imageAdv = findViewById(R.id.tvTitleAdv);

        VolleySingleton.getInstance(getApplicationContext()).getAdventureById(idAdventure, new Consumer<Adventure>() {
            @Override
            public void accept(Adventure adventure) {
                title.setText(adventure.getTitle());
                description.setText(adventure.getDescription());
            }
        });

        Button startAdv = findViewById(R.id.btStartAdventure);
        startAdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VolleySingleton.getInstance(getApplicationContext()).createUserAdventure( userId, idAdventure,
                        new Consumer<UserAdventure>() {
                            @Override
                            public void accept(UserAdventure userAdventure) {
                                UserAdventureSingleton.getInstance().setUserAdventure(userAdventure);
                                Intent intent = new Intent(StartAdventureDescription.this, SearchTreasureActivity.class);
                                startActivity(intent);
                            }
                        });
            }
        });
    }
}
