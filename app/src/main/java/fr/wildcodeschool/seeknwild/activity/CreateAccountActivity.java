package fr.wildcodeschool.seeknwild.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.Consumer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import fr.wildcodeschool.seeknwild.R;
import fr.wildcodeschool.seeknwild.model.User;

public class CreateAccountActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        Button btCreate = findViewById(R.id.btCreate);
        btCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etPseudo = findViewById(R.id.etPseudo);
                EditText etMail = findViewById(R.id.etMail);
                EditText etPw = findViewById(R.id.etPw);
                User newUser = new User();
                newUser.setPseudo(etPseudo.getText().toString());
                newUser.setEmail(etMail.getText().toString());
                newUser.setPassword(etPw.getText().toString());
                if (!etPseudo.getText().toString().isEmpty()
                        && !etMail.getText().toString().isEmpty()
                        && !etPw.getText().toString().isEmpty()) {
                    VolleySingleton.getInstance(getApplicationContext()).createUser(newUser, new Consumer<User>() {
                        @Override
                        public void accept(User user) {
                            Intent intent = new Intent(CreateAccountActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        });
    }
}
