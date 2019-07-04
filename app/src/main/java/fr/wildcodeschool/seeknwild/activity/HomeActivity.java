package fr.wildcodeschool.seeknwild.activity;

import android.os.Bundle;
import android.support.v4.util.Consumer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fr.wildcodeschool.seeknwild.R;
import fr.wildcodeschool.seeknwild.adapter.HomeAdapter;
import fr.wildcodeschool.seeknwild.model.Adventure;
import fr.wildcodeschool.seeknwild.model.User;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //TODO: Photo du trésor associé.

        UserSingleton userSingleton = UserSingleton.getInstance();
        User user = userSingleton.getUser();

        final RecyclerView rvHome = findViewById(R.id.rvAccueil);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvHome.setLayoutManager(layoutManager);

        final ArrayList<Adventure> adventures = new ArrayList<>();
        VolleySingleton.getInstance(getApplicationContext()).getAdventures(new Consumer<List<Adventure>>() {
            @Override
            public void accept(List<Adventure> adventures) {
                final HomeAdapter adapter = new HomeAdapter(adventures, HomeActivity.this);
                rvHome.setAdapter(adapter);
            }
        });
    }
}
