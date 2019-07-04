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

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

import java.nio.charset.Charset;

import fr.wildcodeschool.seeknwild.R;
import fr.wildcodeschool.seeknwild.model.Authentication;
import fr.wildcodeschool.seeknwild.model.User;

import static fr.wildcodeschool.seeknwild.activity.VolleySingleton.ERROR_EMAIL;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Intent intent = getIntent();
        String emailUser = intent.getStringExtra("email");
        final EditText etMail = findViewById(R.id.etMailSign);
        etMail.setText(emailUser);
        TextView tvCreateAccount = findViewById(R.id.tvHaveAccount);
        tvCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, CreateAccountActivity.class));
            }
        });

        Button btConnect = findViewById(R.id.btSignIn);
        btConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etMail = findViewById(R.id.etMailSign);
                EditText etPw = findViewById(R.id.etPwSign);

                if (!etMail.getText().toString().isEmpty() && !etPw.getText().toString().isEmpty()) {
                    HashCode hashCode = Hashing.sha256().hashString(etPw.getText().toString(), Charset.defaultCharset());
                    final User newUser = new User();
                    newUser.setEmail(etMail.getText().toString());
                    newUser.setPassword(etPw.getText().toString());
                    newUser.setPassword(hashCode.toString());
                    VolleySingleton.getInstance(getApplicationContext()).getUserByEmail(newUser, new Consumer<Authentication>() {
                        @Override
                        public void accept(Authentication authentication) {
                            if (authentication == null) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
                                builder.setTitle(getString(R.string.vérifier_connexion));
                                builder.setMessage(getString(R.string.veuillez_verifier_connexion));
                                builder.setPositiveButton(getString(R.string.ok), null);
                                AlertDialog dialog = builder.create();
                                dialog.show();

                            } else if (authentication.getError() != null) {
                                String error = getString(R.string.error_password);
                                if (authentication.getError().equals(ERROR_EMAIL)) {
                                    error = getString(R.string.error_email);
                                }
                                AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
                                builder.setTitle(getString(R.string.erreurEmail));
                                builder.setMessage(error);
                                builder.setPositiveButton(getString(R.string.ok), null);
                                AlertDialog dialog = builder.create();
                                dialog.show();

                            } else if (authentication.getUser() == null) {
                                //TODO afficher une erreur

                            } else {
                                UserSingleton.getInstance().setUser(authentication.getUser());
                                startActivity(new Intent(SignInActivity.this, MainActivity.class));
                            }
                        }
                    });
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
                    builder.setTitle(getString(R.string.votreEmail));
                    builder.setMessage(getString(R.string.veuillezRenseigner));
                    builder.setPositiveButton(getString(R.string.ok), null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }
}