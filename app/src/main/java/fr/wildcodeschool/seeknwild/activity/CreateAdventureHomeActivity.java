package fr.wildcodeschool.seeknwild.activity;

import android.os.Bundle;
import android.support.v4.util.Consumer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fr.wildcodeschool.seeknwild.R;
import fr.wildcodeschool.seeknwild.adapter.CreateHomeAdapter;
import fr.wildcodeschool.seeknwild.model.Adventure;

public class CreateAdventureHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_adventure_home);

        final RecyclerView rvHomeCreate = findViewById(R.id.rvAdventureHomeCreate);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvHomeCreate.setLayoutManager(layoutManager);

        final ArrayList<Adventure> adventures = new ArrayList<>();
        VolleySingleton.getInstance(getApplicationContext()).getAdventures(new Consumer<List<Adventure>>() {
            @Override
            public void accept(List<Adventure> adventures) {
                final CreateHomeAdapter adapter = new CreateHomeAdapter(adventures, CreateAdventureHomeActivity.this);
                rvHomeCreate.setAdapter(adapter);
            }
        });
    }
}