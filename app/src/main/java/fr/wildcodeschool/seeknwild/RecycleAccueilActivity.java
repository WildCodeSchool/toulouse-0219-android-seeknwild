package fr.wildcodeschool.seeknwild;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class RecycleAccueilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_accueil);

        RecyclerView rvAccueil = findViewById(R.id.rvAccueil);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvAccueil.setLayoutManager(layoutManager);

        final ArrayList<AdventureModel> adventures = new ArrayList<>();
        AdventureModel adventure =
                new AdventureModel("Into the Wild", "description", "distance",
                        "https://images.app.goo.gl/gr4vEPEr1VhPkQn18", "https://images.app.goo.gl/YfrK6vWMJfHHwwKG7", "");
        adventures.add(adventure);

        final AccueilAdapter adapter = new AccueilAdapter(adventures, RecycleAccueilActivity.this);
        rvAccueil.setAdapter(adapter);
    }
}
