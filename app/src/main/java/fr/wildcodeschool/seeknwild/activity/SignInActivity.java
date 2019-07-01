package fr.wildcodeschool.seeknwild.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.Consumer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                EditText etMail = findViewById(R.id.etMailSign);
                EditText etPw = findViewById(R.id.etPwSign);
                HashCode hashCode = Hashing.sha256().hashString(etPw.getText().toString(), Charset.defaultCharset());
                final User newUser = new User();
                newUser.setEmail(etMail.getText().toString());
                newUser.setPassword(etPw.getText().toString());
                newUser.setPassword(hashCode.toString());
                VolleySingleton.getInstance(getApplicationContext()).getUserByEmail(newUser, new Consumer<User>() {
                    @Override
                    public void accept(User user) {
                        Toast.makeText(SignInActivity.this, "compte connecté", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }

}
