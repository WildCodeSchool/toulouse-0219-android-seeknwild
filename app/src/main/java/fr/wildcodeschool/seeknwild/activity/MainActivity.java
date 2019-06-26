package fr.wildcodeschool.seeknwild.activity;

import android.content.Intent;
import android.support.v4.util.Consumer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.wildcodeschool.seeknwild.R;
import fr.wildcodeschool.seeknwild.model.Picture;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btAddStudent = findViewById(R.id.accueil);
        btAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
            }
        });

        Button btmenu = findViewById(R.id.gotoMenu);
        btmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MenuActivity.class));
            }
        });

        Button bttreasure = findViewById(R.id.adventure);
        bttreasure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CreateAdventureActivity.class));
            }
        });

        Button btStartAdventure = findViewById(R.id.btStartAdventure);
        btStartAdventure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, StartAdventureActivity.class));
            }
        });

        Button btgoHomeCreateAdventure = findViewById(R.id.btGoHomeCreate);
        btgoHomeCreateAdventure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CreateAdventureHomeActivity.class));
            }
        });

    }
}
