package fr.wildcodeschool.seeknwild.activity;

import android.content.Intent;
import android.support.v4.util.Consumer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fr.wildcodeschool.seeknwild.R;
import fr.wildcodeschool.seeknwild.model.Adventure;
import fr.wildcodeschool.seeknwild.model.UserAdventure;

public class StartAdventureDescription extends AppCompatActivity {

    private Long idAdventure;
    private List<Adventure> listAdventure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_aventure_description);

        final Intent intent = getIntent();
        idAdventure = intent.getLongExtra("idAdventure", 0);

        final TextView title = findViewById(R.id.tvTitleAdv);
        final TextView description = findViewById(R.id.tvDescriptionAdv);
        //ImageView imageAdv = findViewById(R.id.tvTitleAdv);
        final Adventure adventure = new Adventure();
        final UserAdventure userAdventure = new UserAdventure();

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
                //TODO/ récupérer l'id d'aventure et l'id user pour le faire passer dans une création de UserAdventure
            }
        });
    }
}
