package fr.wildcodeschool.seeknwild;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        RecyclerView rvHome = findViewById(R.id.rvAccueil);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvHome.setLayoutManager(layoutManager);

        final ArrayList<AdventureModel> adventures = new ArrayList<>();
        AdventureModel adventure =
                new AdventureModel("Adventure1", "description", "distance",
                        "https://i.goopics.net/kwb0o.jpg", "https://i.goopics.net/vVg57.jpg", false);
        adventures.add(adventure);

        AdventureModel adventure2 =
                new AdventureModel("Adventure2", "description", "distance",
                        "https://i.goopics.net/KxkY0.jpeg", "https://i.goopics.net/vVg57.jpg", false);
        adventures.add(adventure2);

        AdventureModel adventure3 =
                new AdventureModel("Adventure3", "description", "distance",
                        "https://i.goopics.net/loNZ4.jpg", "https://i.goopics.net/vVg57.jpg", true);
        adventures.add(adventure3);

        AdventureModel adventure4 =
                new AdventureModel("Adventure4", "description", "distance",
                        "https://i.goopics.net/EQK8k.jpeg", "https://i.goopics.net/vVg57.jpg", false);
        adventures.add(adventure4);

        final HomeAdapter adapter = new HomeAdapter(adventures, HomeActivity.this);
        rvHome.setAdapter(adapter);
    }
}
