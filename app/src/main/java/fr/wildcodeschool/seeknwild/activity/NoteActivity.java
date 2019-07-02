package fr.wildcodeschool.seeknwild.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import fr.wildcodeschool.seeknwild.R;
import fr.wildcodeschool.seeknwild.model.User;

public class NoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        UserSingleton userSingleton = UserSingleton.getInstance();
        User user = userSingleton.getUser();
        final Long userId = userSingleton.getUserId();

        Button btContinued = findViewById(R.id.btContinue);
        btContinued.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NoteActivity.this,HomeActivity.class));
            }
        });
    }
}
