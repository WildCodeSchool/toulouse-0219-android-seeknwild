package fr.wildcodeschool.seeknwild.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.Consumer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.toolbox.Volley;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

import java.nio.charset.Charset;
import java.util.List;

import fr.wildcodeschool.seeknwild.R;
import fr.wildcodeschool.seeknwild.model.User;


public class SignInActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Button btConnect = findViewById(R.id.btSignIn);
        btConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                VolleySingleton.getInstance(getApplicationContext()).getUsers(new Consumer<List<User>>() {
                    @Override
                    public void accept(List<User> users) {
                        EditText email = findViewById(R.id.etMailSign);
                        EditText pwd = findViewById(R.id.etPwSign);

                        HashCode hashCode = Hashing.sha256().hashString(pwd.getText().toString(), Charset.defaultCharset());
                        for (int i = 0 ; i < users.size() ; i++) {
                            String emailUser = users.get(i).getEmail();
                            if (emailUser.equals(email.getText().toString())) {
                                String password = users.get(i).getPassword();
                                if (password.equals(hashCode.toString())) {
                                    startActivity(new Intent(SignInActivity.this, HomeActivity.class));
                                }
                            }
                        }

                    }
                });
            }
        });

    }
}
