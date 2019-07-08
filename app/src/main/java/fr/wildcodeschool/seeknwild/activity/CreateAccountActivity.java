package fr.wildcodeschool.seeknwild.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.Consumer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

import java.nio.charset.Charset;

import fr.wildcodeschool.seeknwild.R;
import fr.wildcodeschool.seeknwild.model.Authentication;
import fr.wildcodeschool.seeknwild.model.User;

import static fr.wildcodeschool.seeknwild.activity.VolleySingleton.ERROR_EMAIL;

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
                final EditText etPseudo = findViewById(R.id.etPseudo);
                final EditText etMail = findViewById(R.id.etMail);
                final EditText etPw = findViewById(R.id.etPw);

                if (!etPseudo.getText().toString().isEmpty()
                        && !etMail.getText().toString().isEmpty()
                        && !etPw.getText().toString().isEmpty()) {
                    final String emaiVerification = etMail.getText().toString().trim();

                    if (emaiVerification.matches(emailPattern)) {

                        if (etPw.getText().toString().length() >= 8) {
                            HashCode hashCode = Hashing.sha256().hashString(etPw.getText().toString(), Charset.defaultCharset());
                            final User user = new User();
                            user.setPassword(hashCode.toString());
                            user.setEmail(emaiVerification);
                            VolleySingleton.getInstance(getApplicationContext()).getUserByEmail(user, new Consumer<Authentication>() {
                                @Override
                                public void accept(Authentication authentication) {
                                    if (authentication.getError() != null && authentication.getError().equals(ERROR_EMAIL)) {
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
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(CreateAccountActivity.this);
                                                    builder.setTitle(getString(R.string.mot_de_passe));
                                                    builder.setMessage(getString(R.string.votre_mot_de_passe));
                                                    builder.setPositiveButton(getString(R.string.ok), null);
                                                    AlertDialog dialog = builder.create();
                                                    dialog.show();
                                                } else {
                                                    UserSingleton.getInstance().setUser(user);
                                                    startActivity(new Intent(CreateAccountActivity.this, MenuActivity.class));
                                                }
                                            }
                                        });
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(CreateAccountActivity.this);
                                        builder.setTitle(getString(R.string.compte_deja_existant));
                                        builder.setMessage(getString(R.string.voulez_vous_vous_connecter));
                                        builder.setNegativeButton(getString(R.string.non), new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });
                                        builder.setPositiveButton(getString(R.string.oui), new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intentGoSignIn = new Intent(CreateAccountActivity.this, SignInActivity.class);
                                                EditText emailUser = findViewById(R.id.etMail);
                                                intentGoSignIn.putExtra("email", emailUser.getText().toString());
                                                startActivity(intentGoSignIn);
                                            }
                                        });
                                        AlertDialog dialog = builder.create();
                                        dialog.show();
                                    }
                                }
                            });

                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(CreateAccountActivity.this);
                            builder.setTitle(getString(R.string.mot_de_passe));
                            builder.setMessage(getString(R.string.votre_mot_de_passe));
                            builder.setPositiveButton(getString(R.string.ok), null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(CreateAccountActivity.this);
                    builder.setTitle(getString(R.string.veuillez_renseigner_ensemble_des_champs));
                    builder.setPositiveButton(getString(R.string.ok), null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }
}