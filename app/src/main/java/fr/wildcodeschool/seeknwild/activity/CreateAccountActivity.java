package fr.wildcodeschool.seeknwild.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.Consumer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

import java.nio.charset.Charset;

import fr.wildcodeschool.seeknwild.R;
import fr.wildcodeschool.seeknwild.model.User;

public class CreateAccountActivity extends AppCompatActivity {

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        TextView tvAlreadyUser = findViewById(R.id.tvAlreadyUser);
        tvAlreadyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateAccountActivity.this, SignInActivity.class));
            }
        });
        Button btCreate = findViewById(R.id.btCreate);
        btCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etPseudo = findViewById(R.id.etPseudo);
                EditText etMail = findViewById(R.id.etMail);

                EditText etPw = findViewById(R.id.etPw);
                if (!etPseudo.getText().toString().isEmpty()
                        && !etMail.getText().toString().isEmpty()
                        && !etPw.getText().toString().isEmpty()) {
                    String emaiVerification = etMail.getText().toString();
                    if (emaiVerification.trim().matches(emailPattern)) {
                        if (etPw.getText().toString().length() >= 8) {

                            User newUser = new User();
                            newUser.setPseudo(etPseudo.getText().toString());
                            newUser.setEmail(etMail.getText().toString());
                            String password = etPw.getText().toString();
                            HashCode hashCode = Hashing.sha256().hashString(password, Charset.defaultCharset());
                            newUser.setPassword(hashCode.toString());
                            VolleySingleton.getInstance(getApplicationContext()).createUser(newUser, new Consumer<User>() {
                                @Override
                                public void accept(User user) {
                                    if (user == null) {
                                        //TODO afficher un message d'erreur
                                    } else {
                                        UserSingleton.getInstance().setUser(user);
                                        startActivity(new Intent(CreateAccountActivity.this, HomeActivity.class));
                                    }

                                }
                            });
                        } else {
                            Toast.makeText(CreateAccountActivity.this, "Le mot de passe doit contenir au moins 8 caractéres", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(CreateAccountActivity.this);
                        builder.setTitle(getString(R.string.formatEmailInvalide));
                        builder.setMessage(getString(R.string.adresseEmailPasCorrectementFormatee));
                        builder.setPositiveButton(getString(R.string.ok), null);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }

                } else {
                    Toast.makeText(CreateAccountActivity.this, "Veuillez renseigner l'ensemble des champs", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

